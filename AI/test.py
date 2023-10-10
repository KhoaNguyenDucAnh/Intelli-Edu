from claude_api import Client
import os
cookie='intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __cf_bm=v6JqButMzKBvfpTQXoJTrFdg8h.CA.L4ELkCO4K665w-1694945465-0-ATdUa52goRTNwsvZayww9rer2sGOMP3E1gwX8nZD0mQkgrmADeiIFTPZUfTntdHsA1tLtBNuh6R4k5KbPnMHY04=; cf_clearance=yP6Y7jTzeRGd2O_HnKCEKFToerLmWY8GLP0FojwkK0Q-1694945468-0-1-2d56ae04.e69e4851.7a57f1e4-0.2.1694945468; sessionKey=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; intercom-session-lupk8zyo=VTRoNlBWOUpoNDdEYXAvWGJNOHdGUThVNGhEUlFicFBEWWRxanMyWlhmbFh1Q0xoVThQQldwVDhoYjhqbVhNOC0tZGVMV1lPQ3Y2bmZzbGo0VU1zL2hOQT09--00f859741b60b442d2c9c692d09479ec9e329c09'
claude_api=Client(cookie)
#new_chat = claude_api.create_new_chat()
# conversation_id = new_chat['uuid']
# prompt = "Hello, Claude!"
# conversation_id = conversation_id
# response = claude_api.send_message(prompt, conversation_id, attachment=r'C:\Users\heins\Downloads\biopolitics.docx')
# print(response)