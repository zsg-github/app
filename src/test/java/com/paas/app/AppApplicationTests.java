package com.paas.app;

import com.paas.app.dao.EmployeeDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AppApplicationTests {

    @Resource
    EmployeeDao employeeDao;

    @Test
    void contextLoads() {

    }

}
