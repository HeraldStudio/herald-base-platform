/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.sso.core.domain;

/**
 * Represents the user of a SEU student
 * @author rAy <predator.ray@gmail.com>
 */
public class StudentUser {
    
    private int cardNumber;
    
    private String studentId;
    
    private String password;
    
    private String fullName;

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
