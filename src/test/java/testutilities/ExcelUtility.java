package testutilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtility(String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(excelPath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            fis.close();
            throw new IllegalArgumentException("Sheet \"" + sheetName + "\" does not exist.");
        }
        fis.close();
    }

    public int getRowCount() {
        return sheet.getLastRowNum(); // Zero-based index (header is row 0)
    }

    public int getColumnCount() {
        Row row = sheet.getRow(0); // Header row
        return (row != null) ? row.getLastCellNum() : 0;
    }

    public String getCellData(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";

        Cell cell = row.getCell(colIndex);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    public Row getRow(int rowIndex) {
        return sheet.getRow(rowIndex);
    }

    public void close() throws IOException {
        workbook.close();
    }
}
