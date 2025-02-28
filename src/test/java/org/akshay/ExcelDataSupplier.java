package org.akshay;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ExcelDataSupplier {

    @DataProvider(name = "loginData")
    public String[][] getData() throws IOException {
        File excelFile = new File("./src/test/resources/LoginData.xlsx");
        System.out.println(excelFile.exists());
        FileInputStream file = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

       int noOfRows = sheet.getPhysicalNumberOfRows();
       int noOfColumns = sheet.getRow(0).getLastCellNum();

       String[][] data = new String[noOfRows-1][noOfColumns];
        for (int i = 0; i < noOfRows-1; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                DataFormatter df = new DataFormatter();
                data[i][j] = df.formatCellValue(sheet.getRow(i+1).getCell(j));
            }
        }
        workbook.close();
        file.close();
        for (String[] dataArr : data){
            System.out.println(Arrays.toString(dataArr));
        }
        return data;
    }
}
