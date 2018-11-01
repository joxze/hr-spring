package com.josep.hris.bean.form;

import com.josep.hris.constraint.FieldMatch;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class RegistrationForm {
    @NotEmpty
    @Size(max = 255)
    private String username;

    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String passwordConfrim;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfrim() {
        return passwordConfrim;
    }

    public void setPasswordConfrim(String passwordConfrim) {
        this.passwordConfrim = passwordConfrim;
    }

}
