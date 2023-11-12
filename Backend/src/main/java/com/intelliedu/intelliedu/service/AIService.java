package com.intelliedu.intelliedu.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.entity.MindMap;

/** AIService */
@Service
public class AIService {

	private Socket socket;
	private InputStream input;
  private OutputStream output;

	@Value("${python.port}")
	private Integer port;

  private ObjectMapper objectMapper = new ObjectMapper();
  
  public String request(Document document, MindMap mindMap) throws JsonProcessingException {
    return request(
      objectMapper.writeValueAsString(
        Map.of(
          Document.class.getSimpleName(), document.getContent(),
          MindMap.class.getSimpleName(), objectMapper.writeValueAsString(mindMap.getContent())
        )
      )
    );
 	}
	
  private String request(String message) {
		try {
			startConnection();
      String response = sendMessage(message);
      stopConnection();
      return response;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
 	}

  private void startConnection() throws IOException {
		socket = new Socket("localhost", port); 
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
