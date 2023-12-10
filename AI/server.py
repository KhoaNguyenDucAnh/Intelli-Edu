import socket
import logging
import threading
import json
import main

logging.basicConfig(
    level = logging.INFO,
    format = "%(asctime)s [%(levelname)s]: %(message)s",
    handlers = [
        logging.FileHandler("server.log"),
        logging.StreamHandler()
    ])

def handle_client(connection):
    request = connection.recv(1048576)
    if request:
        request = json.loads(request.decode("utf-8"))
        response = ""
        try:
            if request["Request"] == "Check Mindmap":
                response = main.main(request["Document"], request["MindMap"])
            elif request["Request"] == "Generate Question":
                # response = str([{"question":"Chiến tranh thế giới thứ hai bắt đầu vào năm nào?","answers":["1939","1938","1940","1941"]},{"question":"Trong thế chiến thứ hai có bao nhiêu nước tham gia? ","answers":["Hơn 30 nước","25 nước","20 nước","15 nước"]},{"question":"Thế chiến thứ hai kết thúc vào năm nào?","answers":["1945","1946","1944","1943"]},{"question":"Trong thế chiến thứ hai, các bên tham chiến chính có dồn hết nguồn lực cho chiến tranh không?","answers":["Có","Không","Không biết","Không đúng"]},{"question":"Trong thế chiến thứ hai, số lượng thường dân tử vong có nhiều hơn quân nhân không?","answers":["Có","Không","Không biết","Không đúng"]}])
                response = str(main.create_questions(request["Document"]))
            logging.info(response)
        except:
            logging.error(response)
            if request["Request"] == "Check Mindmap":
                response = ""
            elif request["Request"] == "Generate Question":
                response = "[]"
        connection.send(response.encode("utf-8"))
        logging.info("Complete")
    connection.close()

def server(host, port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
        server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        server.bind((host, port)) 
        server.listen()
        logging.info(f"Server listening on {host}:{port}")

        while True:
            try:
                connection, _ = server.accept()
                logging.info("Accepted connection")
                threading.Thread(target=handle_client, args=(connection,)).start()
            except OSError as e:
                logging.error(e)
                return
            except KeyboardInterrupt:
                server.close()
                logging.warning("Server shutting down")
                return

if __name__ == "__main__":
    server("localhost", 65432)
