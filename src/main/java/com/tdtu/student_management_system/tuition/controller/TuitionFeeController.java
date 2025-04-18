package com.tdtu.student_management_system.tuition.controller;

import com.tdtu.student_management_system.tuition.dto.TuitionFeeDTO;
import com.tdtu.student_management_system.tuition.entity.TuitionFee;
import com.tdtu.student_management_system.tuition.service.TuitionFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tuition")
public class TuitionFeeController {
    @Autowired
    private TuitionFeeService tuitionFeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/calculate-fee/{semester}")
    public ResponseEntity<?> calAllFee(@PathVariable String semester){
        List<TuitionFeeDTO> result = tuitionFeeService.calculateAllFees(semester);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/{semester}")
    public ResponseEntity<List<TuitionFee>> getTuitionFeesBySemester(@PathVariable String semester) {
        List<TuitionFee> tuitionFees = tuitionFeeService.getTuitionFeesBySemester(semester);

        if (tuitionFees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tuitionFees);
    }
    @GetMapping("/student")
    public ResponseEntity<?> getStudentTuitionFee(Authentication authentication){
        String studentId = authentication.getName();
        List<TuitionFeeDTO> fees = tuitionFeeService.getStudentFees(studentId);
        return ResponseEntity.ok(fees);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/generate-code")
    public ResponseEntity<byte[]> generateQRCode(Authentication auth) throws Exception {
        String studentId = auth.getName();
        try{
            byte[] img = tuitionFeeService.generateQRCodeForTuition(studentId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(img, headers, HttpStatus.OK);
        }catch(Exception e){
            throw new Exception("Failed generation QR code with error: " + e.toString());
        }

    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/confirm-payment/{semester}")
    public ResponseEntity<String> confirmPayment(@PathVariable String semester, Authentication auth){
        String result = tuitionFeeService.confirmPayment(auth, semester);
        return ResponseEntity.ok(result);
    }
}
