package common.reports;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import common.util.AppConstants;
import common.util.PropertiesReader;
import io.restassured.response.Response;

public class EmailReporter {

	public static Properties prop = PropertiesReader.getProperty(AppConstants.CONFIG_FILE);
	static Logger log = Logger.getLogger(EmailReporter.class);
	static String mailSubject = "Automation Summary Report";
	
	public static void emailReport() {
		String toJenkins = System.getProperty("email");
		String ccJenkins = System.getProperty("ccEmail");
		String to = null;
		String cc = null;
		if (toJenkins != null) {
			to = toJenkins;
		} else {
			to = prop.getProperty("to");
		}
		if (ccJenkins != null) {
			cc = ccJenkins;
		} else {
			cc = prop.getProperty("cc");
		}
		String sendEmailReportFlag = prop.getProperty("sendEmailReportFlag");
		if (sendEmailReportFlag.equalsIgnoreCase("Yes")) {
			sendEmail(to, cc);
		} else {
			log.info("Send email flag is set to No");
		}
	}

	private static void sendEmail(String to, String cc) {
		Response res = null;
		try {
			log.info("Template Name - "+prop.getProperty("emailTemplate"));
			if(System.getProperty("mailSubject")!=null) {
				mailSubject = System.getProperty("mailSubject").replaceAll("_",	" ");
			}
			String body = "{\n" + "    \"to\": \"{" + getEmailString(to.split(",")) + "}\",\n" + "    \"cc\": \"{"
					+ getEmailString(cc.split(",")) + "}\",\n" + "    \"template_name\": \""
					+ prop.getProperty("emailTemplate") + "\",\n" + "    \"template_variable\": \"{\\\"Report\\\":\\\""
					+ getReportContent().replaceAll("\"", "'").replaceAll("\t", "") + "\\\"}\",\n" + "    \"reference_type\": \"1\",\n"
					+ "    \"reference_id\": \"1\",\n" + "    \"subject_variable\": \"{\\\"Subject\\\":\\\""+mailSubject+"\\\"}\"\n" + "}";
			res = given().contentType("application/json").cookie("PHPSESSID", "1jhi61k8h6c6qi6meljb89iuo6").body(body)
					.when().post(prop.getProperty("emailApiEndPoint"));
			log.info("email api response " + res.asString());
			if (res.getBody().jsonPath().get("statusCode").equals(200)) {
				log.info("Sent mail to - " + to);
			} else {
				log.error("mail is not send to - " + to);
			}

		} catch (Exception e) {
			log.error("email api response " + res.asString());
			log.error("Error to send email - " + e.getMessage());
		}
	}

	private static String getEmailString(String[] emails) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < emails.length; i++) {
			String a = "\\\"" + emails[i] + "\\\":\\\"\\\"";
			if (i > 0) {
				str.append(",");
				str.append(a);
			} else {
				str.append(a);
			}
		}
		return str.toString();
	}

	private static String getReportContent() {
		log.info("Report path is " + AppConstants.EmailReport);
		File htmlFile = new File(AppConstants.EmailReport);
		StringBuilder contentBuilder = new StringBuilder();
		String str1 = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(htmlFile));
			while ((str1 = in.readLine()) != null) {
				contentBuilder.append(str1);
			}
			in.close();
		} catch (IOException e) {
		}
		String htmlCodeAsString = contentBuilder.toString();
		return htmlCodeAsString;
	}

	public static String generateSummay(int totalTcs, int passedTcs, int failedTcs, int skippedTcs) {

		String summary = "<div class='chartStyle summary' style='width: 32%;background-color: #3B9C9C;padding-left:.5em;padding-top:1em;'>\n"
				+ "<b>Summary</b><br><br>\n" + "<table>\n" + "<tbody>\n" + "         <tr>\n"
				+ "            <td>Execution Date</td>\n" + "            <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n"
				+ "            <td>" + getExecutionTime() + "</td>\n" + "         </tr>\n" + "         <tr>\n"
				+ "            <td>Total Test Cases</td>\n" + "            <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n"
				+ "            <td>" + totalTcs + "</td>\n" + "         </tr>\n" + "         <tr>\n"
				+ "            <td>Passed</td>\n" + "            <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n"
				+ "            <td>" + passedTcs + "</td>\n" + "         </tr>\n" + "         <tr>\n"
				+ "            <td>Failed</td>\n" + "            <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n"
				+ "            <td>" + failedTcs + "</td>\n" + "         </tr>\n" + "         <tr>\n"
				+ "            <td>Skipped</td>\n" + "            <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n"
				+ "            <td>" + skippedTcs + "</td>\n" + "         </tr>\n" + "      </tbody>" + "   </table>\n"
				+ "</div>";
		return summary;
	}

	private static String getExecutionTime() {
		LocalDateTime date = LocalDateTime.now();
		String time = date.format(DateTimeFormatter.ofPattern("d-MMM-uuuu hh:mm:ss"));
		return time;
	}

//	private static String getPieChart() {
//		String chart = "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n"
//				+ "    <script type=\"text/javascript\">\n"
//				+ "      google.charts.load(\"current\", {packages:[\"corechart\"]});\n"
//				+ "      google.charts.setOnLoadCallback(drawChart);\n" + "      function drawChart() {\n"
//				+ "        var data = google.visualization.arrayToDataTable([\n" + "          ['Status', 'Count'],\n"
//				+ "          ['Pass',     11],\n" + "          ['Fail',      2],\n" + "          ['Skip',  2]       \n"
//				+ "        ]);\n" + "\n" + "        var options = {\n" + "          title: 'Execution Summay',\n"
//				+ "          is3D: true,\n" + "        };\n" + "\n"
//				+ "        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));\n"
//				+ "        chart.draw(data, options);\n" + "      }\n" + "    </script>";
//		return chart;
//	}

	public static String getBody() {
		String str = "<!DOCTYPE html>\n" + "<html>\n" + "  <head>\n" + "    <title>Title of the document</title>\n"
				+ "  </head>\n" + "  <body>\n" + "    <div>\n" + "      <p>Automation</p>\n" + "      <img \n"
				+ " alt='Red dot'/>\n" + "    </div>\n" + "  </body>\n" + "</html>";
		return str;
	}

	public static String getImageBase64() throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File("./Sample_Chart.png"));
		return Base64.getEncoder().encodeToString(fileContent);
	}

}
