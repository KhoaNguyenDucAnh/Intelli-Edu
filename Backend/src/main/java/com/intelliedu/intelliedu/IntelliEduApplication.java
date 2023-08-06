package com.intelliedu.intelliedu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntelliEduApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(IntelliEduApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

  }
}
