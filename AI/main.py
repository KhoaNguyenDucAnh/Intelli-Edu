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


cookie = "__stripe_mid=4c6e5540-a660-4d32-a6f2-62b9fcded373da5ad7; intercom-device-id-lupk8zyo=b7543c5c-5b57-4019-b325-3287684b15e7; __ssid=a2165455b3b64be1cd688fb4502b2fe; activitySessionId=4d23e059-7ad5-472d-983a-22ab70c7310d; __cf_bm=pKPBScjF.NBk6lkh5tiS2mnriD9P4hgkIbIyYPeddaI-1702017695-0-AYmbVx8TtMGCaqzmePIE924FDvW8UssZOyKkkmFsHbFDkg4LXd7OF+qOVd8yQUilfILT4Hljro9SuaE09pPdcT8=; __stripe_sid=73066245-b1cf-4cf6-be2c-18dca98184803b8f43; cf_clearance=r31TKrqxdBGEizu.6simkILsMQ6xk5XMPk8fsaCbr3I-1702018029-0-1-deb7680b.a30a0948.89b637eb-0.2.1702018029; sessionKey=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"

claude_api = Custom_Client(cookie)

def to_mindmap(file: str):
    conversation_id = claude_api.create_new_chat()["uuid"]
    mindmap = claude_api.send_message(
        """
        Human: We want a comprehensive mind map in vietnamese for this text in a valid json structure by this type:

        {"title":title,
            "root":
            {
                    "label":label,
                    "children":[
                        {"label":label,
                        "children":[]}
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
    print(mindmap)
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
    claude_api.delete_conversation(conversation_id)
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
    values = [[None for i in range(100)], [None for i in range(500)]]
    adj = [[[] for i in range(500)], [[] for i in range(500)]]
    nBase = np.zeros(2)
    pos = np.zeros((2, 500))
    parent = np.zeros((2, 500))
    nChild = np.zeros((2, 500))
    rev = np.zeros((2, 500))
    flat = [[None for i in range(100)], [None for i in range(100)]]
    count = deque([0])
    for i in range(500):
        count.append(i + 1)

    print(1)
    json1 = to_mindmap(document)["root"]
    print(json1)
    print(2)
    json2 = json.loads(mindmap)["root"]
    print(json2)
    print(3)
    def compare(text):
        new_chat = claude_api.create_new_chat()
        conversation_id = new_chat["uuid"]
        prompt = text + "\n"
        # print(prompt)
        prompt += """
        Human: The two texts is formatted in the strcuture:

            [TEXT 1] [TEXT 2]
            [TEXT 1] [TEXT 2]

        Can you see if the second text is missing any information or numbers or wrong order compare to the first text in less than 7 sentences in Vietnamese.If the second text misses too much information compare to the first then say 'Qua it thong tin'

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
            # print("sai vi tri dang le", flat[1][findHead], "va", flat[1][findEnd], "o cung 1 chuoi",)
            continue
        text += str(comp_text) + " " + str(user_text) + "\n"
    print(text)
    return str(compare(text))
# print(main("Chiến tranh thế giới thứ hai (còn được nhắc đến với các tên gọi Đệ nhị thế chiến, Thế chiến II hay Đại chiến thế giới lần thứ hai) là một cuộc chiến tranh thế giới bắt đầu từ khoảng năm 1939 và chấm dứt vào năm 1945. Cuộc chiến có sự tham gia của đại đa số các quốc gia trên thế giới — bao gồm tất cả các cường quốc — tạo thành hai liên minh quân sự đối lập: Đồng Minh và Phe Trục. Trong diện mạo một cuộc chiến tranh toàn diện, Thế chiến II có sự tham gia trực tiếp của hơn 100 triệu nhân sự từ hơn 30 quốc gia. Các bên tham chiến chính đã dồn toàn bộ nguồn lực kinh tế, công nghiệp và khoa học cho nỗ lực tham chiến, làm mờ đi ranh giới giữa nguồn lực dân sự và quân sự. Chiến tranh thế giới thứ hai là cuộc xung đột đẫm máu nhất trong lịch sử nhân loại, gây nên cái chết của 70 đến 85 triệu người, với số lượng thường dân tử vong nhiều hơn quân nhân. Hàng chục triệu người đã phải bỏ mạng trong các vụ thảm sát, diệt chủng (trong đó có Holocaust), chết vì thiếu lương thực hay vì bệnh tật. Máy bay đóng vai trò quan trọng đối với tiến trình cuộc chiến, bao gồm ném bom chiến lược vào các trung tâm dân cư, và đối với sự phát triển vũ khí hạt nhân cũng như hai lần duy nhất sử dụng loại vũ khí này trong chiến tranh.",{"title": "Chiến tranh thế giới thứ hai", "root": {"label": "Chiến tranh thế giới thứ hai","children": [{"label": "Thời gian","children": [ {"label": "Bắt đầu: Khoảng năm 1939"}, {"label": "Kết thúc: Năm 1945"} ] }, { "label": "Tên gọi khác", "children": [ {"label": "Đệ nhị thế chiến"}, {"label": "Thế chiến II"}, {"label": "Đại chiến thế giới lần thứ hai"} ] }, { "label": "Các bên tham chiến", "children": [ {"label": "Đồng Minh"}, {"label": "Phe Trục"} ] }, { "label": "Quy mô tham chiến", "children": [ {"label": "Hơn 100 triệu nhân sự"}, {"label": "Hơn 30 quốc gia"} ] }, { "label": "Sử dụng nguồn lực", "children": [ {"label": "Kinh tế"}, {"label": "Công nghiệp"}, {"label": "Khoa học"} ] }, { "label": "Quy mô thiệt hại", "children": [ {"label": "70-85 triệu người"}, {"label": "Dân thường > quân nhân"} ] }, { "label": "Nguyên nhân tử vong", "children": [ {"label": "Chiến đấu"}, {"label": "Thảm sát/Diệt chủng", "children": [{"label": "Holocaust"}]}, {"label": "Đói"}, {"label": "Bệnh dịch"} ] }, { "label": "Vai trò máy bay", "children": [ {"label": "Ném bom chiến lược", "children": [{"label": "Trung tâm dân cư"}]}, {"label": "Phát triển vũ khí hạt nhân"} ] }, { "label": "Vũ khí hạt nhân", "children": [ {"label": "Đầu tiên sử dụng trong chiến tranh"} ] } ] }}))
