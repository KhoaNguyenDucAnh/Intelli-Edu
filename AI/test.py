from claude_api import Client
import json 


cookie = "intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; sessionKey=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; intercom-session-lupk8zyo=ZzdCQVBCNXR1dXRta2lOUUVLOUQxNllpYkVmc0tEcTIvUWJQYXlWRU1IY2JNdnpPSGZNOUd2dVJoRTVTWEFkeC0tWXVTVXhlTXZXeEwvYzBoWVh0dVB2UT09--09ef62d400c2e3351b5a0e0aeb03792924e049b7; activitySessionId=76d934cd-1ac0-4013-967e-6a0cf29b58ea; __cf_bm=CSa54YByr2rz6ECrXDp_8cj5Lbz2dXYWgbQcUqz7y1w-1700591156-0-AYElRqtfPYVL9LNZz2+QUGI03SpGXUkSLmILET68j0v2RISdTsnabfeck+GWf/MBJEfg0HuHvfFRKjsucN960Zo=; cf_clearance=AHl0grYiPgRUZpICm7k99TAml2i_VAfz2IFsgEVfCv4-1700591157-0-1-4a351f37.411d4da8.1d3440d7-0.2.1700591157; __stripe_sid=a7543311-72c6-40e7-b570-401b985414a2c86883"
conversation_id='43380088-8b3d-47bd-9be6-0d8219509a08'

claude_api=Client(cookie)

def to_mindmap(file):
    prompt="Create a comprehensive mind map in vietnamese for that essay in json with type: title -> root -> label -> children. Thêm những nhánh về ví dụ và số liệu quan trọng nếu có. Only answer with the mindmap."
    mindmap = claude_api.send_message(prompt,conversation_id,attachment=file)
    return json.loads(str(mindmap))
def create_questions(file):
    prompt="Can you create 5 multiple choice questions with 4 options from this in json with type: questions->options. Only answer with the json."
    questions = claude_api.send_message(prompt,conversation_id,attachment=file)
    return json.loads(str(questions))
mindmap = to_mindmap('AI/mmtest.txt')
questions = create_questions('AI/mmtest.txt')
print(questions)
