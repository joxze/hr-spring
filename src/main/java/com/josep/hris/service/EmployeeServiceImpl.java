package com.josep.hris.service;

import com.josep.hris.entity.Employee;
import com.josep.hris.repository.EmployeePagingRepository;
import com.josep.hris.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeePagingRepository employeeRepository;

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> findAll(Pageable var1) {
        return employeeRepository.findAll(var1);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee delete(Long id) {
        Employee employee = findById(id);
        if (employee != null) {
            employeeRepository.delete(employee);
            return employee;
        }
        return null;
    }
}
