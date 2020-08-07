package com.paas.app.controller;


import com.paas.app.dto.CommonResult;
import com.paas.app.enums.ErrorsEnum;
import com.paas.app.exception.BusinessException;
import com.paas.app.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Resource
    StudentService studentService;
    @RequestMapping(value = "/getStudentById", method = RequestMethod.POST)
    private CommonResult getStudentInfoBy(int studentId){
        CommonResult result;
        try {
            result = studentService.getStudentById(studentId);
        } catch (BusinessException e) {
            logger.error("/admin/getStudentInfoBy 异常，异常原因：" + e);
            e.printStackTrace();
            result = new CommonResult();
            result.setState(Integer.parseInt(e.getErrCode()));
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("/admin/getStudentInfoBy 底层处理异常，异常原因", e.getMessage());
            e.printStackTrace();
            result = new CommonResult();
            result.setState(ErrorsEnum.COMMON_ERROR.getErrorCode());
            result.setSuccess(false);
            result.setMessage("系统内部错误");
        }
        return result;
    }



}
