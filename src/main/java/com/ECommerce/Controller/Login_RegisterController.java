package com.ECommerce.Controller;

import com.ECommerce.Entity.Customer;
import com.ECommerce.Entity.Dto.LoginDto;
import com.ECommerce.Entity.Dto.RegisterDto;
import com.ECommerce.Service.Login_RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Login_RegisterController {

    @Autowired
    private Login_RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(this.registerService.register(registerDto) ,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> register(@RequestBody LoginDto loginDto, @RequestHeader("token") String token){
        return new ResponseEntity<>(this.registerService.login(loginDto, token), HttpStatus.OK);
    }

}
