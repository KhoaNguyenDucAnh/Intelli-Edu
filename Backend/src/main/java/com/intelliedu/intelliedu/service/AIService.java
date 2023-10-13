package com.intelliedu.intelliedu.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.entity.MindMap;

/** AIService */
@Service
public class AIService {

	private SSLSocket socket;
	private InputStream input;
  private OutputStream output;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${python.port}")
	private Integer port;

	public String request(String message) {
		try {
			startConnection();

			String response = sendMessage(objectMapper.writeValueAsString(
				Map.of(
					"message", message	
				)
			));

			stopConnection();

			return response;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
 	}

	public String request(Document document, MindMap mindMap) {
		try {
			startConnection();

			String response = sendMessage(objectMapper.writeValueAsString(
				Map.of(
					Document.class.toString(), document.getContent(),
					MindMap.class.toString(), objectMapper.writeValueAsString(mindMap.getContent())
				)
			));

			stopConnection();

			return response;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
 	}

  private void startConnection() throws IOException {
		socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("localhost", port);
		input = socket.getInputStream();
    output = socket.getOutputStream();
	}

  private String sendMessage(String message) throws IOException {
		output.write(message.getBytes());
		return new String(input.readAllBytes());
  }

  private void stopConnection() throws IOException {
		input.close();
		output.close();
		socket.close();
	}
}
