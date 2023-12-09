import numpy as np
import json
from sentence_transformers import SentenceTransformer, util
from collections import deque
from curl_cffi import requests
import re
from claude_api import Client

model = SentenceTransformer("keepitreal/vietnamese-sbert")

class Custom_Client(Client):
    def upload_attachment(self, content):
        return {
            "file_name": "document",
            "file_type": "text/plain",
            "file_size": len(content.encode("utf-8")),
            "extracted_content": content,
        }

    # Send Message to Claude
    def send_message(self, prompt, conversation_id, attachment=None, timeout=500):
        url = "https://claude.ai/api/append_message"

        # Upload attachment if provided
        attachments = []
        if attachment:
            attachment_response = self.upload_attachment(attachment)
            if attachment_response:
                attachments = [attachment_response]
            else:
                return {"Error: Invalid file format. Please try again."}

        # Ensure attachments is an empty list when no attachment is provided
        if not attachment:
            attachments = []

        payload = json.dumps(
            {
                "completion": {
                    "prompt": f"{prompt}",
                    "timezone": "Asia/Kolkata",
                    "model": "claude-2.1",
                },
                "organization_uuid": f"{self.organization_id}",
                "conversation_uuid": f"{conversation_id}",
                "text": f"{prompt}",
                "attachments": attachments,
            }
        )

        headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0",
            "Accept": "text/event-stream, text/event-stream",
            "Accept-Language": "en-US,en;q=0.5",
            "Referer": "https://claude.ai/chats",
            "Content-Type": "application/json",
            "Origin": "https://claude.ai",
            "DNT": "1",
            "Connection": "keep-alive",
            "Cookie": f"{self.cookie}",
            "Sec-Fetch-Dest": "empty",
            "Sec-Fetch-Mode": "cors",
            "Sec-Fetch-Site": "same-origin",
            "TE": "trailers",
        }

        response = requests.post(
            url, headers=headers, data=payload, impersonate="chrome110", timeout=500
        )
        decoded_data = response.content.decode("utf-8")
        decoded_data = re.sub("\n+", "\n", decoded_data).strip()
        data_strings = decoded_data.split("\n")
        completions = []
        for data_string in data_strings:
            json_str = data_string[6:].strip()
            data = json.loads(json_str)
            if "completion" in data:
                completions.append(data["completion"])

        answer = "".join(completions)

        # Returns answer
        return answer


cookie = "__ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; activitySessionId=2a6d3a78-1cef-4593-b1fa-1e7a550b838d; __cf_bm=BTOQOwKzGVeFvzlYusPwDLYvzsapVg3eM8MXPQhNsj0-1702097559-0-AbiEaSzGh5Z+y1zN3PNxcWq34opIFNlRvPx9Tp5J6WictMPo1L0mgytlIy8F5TYqtq0NxysVp8mLvYVk12DKstI=; cf_clearance=J5SqPqX3F3h4yATZXfyqC_tdd4fXEdBPxzbhMp0f40Q-1702097560-0-1-af0c7280.8eb62f98.f80d2d60-0.2.1702097560; __stripe_sid=71259778-4788-4ef0-b18b-4bbc37a1bc3cee5c0f; sessionKey=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"

claude_api = Custom_Client(cookie)

def to_mindmap(file: str):
    conversation_id = claude_api.create_new_chat()["uuid"]
    mindmap = claude_api.send_message(
        """
        Human: We want a comprehensive mind map in vietnamese for this text in a valid json structure by this type:

        {
            "title":title,
            "root": {
                "label":label,
                "children": [
                    {
                        "label":label,
                        "children":[]
                    }
                ]
            }
        }

        The keys of the json should strictly follow the given structure. Give additional details and statistics if needed.

        Say 'Start' before the json and 'End' after the json in your answer. No lines between the json elements. No lines between Start and json. No lines between the json and End. Check the validity of the json in the numbers of {, }, [, ] and the position of commas.

        Assistant:
        """,
        conversation_id,
        attachment=file,
    )
    # claude_api.delete_conversation(conversation_id)
    mindmap = str(mindmap)
    begin = 5
    end = -3
    for i in range(5, len(mindmap) + 1):
        if mindmap[i - 5:i] == "Start":
            begin = i
            break
    for i in range(len(mindmap) - 3, -1, -1):
        if mindmap[i:i + 3] == "End":
            end = i
            break
    mindmap = mindmap[begin:end]
    return json.loads(mindmap)

def create_questions(file):
    conversation_id = claude_api.create_new_chat()['uuid']
    questions = claude_api.send_message(
        """
        Human:We want to create 5 multiple choices questions in Vietnamese for this text. Each questions should be in json format, and there should be commas in betwen the questions. For each question, include 1 correct answer and 3 incorrect answers with the following structure: {"question": question goes here, "answers": [correct answer, incorrect answer 1, incorrect answer 2, incorrect answer 3]}.
        
        Say 'Start' before the json and 'End' after the json in your answer. No lines between the json elements. No lines between Start and json. No lines between the json and End.  

        Assistant:
        """,
        conversation_id,
        attachment=file
    )
    # claude_api.delete_conversation(conversation_id)
    questions = str(questions)
    begin = 5
    end = -3
    for i in range(5, len(questions) + 1):
        if questions[i - 5:i] == "Start":
            begin = i
            break
    for i in range(len(questions) - 3, -1, -1):
        if questions[i:i + 3] == "End":
            end = i
            break
    questions = questions[begin:end]
    return "[" + questions + "]"

def main(document, mindmap):
    nChain = np.zeros(2)
    chainHead = np.zeros((2, 500))
    chainEnd = np.zeros((2, 500))
    chainInd = np.zeros((2, 500))
    values = [[None for _ in range(100)], [None for _ in range(500)]]
    adj = [[[] for _ in range(500)], [[] for _ in range(500)]]
    nBase = np.zeros(2)
    pos = np.zeros((2, 500))
    parent = np.zeros((2, 500))
    nChild = np.zeros((2, 500))
    rev = np.zeros((2, 500))
    flat = [[None for _ in range(100)], [None for _ in range(100)]]
    count = deque([0])
    for i in range(500):
        count.append(i + 1)

    json1 = to_mindmap(document)["root"]

    print(mindmap)

    json2 = json.loads(mindmap)["root"]

    print(json2)

    def compare(text):
        new_chat = claude_api.create_new_chat()
        conversation_id = new_chat["uuid"]
        prompt = text + "\n"
        prompt += """

        Human: A list of texts written in pair has the following structure:

            [TEXT 1] [TEXT 2],
            [TEXT 1] [TEXT 2],
            ....

        Take the text 1 in each pair of texts as the standard and correct text. We want a compairison between text 2 and text 1 for each pair of texts. Refer to text 2 as the user's mindmap and text 1 as the document's information. 
        If there is not enough information from text 2, please say 'không đủ thông tin để đưa ra nhận xét'.
        Everything should be written in Vietnamese.

        Assistant:
        """
        response = claude_api.send_message(prompt, conversation_id)
        claude_api.delete_conversation(conversation_id)
        return response

    def to_tree(index, js, u):
        values[index][u] = js["label"]
        count.popleft()
        if ("children" not in js) or (js["children"]==[]):
            return 1
        js = js["children"]
        dem = 0
        for i in range(len(js)):
            v = count[0]
            adj[index][u].append(v)
            adj[index][v].append(u)
            parent[index][v] = u
            dem += to_tree(index, js[i], v)
        return dem + 1

    def dfs(index, u):
        nChild[index][u] = 1
        for v in adj[index][u]:
            if v != parent[index][u]:
                dfs(index, v)
                nChild[index][u] += nChild[index][v]

    def hld(index, u):
        if int(chainHead[index][int(nChain[index])]) == 0:
            chainHead[index][int(nChain[index])] = u
        chainInd[index][u] = nChain[index]
        nBase[index] += 1
        pos[index][u] = nBase[index]
        rev[index][int(pos[index][u])] = u
        spec = -1
        for i in range(len(adj[index][u])):
            v = adj[index][u][i]
            if v != parent[index][u]:
                if spec == -1 or nChild[index][v] > nChild[index][spec]:
                    spec = v
        if spec > -1:
            hld(index, spec)
        if spec == -1:
            chainEnd[index][int(nChain[index])] = u
        for v in adj[index][u]:
            if v != parent[index][u] and v != spec:
                nChain[index] += 1
                hld(index, v)

    def flatten(index, n):
        for i in range(n):
            flat[index][int(pos[index][i])] = values[index][i]

    def find(text, n):
        corpus_em = model.encode(flat[1][1:n])
        text = model.encode(text)
        hits = util.semantic_search(text, corpus_em, score_function=util.dot_score)
        position = hits[0][0]["corpus_id"]
        return position

    d1 = to_tree(0, json1, 1)
    count = deque([0])
    for i in range(500):
        count.append(i + 1)
    d2 = to_tree(1, json2, 1)
    if(d2==1):
        return 'Mindmap quá nhỏ để có thể so sánh'
    dfs(0, 1)
    dfs(1, 1)
    hld(0, 1)
    hld(1, 1)
    flatten(0, d1)
    flatten(1, d2)
    text = ""
    for i in range(int(nChain[0])):
        findHead = int(find(values[0][int(chainHead[0][i])], d2)) + 1
        findEnd = int(find(values[0][int(chainEnd[0][i])], d2)) + 1
        comp_text = flat[0][int(pos[0][int(chainHead[0][i])]) : (int(pos[0][int(chainEnd[0][i])]) + 1)]
        Head_index = int(rev[1][findHead])
        End_index = int(rev[1][findEnd])
        user_text = []
        if Head_index > End_index:
            Head_index, End_index = End_index, Head_index
        if End_index == Head_index:
            user_text = flat[1][int(pos[1][int(Head_index)]) : int(pos[1][int(End_index)]) + 1]
            text += str(comp_text) + " " + str(user_text) + "\n"
            continue
        loca = 0
        # print(Head_index, " ", End_index)
        while End_index != Head_index:
            user_chain = int(chainInd[1][int(End_index)])
            if chainInd[1][int(End_index)] == chainInd[1][int(Head_index)]:
                user_text = (flat[1][int(pos[1][int(Head_index)]) : int(pos[1][int(End_index)]) + 1] + user_text)
                break
            user_text = (flat[1][int(pos[1][int(chainHead[1][int(user_chain)])]) : int(pos[1][int(End_index)]) + 1] + user_text)
            End_index = int(parent[1][int(chainHead[1][user_chain])])
            if End_index == 1:
                loca = -1
                break
            user_chain = int(chainInd[1][int(End_index)])
        if loca == -1:
            print("sai vị trí đáng lẽ", flat[1][findHead], "và", flat[1][findEnd], "phải ở cùng 1 chuỗi",)
            continue
        text += str(comp_text) + " " + str(user_text) + "\n"
    return str(compare(text))
# print(main('Chiến tranh thế giới thứ hai (còn được nhắc đến với các tên gọi Đệ nhị thế chiến, Thế chiến II hay Đại chiến thế giới lần thứ hai) là một cuộc chiến tranh thế giới bắt đầu từ khoảng năm 1939 và chấm dứt vào năm 1945. Cuộc chiến có sự tham gia của đại đa số các quốc gia trên thế giới — bao gồm tất cả các cường quốc — tạo thành hai liên minh quân sự đối lập: Đồng Minh và Phe Trục. Trong diện mạo một cuộc chiến tranh toàn diện, Thế chiến II có sự tham gia trực tiếp của hơn 100 triệu nhân sự từ hơn 30 quốc gia. Các bên tham chiến chính đã dồn toàn bộ nguồn lực kinh tế, công nghiệp và khoa học cho nỗ lực tham chiến, làm mờ đi ranh giới giữa nguồn lực dân sự và quân sự. Chiến tranh thế giới thứ hai là cuộc xung đột đẫm máu nhất trong lịch sử nhân loại, gây nên cái chết của 70 đến 85 triệu người, với số lượng thường dân tử vong nhiều hơn quân nhân. Hàng chục triệu người đã phải bỏ mạng trong các vụ thảm sát, diệt chủng (trong đó có Holocaust), chết vì thiếu lương thực hay vì bệnh tật. Máy bay đóng vai trò quan trọng đối với tiến trình cuộc chiến, bao gồm ném bom chiến lược vào các trung tâm dân cư, và đối với sự phát triển vũ khí hạt nhân cũng như hai lần duy nhất sử dụng loại vũ khí này trong chiến tranh.',{"title": "Chiến tranh thế giới thứ hai", "root": {"label": "Chiến tranh thế giới thứ hai","children": [{"label": "Thời gian","children": [ {"label": "Bắt đầu: Khoảng năm 1939"}, {"label": "Kết thúc: Năm 1945"} ] }, { "label": "Tên gọi khác", "children": [ {"label": "Đệ nhị thế chiến"}, {"label": "Thế chiến II"}, {"label": "Đại chiến thế giới lần thứ hai"} ] }, { "label": "Các bên tham chiến", "children": [ {"label": "Đồng Minh"}, {"label": "Phe Trục"} ] }, { "label": "Quy mô tham chiến", "children": [ {"label": "Hơn 100 triệu nhân sự"}, {"label": "Hơn 30 quốc gia"} ] }, { "label": "Sử dụng nguồn lực", "children": [ {"label": "Kinh tế"}, {"label": "Công nghiệp"}, {"label": "Khoa học"} ] }, { "label": "Quy mô thiệt hại", "children": [ {"label": "70-85 triệu người"}, {"label": "Dân thường > quân nhân"} ] }, { "label": "Nguyên nhân tử vong", "children": [ {"label": "Chiến đấu"}, {"label": "Thảm sát/Diệt chủng", "children": [{"label": "Holocaust"}]}, {"label": "Đói"}, {"label": "Bệnh dịch"} ] }, { "label": "Vai trò máy bay", "children": [ {"label": "Ném bom chiến lược", "children": [{"label": "Trung tâm dân cư"}]}, {"label": "Phát triển vũ khí hạt nhân"} ] }, { "label": "Vũ khí hạt nhân", "children": [ {"label": "Đầu tiên sử dụng trong chiến tranh"} ] } ] }}))
