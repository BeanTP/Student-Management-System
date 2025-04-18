package com.tdtu.student_management_system.tuition.service;

import com.tdtu.student_management_system.subject.entity.CourseOffering;
import com.tdtu.student_management_system.subject.repository.CourseOfferingRepository;
import com.tdtu.student_management_system.tuition.dto.TuitionFeeDTO;
import com.tdtu.student_management_system.tuition.entity.TuitionFee;
import com.tdtu.student_management_system.tuition.repository.TuitionFeeRepository;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TuitionFeeService {
    @Autowired
    TuitionFeeRepository tuitionFeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    private final double CREDIT_COST = 350000;

    public List<TuitionFeeDTO> calculateAllFees(String semester) {
        List<User> students = userRepository.findByRole(User.Role.STUDENT);
        List<TuitionFeeDTO> results = new ArrayList<>();

        for (User student : students) {
            String userId = student.getUserId();

            List<CourseOffering> offerings = courseOfferingRepository.findBySemesterAndStudentsUserId(semester, userId);

            int totalCredit = offerings.stream()
                    .mapToInt(o -> o.getSubject().getCredit())
                    .sum();

            double amount = totalCredit * CREDIT_COST;

            TuitionFee fee = new TuitionFee();
            fee.setStudentId(userId);
            fee.setSemester(semester);
            fee.setTotalCredit(totalCredit);
            fee.setAmount(amount);
            tuitionFeeRepository.save(fee);

            TuitionFeeDTO dto = new TuitionFeeDTO();
            dto.setStudentId(userId);
            dto.setSemester(semester);
            dto.setTotalCredit(totalCredit);
            dto.setAmount(amount);
            results.add(dto);
        }

        return results;
    }

    public List<TuitionFeeDTO> getStudentFees(String userId) {
        return tuitionFeeRepository.findByStudentId(userId).stream().map(fee -> {
            TuitionFeeDTO dto = new TuitionFeeDTO();
            dto.setStudentId(fee.getStudentId());
            dto.setSemester(fee.getSemester());
            dto.setTotalCredit(fee.getTotalCredit());
            dto.setAmount(fee.getAmount());
            dto.setPair(fee.isPair());
            return dto;
        }).collect(Collectors.toList());
    }

    public String confirmPayment(Authentication auth, String semester){
        TuitionFee tuition = getTuitionByStudent(auth.getName(), semester);
        if(tuition.isPair()){
            return "Tuition of this semester already paid!";
        }
        tuition.setPair(true);
        tuitionFeeRepository.save(tuition);
        return "Successfully payment!";
    }

    public byte[] generateQRCodeForTuition(String studentId) throws Exception{
        String baseUrl = "http://localhost:8080/tuition/confirm-payment/";
        String qrContent = baseUrl + studentId;
        return QRCodeGenerator.getQRCodeByAsByteArray(qrContent, 300, 300);
    }

    public TuitionFee getTuitionByStudent(String studentId, String semester){
        return tuitionFeeRepository.findByStudentIdAndSemester(studentId, semester)
                .orElseThrow(() -> new RuntimeException("Student don't have any course in this semester"));
    }
    public List<TuitionFeeDTO> getAllTuitionFees() {
        List<TuitionFee> fees = tuitionFeeRepository.findAll();
        return fees.stream().map(fee -> {
            TuitionFeeDTO dto = new TuitionFeeDTO();
            dto.setStudentId(fee.getStudentId());
            dto.setSemester(fee.getSemester());
            dto.setTotalCredit(fee.getTotalCredit());
            dto.setAmount(fee.getAmount());
            return dto;
        }).collect(Collectors.toList());
    }
    public List<TuitionFee> getTuitionFeesBySemester(String semester) {
        return tuitionFeeRepository.findBySemester(semester);
    }
}
