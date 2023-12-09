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


cookie = "__ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; activitySessionId=2a6d3a78-1cef-4593-b1fa-1e7a550b838d; sessionKey=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; cf_clearance=gP6BJEYOrKnUoMWMW3U1eJ66mwczitBCSKcSUFrtsQY-1702130552-0-1-af0c7280.1274d7ef.f80d2d60-0.2.1702130552; __stripe_sid=daec514e-c086-40a8-9ca2-2453eef97e75fd90f4; __cf_bm=xcx8xsKQqUjwbdwhazJpnN.6QmCNzLG3nkmef26.b08-1702130571-1-Aa/3lQtTnVIplnNKkf64Vx+Uu2kduWq01Kxz7TCcyLJoSCiorcoHt/qOmRzIpELc1qzJ3ciNF/GnRX23aJfOh44="

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
    # print('here is mindmap')
    # print(mindmap)
    # print()
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

    #print(json1)

    json2 = json.loads(mindmap)["root"]

    #print(json2)

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
            #print("sai vị trí đáng lẽ", flat[1][findHead], "và", flat[1][findEnd], "phải ở cùng 1 chuỗi",)
            continue
        text += str(comp_text) + " " + str(user_text) + "\n"
    return str(compare(text))
# print(main("""## Dựa vào nguồn gốc
# Dựa vào nguồn gốc, polime được chia thành hai loại chính: Polime có nguồn gốc tự nhiên và polime tổng hợp. 
# - Polime có nguồn gốc từ thiên nhiên như cao su, xenlulozơ… 
# - Polime tổng hợp do con người tổng hợp nên như polietilen, nhựa phenol-fomanđehit. 
# - Ngoài ra, polime nhân tạo (hay được gọi là bán tổng hợp) được lấy từ polime thiên nhiên và chế hóa thành polime mới như xenlulozơ trinitrat, tơ visco ...

# ## Dựa vào cách tổng hợp
# Dựa vào cách tổng hợp, polime được chia thành hai loại chính như sau: 
# - Polime trùng hợp được tổng hợp bằng phản ứng trùng hợp: (–CH2–CH2–)n và (–CH2–CHCl–)n. 
# - Polime trùng ngưng được tổng hợp bằng phản ứng trùng ngưng: (–HN–[CH2]6–NH–CO–[CH2]4–CO–)n

# ## Dựa vào cấu trúc:
# Bên cạnh đó, polime còn được phân loại dựa vào đặc điểm cấu trúc. 
# - Polime có mạch không phân nhánh, ví dụ như: PVC, PE, PS, cao su, xenlulozơ, tinh bột...
# - Polime có mạch nhánh, ví dụ như amilopectin, glicogen.
# - Polime có cấu trúc mạng không gian, ví dụ như rezit, cao su lưu hóa.""",{"root":{"index":0,"x":241,"y":378,"width":200,"color":"Red","label":"lmao. ","subject":"lmao","body":"","file":'null',"children":[{"index":1,"x":494,"y":229,"width":200,"color":"Red","label":"bruh. ","subject":"bruh","body":"","file":'null',"children":[]},{"index":2,"x":487,"y":503,"width":200,"color":"Red","label":"huhu. hehe","subject":"huhu","body":"hehe","file":'null',"children":[]}]}}))
