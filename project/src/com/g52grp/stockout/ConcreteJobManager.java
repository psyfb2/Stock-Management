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
			PreparedStatement ps = con.getPreparedStatement("SELECT CASE WHEN EXISTS(SELECT * FROM Jobs WHERE siteName = ? AND plotNumber = ?) THEN 'true' ELSE 'false' END as jobExists");
			ps.setString(1, siteName);
			ps.setInt(2, plotNumber);
			ps.toString();
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getString("jobExists").equals("true")) {
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
	public Job[] getAllJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Jobs");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				jobs.add(new Job(rs.getInt("jobID"), rs.getString("siteName"), rs.getInt("plotNumber"), rs.getDate("startDate")));
			}
			ps.close();
			Job[] jobsArr = new Job[jobs.size()];
			jobsArr = jobs.toArray(jobsArr);
			return jobsArr;
		} catch(SQLException e) {
			return null;
		}
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

}
