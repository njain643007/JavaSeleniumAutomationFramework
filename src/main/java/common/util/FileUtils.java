package common.util;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	private static String logFolder = "Logs";

	public static boolean getFileAvailability(String filePath) {
		try {
			File file = new File(System.getProperty("user.dir") + File.separator + filePath);
			if (file.exists())
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public static void deleteOldLogFiles() throws IOException {
		File dir = new File(System.getProperty("user.dir") + File.separator + logFolder);
		File[] listFiles = dir.listFiles();
		String fileName = "Log_"+System.getProperty("current.date.time");
		if (dir.exists()) {
			for (File file : listFiles) {
				if (!file.getName().contains(fileName)) {
					file.delete();
				}
			}
		}
	}
	
	public static void emptyDir(String folder) throws IOException {
		File dir = new File(System.getProperty(AppConstants.USER_CURR_DIR) + folder);
		File[] listFiles = dir.listFiles();
		if (listFiles != null) {
			for (File file : listFiles) {
				file.delete();
			}
		}
	}
}
