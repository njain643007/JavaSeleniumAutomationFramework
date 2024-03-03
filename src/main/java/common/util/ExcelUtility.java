/**
 * 
 */
package common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * @author nikhil
 *
 */
public class ExcelUtility {

	public static Collection<Object[]> createDataProviderFromExcel(String sheetName)
			throws FilloException {
		List<LinkedHashMap<String, String>> lom = new ArrayList<LinkedHashMap<String, String>>();
		Fillo fillo = new Fillo();
		Connection connection = fillo
				.getConnection(AppConstants.USER_CURR_DIR + AppConstants.TestDataFolder + AppConstants.TestDataWorkbook);
		Recordset recordset = connection.executeQuery("SELECT * FROM " + sheetName);
		LinkedHashMap<String, String> map;
		List<String> columns = recordset.getFieldNames();
		while (recordset.next()) {
			map = new LinkedHashMap<String, String>();
			for (String column : columns) {
				map.put(column, recordset.getField(column));
			}
			lom.add(map);
		}
		Collection<Object[]> dp = new ArrayList<Object[]>();
		for (Map<String, String> m : lom) {
			dp.add(new Object[] { m });
		}
		connection.close();
		return dp;
	}

	public static Collection<Object[]> createDataProviderFromExcel(String workBook,String sheetName)
			throws FilloException {
		List<LinkedHashMap<String, String>> lom = new ArrayList<LinkedHashMap<String, String>>();
		Fillo fillo = new Fillo();
		Connection connection = fillo
				.getConnection(AppConstants.USER_CURR_DIR + AppConstants.TestDataFolder + workBook);
		Recordset recordset = connection.executeQuery("SELECT * FROM " + sheetName);
		LinkedHashMap<String, String> map;
		List<String> columns = recordset.getFieldNames();
		while (recordset.next()) {
			map = new LinkedHashMap<String, String>();
			for (String column : columns) {
				map.put(column, recordset.getField(column));
			}
			lom.add(map);
		}
		Collection<Object[]> dp = new ArrayList<Object[]>();
		for (Map<String, String> m : lom) {
			dp.add(new Object[] { m });
		}
		connection.close();
		return dp;
	}

}
