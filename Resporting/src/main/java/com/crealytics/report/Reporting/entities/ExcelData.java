package com.crealytics.report.Reporting.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * This is the entity class to be persisted.
 * 
 * @author NMUKHER
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ExcelData.findByMonthAndSite", 
			query = "SELECT p FROM ExcelData p WHERE p.month=?1 and p.site=?2"),
	@NamedQuery(name = "ExcelData.getAllReportForMonth", 
			query = "SELECT new map(a.month as month, sum(a.requests) as requests, sum(a.impressions) as impressions, sum(a.clicks) as clicks, sum(a.conversions) as conversions, sum(a.revenue) as revenue, sum(a.CTR) as CTR, sum(a.CR) as CR, sum(a.fillRate) as fillRate, sum(a.eCPM) as eCPM )from ExcelData a where a.month=?1 group by a.month"),
	@NamedQuery(name = "ExcelData.getAllReportForSite", 
			query = "SELECT new map(a.site as site, sum(a.requests) as requests, sum(a.impressions) as impressions, sum(a.clicks) as clicks, sum(a.conversions) as conversions, sum(a.revenue) as revenue, sum(a.CTR) as CTR, sum(a.CR) as CR, sum(a.fillRate) as fillRate, sum(a.eCPM) as eCPM )from ExcelData a where a.site=?1 group by a.site"),})
public class ExcelData {

	@Id
	@GeneratedValue
	private int id;
	private String site;
	private Long requests;
	private Long impressions;
	private Long clicks;
	private Long conversions;
	private double revenue;
	private String month;
	private double CTR;
	private double CR;
	private double fillRate;
	private double eCPM;
	
	

	public ExcelData() {

	}

	public ExcelData(int id, String site, Long requests, Long impressions, Long clicks, Long conversions,
			double revenue, String month) {
		super();
		this.id = id;
		this.site = site;
		this.requests = requests;
		this.impressions = impressions;
		this.clicks = clicks;
		this.conversions = conversions;
		this.revenue = revenue;
		this.month = month;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Long getRequests() {
		return requests;
	}

	public void setRequests(Long requests) {
		this.requests = requests;
	}

	public Long getImpressions() {
		return impressions;
	}

	public void setImpressions(Long impressions) {
		this.impressions = impressions;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Long getConversions() {
		return conversions;
	}

	public void setConversions(Long conversions) {
		this.conversions = conversions;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getCTR() {
		return CTR;
	}

	public void setCTR(double cTR) {
		CTR = cTR;
	}

	public double getCR() {
		return CR;
	}

	public void setCR(double cR) {
		CR = cR;
	}

	public double getFillRate() {
		return fillRate;
	}

	public void setFillRate(double fillRate) {
		this.fillRate = fillRate;
	}

	public double geteCPM() {
		return eCPM;
	}

	public void seteCPM(double eCPM) {
		this.eCPM = eCPM;
	}

	@Override
	public String toString() {
		return "ExcelData [id=" + id + ", site=" + site + ", requests=" + requests + ", impressions=" + impressions
				+ ", clicks=" + clicks + ", conversions=" + conversions + ", revenue=" + revenue + ", month=" + month
				+ "]";
	}
	

}
