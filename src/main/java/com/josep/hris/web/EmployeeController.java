package com.josep.hris.web;

import com.josep.hris.bean.form.EmployeeForm;
import com.josep.hris.entity.Employee;
import com.josep.hris.helpers.Paginate;
import com.josep.hris.repository.EmployeePagingRepository;
import com.josep.hris.repository.EmployeeRepository;
import com.josep.hris.service.AuthService;
import com.josep.hris.service.EmployeeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private AuthService auth;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String index(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) String sortType,
            Model model,
            Principal principal) {

        Sort sortQuery = new Sort(Sort.Direction.ASC, "id");
        if (sort != null) {
            if ("asc".equalsIgnoreCase(sortType))
                sortQuery = new Sort(Sort.Direction.ASC, sort);
            else
                sortQuery = new Sort(Sort.Direction.DESC, sort);
        }

        Page<Employee> employees = employeeService.findAll(PageRequest.of((page-1), size, sortQuery));
        Paginate paginate = new Paginate(page, employees.getTotalPages(), employees.getTotalElements());

        model.addAttribute("auth", auth.getIdentity(principal));
        model.addAttribute("employees", employees);
        model.addAttribute("page", page);
        model.addAttribute("sort", sort);
        model.addAttribute("sortType", sortType);
        model.addAttribute("paginate", paginate);
        return "employee/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        EmployeeForm employee = new EmployeeForm();
        model.addAttribute("employee", employee);
        return "employee/create";
    }

    @PostMapping("create")
    public String doCreate(@ModelAttribute("employee") EmployeeForm employeeForm, Model model, BindingResult result) {
        return "employee/create";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes red) {
        Employee employee = employeeService.delete(id);
        if (employee != null) {
        }

        return "redirect: ";
    }

    @GetMapping("pdf")
    @ResponseBody
    public void pdf(HttpServletResponse response) throws JRException, IOException {
        Map<String,Object> params = new HashMap<>();
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/report_employee_list_pdf.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((List<Employee>) employeeService.findAll());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=employee-list.pdf");
        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping("xls")
    @ResponseBody
    public void xls(HttpServletResponse response) throws JRException, IOException {
        Map<String,Object> params = new HashMap<>();
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/report_employee_list_xlsx.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((List<Employee>) employeeService.findAll());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
        response.setHeader("Content-Disposition", "attachment; filename=employee-list.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        JRXlsxExporter xlsExporter = new JRXlsxExporter(DefaultJasperReportsContext.getInstance());
        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        xlsExporter.exportReport();

    }
}
