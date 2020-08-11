package com.paas.app;

import com.paas.app.dao.StudentDao;
import com.paas.app.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.*;

@SpringBootTest
class AppApplicationTests {

    @Resource
    StudentDao studentDao;

    @Test
    void contextLoads() {
        DateTimeFormatter df = ofPattern("yyyy-MM-dd HH:mm:ss");
        StudentDao studentDao = null;
        //查询有多少条数据
        ArrayList<Student> arrayList = studentDao.queryAllStudent();
        //第一步：创建一个workbook对应一个Excel文件
        HSSFWorkbook workbook=new HSSFWorkbook();
        //第二部：在workbook中创建一个sheet对应Excel中的sheet
        HSSFSheet sheet=workbook.createSheet("用户表");
        //第三部：在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row=sheet.createRow(0);
        //第四部：创建单元格，设置表头
        HSSFCell cell=row.createCell( 0);
        cell.setCellValue("用户名");
        cell=row.createCell(1);
        cell.setCellValue("性别");
        cell=row.createCell( 2);
        cell.setCellValue("电话");
        cell=row.createCell(3);
        cell.setCellValue("创建时间");

        //第五部：写入实体数据，实际应用中这些 数据从数据库得到，对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for(int i=0;i < arrayList.size(); i++){
            HSSFRow row1=sheet.createRow(i+1);
            //创建单元格设值
            row1.createCell(0).setCellValue(arrayList.get(i).getStudentName());
            row1.createCell(1).setCellValue(arrayList.get(i).getGender());
            row1.createCell(2).setCellValue(arrayList.get(i).getPhone());
            row1.createCell(3).setCellValue(df.format(arrayList.get(i).getCreateTime()));
        }
        //将文件保存到指定的位置
        try {
            FileOutputStream fos=new FileOutputStream("D:\\user.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
