package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.service.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
@ResponseStatus(code = HttpStatus.OK)
public class AccountController {

  
}
