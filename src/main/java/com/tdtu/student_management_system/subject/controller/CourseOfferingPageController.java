package com.tdtu.student_management_system.subject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class CourseOfferingPageController {

    @GetMapping("/schedule")
    public String redirectBasedOnRole(Authentication authentication) {
            return "schedule";
    }
}
