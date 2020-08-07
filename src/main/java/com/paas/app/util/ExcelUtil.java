package com.paas.app.util;

import com.paas.app.enums.ErrorsEnum;
import com.paas.app.exception.BaseRunTimeException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 功能描述：
 * 实现Excel文件的基本功能
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private Workbook workbook;
    private String pattern;


    public ExcelUtil(InputStream inputStream) {
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            logger.error("初始化Excel文件异常.............................");
            e.printStackTrace();
            throw new BaseRunTimeException(ErrorsEnum.COMMON_ERROR);
        }
    }

    public ExcelUtil(File file) {
        try {
            workbook = WorkbookFactory.create(file);
        } catch (Exception e) {
            logger.error("初始化Excel文件异常.............................");
            e.printStackTrace();
            throw new BaseRunTimeException(ErrorsEnum.COMMON_ERROR);
        }
    }


    /**
     * 将数据写入到 Excel 默认第一页中，从第1行开始写入
     *
     * @param rowData 数据
     * @return
     * @throws IOException
     */
    public boolean write(List<List<String>> rowData) throws IOException {
        return write(0, rowData, 0);
    }

    /**
     * 将数据写入到 Excel 新创建的 Sheet 页
     *
     * @param rowData   数据
     * @param sheetName 长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     * @return
     * @throws IOException
     */
    public boolean write(List<List<String>> rowData, String sheetName, boolean isNewSheet) throws IOException {
        Sheet sheet = null;
        if (isNewSheet) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        int sheetIx = workbook.getSheetIndex(sheet);
        return write(sheetIx, rowData, 0);
    }

    /**
     * 将数据追加到sheet页最后
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param isAppend 是否追加,true 追加，false 重置sheet再添加
     * @return
     * @throws IOException
     */
    public boolean write(int sheetIx, List<List<String>> rowData, boolean isAppend) throws IOException {
        if (isAppend) {
            return write(sheetIx, rowData, getRowCount(sheetIx));
        } else {// 清空再添加
            clearSheet(sheetIx);
            return write(sheetIx, rowData, 0);
        }
    }

    /**
     * 将数据写入到 Excel 指定 Sheet 页指定开始行中,指定行后面数据向后移动
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param startRow 指定开始行，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean write(int sheetIx, List<List<String>> rowData, int startRow) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        int dataSize = rowData.size();
        if (getRowCount(sheetIx) > 0) {// 如果小于等于0，则一行都不存在
            sheet.shiftRows(startRow, getRowCount(sheetIx), dataSize);
        }
        for (int i = 0; i < dataSize; i++) {
            Row row = sheet.createRow(i + startRow);
            for (int j = 0; j < rowData.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(i).get(j) + "");
            }
        }
        return true;
    }

    /**
     * 设置cell 样式
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param colIndex 指定列，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean setStyle(int sheetIx, int rowIndex, int colIndex, CellStyle style) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        // sheet.autoSizeColumn(colIndex, true);// 设置列宽度自适应
        sheet.setColumnWidth(colIndex, 4000);

        Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
        cell.setCellStyle(style);

        return true;
    }

    /**
     * 创建单元格
     *
     * @param sheetIx  指定 sheet 页，从 0 开始
     * @param rowIndex 指定行，从 0 开始
     * @param colIndex 指定创建列，从 0 开始
     * @return true 列为空，false 行不为空
     * @throws IOException
     */
    public boolean createCell(int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        row.createCell(colIndex);
        return true;
    }

    /**
     * 返回sheet 中的行数
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     */
    public int getRowCount(int sheetIx) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (sheet.getPhysicalNumberOfRows() == 0) {
            return 0;
        }
        return sheet.getLastRowNum() + 1;

    }

    /**
     * 返回所在行的列数
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return 返回-1 表示所在行为空
     */
    public int getColumnCount(int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        return row == null ? -1 : row.getLastCellNum();

    }

    /**
     * 设置row 和 column 位置的单元格值
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @param value    值
     * @return
     * @throws IOException
     */
    public boolean setValueAt(int sheetIx, int rowIndex, int colIndex, String value) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.getRow(rowIndex).getCell(colIndex).setCellValue(value);
        return true;
    }

    /**
     * 重置指定行的值
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return
     * @throws IOException
     */
    public boolean setRowValue(int sheetIx, List<String> rowData, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        for (int i = 0; i < rowData.size(); i++) {
            row.getCell(i).setCellValue(rowData.get(i));
        }
        return true;
    }


    /**
     * 获取excel 中sheet 总页数
     *
     * @return
     */
    public int getSheetCount() {
        return workbook.getNumberOfSheets();
    }

    public void createSheet() {
        workbook.createSheet();
    }

    /**
     * 设置sheet名称，长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始，//
     * @param name
     * @return
     * @throws IOException
     */
    public boolean setSheetName(int sheetIx, String name) throws IOException {
        workbook.setSheetName(sheetIx, name);
        return true;
    }

    /**
     * 获取 sheet名称
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public String getSheetName(int sheetIx) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getSheetName();
    }

    /**
     * 获取sheet的索引，从0开始
     *
     * @param name sheet 名称
     * @return -1表示该未找到名称对应的sheet
     */
    public int getSheetIndex(String name) {
        return workbook.getSheetIndex(name);
    }

    /**
     * 删除指定sheet
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean removeSheetAt(int sheetIx) throws IOException {
        workbook.removeSheetAt(sheetIx);
        return true;
    }

    /**
     * 删除指定sheet中行，改变该行之后行的索引
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return
     * @throws IOException
     */
    public boolean removeRow(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.shiftRows(rowIndex + 1, getRowCount(sheetIx), -1);
        Row row = sheet.getRow(getRowCount(sheetIx) - 1);
        sheet.removeRow(row);
        return true;
    }

    /**
     * 设置sheet 页的索引
     *
     * @param sheetname Sheet 名称
     * @param sheetIx   Sheet 索引，从0开始
     */
    public void setSheetOrder(String sheetname, int sheetIx) {
        workbook.setSheetOrder(sheetname, sheetIx);
    }

    /**
     * 清空指定sheet页（先删除后添加并指定sheetIx）
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean clearSheet(int sheetIx) throws IOException {
        String sheetname = getSheetName(sheetIx);
        removeSheetAt(sheetIx);
        workbook.createSheet(sheetname);
        setSheetOrder(sheetname, sheetIx);
        return true;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

}
