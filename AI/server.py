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
        if request["Request"] == "Check Mindmap":
            response = main.main(request["Document"], request["MindMap"])
        elif request["Request"] == "Generate Question":
            response = str([
                    {
			            "question": "a",
			            "answer": [
				            "a",
				            "b",
				            "c",
				            "d"
			            ]
		            }
	            ]
            )
        else:
            response = "Error"
        logging.info(response)
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
                connection, address = server.accept()
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
