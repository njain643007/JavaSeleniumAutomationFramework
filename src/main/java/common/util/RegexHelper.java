package common.util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

	public static int getNumericFromString(String str) {
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(str);
		while(m.find())
		{
			return Integer.parseInt(m.group(1));
		}
		return 0;
	}
	
	public static List<Integer> getNumericsFromString(String str) {
		List<Integer> list = new ArrayList<Integer>();
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(str);
		while(m.find())
		{
			list.add(Integer.parseInt(m.group(1)));
		}
		return list;
	}
	
}
