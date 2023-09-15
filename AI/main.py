import numpy as np
import json 
from sentence_transformers import SentenceTransformer,util

nChain=np.zeros(2)
chainHead=np.zeros((2,100000))
chainEnd=np.zeros((2,100000))
chainInd=np.zeros((2,100000))
values=[[None for i in range(100000)],[None for i in range(100000)]]
adj=[[[] for i in range(100)],[[] for i in range(100)]]
nBase=np.zeros(2)
pos=np.zeros((2,100000))
parent=np.zeros((2,100000))
nChild=np.zeros((2,100000))
rev=np.zeros((2,100000))
flat=[[None for i in range(100000)],[None for i in range(100000)]]
n=1
d1=0
d2=0

model = SentenceTransformer('keepitreal/vietnamese-sbert')

from claude_api import Client
import os
cookie='intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __cf_bm=0meTOJSogp4qPQ_1VBmhqL5Lamyy_.g4mIHT4BQQn3s-1694107625-0-AQ/1ogiV1p4p1e2Y1Hkt3NNCmfIYFK8w265IOjNuWWsKbw88zFa+DKEWyHN2iLNDJFvFfnOzFlkGNc5BxNls0ho=; cf_clearance=yPRpEXnThZTOnPyDzIgSRdPC3xrw3KtgIED3ftmDoMU-1694107627-0-1-2d56ae04.7f0bbe63.7a57f1e4-0.2.1694107627; sessionKey=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; intercom-session-lupk8zyo=dEVWZk9WMmlFdWJ0cUlhZXZ4S2szcnhYL29wRjB3Nis1RXdBcFVLUWdCSEZHRThCdmlETEZHcG0yVjZVUjRlaS0tR3FCVHc4UlpwNE5VRGFKeXV0bDZiZz09--f5244f8d1d57e8176b67dc3c50030a39fee7dcf3'
claude_api=Client(cookie)

json1={
  "title": "Sự thành lập Liên hợp quốc",
  "root": {
    "label": "Sự thành lập LHQ",
    "children": [
      {
        "label": "Sự thành lập",
        "children": [
          {
            "label": "Kết thúc WWII"
          },
          {
            "label": "Nguyện vọng hòa bình toàn cầu"  
          },
          {
            "label": "Thỏa thuận tại Hội nghị Yalta"
          },
          {
            "label": "Thông qua Hiến chương tại HNC Phrancisco"  
          }
        ]
      },
      {
        "label": "Mục tiêu & Nguyên tắc",
        "children": [
          {
            "label": "Mục tiêu",
            "children": [
              { "label": "Duy trì hòa bình" },
              { "label": "Phát triển quan hệ hữu nghị" },
              { "label": "Hợp tác quốc tế" }
            ]
          },
          {
            "label": "Nguyên tắc",
            "children": [
              { "label": "Bình đẳng chủ quyền" },
              { "label": "Toàn vẹn lãnh thổ" }, 
              { "label": "Không can thiệp nội bộ" },
              { "label": "Giải quyết tranh chấp hòa bình" },
              { "label": "Đồng thuận các cường quốc(Liên Xô, Mĩ, Anh, Pháp, Trung Quốc)" }
            ]
          }
        ]
      },  
      {
        "label": "Cơ cấu tổ chức",
        "children": [
          { "label": "Đại hội đồng" },
          { "label": "Hội đồng Bảo an" },
          { "label": "Hội đồng Kinh tế & Xã hội" }, 
          { "label": "Hội đồng Quản trị" },
          { "label": "Tòa án Quốc tế" },
          { "label": "Ban Thư ký" }
        ]
      },
      {
        "label": "Trụ sở",
        "children": [
          { "label": "Thành phố New York" }
        ]
      },
      {
        "label": "Vai trò",
        "children": [
          { "label": "Diễn đàn hợp tác & đấu tranh cho hòa bình" },
          { "label": "Giải quyết xung đột" },
          { "label": "Thúc đẩy quan hệ quốc tế" }
        ]
      }
    ]
  } 
}

json2={
  "title": "Sự thành lập Liên hợp quốc",
  "root": {
    "label": "Sự thành lập LHQ", 
    "children": [
      {
        "label": "Sự thành lập",
        "children": [
          {
            "label": "Kết thúc WW2"  
          },
          {
            "label": "Nguyện vọng hòa bình toàn cầu"
          }
        ]
      },
      {
        "label": "Mục tiêu & Nguyên tắc",
        "children": [
          {
            "label": "Mục tiêu", 
            "children": [
              { "label": "Duy trì hòa bình thế giới" },
              { "label": "Phát triển quan hệ hữu nghị" },
              { "label": "Hợp tác quốc tế" }
            ]
          },
          {
            "label": "Nguyên tắc",
            "children": [
              { "label": "Bình đẳng chủ quyền" }, 
              { "label": "Toàn vẹn lãnh thổ" },
              { "label": "Không can thiệp" },
              { "label": "Giải quyết tranh chấp hòa bình" },
              { "label": "Đồng thuận các cường quốc (Thái Lan, Mĩ, Anh, Pháp, Trung Quốc" }
            ]
          }
        ]
      },
      {
        "label": "Cơ cấu tổ chức",
        "children": [
          { "label": "Đại hội đồng" },
          { "label": "Hội đồng Bảo an" },
          { "label": "Hội đồng Kinh tế & Xã hội" },
          { "label": "Hội đồng Quản trị" },
          { "label": "Tòa án Quốc tế" },
          { "label": "Ban Thư ký" }
        ]
      },
      {
        "label": "Trụ sở",
        "children": [
          { "label": "Thành phố New York" }
        ]
      },
      {
        "label": "Vai trò",
        "children": [
          { 
            "label": "Hòa bình" ,
            "children":[
              {"label":"diễn đàn hợp tác"},
              {"label":"đấu tranh"}
            ]
          }
        ]  
      }
    ]
  }
}
json1=json1['root']
json2=json2['root']
def to_tree(index,js,u):
    values[index][u]=js['label']
    if 'children' not in js:
        return
    js=js['children']
    global n
    for i in range(len(js)):
        n+=1
        adj[index][u].append(n)
        adj[index][n].append(u)
        parent[index][n]=u
        to_tree(index,js[i],n)
def dfs(index,u):
    nChild[index][u]=1
    for v in adj[index][u]:
      if v!=parent[index][u]:
            dfs(index,v)
            nChild[index][u]+=nChild[index][v]

def hld(index,u):
    if int(chainHead[index][int(nChain[index])])==0:
        chainHead[index][int(nChain[index])]=u 
    chainInd[index][u]=nChain[index]
    nBase[index]+=1
    pos[index][u]=nBase[index]
    rev[index][int(pos[index][u])]=u
    spec=-1
    for i in range(len(adj[index][u])):
        v=adj[index][u][i]
        if(v!=parent[index][u]):
            if(spec==-1 or nChild[index][v]>nChild[index][spec]):
                spec=v
    if(spec>-1):
        hld(index,spec)
    if(spec==-1):
        chainEnd[index][int(nChain[index])]=u
    for v in adj[index][u]:
        if(v!=parent[index][u] and v!=spec):
            nChain[index]+=1
            hld(index,v)

def flatten(index,n):
  for i in range(n):
    flat[index][int(pos[index][i])]=values[index][i]

def find(text,n):
  corpus_em=model.encode(flat[1][1:n+1])
  text=model.encode(text)
  hits = util.semantic_search(text, corpus_em, score_function=util.dot_score)
  position=hits[0][0]['corpus_id']
  return position
def compare(ct,up):
  prompt=''
  new_chat = claude_api.create_new_chat()
  conversation_id = new_chat['uuid']
  prompt+=ct+' '+up+'\n'
  prompt+='Can you see if the second text is missing any information or numbers or wrong order compare to the first text'
  conversation_id = conversation_id
  response = claude_api.send_message(prompt, conversation_id)
  return response

to_tree(0,json1,1)
d1=n
n=1
to_tree(1,json2,1)
d2=n
dfs(0,1)
dfs(1,1)
hld(0,1)
hld(1,1)
flatten(0,d1)
flatten(1,d2)
for i in range(int(nChain[0])):
  findHead=int(find(values[0][int(chainHead[0][i])],d2))+1
  findEnd=int(find(values[0][int(chainEnd[0][i])],d2))+1
  comp_text=flat[0][int(pos[0][int(chainHead[0][i])]):(int(pos[0][int(chainEnd[0][i])])+1)]
  Head_index=int(rev[1][findHead])
  End_index=int(rev[1][findEnd])
  user_text=[]
  if(End_index==Head_index):
    user_text=flat[1][int(pos[1][int(Head_index)]):int(pos[1][int(End_index)])+1]
    print(compare(comp_text,user_text))
  loca=0
  while(End_index!=Head_index):
    user_chain=int(chainInd[1][int(End_index)])
    if(chainInd[1][int(End_index)]==chainInd[1][int(Head_index)]):
      user_text=flat[1][int(pos[1][int(Head_index)]):int(pos[1][int(End_index)])+1]+user_text
      break
    user_text=flat[1][int(pos[1][int(chainHead[1][int(user_chain)])]):int(pos[1][int(End_index)])+1]+user_text
    End_index=int(parent[1][int(chainHead[1][user_chain])]) 
    if(End_index==0):
      loca=-1
      break
    user_chain=int(chainInd[1][int(End_index)])
  if(loca==-1):
    print('sai vi tri dang le',flat[1][findHead],'va',flat[1][findEnd],'o cung 1 chuoi')
  print(compare(comp_text,user_text))
