package com.paas.app.service.impl;

import com.paas.app.dao.StudentDao;
import com.paas.app.dto.CommonResult;
import com.paas.app.entity.Student;
import com.paas.app.enums.ErrorsEnum;
import com.paas.app.exception.BusinessException;
import com.paas.app.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Resource
    StudentDao studentDao;

    @Override
    public CommonResult getStudentById(int studentId) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Student student = studentDao.queryStudentById(studentId);
        //如果查询结果为空，抛出异常
        if(student == null){
            logger.error("学生不存在");
            BusinessException.throwMessage(ErrorsEnum.OBJECT_NULL);
        }
        //封装学生信息到Map中
        Map<String,Object> modelMap = new HashMap<>();
        modelMap.put("studentId",student.getId());
        modelMap.put("studentName", student.getStudentName());
        modelMap.put("gender", student.getGender());
        modelMap.put("phone",student.getPhone());
        modelMap.put("createTime",dateTimeFormatter.format(student.getCreateTime()));

        //封装返回体
        CommonResult resultModel = new CommonResult();
        resultModel.setState(ErrorsEnum.SUCCESS.getErrorCode());
        resultModel.setSuccess(true);
        resultModel.setMessage(ErrorsEnum.SUCCESS.getMessage());
        resultModel.addAttribute("student", modelMap);
        return resultModel;
    }
}
