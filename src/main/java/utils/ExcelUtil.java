package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * ExcelUtil - Reads test data from Excel files for data-driven testing.
 */
public class ExcelUtil {

    private static final Logger log = LogManager.getLogger(ExcelUtil.class);

    /**
     * Reads all rows from a given sheet in an Excel file.
     * @param filePath - path to .xlsx file
     * @param sheetName - name of the sheet
     * @return 2D Object array for use with TestNG @DataProvider
     */
    public static Object[][] getTestData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();      // excludes header row
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = getCellValue(cell);
                }
            }
            log.info("Loaded {} rows from sheet '{}' in {}", rowCount, sheetName, filePath);
        } catch (IOException e) {
            log.error("Failed to read Excel file: " + e.getMessage());
        }
        return data;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default:      return "";
        }
    }
}
