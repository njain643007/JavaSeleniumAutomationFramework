package common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteUtils {

	public static void writeInFile(String filePath, String data, String fileFormat) throws IOException {
		String file_path = System.getProperty("user.dir") + filePath+fileFormat;

		File fileObj = new File(file_path);

		if(fileObj.createNewFile()) {
			FileWriter myWriter = new FileWriter(file_path);
			myWriter.write(data.toString());
			myWriter.close();
		}else {
			System.out.println("Failed");
		}
	}
}
