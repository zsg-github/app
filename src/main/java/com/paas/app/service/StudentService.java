package com.paas.app.service;

import com.paas.app.dto.CommonResult;
import com.paas.app.entity.Student;

import java.util.ArrayList;

public interface StudentService {
    CommonResult getStudentById(int studentId) throws Exception;

    CommonResult queryAllStudent() throws Exception;
}
