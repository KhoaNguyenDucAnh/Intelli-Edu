package com.intelliedu.intelliedu.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.springframework.stereotype.Service;

/** AIService */
@Service
public class AIService {

  private ProcessBuilder processBuilder;

  public void doc2query(String doc) {
    try {
      processBuilder = new ProcessBuilder("ipython");
      processBuilder.directory(new File("/media/Data/Project/Intelli-Edu/Backend/AI/"));

      Process process = processBuilder.start();

      InputStream inputStream = process.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      OutputStream outputStream = process.getOutputStream();
      PrintWriter writer = new PrintWriter(outputStream, true);

      // Create a separate thread to continuously read IPython's output
      Thread outputReaderThread = new Thread(() -> {
        try {
          String line;
          while ((line = reader.readLine()) != null) {
            System.out.println("IPython output: " + line);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      
      outputReaderThread.start();

      writer.println("print(0)");

      writer.println("import doc2query");

      writer.println("print(1)");

      writer.println(String.format("doc2query.create_queries(\"%s\")", doc));

      writer.println("print(2)");

      writer.println("exit()");

      process.waitFor();

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
