package com.josep.hris.web;

import com.josep.hris.entity.Employee;
import com.josep.hris.helpers.Paginate;
import com.josep.hris.repository.EmployeePagingRepository;
import com.josep.hris.service.AuthService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.InputStream;
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
    public void pdf() throws JRException {
//        File reportFile = new File("report/report_employee_list.jasper");
//        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
        InputStream employeeReportStream
                = getClass().getResourceAsStream("report/report_employee_list.jrxml");
        JasperReport jasperReport
                = JasperCompileManager.compileReport(employeeReportStream);
        
        if (jasperReport != null) {
            JRBeanCollectionDataSource listDeps = new JRBeanCollectionDataSource(null);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, null, listDeps);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput("employeeReport.pdf"));

            SimplePdfReportConfiguration reportConfig
                    = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("baeldung");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);

            exporter.exportReport();
        }
    }
}
