package com.crealytics.report.Reporting.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crealytics.report.Reporting.entities.ExcelData;
import com.crealytics.report.Reporting.entities.ExcelDataRepository;

/**
 * 
 * @author NMUKHER
 *
 */
@Service
@Transactional
public class ReportDataToDBService {
	
	@Autowired
	ExcelDataRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public int insert(ExcelData data) {
		entityManager.persist(data);
		return data.getId();	
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public List<ExcelData> getAllReports() {
		return (List<ExcelData>) repository.findAll();
	}

	
	public List<ExcelData> findByMonthAndSite(String month, String site) {
		return repository.findByMonthAndSite(month, site);
	}

	public List<ExcelData> getAllReportForMonth(String pathMonth) {
		// TODO Auto-generated method stub
		return repository.getAllReportForMonth(pathMonth);
	}

	public List<ExcelData> getAllReportForSite(String site) {
		// TODO Auto-generated method stub
		return repository.getAllReportForSite(site);
	}

	
}
