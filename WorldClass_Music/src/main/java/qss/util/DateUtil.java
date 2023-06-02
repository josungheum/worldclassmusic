package qss.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	protected static final String defaultDateFormat = "yyyy-MM-dd";
	
	public static String getDateNow() {
		return getDateNow(defaultDateFormat);
	}
	
	public static String getDateNow(String pattern) {
		if(pattern == null){
			throw new NullPointerException();
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(cal.getTime());
	}
}