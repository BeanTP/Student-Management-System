package com.tdtu.student_management_system.subject.controller;

import com.tdtu.student_management_system.subject.dto.SubjectDTO;
import com.tdtu.student_management_system.subject.entity.Subject;
import com.tdtu.student_management_system.subject.service.SubjectService;
import com.tdtu.student_management_system.user.dto.UserDTO;
import com.tdtu.student_management_system.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SubjectPageController {
    @Autowired
    SubjectService subjectService;

    @GetMapping("/subjects/all-subject")
    public String allSubject(Model model){
        List<Subject> subs = subjectService.getAllSubject();
        model.addAttribute("subjects", subs);
        return "all-subject";
    }

    @GetMapping("/subjects/filter")
    public String filterSubject(@RequestParam String type, @RequestParam long value, Model model) {
        List<Subject> filteredSubject;

        filteredSubject = List.of(subjectService.getSubjectById(value));

        model.addAttribute("subjects", filteredSubject);
        return "all-subject :: tableContent";
    }

    @GetMapping("/subjects/create-subject")
    public String createSubject(){
        return "create-subject";
    }

    @GetMapping("/subjects/update/{subjectId}")
    public String showEditForm(@PathVariable long subjectId, Model model) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject == null) {
            return "redirect:/subjects/all-subject";
        }
        model.addAttribute("subject", subject);
        return "edit-subject";
    }

    @PostMapping("/subjects/update/{subjectId}")
    public String updateSubject(@PathVariable long subjectId, @ModelAttribute("subject") SubjectDTO dto,
                             RedirectAttributes redirectAttributes) {
        try {
            subjectService.updateSubject(subjectId, dto);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:/subjects/all-subject";
    }

    @GetMapping("/subjects/delete/{subjectId}")
    public String deleteUser(@PathVariable long subjectId, RedirectAttributes redirectAttributes) {
        try {
            subjectService.delSubject(subjectId);
            redirectAttributes.addFlashAttribute("message", "Xoá người dùng thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Xoá thất bại: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:/subjects/all-subject";

    }
}
