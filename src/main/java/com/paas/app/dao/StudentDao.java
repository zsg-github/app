package com.paas.app.dao;

import com.paas.app.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao {

    Student queryStudentById(@Param("studentId") int studentId);
}
