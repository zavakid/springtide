package com.zavakid.springtide.example.todolist.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author zebin.xuzb 2014-10-20
 */
public class TestForm {
    @NotNull
    private String userName;

    @Email
    private String email;

    @Length(max = 10, min = 1)
    private String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
