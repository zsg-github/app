package com.paas.app.service;

import com.paas.app.dto.CommonResult;
import com.paas.app.entity.Employee;
import com.paas.app.entity.Student;

import java.util.ArrayList;

public interface EmployeeService {

    CommonResult queryAllEmployee() throws Exception;

    CommonResult addEmployee(Employee employee)  throws Exception;
}
