package com.crealytics.report.Reporting.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.crealytics.report.Reporting.entities.ExcelData;
import com.crealytics.report.Reporting.entities.ExcelDataRepository;
import com.crealytics.report.Reporting.service.ReportDataToDBService;


/**
 * This class reads the data from CSV and parse it.
 * @author NMUKHER
 *
 */
@Component
public class ReadExcel {	
	
	@Value("${app.filepath}")
	private String fileDirectory;
	
	/**
	 * 
	 * @return List<ExcelData>
	 * 
	 */
	public List<ExcelData> persistData() {
		
		ReadExcel readExcel = new ReadExcel();		
		List<ExcelData> listData = new ArrayList<>();		
		List<String> file = parseForCsvFiles(fileDirectory);
		for (int i = 0; i < file.size(); i++) {
			listData.addAll(parseCSVFile(file.get(i)));
		}
		return listData;
	}

	/**
	 * This method parse the CSV file.
	 * @param file
	 */
	public static List<ExcelData> parseCSVFile(String file) {
		
		List<ExcelData> listData = new ArrayList<>();
		String csvFile = file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		//Get the month name from the CSV filename
		String month = getMonthForInt(Integer.parseInt(getMonth(file))-1);
				
		try {

			br = new BufferedReader(new FileReader(csvFile));
			String headerLine = br.readLine();
			while ((line = br.readLine()) != null) {	
				ExcelData excelData = new ExcelData();

				// use comma as separator
				String[] report = line.split(cvsSplitBy);

				excelData.setSite(report[0]);
				excelData.setRequests(Long.parseLong(report[1].trim()));
				excelData.setImpressions(Long.parseLong(report[2].trim()));
				excelData.setClicks(Long.parseLong(report[3].trim()));
				excelData.setConversions(Long.parseLong(report[4].trim()));
				excelData.setRevenue(Double.parseDouble(report[5].trim()));
				excelData.setMonth(month);	
				
				double CTR = calculateClickThroughRate(excelData.getClicks(),excelData.getImpressions());
				double CR = calculateConversionRate(excelData.getConversions(), excelData.getImpressions());
				double fillRate = calculateFillRate(excelData.getImpressions(), excelData.getRequests());
				double eCPM = calculateEffectiveCostPerThousand(excelData.getRevenue(), excelData.getImpressions());
				
				excelData.setCTR(CTR);
				excelData.setCR(CR);
				excelData.setFillRate(fillRate);
				excelData.seteCPM(eCPM);
				
				listData.add(excelData);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listData;

	}
	/**
	 * calculateEffectiveCostPerThousand
	 * @param revenue
	 * @param impressions
	 * @return
	 */
	private static double calculateEffectiveCostPerThousand(double revenue, Long impressions) {
		// TODO Auto-generated method stub
		return roundNumber((long) ((revenue*1000)/impressions));
	}

	/**
	 * calculateFillRate
	 * @param impressions
	 * @param requests
	 * @return
	 */
	private static double calculateFillRate(Long impressions, Long requests) {
		// TODO Auto-generated method stub
		return roundNumber((impressions/requests)*100);
	}

	
	/**
	 * calculateConversionRate
	 * @param conversions
	 * @param impressions
	 * @return
	 */
	private static double calculateConversionRate(Long conversions, Long impressions) {
		// TODO Auto-generated method stub
		return roundNumber((conversions/impressions)*100);
	}

	/**
	 * calculateClickThroughRate
	 * @param clicks
	 * @param impressions
	 * @return
	 */
	private static double calculateClickThroughRate(Long clicks, Long impressions) {
		
		return roundNumber((clicks/impressions)*100);
	}

	/**
	 * Round the number to 2 decimal points.
	 * @param l
	 * @return
	 */
	private static double roundNumber(long l) {
		
		double finalValue = 0;
		double value = (double)l;
		DecimalFormat df=new DecimalFormat("0.00");
		String formate = df.format(value); 
		//finalValue = (Double)df.parse(formate) ;
		finalValue = Double.parseDouble(formate);
		
		return finalValue;
	}

	/**
	 * Returns the list of CSV files present in the directory.
	 * @param parentDirectory
	 * @return
	 */
	public static List<String> parseForCsvFiles(String parentDirectory) {
		List<String> fileFound = new ArrayList<>();
		File[] filesInDirectory = new File(parentDirectory).listFiles();
		for (File f : filesInDirectory) {
			if (f.isDirectory()) {
				parseForCsvFiles(f.getAbsolutePath());
			}
			String filePath = f.getAbsolutePath();
			String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
			if ("csv".equals(fileExtenstion)) {
				fileFound.add(filePath);
			}
		}
		return fileFound;
	}
	
	/**
	 * Get month number from CSV file name.
	 * @param str
	 * @return
	 */
	public static String getMonth(String str) {

		String month = null;
		StringTokenizer st2 = new StringTokenizer(str, "_");
		
		while (st2.hasMoreElements()) {
			st2.nextElement();
			month=st2.nextElement().toString();
			st2.nextElement();
		}
		return month;
	}
	
	/**
	 * get Month name from the number.
	 * @param m
	 * @return
	 */
	public static String getMonthForInt(int m) {
		System.out.println(m);
	    String month = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (m >= 0 && m <= 11 ) {
	        month = months[m];
	    }
	    return month;
	}
}
