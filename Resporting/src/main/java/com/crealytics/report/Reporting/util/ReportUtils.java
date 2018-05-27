package com.crealytics.report.Reporting.util;

import java.text.DateFormatSymbols;

/**
 * Util class 
 * @author NMUKHER
 *
 */
public class ReportUtils {
	
	public static String MonthNames(String month)
	{
		if(isNumeric(month)) {
			String mon = "invalid";
			int m = Integer.parseInt(month)-1;
		    DateFormatSymbols dfs = new DateFormatSymbols();
		    String[] months = dfs.getMonths();
		    if (m >= 0 && m <= 11 ) {
		        mon = months[m];
		    }
		    return mon;
		}
		if(isMonthChar(month))
		{
			String mon;
			switch(month.toLowerCase())
			{
				case "jan": mon="January";
							break;
				case "feb": mon="February";
							break;
				case "mar": mon="March";
							break;
				case "apr": mon="April";
							break;
				case "may": mon="May";
							break;
				case "jun": mon="June";
							break;
				case "jul": mon="July";
							break;
				case "aug": mon="August";
							break;
				case "sep": mon="September";
							break;
				case "oct": mon="October";
							break;
				case "nov": mon="November";
							break;
				case "dec": mon="December";
							break;
				default:    mon="Invalid";
							break;
							
			}
			return mon;
		}
		return month;
		
		
		
	}
	
	private static boolean isMonthChar(String month) {
		if(month.length()==3) {
			return true;
		}
		return false;
	}

	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  
	}

}
