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
        connection.send(main.main(request["Document"], request["MindMap"]))
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
                logging.info(f"Accepted connection from {address}")
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
