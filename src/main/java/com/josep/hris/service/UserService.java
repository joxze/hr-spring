package com.josep.hris.service;

import com.josep.hris.bean.form.RegistrationForm;
import com.josep.hris.entity.Users;

public interface UserService {
    void save(RegistrationForm registrationForm);
    Users findByUsername(String username);
}
