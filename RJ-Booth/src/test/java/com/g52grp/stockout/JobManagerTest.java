package com.g52grp.stockout;

import java.util.ArrayList;

import org.junit.Test;

import com.g52grp.database.Job;

/**
 * Tests JobManager backend class which utilises SQL statements.
 * Runs with dbunit to set up and tear down database tables and HyperSQL as a mysql database running on the local machine.
 * Note: Between Tests database is reset to a known state (delete all then insert data set again between tests) so that tests don't interfere with one another
 * @author psyfb2
 */
public class JobManagerTest extends TestDB {
	private ConcreteJobManager jm;
	
	public JobManagerTest(String name) {
		super( name );
		jm = new ConcreteJobManager(con);
	}
	
	/**
	 * Test getting all jobs as an array and that the first job is correct
	 */
	@Test
	public void testGetAllJobs1() {
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(3, allJobs.length);
		
		// test first job
		assertEquals(1, allJobs[0].getJobId());
		assertEquals("testing ground", allJobs[0].getSiteName());
		assertEquals(1, allJobs[0].getPlotNumber());
		assertEquals("2019-04-12", allJobs[0].getDate());
		assertEquals(false, allJobs[0].getArchived());
	}
	
	/**
	 * Test getting all jobs as an array and that the second job is correct
	 */
	@Test
	public void testGetAllJobs2() {
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(3, allJobs.length);

		// test second job
		assertEquals(2, allJobs[1].getJobId());
		assertEquals("testing ground", allJobs[1].getSiteName());
		assertEquals(2, allJobs[1].getPlotNumber());
		assertEquals("2019-05-12", allJobs[1].getDate());
		assertEquals(true, allJobs[1].getArchived());
	}
	
	/**
	 * Test getting all jobs as an array and that the third job is correct
	 */
	@Test
	public void testGetAllJobs3() {
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(3, allJobs.length);
		
		// test third job
		assertEquals(3, allJobs[2].getJobId());
		assertEquals("Empty Job1", allJobs[2].getSiteName());
		assertEquals(10124701, allJobs[2].getPlotNumber());
		assertEquals("2019-04-12", allJobs[2].getDate());
		assertEquals(false, allJobs[2].getArchived());
	}
	
	
	/**
	 * Test getting all jobs as an array list
	 */
	@Test
	public void testGetAllJobsArrayList() {
		ArrayList<Job> allJobs = jm.getAllJobsArrayList();
		
		assertEquals(3, allJobs.size());
		// uses same method as getAllJobs so don't need to test all jobs seperately again
	}
	
	/**
	 * Test deleting a single job, normal case: giving a valid jobID
	 */
	@Test 
	public void testDeleteJob() {
		assertEquals(true, jm.deleteJob(3));
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(2, allJobs.length);
		
		// confirm the correct job was deleted
		assertEquals(1, allJobs[0].getJobId());
		assertEquals(2, allJobs[1].getJobId());
	}
	
	/**
	 * Test deleting a single job, erroneous case: giving an invalid ID
	 */
	@Test 
	public void testDeleteJobUnvalidId() {
		assertEquals(true, jm.deleteJob(400));
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(3, allJobs.length);
	}
}
