package com.josep.hris;

import com.josep.hris.entity.Employee;
import com.josep.hris.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeJpaTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void contextLoads() {
        assertEquals("test a", "test a");
    }

    @Test
    public void testInsertDatabaseEmployee() {
        Employee employee = new Employee();
        employee.setFullName("Yhose Christ Widtantio Test");
        employee.setNik("TDI-00001");
        employee.setBarcode("TDI-00001");
        employee.setJoinDate(new Date());

        entityManager.persist(employee);
        entityManager.flush();

        Employee employeeFind = employeeRepository.findByNikAndFullName("TDI-00001", "Yhose Christ Widtantio Test");
        assertEquals(employee.getFullName(), employeeFind.getFullName());
        assertEquals(employee.getNik(), employeeFind.getNik());
        assertEquals(employee.getBarcode(), employeeFind.getBarcode());
        assertEquals(employee.getJoinDate(), employeeFind.getJoinDate());


    }
}
