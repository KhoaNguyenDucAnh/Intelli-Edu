import numpy as np
import json
from sentence_transformers import SentenceTransformer, util
from collections import deque
from curl_cffi import requests
import re

model = SentenceTransformer("keepitreal/vietnamese-sbert")

from claude_api import Client


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


cookie = "intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; activitySessionId=cac39620-4f7a-4a13-9f84-ec91c91cd857; __stripe_sid=1f25e942-288a-4a33-bd8f-ca519b529805a12a23; __cf_bm=JV9TmU0XaU.nA09Ge3txMNulrZ4KiXlq8Xq5_9a.DDQ-1700790319-0-AUL9cB9c63dECcuEapQqP19X9INOGPIP9bBI0Nu9Oez1QLt+3+Mn6iESvJdHXcZfrM74ABIWYY10TBkyQ9frTJc=; cf_clearance=bwKArpu44ulMLNDcfUFCw8OFDnB2dbxXrcGU0OpY4H0-1700790339-0-1-afc0c2e4.a6a158a9.f8c19d04-0.2.1700790339; sessionKey=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; intercom-session-lupk8zyo=S1RGL0VDakwzZ1J5T3BlNzBlMlJWZzB5L3AyRHVQcm1tdzhVelYvRE1sOXRQajJsRUV3TGVNdHlNZDdQOTRZMi0tSm1ITUx3NmpvZFc4K25RN1lIcmlwUT09--50fa01f0d64699246ef6775ab868d9650f0d2c5a"

claude_api = Custom_Client(cookie)

def to_mindmap(file: str):
    conversation_id = claude_api.create_new_chat()["uuid"]
    mindmap = claude_api.send_message(
        "Create a comprehensive mind map in vietnamese for that essay in json with type: title -> root -> label -> children. Thêm những nhánh về ví dụ và số liệu quan trọng nếu có. Only answer with the mindmap.",
        conversation_id,
        attachment=file,
    )
    claude_api.delete_conversation(conversation_id)
    return json.loads(str(mindmap))

def create_questions(file):
    conversation_id = claude_api.create_new_chat()['uuid']
    mindmap = claude_api.send_message(
        """
        Create a list of 5 questions in Vietnamese for that essay. For each question, include 1 correct answer and 3 incorrect answers. Convert each question to json with the following structure: {"question": question goes here, "answer": [correct answer, incorrect answer 1, incorrect answer 2, incorrect answer 3]}. For Yes No question, fill in the remaining 2 options with null value
        Only answer with the json
        """,
        conversation_id,
        attachment=file
    )
    claude_api.delete_conversation(conversation_id)
    return json.loads(str(mindmap).split("```")[1][5:-1])

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

    json1 = to_mindmap(document)["root"]
    json2 = json.loads(mindmap)["root"]

    def compare(text):
        new_chat = claude_api.create_new_chat()
        conversation_id = new_chat["uuid"]
        prompt = text + "\n"
        # print(prompt)
        prompt += "Can you see if the second text is missing any information or numbers or wrong order compare to the first text in less than 3 sentences in Vietnamese"
        response = claude_api.send_message(prompt, conversation_id)
        claude_api.delete_conversation(conversation_id)
        return response

    def to_tree(index, js, u):
        values[index][u] = js["label"]
        count.popleft()
        if "children" not in js:
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
    return str(compare(text))

# print(main("Chiến tranh thế giới thứ hai (còn được nhắc đến với các tên gọi Đệ nhị thế chiến, Thế chiến II hay Đại chiến thế giới lần thứ hai) là một cuộc chiến tranh thế giới bắt đầu từ khoảng năm 1939 và chấm dứt vào năm 1945. Cuộc chiến có sự tham gia của đại đa số các quốc gia trên thế giới — bao gồm tất cả các cường quốc — tạo thành hai liên minh quân sự đối lập: Đồng Minh và Phe Trục. Trong diện mạo một cuộc chiến tranh toàn diện, Thế chiến II có sự tham gia trực tiếp của hơn 100 triệu nhân sự từ hơn 30 quốc gia. Các bên tham chiến chính đã dồn toàn bộ nguồn lực kinh tế, công nghiệp và khoa học cho nỗ lực tham chiến, làm mờ đi ranh giới giữa nguồn lực dân sự và quân sự. Chiến tranh thế giới thứ hai là cuộc xung đột đẫm máu nhất trong lịch sử nhân loại, gây nên cái chết của 70 đến 85 triệu người, với số lượng thường dân tử vong nhiều hơn quân nhân. Hàng chục triệu người đã phải bỏ mạng trong các vụ thảm sát, diệt chủng (trong đó có Holocaust), chết vì thiếu lương thực hay vì bệnh tật. Máy bay đóng vai trò quan trọng đối với tiến trình cuộc chiến, bao gồm ném bom chiến lược vào các trung tâm dân cư, và đối với sự phát triển vũ khí hạt nhân cũng như hai lần duy nhất sử dụng loại vũ khí này trong chiến tranh.", {"title": "Chiến tranh thế giới thứ hai", "root": {"label": "Chiến tranh thế giới thứ hai", "children": [{"label": "Thời gian", "children": [{"label": "Bắt đầu: 1939"}, {"label": "Kết thúc: 1945"}]}, {"label": "Các bên tham chiến", "children": [{"label": "Đồng Minh", "children": [{"label": "Anh"}, {"label": "Pháp"}, {"label": "Liên Xô"}, {"label": "Hoa Kỳ"}]}, {"label": "Phe Trục", "children": [{"label": "Đức"}, {"label": "Ý"}, {"label": "Nhật Bản"}]}]}, {"label": "Quy mô", "children": [{"label": "Hơn 100 triệu nhân sự tham chiến"}, {"label": "Hơn 30 quốc gia tham gia"}, {"label": "Đẫm máu nhất trong lịch sử"}]}, {"label": "Hậu quả", "children": [{"label": "70-85 triệu người chết"}, {"label": "Dân thường chết nhiều hơn quân nhân"}, {"label": "Diệt chủng, đói kém, dịch bệnh"}]}]}}))
