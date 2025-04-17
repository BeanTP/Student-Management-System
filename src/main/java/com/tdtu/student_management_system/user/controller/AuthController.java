package com.tdtu.student_management_system.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(){
        return "forgot-password";
    }

    @GetMapping("/settings-account")
    public String settingsAcc(){
        return "settings-account";
    }

    @GetMapping("/change-password")
    public String changePassword(){
        return "change-password";
    }

    @GetMapping("/register")
    public String createUser(){
        return "create-user";
    }
}
