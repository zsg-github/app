package com.paas.app.service;

import com.paas.app.dto.CommonResult;

public interface StudentService {
    CommonResult getStudentById(int studentId) throws Exception;
}
