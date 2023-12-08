from claude_api import Client
import json
from curl_cffi import requests
import re

class Custom_Client(Client):
  def upload_attachment(self, content):
    return {
      "file_name": "document",
      "file_type": "text/plain",
      "file_size": len(content.encode("utf-8")),
      "extracted_content": content
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

    payload = json.dumps({
      "completion": {
        "prompt": f"{prompt}",
        "timezone": "Asia/Kolkata",
        "model": "claude-2.1"
      },
      "organization_uuid": f"{self.organization_id}",
      "conversation_uuid": f"{conversation_id}",
      "text": f"{prompt}",
      "attachments": attachments
    })

    headers = {
      "User-Agent":
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0",
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
      "TE": "trailers"
    }

    response = requests.post( url, headers=headers, data=payload,impersonate="chrome110",timeout=500)
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

def to_mindmap(file: str):
    conversation_id = claude_api.create_new_chat()['uuid']
    mindmap = claude_api.send_message(
        """Convert the file to Vietnamese
        Create a comprehensive mind map in vietnamese for that essay, please provide a code box with Markdown language
        Thêm những nhánh về ví dụ và số liệu quan trọng nếu có
        Convert mindmap to json with type : title -> root -> label -> children""",
        conversation_id,
        attachment=file
    )
    claude_api.delete_conversation(conversation_id)
    return json.loads(str(mindmap).split("```")[1][5:-1])

def create_questions(file):
    conversation_id = claude_api.create_new_chat()['uuid']
    questions = claude_api.send_message(
        """
        Human:We want to create 5 multiple choices questions in Vietnamese for this text. Each questions should be in json format, and there should be commas in between the questions. For each question, include 1 correct answer and 3 incorrect answers with the following structure: {"question": question goes here, "answers": [correct answer, incorrect answer 1, incorrect answer 2, incorrect answer 3]}.

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

cookie = "intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; activitySessionId=cac39620-4f7a-4a13-9f84-ec91c91cd857; __stripe_sid=1f25e942-288a-4a33-bd8f-ca519b529805a12a23; __cf_bm=JV9TmU0XaU.nA09Ge3txMNulrZ4KiXlq8Xq5_9a.DDQ-1700790319-0-AUL9cB9c63dECcuEapQqP19X9INOGPIP9bBI0Nu9Oez1QLt+3+Mn6iESvJdHXcZfrM74ABIWYY10TBkyQ9frTJc=; cf_clearance=bwKArpu44ulMLNDcfUFCw8OFDnB2dbxXrcGU0OpY4H0-1700790339-0-1-afc0c2e4.a6a158a9.f8c19d04-0.2.1700790339; sessionKey=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; intercom-session-lupk8zyo=S1RGL0VDakwzZ1J5T3BlNzBlMlJWZzB5L3AyRHVQcm1tdzhVelYvRE1sOXRQajJsRUV3TGVNdHlNZDdQOTRZMi0tSm1ITUx3NmpvZFc4K25RN1lIcmlwUT09--50fa01f0d64699246ef6775ab868d9650f0d2c5a"

claude_api = Custom_Client(cookie)
mindmap = create_questions("Chiến tranh thế giới thứ hai (còn được nhắc đến với các tên gọi Đệ nhị thế chiến, Thế chiến II hay Đại chiến thế giới lần thứ hai) là một cuộc chiến tranh thế giới bắt đầu từ khoảng năm 1939 và chấm dứt vào năm 1945. Cuộc chiến có sự tham gia của đại đa số các quốc gia trên thế giới — bao gồm tất cả các cường quốc — tạo thành hai liên minh quân sự đối lập: Đồng Minh và Phe Trục. Trong diện mạo một cuộc chiến tranh toàn diện, Thế chiến II có sự tham gia trực tiếp của hơn 100 triệu nhân sự từ hơn 30 quốc gia. Các bên tham chiến chính đã dồn toàn bộ nguồn lực kinh tế, công nghiệp và khoa học cho nỗ lực tham chiến, làm mờ đi ranh giới giữa nguồn lực dân sự và quân sự. Chiến tranh thế giới thứ hai là cuộc xung đột đẫm máu nhất trong lịch sử nhân loại, gây nên cái chết của 70 đến 85 triệu người, với số lượng thường dân tử vong nhiều hơn quân nhân. Hàng chục triệu người đã phải bỏ mạng trong các vụ thảm sát, diệt chủng (trong đó có Holocaust), chết vì thiếu lương thực hay vì bệnh tật. Máy bay đóng vai trò quan trọng đối với tiến trình cuộc chiến, bao gồm ném bom chiến lược vào các trung tâm dân cư, và đối với sự phát triển vũ khí hạt nhân cũng như hai lần duy nhất sử dụng loại vũ khí này trong chiến tranh.")
print(mindmap)

#mindmap = to_mindmap('AI/mmtest.txt')
#questions = create_questions('AI/mmtest.txt')
#print(questions)
