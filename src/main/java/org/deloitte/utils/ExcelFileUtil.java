package org.deloitte.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFileUtil {
    private final Logger logger = LogManager.getLogger(ExcelFileUtil.class);
    private XSSFWorkbook workbook;

    public XSSFSheet getWorkbookSheet(String filePath, String sheetName) {
        logger.info("Reading workbook getWorkbookSheet({}, {})", filePath, sheetName);
        File file = new File(filePath);
        XSSFSheet xssfSheet = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            xssfSheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            String message = String.format("Failed to read workbook: {} & sheet: {}", filePath, sheetName);
            logger.error(message, e);
        }
        return xssfSheet;
    }

    public List<Map<String, String>> readData(String filePath, String sheetName) {
        logger.info("Reading the data from {} and sheet {}", filePath, sheetName);
        XSSFSheet sheet = getWorkbookSheet(filePath, sheetName);
        int lastRow = sheet.getLastRowNum();
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 1; i <= lastRow; i++) {
            int lastCell = sheet.getRow(i).getLastCellNum();
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < lastCell; j++) {
                String key = sheet.getRow(0).getCell(j).getStringCellValue();
                String value = sheet.getRow(i).getCell(j).getStringCellValue();
                map.put(key, value);
            }
            data.add(map);
        }
        logger.debug("Excel Sheet Data: {}", data);
        return data;
    }

    public Map<String, String> readData(String filePath, String sheetName, int row) {
        logger.info("Reading the data from {} and sheet {} and row{}", filePath, sheetName, row);
        XSSFSheet sheet = getWorkbookSheet(filePath, sheetName);
        Map<String, String> data = readData(filePath, sheetName).get(row);
        logger.debug("Excel Sheet Data with row: {}", data);
        return data;
    }

    public void writeData(String filePath, String sheetName, int rownum, int colnum, String data)  {
        XSSFSheet sheet = getWorkbookSheet(filePath, sheetName);
        // At this point, the row and cell need to be created or accessed
        XSSFRow row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }

        XSSFCell cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }
        cell.setCellValue(data);

        // Write the updated workbook to the file
        try (FileOutputStream outStream = new FileOutputStream(filePath)) {
            workbook.write(outStream);
            workbook.close();
        } catch (Exception ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
    }
}
