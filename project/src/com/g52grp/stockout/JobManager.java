package com.g52grp.stockout;

import com.g52grp.database.Job;

/**
 * @author psyfb2 
 * For adding new jobs to the database
 */
public interface JobManager {
	
	/**
	 * Database Job table has columns (jobID (auto increment), siteName, plotNumber, startDate
	 * Connect to the database and add a new job to the Job table
	 * @param siteName The site name for the job to be created
	 * @param plotNumber The plot number of the job to be created
	 * @return Was adding the new job to the Job table successful
	 */
	boolean addNewJobToDb(String siteName, int plotNumber);
	
	/**
	 * @return A list of all jobs retrieved from the Job table, returns null if database cannot be accessed 
	 */
	Job[] getAllJobs();
}
