package com.gangE.DongoShop.controller;


import com.gangE.DongoShop.dto.EmailAndPwd;
import com.gangE.DongoShop.dto.UserToken;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = " Login Controller", description = "로그인")
@RestController
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @Operation(summary = "login", description = "로그인 토큰을 받습니다.")
    @PostMapping("/login")
    public UserToken loginUser(@RequestBody EmailAndPwd authentication) {
        return loginService.loginUser(authentication);
    }
    @Operation(summary = "register",
            description =
                    "회원가입을 합니다." +
                    "이때 name은 중복일 수가 없습니다." +
                    "이때 email은 중복일 수가 없습니다."
    )
    @PostMapping("/register")
    public UserToken registerUser(@RequestBody Customer customer) {

        UserToken success = loginService.registerUser(customer);
      return success;
    }


}
