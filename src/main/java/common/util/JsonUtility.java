package common.util;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jayway.jsonpath.JsonPath;

public class JsonUtility {
	Object obj = null;

	static String filePath = AppConstants.USER_CURR_DIR + AppConstants.TestDataFolder;
	
	public JsonUtility(String filename) {
		try {
			JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader(filePath +filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String extractJsonField(String jsonString, String pathToExtract) {
		try {
			Object dataObject = JsonPath.parse(jsonString).read(pathToExtract);
			String dataString = dataObject.toString();
			return dataString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public JsonUtility(String filePath,String filename) {
		try {
			JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader(filePath +filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject getJsonObject() {
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}

	public JSONArray getJsonArray(String array) {
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray jsonArray = (JSONArray) jsonObject.get(array);
		return jsonArray;
	}
	
	public JSONObject getJsonObject(String jsonKey) {
		JSONObject jsonObject = (JSONObject) obj;
		return (JSONObject) jsonObject.get(jsonKey);
	}
	
	public String getJsonKeyValue(String jsonKey) {
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject.get(jsonKey).toString();
	}
	
	public static String extractJsonField(String jsonString) {
		Object dataObject = JsonPath.parse(jsonString).read(filePath);
		String dataString = dataObject.toString();
		return dataString;
	}
}
