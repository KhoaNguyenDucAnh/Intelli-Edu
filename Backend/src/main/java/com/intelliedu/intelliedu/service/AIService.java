package com.intelliedu.intelliedu.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

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

	private Socket socket;
	private BufferedReader input;
  private PrintWriter output;

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
		socket = new Socket("localhost", port);
		output = new PrintWriter(socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

  private String sendMessage(String message) throws IOException {
		output.println(message);
		return input.readLine();
  }

  private void stopConnection() throws IOException {
		input.close();
		output.close();
		socket.close();
	}
}
