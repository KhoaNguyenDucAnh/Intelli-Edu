from claude_api import Client
import os
cookie='intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __cf_bm=0meTOJSogp4qPQ_1VBmhqL5Lamyy_.g4mIHT4BQQn3s-1694107625-0-AQ/1ogiV1p4p1e2Y1Hkt3NNCmfIYFK8w265IOjNuWWsKbw88zFa+DKEWyHN2iLNDJFvFfnOzFlkGNc5BxNls0ho=; cf_clearance=yPRpEXnThZTOnPyDzIgSRdPC3xrw3KtgIED3ftmDoMU-1694107627-0-1-2d56ae04.7f0bbe63.7a57f1e4-0.2.1694107627; sessionKey=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; intercom-session-lupk8zyo=dEVWZk9WMmlFdWJ0cUlhZXZ4S2szcnhYL29wRjB3Nis1RXdBcFVLUWdCSEZHRThCdmlETEZHcG0yVjZVUjRlaS0tR3FCVHc4UlpwNE5VRGFKeXV0bDZiZz09--f5244f8d1d57e8176b67dc3c50030a39fee7dcf3'
claude_api=Client(cookie)
new_chat = claude_api.create_new_chat()
conversation_id = new_chat['uuid']
prompt = "Hello, Claude!"
conversation_id = conversation_id
response = claude_api.send_message(prompt, conversation_id, attachment=r'C:\Users\heins\Downloads\biopolitics.docx')
print(response)