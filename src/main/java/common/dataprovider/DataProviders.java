/**
 * 
 */
package common.dataprovider;

import java.util.Collection;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.codoid.products.exception.FilloException;
import common.util.ExcelUtility;

/**
 * @author nikhil
 *
 */
public class DataProviders {

	@DataProvider(name = "test-data")
	public static Iterator<Object[]> dataProvFunc(ITestContext context) throws FilloException {
		String fileName = context.getCurrentXmlTest().getParameter("file_name");
		String sheetName = context.getCurrentXmlTest().getParameter("sheet_name");
		System.out.println(context.getCurrentXmlTest().getName());
		System.out.println(context.getCurrentXmlTest().getSuite());
		System.out.println(context.getCurrentXmlTest());
		System.out.println(fileName);
		System.out.println(sheetName);
		Collection<Object[]> dp = ExcelUtility.createDataProviderFromExcel(fileName, sheetName);
		return dp.iterator();
	}

}
