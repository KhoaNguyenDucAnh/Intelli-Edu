import math
import numpy as np
import torch

from sentence_transformers import SentenceTransformer,util

model = SentenceTransformer('keepitreal/vietnamese-sbert')

query_em=model.encode('Người anh hùng đi bộ')
corpus_em=model.encode(['Người hùng rảo bước','Anh hùng nghỉ ngơi'])
hits = util.semantic_search(query_em, corpus_em, score_function=util.dot_score)
print(hits[0][0]['corpus_id'])
