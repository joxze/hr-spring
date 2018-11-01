package com.josep.hris.repository;

import com.josep.hris.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    public Users findByUsername(String username);
}
