package com.crealytics.report.Reporting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.crealytics.report.Reporting.entities.ExcelData;
import com.crealytics.report.Reporting.entities.ExcelDataRepository;
import com.crealytics.report.Reporting.service.ReportDataToDBService;
import com.crealytics.report.Reporting.util.ReportUtils;

/**
 * Controller for the reporting application.
 * @author NMUKHER
 *
 */
@RestController
public class ReportController {
	
	@Autowired
	ReadExcel readExcel;
	
	@Autowired
	ReportDataToDBService DBservice;
	
	@Autowired
	ExcelDataRepository repository;
	
	
	
	
	@GetMapping(value="/save")
	public int test() {
		List<ExcelData> listdata = readExcel.persistData();	
		int valueId = 0;
		for(int i=0;i<listdata.size();i++) {
			valueId=DBservice.insert(listdata.get(i));
		}
		return valueId;
		
	}
	
	@GetMapping(value = "/reports")
	public List<ExcelData> getAll() {
		return DBservice.getAllReports();
	}
	
	@GetMapping(value = "/reports/{month}/{site}")
	public List<ExcelData> getByMonthAndSite(@PathVariable String month, @PathVariable String site) {
		String pathMonth=ReportUtils.MonthNames(month);
		return DBservice.findByMonthAndSite(pathMonth, site);
	}
	
	
	@GetMapping(value = "/reports/month/{month}")
	public List<ExcelData> getAllReportForMonth(@PathVariable String month) {
		String pathMonth=ReportUtils.MonthNames(month);
		return DBservice.getAllReportForMonth(pathMonth);
	}
	
	@GetMapping(value = "/reports/{site}")
	public List<ExcelData> getAllReportForSite(@PathVariable String site) {		
		return DBservice.getAllReportForSite(site);
	}
}
