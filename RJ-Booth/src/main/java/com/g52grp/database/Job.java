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
	private boolean archived;
	
	public Job(int jobId, String siteName, int plotNumber, Date date, boolean archived) {
		this.jobId = jobId;
		this.siteName = siteName;
		this.plotNumber = plotNumber;
		this.date = date;
		this.archived = archived;
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
		String d = date.toString();
		return d.substring(8, 10) + "-" + d.substring(5, 7) + "-" + d.substring(0, 4);
	}
	
	public boolean getArchived() {
		return archived;
	}
	
	public String toString() {
		return getSiteName() + " " + getPlotNumber();
	}
}
