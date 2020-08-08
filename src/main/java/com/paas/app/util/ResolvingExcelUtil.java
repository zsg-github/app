package com.paas.app.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * @Description: analyze sheets Incoming
 * @Param:
 * @return:
 * @Author: yhc
 * @Date: 2020/8/8
 */

public class ResolvingExcelUtil {

    private static Calendar calendar = Calendar.getInstance();
    private ExcelUtil excelUtil;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
    private static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * @Description:
     * @Param: [file]
     * @return:  HashMap<String, ArrayList<String[]>>
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static HashMap<String, ArrayList<String[]>> analysisFile(MultipartFile file) throws IOException {

        HashMap<String, ArrayList<String[]>> hashMap = new HashMap<>();
        //acquire Workbook Object
        Workbook workbook = null;
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        //Differentiate files based on suffix
        if (filename.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (filename.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }

        /*Create an object, treat each row as a String array,
         *so the array is stored in the collection
         */
        ArrayList<String[]> arrayList = new ArrayList<>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //obtain sheet
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    hashMap.put("no sheet exists!!!", arrayList);
                    return hashMap;
                }
                //Get the start line and end line of the current sheet
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    //Get the current row
                    Row row = sheet.getRow(rowNum);
                    //Get the start and end columns of the current row
                    short firstCellNum = row.getFirstCellNum();
                    short lastCellNum = row.getLastCellNum();

                    //Get the total number of rows
                    int lastCellNum2 = row.getPhysicalNumberOfCells();
                    String[] strings = new String[lastCellNum2];
                    //Loop current line
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        if (cell == null || "".equals(cell) || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                            hashMap.put("第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列为空", arrayList);
                            return hashMap;
                        }
                        String cellValue = "";
                        cellValue = getCellValueByType(cell);
                        strings[cellNum] = cellValue;
                    }
                    arrayList.add(strings);

                }
            }
        }
        inputStream.close();
        hashMap.put("OK", arrayList);
        return hashMap;
    }

    //turn each cell to String type
    public static String getCellValueByType(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(cell.CELL_TYPE_STRING);
        }
        //swith the type Incoming
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //if it's num
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //if it's String
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //if it's boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: //null
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //error
                cellValue = "illegal type!!!";
                break;
            default:
                cellValue = "unknown type found";
                break;
        }
        return cellValue;
    }

    /**
     * @Description: judge whether a row is empty
     * @Param: [row]
     * @return: boolean
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    //check the file Incoming
    public static Boolean checkFile(MultipartFile file) {
        boolean empty = file.isEmpty();
        if (empty || file == null) {
            return false;
        }
        String filename = file.getOriginalFilename();
        if (!filename.endsWith("xls") && !filename.endsWith("xlsx")) {
            return false;
        }
        return true;
    }


    /**
     * @Description: format Date to String
     * @Param:
     * @return:
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static String getExcelDate(Cell cell) {
        Date dateCellValue = cell.getDateCellValue();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(dateCellValue);
        return format;
    }


    /**
     * @Description: check whether it's a Number
     * @Param:
     * @return:
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static Boolean checkWhetherNumber(String str) {
        try {
            BigDecimal bigDecimal = new BigDecimal(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * @Description: heck whether it's a Date format as yyyy/MM/dd HH:mm:ss
     * @Param:
     * @return:
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static Boolean checkWhetherDate(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * @Description: check whether it's a Date format as yyyy/MM/dd
     * @Param:
     * @return:
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static Boolean checkWhetherDate2(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * @Description: check whether it's month
     * @Param:
     * @return:
     * @Author: yhc
     * @Date: 2020/8/8
     */
    public static Boolean checkWhetherMonth(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
