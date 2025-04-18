    package com.tdtu.student_management_system.schedule.controller;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.security.core.Authentication;

    @Controller
    public class ExamSchedulePageController {

        @GetMapping("/examschedule")
        public String redirectBasedOnRole(Authentication authentication) {
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "schedule_admin";
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_STUDENT"))) {
                return "schedule_student";
            } else {
                return "redirect:/access-denied";
            }
        }

    }
