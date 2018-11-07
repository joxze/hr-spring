package com.josep.hris.web;

import com.josep.hris.entity.Employee;
import com.josep.hris.helpers.Paginate;
import com.josep.hris.repository.EmployeePagingRepository;
import com.josep.hris.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private AuthService auth;

    @Autowired
    private EmployeePagingRepository employeePaging;

    @GetMapping("/")
    public String index(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) String sortType,
            Model model,
            Principal principal) {

        Sort sortQuery = new Sort(Sort.Direction.ASC, "id");
        if (sort != null) {
            if ("asc".equals(sortType.toLowerCase()))
                sortQuery = new Sort(Sort.Direction.ASC, sort);
            else
                sortQuery = new Sort(Sort.Direction.DESC, sort);
        }

        Page<Employee> employees = employeePaging.findAll(new PageRequest((page-1), size, sortQuery));
        Paginate paginate = new Paginate(page, employees.getTotalPages(), employees.getTotalElements());

        model.addAttribute("auth", auth.getIdentity(principal));
        model.addAttribute("employees", employees);
        model.addAttribute("page", page);
        model.addAttribute("sort", sort);
        model.addAttribute("sortType", sortType);
        model.addAttribute("paginate", paginate);
        return "employee/index";
    }

    @GetMapping("pdf")
    public String
}
