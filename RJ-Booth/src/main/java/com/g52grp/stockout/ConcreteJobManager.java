package com.g52grp.stockout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.g52grp.database.DatabaseConnection;
import com.g52grp.database.Job;

public class ConcreteJobManager implements JobManager {

	private DatabaseConnection con;
	
	public ConcreteJobManager(DatabaseConnection con) {
		this.con = con;
	}

	@Override
	public boolean addNewJobToDb(String siteName, int plotNumber) {
		try {
			// check this job doesn't already exist, if it does return false
			PreparedStatement ps = con.getPreparedStatement("SELECT IF(EXISTS(SELECT * FROM Jobs WHERE siteName = ? AND plotNumber = ?), 'true', 'false');");
			ps.setString(1, siteName);
			ps.setInt(2, plotNumber);
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getString(1).equals("true")) {
				return false;
			}
			
			ps = con.getPreparedStatement("INSERT INTO Jobs (siteName, plotNumber, startDate) VALUES (?, ?, CURDATE())");
			ps.setString(1, siteName);
			ps.setInt(2, plotNumber);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public ArrayList<Job> getAllJobsArrayList() {
		return getAllJobs(false);
	}

	@Override
	public Job[] getAllJobs() {
		ArrayList<Job> jobs = getAllJobsArrayList();
		Job[] jobsArr = new Job[jobs.size()];
		jobsArr = jobs.toArray(jobsArr);
		return jobsArr;
	}

	@Override
	public boolean deleteJob(int jobID) {
		try {
			PreparedStatement ps = con.getPreparedStatement("DELETE FROM Jobs WHERE jobID = ?");
			ps.setInt(1, jobID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean archiveJob(int jobID) {
		return setArchived(jobID, true);
	}

	@Override
	public boolean unarchiveJob(int jobID) {
		return setArchived(jobID, false);
	}

	@Override
	public Job[] getAllArchivedJobs() {
		ArrayList<Job> jobs = getAllArchivedJobsArrayList();
		Job[] jobsArr = new Job[jobs.size()];
		jobsArr = jobs.toArray(jobsArr);
		return jobsArr;
	}

	private boolean setArchived(int jobID, boolean archived) {
		try {
			PreparedStatement ps = con.getPreparedStatement("UPDATE Jobs SET archived = ? WHERE jobID = ?");
			ps.setBoolean(1, archived);
			ps.setInt(2, jobID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	private ArrayList<Job> getAllJobs(boolean archived) {
		ArrayList<Job> jobs = new ArrayList<Job>();
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Jobs WHERE archived = ?");
			ps.setBoolean(1, archived);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				jobs.add(new Job(rs.getInt("jobID"), rs.getString("siteName"), rs.getInt("plotNumber"), rs.getDate("startDate"), rs.getBoolean("archived")));
			}
			ps.close();
			return jobs;
		} catch(SQLException e) {
			return null;
		}
	}

	@Override
	public ArrayList<Job> getAllArchivedJobsArrayList() {
		return getAllJobs(true);
	}

	@Override
	public boolean isArchived(int jobID) {
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT archived FROM Jobs WHERE jobID = ?");
			ps.setInt(1, jobID);
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getBoolean("archived")) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}


}
