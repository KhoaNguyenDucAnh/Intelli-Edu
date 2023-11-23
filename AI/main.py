import numpy as np

from sentence_transformers import SentenceTransformer,util
from collections import deque

model = SentenceTransformer('keepitreal/vietnamese-sbert')

from claude_api import Client
cookie='intercom-device-id-lupk8zyo=435acf1c-a907-4409-ae7b-593d8b45e4a5; __ssid=ae3af040adac10d2aec8d3d99438209; __stripe_mid=439d2fe6-7918-47a3-aece-aea314af663ae04ec2; sessionKey=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; intercom-session-lupk8zyo=ZzdCQVBCNXR1dXRta2lOUUVLOUQxNllpYkVmc0tEcTIvUWJQYXlWRU1IY2JNdnpPSGZNOUd2dVJoRTVTWEFkeC0tWXVTVXhlTXZXeEwvYzBoWVh0dVB2UT09--09ef62d400c2e3351b5a0e0aeb03792924e049b7; activitySessionId=76d934cd-1ac0-4013-967e-6a0cf29b58ea; __cf_bm=CSa54YByr2rz6ECrXDp_8cj5Lbz2dXYWgbQcUqz7y1w-1700591156-0-AYElRqtfPYVL9LNZz2+QUGI03SpGXUkSLmILET68j0v2RISdTsnabfeck+GWf/MBJEfg0HuHvfFRKjsucN960Zo=; cf_clearance=AHl0grYiPgRUZpICm7k99TAml2i_VAfz2IFsgEVfCv4-1700591157-0-1-4a351f37.411d4da8.1d3440d7-0.2.1700591157; __stripe_sid=a7543311-72c6-40e7-b570-401b985414a2c86883'
claude_api=Client(cookie)

conversation_id='43380088-8b3d-47bd-9be6-0d8219509a08'
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

  json1 = to_mindmap(document)['root']
  json2 = mindmap['root']

  def compare(text):
    prompt=text + '\n'
    prompt+='Can you see if the second text is missing any information or numbers or wrong order compare to the first text in no more than 7 sentences in Vietnamese'
    response = claude_api.send_message(prompt, conversation_id)
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
