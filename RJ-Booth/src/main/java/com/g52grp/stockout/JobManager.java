package com.g52grp.stockout;

import java.util.ArrayList;

import com.g52grp.database.Job;

/**
 * @author psyfb2 
 * For adding new jobs to the database
 */
public interface JobManager {
	
	/**
	 * Database Job table has columns (jobID (auto increment), siteName, plotNumber, startDate
	 * Connect to the database and add a new job to the Job table
	 * NOTE: although siteName and plotNumber are not in the PRIMARY KEY these two attributes combined together must be unique
	 * (i.e. not two jobs can have the same site name and plot number)
	 * @param siteName The site name for the job to be created
	 * @param plotNumber The plot number of the job to be created
	 * @return False if database could be accessed or if a job with the same site name and plot number already exists
	 */
	boolean addNewJobToDb(String siteName, int plotNumber);
	
	
	/**
	 * Deletes a job from the database (all products linked to the job in JobStockLink table are also deleted by cascade)
	 * @param jobID jobID of the job to delete
	 * @return whether this operation was successful
	 */
	boolean deleteJob(int jobID);
	
	/**
	 * @return A list of all jobs retrieved from the Job table, returns null if database cannot be accessed 
	 */
	Job[] getAllJobs();
	
	ArrayList<Job> getAllJobsArrayList();
}
