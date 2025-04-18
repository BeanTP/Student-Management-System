package com.tdtu.student_management_system.user.controller;

import com.tdtu.student_management_system.user.dto.ChangePasswordRequest;
import com.tdtu.student_management_system.user.dto.LoginDTO;
import com.tdtu.student_management_system.user.dto.UserDTO;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO){
        try {
            User registeredUser = userService.registerUser(userDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e){
            System.err.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        try {
            String result = userService.login(loginDTO);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserId = authentication.getName();

        if(!curUserId.equals(userDTO.getUserId()) && !authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            return new ResponseEntity<>("You are not authorized to update this user's information", HttpStatus.FORBIDDEN);
        }

        User updateUser = userService.updateUser(userId, userDTO);

        if(updateUser != null){
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        try{
            userService.changePassword(userId, request.getCurrPass(), request.getNewPass());
            return ResponseEntity.ok("Password changed successfully.");
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        if (!currentUserId.equals(userId) && !authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return new ResponseEntity<>("You are not authorized to delete this user's information", HttpStatus.FORBIDDEN);
        }

        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully!");
        } catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //lấy danh sách tất cả user
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //lấy user theo id
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId){return userService.getUserById(userId);}

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currUserId = auth.getName();

        User currUser = getUserById(currUserId);

        UserDTO dto = new UserDTO();
        dto.setUserId(currUser.getUserId());
        dto.setFullName(currUser.getFullName());
        dto.setRole(currUser.getRole());
        dto.setCccd(currUser.getCccd());
        dto.setEmail(currUser.getEmail());
        dto.setDepartmentCode(currUser.getDepartmentCode());
        dto.setDoBirth(currUser.getDoBirth());
        dto.setYearOfAdmission(currUser.getYearOfAdmission());
        dto.setPhoneNum(currUser.getPhoneNum());

        return ResponseEntity.ok(dto);
    }

    //Lấy dsach user theo departmentCode
    @GetMapping("/department/{departmentCode}")
    public ResponseEntity<?> getUsersByDepartmentCode(@PathVariable int departmentCode){
        try{
            List<User> users = userService.getUserByDepartmentCode(departmentCode);
            return ResponseEntity.ok(users);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Lấy dsach user theo role
    @GetMapping("/role/{role}")
    public ResponseEntity<?> getUserByRole(@PathVariable User.Role role){
        try{
            List<User> users = userService.getUserByRole(role);
            return ResponseEntity.ok(users);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Lấy user theo role và userId
    @GetMapping("/role/{role}/userId/{userId}")
    public ResponseEntity<?> getUserByRoleAndUserId(@PathVariable User.Role role, @PathVariable String userId){
        try{
            User user = userService.getUserByRoleAndUserId(role, userId);
            return ResponseEntity.ok(user);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
