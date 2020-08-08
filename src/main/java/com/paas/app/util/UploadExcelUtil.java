package com.paas.app.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @program: app
 * @description upload xls/xlsx file to a File
 * @author: yhc
 * @create: 2020-08-08 11:46
 **/
public class UploadExcelUtil {
    // define a file for accepting file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";


    public static String getFileInfo(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        String filePath = new String();
        String uploadPath = request.getSession().getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        if (!file.isEmpty()) {
            try {
                filePath = request.getSession().getServletContext().getRealPath("/") + UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();

                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }
}
