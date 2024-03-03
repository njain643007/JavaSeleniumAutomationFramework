package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriteUtils {


	public static void modifyExistingExcel(String fileName, String sheetName, int row_num, int cell_num, String updatedValue) throws InvalidFormatException, IOException {
	    // Obtain a workbook from the excel file
		String file_path = System.getProperty("user.dir")+"/" + fileName;

		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetName);
	    // Get Sheet at index 0

	    // Get Row at index 1
	    Row r = sheet.getRow(row_num);
	    
	    // Get the Cell at index 2 from the above row
	    Cell c = r.getCell(cell_num);

	    // Create the cell if it doesn't exist
	    if (c == null)
	        c = r.createCell(cell_num);

	    // Update the cell's value
	    c.setCellType(CellType.STRING);
	    c.setCellValue(updatedValue);

	    // Write the output to the file
	    FileOutputStream fileOut = new FileOutputStream(file);
	    wb.write(fileOut);
	    fileOut.close();

	    // Closing the workbook
	    wb.close();
	}

	public static void main(String[] args) throws Exception {
//		modifyExistingExcel("/Testing.xlsx", 1, 11, "Pass");
	}

}