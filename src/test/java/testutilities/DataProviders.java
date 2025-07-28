package testutilities;

import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {

        String path = ".\\test-datanew\\TestUserData.xlsx";
        String sheetName = "UserData";
        ExcelUtility xlutil = new ExcelUtility(path, sheetName);

        int totalRows = xlutil.getRowCount();        // Index starts from 0 (excluding header)
        int totalColumns = xlutil.getColumnCount();

        // Count valid (non-empty) rows
        int validRowCount = 0;
        for (int i = 1; i <= totalRows; i++) { // Start from 1 to skip header
            Row row = xlutil.getRow(i);
            if (row == null || row.getCell(0) == null || row.getCell(0).toString().trim().isEmpty())
                continue;
            validRowCount++;
        }

        String[][] loginData = new String[validRowCount][totalColumns];

        int dataIndex = 0;
        for (int i = 1; i <= totalRows; i++) {
            Row row = xlutil.getRow(i);
            if (row == null || row.getCell(0) == null || row.getCell(0).toString().trim().isEmpty())
                continue;

            for (int j = 0; j < totalColumns; j++) {
                loginData[dataIndex][j] = xlutil.getCellData(i, j);
            }
            dataIndex++;
        }

        xlutil.close(); // Clean up
        return loginData;
    }
}
