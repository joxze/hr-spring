package com.josep.hris.service;

import com.josep.hris.bean.form.RegistrationForm;
import com.josep.hris.entity.Users;
import com.josep.hris.repository.RoleRepository;
import com.josep.hris.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(RegistrationForm registrationForm) {
        Users users = new Users();
        users.setUsername(registrationForm.getUsername());
        if (registrationForm.getPassword() != null)
            users.setPassword(bCryptPasswordEncoder.encode(registrationForm.getPassword()));
        users.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(users);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
