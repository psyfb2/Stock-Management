package com.g52grp.stockout;

import java.util.ArrayList;

import com.g52grp.database.Job;

/**
 * @author psyfb2 
 * Acts as interface for front end code to access jobs stored on MYSQL DB
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
	 * Deletes a job from the database permanently (all products linked to the job in JobStockLink table are also deleted by cascade)
	 * @param jobID jobID of the job to delete
	 * @return whether this operation was successful
	 */
	boolean deleteJob(int jobID);
	
	/**
	 * Label a job as archived
	 * @param jobID ID of job to archive
	 * @return success of operation
	 */
	boolean archiveJob(int jobID);
	
	/**
	 * Label a job as active
	 * @param jobID
	 * @return success of operation
	 */
	boolean unarchiveJob(int jobID);
	
	/**
	 * @return An ArrayList of all archived jobs which are not archived retrieved from the Job table, returns null if database cannot be accessed
	 */
	ArrayList<Job> getAllArchivedJobsArrayList();
	
	/**
	 * @return A list of all archived jobs in a array
	 */
	Job[] getAllArchivedJobs();
	
	/**
	 * @return A list of all jobs which are not archived retrieved from the Job table, returns null if database cannot be accessed 
	 */
	Job[] getAllJobs();
	
	/**
	 * @return A list of all jobs which are not archived as an ArrayList
	 */
	ArrayList<Job> getAllJobsArrayList();
}
