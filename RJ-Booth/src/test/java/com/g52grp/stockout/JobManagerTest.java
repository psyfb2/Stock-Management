package com.g52grp.stockout;

import java.util.ArrayList;
import java.util.Comparator;

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
		
		assertEquals(2, allJobs.length);
		
		// test first job
		assertEquals(1, allJobs[0].getJobId());
		assertEquals("testing ground", allJobs[0].getSiteName());
		assertEquals(1, allJobs[0].getPlotNumber());
		assertEquals("2019-04-12", allJobs[0].getDate());
		assertEquals(false, allJobs[0].getArchived());
	}
	
	/**
	 * Test getting all jobs as an array and that the third job is correct
	 */
	@Test
	public void testGetAllJobs3() {
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(2, allJobs.length);
		
		// test third job
		assertEquals(3, allJobs[1].getJobId());
		assertEquals("Empty Job1", allJobs[1].getSiteName());
		assertEquals(10124701, allJobs[1].getPlotNumber());
		assertEquals("2019-04-12", allJobs[1].getDate());
		assertEquals(false, allJobs[1].getArchived());
	}
	
	
	/**
	 * Test getting all jobs as an array list
	 */
	@Test
	public void testGetAllJobsArrayList() {
		ArrayList<Job> allJobs = jm.getAllJobsArrayList();
		
		assertEquals(2, allJobs.size());
		// uses same method as getAllJobs so don't need to test all jobs seperately again
	}
	
	/**
	 * Test deleting a single job, normal case: giving a valid jobID
	 */
	@Test 
	public void testDeleteJob() {
		assertEquals(true, jm.deleteJob(2));
		Job[] allJobs = jm.getAllJobs();
		
		assertEquals(2, allJobs.length);
		
		// confirm the correct job was deleted
		assertEquals(1, allJobs[0].getJobId());
		assertEquals(3, allJobs[1].getJobId());
	}
	
	/**
	 * Test deleting a single job, erroneous case: giving an invalid ID
	 */
	@Test 
	public void testDeleteJobUnvalidId() {
		assertEquals(true, jm.deleteJob(400));
		
		assertEquals(3, jm.getAllJobs().length + jm.getAllArchivedJobsArrayList().size());
		
	}
	
	/**
	 * Test getting archived jobs
	 */
	@Test
	public void testGetAllArchivedJobs() {
		ArrayList<Job> archivedJobs = jm.getAllArchivedJobsArrayList();
		assertEquals(1, archivedJobs.size());
		
		Job j = archivedJobs.get(0);
		assertEquals(2, j.getJobId());
		assertEquals("testing ground", j.getSiteName());
		assertEquals(2, j.getPlotNumber());
		assertEquals("2019-05-12", j.getDate());
		assertEquals(true, j.getArchived());
	}
	
	/**
	 * Test archiving a job
	 */
	@Test
	public void testArchiveJob() {
		assertEquals(true, jm.archiveJob(1));
		
		ArrayList<Job> archivedJobs = jm.getAllArchivedJobsArrayList();
		assertEquals(2, archivedJobs.size());
		
		sortJobsByID(archivedJobs);
		assertEquals(1, archivedJobs.get(0).getJobId());
		assertEquals(2, archivedJobs.get(1).getJobId());
	}
	
	/**
	 * Test archiving a job which does not exist
	 */
	@Test
	public void testArchiveJobInvalid() {
		assertEquals(true, jm.archiveJob(10000)); 
		
		ArrayList<Job> archivedJobs = jm.getAllArchivedJobsArrayList();
		assertEquals(1, archivedJobs.size());
		
		Job j = archivedJobs.get(0);
		assertEquals(2, j.getJobId());
	}
	
	/**
	 * Test un-archiving a job
	 */
	@Test
	public void testUnarchiveJob() {
		assertEquals(true, jm.unarchiveJob(2));
		
		assertEquals(3, jm.getAllJobs().length);
	}
	
	/**
	 * Test un-archiving a job which does not exist
	 */
	@Test
	public void testUnarchiveJobInvalid() {
		assertEquals(true, jm.unarchiveJob(20));
		
		assertEquals(2, jm.getAllJobs().length);
	}
	
	private void sortJobsByID(ArrayList<Job> jobs) {
		jobs.sort(new Comparator<Job>() {
			public int compare(Job l, Job r) {
				return l.getJobId() < r.getJobId() ? -1 : (l.getJobId() > r.getJobId()) ? 1 : 0;
			}
		});
	}
}
