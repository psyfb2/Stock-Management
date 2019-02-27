package com.g52grp.database;

import java.sql.Date;

/**
 * @author psyfb2
 * Represents Job row within the database
 */
public class Job {
	private int jobId;
	private String siteName;
	private int plotNumber;
	private Date date;
	
	public Job(int jobId, String siteName, int plotNumber, Date date) {
		this.jobId = jobId;
		this.siteName = siteName;
		this.plotNumber = plotNumber;
		this.date = date;
	}

	public int getJobId() {
		return jobId;
	}

	public String getSiteName() {
		return siteName;
	}

	public int getPlotNumber() {
		return plotNumber;
	}

	public String getDate() {
		return date.toString();
	}
	
	public Date getDateObject() {
		return date;
	}
}
