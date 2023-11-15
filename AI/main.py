import numpy as np
import json 
from sentence_transformers import SentenceTransformer,util
from collections import deque

model = SentenceTransformer('keepitreal/vietnamese-sbert')

from claude_api import Client
import os
cookie='intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; activitySessionId=5f50df88-6356-4f92-a0bb-e9f7ae4eb16a; __cf_bm=JTPSITiXx2kIJODe63Wc5GU5V1ioqJ7.SArRK7jihQI-1699882511-0-AdRvfn4+F3OjaQOB4JGMKZmKy2m3r9m6c48tCul+COKF8daiyPfragC2wzisd+71zcD476o2/KQMUT7kji6Ceps=; cf_clearance=NpgAAP8bQAGceYC22UXrbLa_CVQTYhmvvUGO5o0B0zo-1699882513-0-1-ff3e925e.7e64c691.a83fcdbe-0.2.1699882513; __stripe_sid=c8038001-5c14-4a25-afd6-e6aa398673468faf9c; sessionKey=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'
claude_api=Client(cookie)

def get_map():
  file=''
  global json1,json2
  json2='user mindmap'
  new_chat = claude_api.create_new_chat()
  conversation_id = new_chat['uuid']
  prompt='Convert the file to Vietnamese'
  claude_api.send_message(prompt, conversation_id, attachment=file)
  prompt='Create a comprehensive mind map in vietnamese for that essay, please provide a code box with Markdown language'
  claude_api.send_message(prompt, conversation_id)
  prompt='Thêm những nhánh về ví dụ và số liệu quan trọng nếu có'
  claude_api.send_message(prompt, conversation_id)
  prompt='convert mindmap to json with type : title -> root -> label -> children'
  json1=claude_api.send_message(prompt, conversation_id)
  claude_api.delete_conversation(conversation_id)
  return

def main():
  nChain=np.zeros(2)
  chainHead=np.zeros((2,500))
  chainEnd=np.zeros((2,500))
  chainInd=np.zeros((2,500))
  values=[[None for i in range(100)],[None for i in range(500)]]
  adj=[[[] for i in range(500)],[[] for i in range(500)]]
  nBase=np.zeros(2)
  pos=np.zeros((2,500))
  parent=np.zeros((2,500))
  nChild=np.zeros((2,500))
  rev=np.zeros((2,500))
  flat=[[None for i in range(100)],[None for i in range(100)]]
  count=deque([0])
  for i in range(500):
     count.append(i+1)
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

  def compare(text):
    new_chat = claude_api.create_new_chat()
    conversation_id = new_chat['uuid']
    prompt=text + '\n'
    print(prompt)
    prompt+='Can you see if the second text is missing any information or numbers or wrong order compare to the first text in less than 3 sentences in Vietnamese'
    response = claude_api.send_message(prompt, conversation_id)
    claude_api.delete_conversation(conversation_id)
    return response
  def to_tree(index,js,u):
    values[index][u]=js['label']
    count.popleft()
    if 'children' not in js:
        return 1
    js=js['children']
    dem=0
    for i in range(len(js)):
        v=count[0]
        adj[index][u].append(v)
        adj[index][v].append(u)
        parent[index][v]=u
        dem+=to_tree(index,js[i],v)
    return dem+1
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
  
  d1=to_tree(0,json1,1)
  count=deque([0])
  for i in range(500):
     count.append(i+1)
  d2=to_tree(1,json2,1)
  dfs(0,1)
  dfs(1,1)
  hld(0,1)
  hld(1,1)
  flatten(0,d1)
  flatten(1,d2)
  text=''
  for i in range(int(nChain[0])):
    findHead=int(find(values[0][int(chainHead[0][i])],d2))+1
    findEnd=int(find(values[0][int(chainEnd[0][i])],d2))+1
    comp_text=flat[0][int(pos[0][int(chainHead[0][i])]):(int(pos[0][int(chainEnd[0][i])])+1)]
    Head_index=int(rev[1][findHead])
    End_index=int(rev[1][findEnd])
    user_text=[]
    if(Head_index>End_index):
       Head_index,End_index=End_index,Head_index
    if(End_index==Head_index):
      user_text=flat[1][int(pos[1][int(Head_index)]):int(pos[1][int(End_index)])+1]
      text+=str(comp_text)+' '+str(user_text)+'\n'
    loca=0
    print(Head_index,' ',End_index)
    while(End_index!=Head_index):
      user_chain=int(chainInd[1][int(End_index)])
      if(chainInd[1][int(End_index)]==chainInd[1][int(Head_index)]):
        user_text=flat[1][int(pos[1][int(Head_index)]):int(pos[1][int(End_index)])+1]+user_text
        break
      user_text=flat[1][int(pos[1][int(chainHead[1][int(user_chain)])]):int(pos[1][int(End_index)])+1]+user_text
      End_index=int(parent[1][int(chainHead[1][user_chain])]) 
      if(End_index==1):
        loca=-1
        break
      user_chain=int(chainInd[1][int(End_index)])
    if(loca==-1):
      print('sai vi tri dang le',flat[1][findHead],'va',flat[1][findEnd],'o cung 1 chuoi')
      continue
    text+=str(comp_text)+' '+str(user_text)+'\n'
  print(compare(text))
main()