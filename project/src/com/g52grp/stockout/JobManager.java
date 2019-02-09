package com.g52grp.stockout;

import com.g52grp.database.Job;

/**
 * @author psyfb2 - Audery, Zhongyue implement this (work on only one class which implement JobManager)
 * For adding new jobs to the database
 */
public interface JobManager {
	
	/**
	 * --- AUDERY IMPLEMENT THIS METHOD ---
	 * Database Job table has columns (jobID (auto increment), siteName, plotNumber, startDate
	 * Connect to the database and add a new job to the Job table
	 * @param siteName The site name for the job to be created
	 * @param plotNumber The plot number of the job to be created
	 * @return Was adding the new job to the Job table successful
	 */
	boolean addNewJobToDb(String siteName, int plotNumber);
	
	/**
	 * --- ZHONGYUE IMPLEMENT THIS METHOD --- 
	 * @return A list of all jobs within the Job table
	 */
	Job[] getAllJobs();
}
