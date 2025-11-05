package com.tdtu.student_management_system.classes.controller;

import com.tdtu.student_management_system.classes.dto.ClassDTO;
import com.tdtu.student_management_system.classes.entity.Class;
import com.tdtu.student_management_system.classes.service.ClassService;
import com.tdtu.student_management_system.user.dto.UserDTO;
import com.tdtu.student_management_system.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClassPageController {
    @Autowired
    private ClassService classService;

    @GetMapping("/class/create-class")
    public String createClass(){
        return "create-class";
    }

    @GetMapping("/class/all-class")
    public String viewAllClasses(Model model) {
        List<Class> classes = classService.getAllClass();
        model.addAttribute("classes", classes);
        return "all-class";
    }

    @GetMapping("/class/filter")
    public String filterClass(@RequestParam String type, @RequestParam long value, Model model) {
        List<Class> filteredClass;
        filteredClass = List.of(classService.getClassById(value));

        model.addAttribute("classes", filteredClass);
        return "all-class :: tableContent"; // Trả về phần tbody
    }

    @GetMapping("/class/edit/{classId}")
    public String showEditForm(@PathVariable long classId, Model model) {
        Class cls = classService.getClassById(classId);
        if (cls == null) {
            return "redirect:/class/all-class";
        }
        model.addAttribute("class", cls);
        return "edit-class";
    }

    @PostMapping("/class/edit/{classId}")
    public String updateUser(@PathVariable long classId, @ModelAttribute("class") ClassDTO dto,
                             RedirectAttributes redirectAttributes) {
        try {
            classService.updateClass(classId, dto);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:/class/all-class";
    }

}
