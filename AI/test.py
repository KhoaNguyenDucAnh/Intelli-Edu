from claude_api import Client
import json 

class Custom_Client(Client):
    def upload_attachment(self, content):
        return {
            "file_name": "document",
            "file_type": "text/plain",
            "extracted_content": content
        }


cookie = "__ssid=58cbed228dbcc9d2deff7693cc0a217; sessionKey=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; intercom-device-id-lupk8zyo=119883fe-4435-4e82-8327-3030999dd0d0; intercom-session-lupk8zyo=Z2cyNlM4YmhhKy9uUy9yN3JBRWRud2NhdVRQcWg5cEYwMklTSkEzaEJ3bFp0Vk9pZm5yQTcvU1NnN29aYXdFRS0tS24vTm5VckVVMlQxZ2RZdnU0ZDFBUT09--cfa76e606998054a51cecde7906666b7c79173b7; activitySessionId=df9a2c68-5215-408e-91a3-44f204042d03; __cf_bm=Xm4FBXEiNIresLVAY3rFftMyVRbWWHCoDtDSDDw_MYw-1698040645-0-AYC9QqMKDBdNa/4xSS314kZYFsmj4e0kxTJT2U/ys5mrb/4xIdFnwXvaXJ8bUHriJAbXnb9h5l4y44bU4x2oAOc=; cf_clearance=qqBlkXXqh6004uFuH22GbRVGTP.5Q2KisySyyHZvhow-1698040647-0-1-e80cda08.449e1d62.bf0d85e8-0.2.1698040647"

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

claude_api = Custom_Client(cookie)
mindmap = to_mindmap("Chiến tranh thế giới thứ hai (còn được nhắc đến với các tên gọi Đệ nhị thế chiến, Thế chiến II hay Đại chiến thế giới lần thứ hai) là một cuộc chiến tranh thế giới bắt đầu từ khoảng năm 1939 và chấm dứt vào năm 1945. Cuộc chiến có sự tham gia của đại đa số các quốc gia trên thế giới — bao gồm tất cả các cường quốc — tạo thành hai liên minh quân sự đối lập: Đồng Minh và Phe Trục. Trong diện mạo một cuộc chiến tranh toàn diện, Thế chiến II có sự tham gia trực tiếp của hơn 100 triệu nhân sự từ hơn 30 quốc gia. Các bên tham chiến chính đã dồn toàn bộ nguồn lực kinh tế, công nghiệp và khoa học cho nỗ lực tham chiến, làm mờ đi ranh giới giữa nguồn lực dân sự và quân sự. Chiến tranh thế giới thứ hai là cuộc xung đột đẫm máu nhất trong lịch sử nhân loại, gây nên cái chết của 70 đến 85 triệu người, với số lượng thường dân tử vong nhiều hơn quân nhân. Hàng chục triệu người đã phải bỏ mạng trong các vụ thảm sát, diệt chủng (trong đó có Holocaust), chết vì thiếu lương thực hay vì bệnh tật. Máy bay đóng vai trò quan trọng đối với tiến trình cuộc chiến, bao gồm ném bom chiến lược vào các trung tâm dân cư, và đối với sự phát triển vũ khí hạt nhân cũng như hai lần duy nhất sử dụng loại vũ khí này trong chiến tranh.")
print(mindmap)
