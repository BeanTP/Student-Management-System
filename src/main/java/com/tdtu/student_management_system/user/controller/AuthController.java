package com.tdtu.student_management_system.user.controller;

import com.tdtu.student_management_system.user.dto.UserDTO;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(){
        return "forgot-password";
    }

    @GetMapping("/users/settings-account")
    public String settingsAcc(){
        return "settings-account";
    }

    @GetMapping("/users/change-password")
    public String changePassword(){
        return "change-password";
    }

    @GetMapping("/users/register")
    public String createUser(){
        return "create-user";
    }

    @GetMapping("/users/all-user")
    public String allUser(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "all-user";
    }

    @GetMapping("/users/filter")
    public String filterUser(@RequestParam String type, @RequestParam String value, Model model) {
        List<User> filteredUsers;

        switch (type) {
            case "userId":
                filteredUsers = List.of(userService.getUserById(value));
                break;
            case "departmentCode":
                filteredUsers = userService.getUserByDepartmentCode(Integer.parseInt(value));
                break;
            case "role":
                filteredUsers = userService.getUserByRole(User.Role.valueOf(value));
                break;
            default:
                filteredUsers = List.of();
        }

        model.addAttribute("users", filteredUsers);
        return "all-user :: tableContent"; // Trả về phần tbody
    }

    @GetMapping("/users/edit/{userId}")
    public String showEditForm(@PathVariable String userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/users/all-user";
        }
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/users/edit/{userId}")
    public String updateUser(@PathVariable String userId, @ModelAttribute("user") UserDTO userDto,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(userId, userDto);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:/users/all-user";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable String userId, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("message", "Xoá người dùng thành công!");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Xoá thất bại: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }
        return "redirect:/users/all-user";
    }


}
