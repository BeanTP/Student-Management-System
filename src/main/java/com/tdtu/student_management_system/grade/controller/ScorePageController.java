package com.tdtu.student_management_system.grade.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScorePageController {

    @GetMapping("/score")
    public String redirectBasedOnRole(Authentication authentication) {
        boolean isAdminOrTeacher = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_TEACHER"));

        if (isAdminOrTeacher) {
            return "score_admin";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_STUDENT"))) {
            return "score_student";
        } else {
            return "redirect:/access-denied";
        }
    }
}
