package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadUtils {

	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell cell;

//	private static XSSFRow Row;

	@SuppressWarnings("deprecation")
	public static List<List<Object>> readWholeExcel(String fileName) throws IOException {
		List<List<Object>> excel_data = new ArrayList<List<Object>>();
		XSSFWorkbook wb = null;
		try {
			String file_path = System.getProperty("user.dir") + fileName;

			File file = new File(file_path);
			FileInputStream fis = new FileInputStream(file);
		    wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) {
				ArrayList<Object> temp = new ArrayList<Object>();
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						temp.add(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:

						temp.add(cell.getNumericCellValue());
						break;
					default:
					}
				}
				excel_data.add(temp);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		wb.close();
		return excel_data;
	}

	@SuppressWarnings("deprecation")
	public static Object getCellValue(String fileName, int row_num, int col_num) {
		Object value = null;
		Workbook wb = null;
		try {
			String file_path = System.getProperty("user.dir") + fileName;

			File file = new File(file_path);
			FileInputStream fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(row_num);
		Cell cell = row.getCell(col_num);
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = (cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = cell.getNumericCellValue();
			break;
		default:
		}
		return value; // returns the cell value
	}

	public static Object[][] getDataForDataProvider(String filePath, String SheetName) throws Exception {

		Object[][] tabArray = null;

		try {
			String FilePath = System.getProperty("user.dir") + filePath;
			FileInputStream ExcelFile = new FileInputStream(FilePath);

// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int totalRows = ExcelWSheet.getPhysicalNumberOfRows() - 1;
			int totalCols = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("total row" + totalRows);
			System.out.println("total col" + totalCols);
			int startRow = 1;

			int startCol = 0;

			int ci, cj;

			tabArray = new Object[totalRows][totalCols];
			ci = 0;

			for (int i = startRow; i <= totalRows; i++, ci++) {

				cj = 0;

				for (int j = startCol; j < totalCols; j++, cj++) {

					tabArray[ci][cj] = getCellData(i, j);
// System.out.println(tabArray[ci][cj]);

				}

			}

		}

		catch (FileNotFoundException e) {

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e) {

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return (tabArray);

	}

	@SuppressWarnings("deprecation")
	public static Object getCellData(int RowNum, int ColNum) throws Exception {
		Object value = "";

		try {

			cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (cell != null) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					value = (cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					DecimalFormat df = new DecimalFormat("0");
					value = df.format(cell.getNumericCellValue());
// value = cell.getNumericCellValue();
					break;
				default:
				}
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());

			throw (e);

		}
		return value;

	}

	public static void main(String[] args) throws Exception {
// System.out.println(getTableArray("src/test/java/project/excel/APIData.xlsx", "SampleData"));
	}

}