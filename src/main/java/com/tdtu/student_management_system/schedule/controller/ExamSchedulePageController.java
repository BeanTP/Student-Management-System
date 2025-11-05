    package com.tdtu.student_management_system.schedule.controller;

    import com.tdtu.student_management_system.schedule.dto.ExamScheduleDTO;
    import com.tdtu.student_management_system.schedule.service.ExamScheduleService;
    import com.tdtu.student_management_system.subject.repository.CourseOfferingRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.security.core.Authentication;

    @Controller
    public class ExamSchedulePageController {

        @Autowired
        ExamScheduleService examScheduleService;
        @Autowired
        CourseOfferingRepository courseOfferingRepository;

//        @GetMapping("/exams-schedule")
//        public String redirectBasedOnRole(Authentication authentication) {
//            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
//                return "schedule_admin";
//            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_STUDENT"))) {
//                return "schedule_student";
//            } else {
//                return "redirect:/access-denied";
//            }
//        }

        @GetMapping("/exam-schedule")
        public String showExamSchedulePage(Model model) {
            model.addAttribute("schedules", examScheduleService.getAllSchedules());
            model.addAttribute("examScheduleDTO", new ExamScheduleDTO());
            model.addAttribute("courseOfferings", courseOfferingRepository.findAll()); // để điền select box
            return "exam-schedule";
        }


    }
