package com.paas.app.controller;

import com.paas.app.dto.CommonResult;
import com.paas.app.entity.Student;
import com.paas.app.enums.ErrorsEnum;
import com.paas.app.exception.BusinessException;
import com.paas.app.service.StudentService;
import com.paas.app.util.ResolvingExcelUtil;
import com.paas.app.util.UploadExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ofPattern;

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

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "fileinfo", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String path = UploadExcelUtil.getFileInfo(request, response, file);
    }

    @RequestMapping(value = "/resolveExcel", method = RequestMethod.POST)
    public void resolveExcel(@RequestParam(value = "fileinfo", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path= UploadExcelUtil.getFileInfo(request, response, file);
        File file1=new File(path);
        ResolvingExcelUtil.analysisFile((MultipartFile) file1);
    }

    @RequestMapping(value = "/getExcel", method = RequestMethod.POST)
    private CommonResult getExcel(){
        CommonResult result;
        try {
            result = studentService.queryAllStudent();
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
