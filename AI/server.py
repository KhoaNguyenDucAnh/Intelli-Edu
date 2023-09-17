import socket
import logging
import threading

logging.basicConfig(
    level = logging.INFO,
    format = "%(asctime)s [%(levelname)s]: %(message)s",
    handlers = [
        logging.FileHandler("server.log"),
        logging.StreamHandler()
    ])

def handle_client(connection):
    request = connection.recv(1024)
    
    if request:
        response = request

        connection.send(response)

    connection.close()

def main(host, port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
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
    main("localhost", 65432)
