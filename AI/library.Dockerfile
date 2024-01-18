FROM python:3.11

COPY AI/requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY AI/main.py .

RUN python main.py
