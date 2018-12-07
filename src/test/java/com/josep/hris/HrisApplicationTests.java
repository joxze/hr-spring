package com.josep.hris;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HrisApplicationTests {

    @Test
    public void contextLoads() {
        assertEquals("test a", "test a");
    }

    @Test
    public void validateWord() {
        assertEquals("testing", "testing");
    }

}
