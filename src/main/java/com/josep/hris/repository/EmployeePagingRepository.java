package com.josep.hris.repository;

import com.josep.hris.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeePagingRepository extends PagingAndSortingRepository<Employee, Long> {
}
