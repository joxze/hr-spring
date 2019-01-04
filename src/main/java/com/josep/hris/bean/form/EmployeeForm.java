package com.josep.hris.bean.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class EmployeeForm {
    @Size(max = 50)
    private String nik;

    @Size(max = 50)
    private String barcode;

    @NotEmpty
    @Size(min = 3, max = 255)
    private String fullName;

    @Size(max = 500)
    private String address;

    private String phone;

    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resignDate;

    private Integer status;
}
