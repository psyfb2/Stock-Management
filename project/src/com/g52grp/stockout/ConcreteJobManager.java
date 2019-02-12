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
			PreparedStatement ps = con.getPreparedStatement("INSERT INTO Jobs (siteName, plotNumber, startDate) VALUES (?, ?, CURDATE())");
			ps.setString(1, siteName);
			ps.setInt(2, plotNumber);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
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

}
