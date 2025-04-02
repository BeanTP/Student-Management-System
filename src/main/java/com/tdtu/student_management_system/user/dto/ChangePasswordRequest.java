package com.tdtu.student_management_system.user.dto;

public class ChangePasswordRequest {
    private String currPass;
    private String newPass;

    public String getCurrPass() {
        return currPass;
    }

    public void setCurrPass(String currPass) {
        this.currPass = currPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
