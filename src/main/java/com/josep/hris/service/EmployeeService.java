package com.josep.hris.service;

import com.josep.hris.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Iterable<Employee> findAll();
    Page<Employee> findAll(Pageable var1);
    Employee findById(Long id);
    Employee delete(Long id);
}
