package com.crealytics.report.Reporting.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author NMUKHER
 *
 */
@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {

	List<ExcelData> findByMonthAndSite(String month, String site);

	List<ExcelData> getAllReportForMonth(String pathMonth);

	List<ExcelData> getAllReportForSite(String site);



}
