package common.util;

import java.io.File;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.testng.Assert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.Server;

import io.restassured.RestAssured;

public class DataBaseUtils {

	private static Connection conn = null;
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> collection;
	private static File dbConfigFile = new File(AppConstants.DB_CONFIG_FILE);

	public DataBaseUtils(String server, String dbName, String userid, String password)
			throws IOException, ClassNotFoundException, SQLException {

		if (server.contains("mysql")) {
			connectMySql(server, dbName, userid, password);
		}
		if (server.contains("mongo")) {
			connectMongo(server, dbName);
		}

	}

	private void connectMySql(String server, String db, String userid, String password)
			throws IOException, ClassNotFoundException, SQLException {
		String url = server + db;

		System.out.println("Connecting SQL Database...");

		Class.forName("com.mysql.cj.jdbc.Driver");
		DataBaseUtils.conn = DriverManager.getConnection(url, userid, password);
		System.out.println("Connected with SQL Database successfully!");

	}

	public ResultSet executeSqlQuery(String query) throws SQLException, InterruptedException {

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;

	}

	public int executeUpdateSqlQuery(String query) throws SQLException {

		Statement stmt = conn.createStatement();
		int statement = stmt.executeUpdate(query);
		return statement;

	}

	public void closeSqlConnection() throws SQLException {
		conn.close();
	}

	private void connectMongo(String server, String db) throws IOException {
		System.out.println("Connecting Mongo Database...");
		mongoClient = new MongoClient(new MongoClientURI(server));
		database = mongoClient.getDatabase(db);
		System.out.println("Connected with " + db + " Database successfully!");

	}

	public Object extractDataFromMongo(String collectionName, String filter, String pathToExtract)
			throws JsonParseException, JsonMappingException, IOException {

		String jsonString = extractDataInJson(collectionName, filter);
		Object obj = JsonUtility.extractJsonField(jsonString, pathToExtract);
		return obj;

	}

	public String extractDataInJson(String collectionName, String filter)
			throws JsonParseException, JsonMappingException, IOException {

		JsonWriterSettings settings = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
				.objectIdConverter((value, writer) -> writer.writeString(value.toHexString()))
				.dateTimeConverter((value, writer) -> {
					ZonedDateTime zonedDateTime = Instant.ofEpochMilli(value).atZone(ZoneOffset.UTC);
					writer.writeString(DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime));
				}).build();

		MongoCollection<Document> collection = database.getCollection(collectionName);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(filter, Map.class);
		for (Entry<String, Object> objMap : map.entrySet()) {
			if (objMap.getKey().contains("_id")) {
				String valueString = objMap.getValue().toString().replace("ObjectId(", "").replace(")", "")
						.replaceAll("'", "").toString();
				map.put(objMap.getKey(), new ObjectId(valueString));
			}
			if (objMap.getValue().toString().contains("ISODate")) {
				String valueString = objMap.getValue().toString().replace("ISODate(", "").replace(")", "")
						.replaceAll("'", "").toString();
				Instant instant = Instant.parse(valueString); // Pass your date.
				Date timestamp = Date.from(instant);
				map.put(objMap.getKey(), timestamp);
			}
		}
		System.out.println(map);
		Document query = new Document(map);

		String result = collection.find().filter(query).first().toJson(settings);

		System.out.println(result);
		return result;

	}

	public void closeMongoConnection() {
		mongoClient.close();
	}

	public static void main(String... strings) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
		DataBaseUtils mySqlDb = new DataBaseUtils("jdbc:mysql://172.50.3.197:3306/", "otp", "otp_user", "ZaRTG534a3n");
		ResultSet rs = mySqlDb.executeSqlQuery(String
				.format("SELECT * FROM `otp` WHERE `mobile` = %s ORDER BY `otp`.`id` DESC LIMIT 1", "8745036354"));
		rs.next();
		System.out.println( rs.getString(2));
	}

}