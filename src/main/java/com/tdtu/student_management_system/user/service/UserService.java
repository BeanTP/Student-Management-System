package com.tdtu.student_management_system.user.service;

import com.tdtu.student_management_system.user.dto.LoginDTO;
import com.tdtu.student_management_system.user.dto.UserDTO;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login successful";
    }

    public User registerUser(UserDTO userDTO){
        String userId = generateUserId(userDTO);
        userDTO.setUserId(userId);

        if(userRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User ID already exists.");
        }


        User user = new User();
        user.setId(userDTO.getId());
        user.setUserId(userDTO.getUserId());
        user.setFullName(userDTO.getFullName());
        user.setDoBirth(userDTO.getDoBirth());
        user.setCccd(userDTO.getCccd());
        user.setPhoneNum(userDTO.getPhoneNum());
        user.setDepartmentCode(userDTO.getDepartmentCode());
        user.setYearOfAdmission(userDTO.getYearOfAdmission());
        user.setRole(User.Role.TEMP);

        String encodedPassword = passwordEncoder.encode(userDTO.getCccd());
        user.setPassword(encodedPassword);

        String email = generateEmail(userDTO);
        user.setEmail(email);

        return userRepository.save(user);
    }

    public User updateUser(String userId, UserDTO userDTO){
        Optional<User> existingUser = userRepository.findByUserId(userId);
        if(existingUser.isPresent()){
            User user = existingUser.get();

            if(userDTO.getFullName() != null && !userDTO.getFullName().isEmpty() && !userDTO.getFullName().equals(user.getFullName()))
                user.setFullName(userDTO.getFullName());

            if(userDTO.getDoBirth() != null && !userDTO.getDoBirth().equals(user.getDoBirth()))
                user.setDoBirth(userDTO.getDoBirth());

            if(userDTO.getPhoneNum() != null && !userDTO.getPhoneNum().isEmpty() && !userDTO.getPhoneNum().equals(user.getPhoneNum()))
                user.setPhoneNum(userDTO.getPhoneNum());

            if(userDTO.getDepartmentCode() != user.getDepartmentCode())
                user.setDepartmentCode(userDTO.getDepartmentCode());

            if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty() && !userDTO.getPassword().equals(user.getPassword()))
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            if(userDTO.getRole() != null)
                user.setRole(userDTO.getRole());

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }

    public void deleteUser(String userId){
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String userId){
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }

    public List<User> getUserByDepartmentCode(int departmentCode){
        List<User> users = userRepository.findByDepartmentCode(departmentCode);
        if(users.isEmpty()){throw new RuntimeException("No user found in this department.");}
        return users;
    }

    public List<User> getUserByRole(User.Role role){
        List<User> users = userRepository.findByRole(role);
        if(users.isEmpty()){throw new RuntimeException("No user found in this role.");}
        return users;
    }

    public User getUserByRoleAndUserId(User.Role role, String userId){
        Optional<User> user = userRepository.findByRoleAndUserId(role, userId);
        if(user.isEmpty()){throw new RuntimeException("User not found with role: " + role + " and userId: " + userId);}
        return user.get();
    }

    private String generateUserId(UserDTO user){
        String departmentCode = user.getDepartmentCode() + "";
        String yearOfAdmission = user.getYearOfAdmission() % 100 + "";

        long userCount = userRepository.count();
        long sequentialNum = userCount + 1;

        String formattedSequentialNum = String.format("%05d", sequentialNum);

        return departmentCode + yearOfAdmission + formattedSequentialNum;
    }

    private String generateEmail(UserDTO user){
        String userId = user.getUserId();

        String fullName = Normalizer.normalize(user.getFullName(), Normalizer.Form.NFD);
        fullName = fullName.replaceAll("[^\\p{ASCII}]", "");
        fullName = fullName.toLowerCase().replaceAll("\\s+", "");

        return userId + "_" + fullName + "@student.tdtu.edu.vn";
    }
}
