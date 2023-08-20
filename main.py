import numpy as np
import json 
from sentence_transformers import SentenceTransformer,util

nChain=np.zeros(2)
chainHead=np.zeros((2,100000))
chainEnd=np.zeros((2,100000))
chainInd=np.zeros((2,100000))
values=[[None for i in range(100000)],[None for i in range(100000)]]
adj=[[[] for i in range(50)],[[] for i in range(50)]]
nBase=np.zeros(2)
pos=np.zeros((2,100000))
parent=np.zeros((2,100000))
nChild=np.zeros((2,100000))
rev=np.zeros((2,100000))
flat=[[None for i in range(100000)],[None for i in range(100000)]]
n=0
model = SentenceTransformer('keepitreal/vietnamese-sbert')

json1={
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

json2={
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
    global n 
    n+=1
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

def hld(u,index):
    if int(chainHead[index][int(nChain[index])])==0:
        chainHead[index][int(nChain[index])]=u 
    chainInd[index][u]=nChain[index]
    nBase[index]+=1
    pos[index][u]=nBase[index]
    rev[index][pos[index][u]]=u
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

def flatten(index):
  for i in range(n):
    flat[index][int(pos[index][i])]=values[index][i]

def find(text):
  corpus_em=model.encode(flat[1])
  hits = util.semantic_search(text, corpus_em, score_function=util.dot_score)
  position=hits[0][0]['corpus_id']
  return position

to_tree(0,json,0)
to_tree(1,json,0)
dfs(0,0)
dfs(1,0)
hld(0,0)
hld(1,0)
flatten(0)
flatten(1)

for i in range(nChain[0]):
  findHead=find(chainHead[0][i])
  findEnd=find(chainEnd[0][i])
  comp_text=flat[0][chainHead[0][i]:chainEnd[0][i]]
  Head_index=rev[0][findHead]
  End_index=rev[0][findEnd]
  user_text=''
  while(End_index!=Head_index):
    user_chain=chainInd[0][End_index]
    user_text+=flat[0][pos[0][chainHead[0][user_chain]]:pos[0][End_index]]
    End_index=parent[0][chainHead[0][user_chain]]   
    user_chain=chainInd[0][End_index]


        




