package com.paas.app.dao;

import com.paas.app.entity.Employee;
import com.paas.app.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EmployeeDao {

    Student queryStudentById(@Param("studentId") int studentId);


    ArrayList<Employee> queryAllEmployee();


    int insertEmp(Employee employee);
}
