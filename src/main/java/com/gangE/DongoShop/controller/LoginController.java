package com.gangE.DongoShop.controller;


import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginUser(@RequestBody Customer authentication) {
        return loginService.loginUser(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

        ResponseEntity<String> success = loginService.registerUser(customer);
      return success;
    }


}
