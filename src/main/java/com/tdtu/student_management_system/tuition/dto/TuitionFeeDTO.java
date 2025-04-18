package com.tdtu.student_management_system.tuition.dto;

public class TuitionFeeDTO {
    private String studentId;
    private String semester;
    private int totalCredit;
    private double amount;
    private boolean isPair;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String userId) {
        this.studentId = userId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPair() {
        return isPair;
    }

    public void setPair(boolean pair) {
        isPair = pair;
    }
}
