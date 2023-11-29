package com.intelliedu.intelliedu.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelliedu.intelliedu.dto.QuestionDtoDetail;
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

  public String checkMindMap(Document document, MindMap mindMap) {
    return request(
      toMessage(
        Map.of(
          "Request", "Check Mindmap",
          "Document", document.getContent(),
          "MindMap", toMessage(mindMap.getContent().get("main"))
        )
      )
    );
 	}

  public List<QuestionDtoDetail> generateQuestion(Document document) {
    try {
      return objectMapper.readValue(
        request(toMessage(
          Map.of(
            "Request", "Generate Question", 
            "Document", document.getContent()
          )
        )).replace("'", "\""),
        new TypeReference<List<QuestionDtoDetail>>(){}
      );
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private String toMessage(Map<String, Object> message) {
    try {
      return objectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing data");
    }
  }

  private String toMessage(Object message) {
    try {
      return objectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing data");
    }
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
