import numpy as np
import json 

nChain=np.zeros(2)
chainHead=np.zeros((2,100000))
chainEnd=np.zeros((2,100000))
chainInd=np.zeros((2,100000))
values=[[None for i in range(100000)],[None for i in range(100000)]]
adj=[[[] for i in range(50)],[[] for i in range(50)]]
nBase=np.zeros(2)
pos=np.zeros((2,100000))
rev=np.zeros((2,100000))
parent=np.zeros((2,100000))
nChild=np.zeros((2,100000))

json={
  "title": "Conversation",
  "label":"gay",
  "nodes": [
    {
      "id": "howareyou",
      "label": "how are u",
      "nodes": [
        {
          "id": "imfine",
          "label": "i'm fine thank you"
        },
        {
          "id": "andyou",
          "label": "and you",
          "nodes": [
            {
              "id": "doyouhaveapie",
              "label": "Do you have a pie"  
            }
          ]
        }
      ]
    }
  ]
}
def to_tree(index,js,u):
    values[index][u]=js['label']
    if 'nodes' not in js:
        return
    js=js['nodes']
    v=u
    for i in range(len(js)):
        v=v+1
        adj[index][u].append(v)
        adj[index][v].append(u)
        parent[index][v]=u
        to_tree(index,js[i],v)
def dfs(u,index):
    nChild[index][u]=1
    for v in adj[index][u]:
        if v!=parent[index][u]:
            dfs(v,index)
            nChild[index][u]+=nChild[index][v]
        
to_tree(0,json,0)
dfs(0,0)
def hld(u,index):
    if int(chainHead[index][int(nChain[index])])==0:
        chainHead[index][int(nChain[index])]=u 
    chainInd[index][u]=nChain[index]
    nBase[index]+=1
    pos[index][u]=nBase[index]
    rev[index][int(nBase[index])]=u
    spec=-1
    for i in range(len(adj[index][u])):
        v=adj[index][u][i]
        if(v!=parent[index][u]):
            if(spec==-1 or nChild[index][v]>nChild[index][spec]):
                spec=v
    if(spec>-1):
        hld(spec,index)
    if(spec==-1):
        chainEnd[index][int(nChain[index])]=u
    for v in adj[index][u]:
        if(v!=parent[index][u] and v!=spec):
            nChain[index]+=1
            hld(v,index)
hld(0,0)
for i in range(int(nChain[0]+1)):
    print(rev[0][int(chainHead[0][0]):int(chainEnd[0][0]+1)])
# for i in range(total):
#     if (chainHead[0][i+1]==chain[0][i] and i+1!=total):
#         text=text+value[0][i]
#     else:
#         user_text=find(text)
#         fb=comment(text,user_text)


        




