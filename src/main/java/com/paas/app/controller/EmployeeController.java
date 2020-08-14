package com.paas.app.controller;

import com.paas.app.dto.CommonResult;
import com.paas.app.entity.Employee;
import com.paas.app.enums.ErrorsEnum;
import com.paas.app.exception.BusinessException;
import com.paas.app.service.EmployeeService;
import com.paas.app.util.ResolvingExcelUtil;
import com.paas.app.util.UploadExcelUtil;
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


@RestController
@RequestMapping("/admin")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Resource
    EmployeeService employeeService;

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

    @RequestMapping(value = "/getExcelFile", method = RequestMethod.POST)
    private CommonResult getExcel(){
        CommonResult result;
        try {
            result = employeeService.queryAllEmployee();
        } catch (BusinessException e) {
            logger.error("/admin/getExcelFile 异常，异常原因：" + e);
            e.printStackTrace();
            result = new CommonResult();
            result.setState(Integer.parseInt(e.getErrCode()));
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("/admin/getExcelFile 底层处理异常，异常原因", e.getMessage());
            e.printStackTrace();
            result = new CommonResult();
            result.setState(ErrorsEnum.COMMON_ERROR.getErrorCode());
            result.setSuccess(false);
            result.setMessage("系统内部错误");
        }
        return result;


    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    private CommonResult addEmployee(Employee employee){
        CommonResult result;
        try {
            result = employeeService.addEmployee(employee);
        } catch (BusinessException e) {
            logger.error("/admin/ddEmployee 异常，异常原因：" + e);
            e.printStackTrace();
            result = new CommonResult();
            result.setState(Integer.parseInt(e.getErrCode()));
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("/admin/addEmployee 底层处理异常，异常原因", e.getMessage());
            e.printStackTrace();
            result = new CommonResult();
            result.setState(ErrorsEnum.COMMON_ERROR.getErrorCode());
            result.setSuccess(false);
            result.setMessage("系统内部错误");
        }
        return result;


    }




}
