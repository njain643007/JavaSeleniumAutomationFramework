/**
 * 
 */
package common.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import common.listeners.TestListener;

/**
 * @author nikhil
 *
 */

public class AWSHelper {

	private static PropertiesReader prop = new PropertiesReader(AppConstants.USER_CURR_DIR + AppConstants.CONFIG_FILE);
	private static AmazonS3 s3client;
	private String keyFullPath;
	static Logger log;

	private static AWSHelper instance;

	public static AWSHelper getInstance() {
		if (instance == null) {
			instance = new AWSHelper();
		}
		log = Logger.getLogger(TestListener.class);

		return instance;
	}

	public String getKeyFullPath() {
		return keyFullPath;
	}

	public void setKeyFullPath(String keyFullPath) {
		this.keyFullPath = keyFullPath;
	}

	private AWSHelper() {
		AWSCredentials credentials = new BasicAWSCredentials(prop.getPropValue("AWSaccesskey"),
				prop.getPropValue("AWSsecretkey"));
		s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_SOUTH_1).build();
	}

	public String getS3Path(String screenShotPath) {
		String path = null;
		path = "https://" + prop.getPropValue("bucketName") + ".s3."
				+ Regions.AP_SOUTH_1.toString().toLowerCase().replace("_", "-") + ".amazonaws.com" + "/"
				+ System.getProperty("jobName") + "/" + System.getProperty("buildNumber") + screenShotPath;
		return path;
	}

	public void uploadScreenshotToS3(String key, String filePath) {
		s3client.putObject(prop.getPropValue("bucketName"), key, new File(filePath));
	}

	public String uploadFileToS3Bucket(String file) {
		String filePath = AppConstants.USER_CURR_DIR + file;
		String key = System.getProperty("jobName") + "/" + System.getProperty("buildNumber") + file;
		s3client.putObject(prop.getPropValue("bucketName"), key, new File(filePath));
		log.info("uploaded file into s3 bucket :- " + prop.getPropValue("bucketName"));
		log.info("key is : - " + key);
		return getKeyFullPath(key);
	}

	public static String getKeyFullPath(String key) {
		String path = null;
		path = prop.getPropValue("bucketName") + ".s3." + Regions.AP_SOUTH_1.toString().toLowerCase().replace("_", "-")
				+ ".amazonaws.com" + "/" + key;
		log.info("key full path : - " + path);
		return path;
	}
}
