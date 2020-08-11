package com.paas.app.service.impl;

import com.paas.app.dao.StudentDao;
import com.paas.app.dto.CommonResult;
import com.paas.app.entity.Employee;
import com.paas.app.entity.Student;
import com.paas.app.enums.ErrorsEnum;
import com.paas.app.exception.BusinessException;
import com.paas.app.service.StudentService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

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
        if (student == null) {
            logger.error("学生不存在");
            BusinessException.throwMessage(ErrorsEnum.OBJECT_NULL);
        }
        //封装学生信息到Map中
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("studentId", student.getId());
        modelMap.put("studentName", student.getStudentName());
        modelMap.put("gender", student.getGender());
        modelMap.put("phone", student.getPhone());
        modelMap.put("createTime", dateTimeFormatter.format(student.getCreateTime()));

        //封装返回体
        CommonResult resultModel = new CommonResult();
        resultModel.setState(ErrorsEnum.SUCCESS.getErrorCode());
        resultModel.setSuccess(true);
        resultModel.setMessage(ErrorsEnum.SUCCESS.getMessage());
        resultModel.addAttribute("student", modelMap);
        return resultModel;
    }

    @Override
    public CommonResult queryAllStudent() throws Exception {
        DateTimeFormatter df = ofPattern("yyyy-MM-dd HH:mm:ss");
        //查询有多少条数据
        ArrayList<Employee> arrayList = studentDao.queryAllEmployee();
        //第一步：创建一个workbook对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部：在workbook中创建一个sheet对应Excel中的sheet
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        sheet.autoSizeColumn(0, true);
        //第三部：在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四部：创建单元格，设置表头
        String[] headArray = {"员工编号", "姓名", "班组", "职位", "身份证", "开户行", "银行账户"};
        HSSFCell cell;
        for (int i = 0; i < headArray.length; i++) {
            row.createCell(i).setCellValue(headArray[i]);
        }

        //第五部：写入实体数据，实际应用中这些 数据从数据库得到，对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < arrayList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            //创建单元格设值
            row1.createCell(0).setCellValue(arrayList.get(i).getId());
            row1.createCell(1).setCellValue(arrayList.get(i).getEmployeeName());
            row1.createCell(2).setCellValue(arrayList.get(i).getGroup());
            row1.createCell(3).setCellValue(arrayList.get(i).getPost());
            row1.createCell(4).setCellValue(arrayList.get(i).getIdentityCard());
            row1.createCell(5).setCellValue(arrayList.get(i).getBank());
            row1.createCell(6).setCellValue(arrayList.get(i).getBankAccount());
        }
        //将文件保存到指定的位置
        String path = "/usr/local/excel/employee.xls";
        try {
            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //封装返回体
        CommonResult resultModel = new CommonResult();
        resultModel.setState(ErrorsEnum.SUCCESS.getErrorCode());
        resultModel.setSuccess(true);
        resultModel.setMessage(ErrorsEnum.SUCCESS.getMessage());
        resultModel.addAttribute("filePath", path);
        return resultModel;


    }
}
