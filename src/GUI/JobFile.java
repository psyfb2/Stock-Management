package GUI;

import java.io.FileWriter;
import java.util.ArrayList;

public class JobFile {
	public ArrayList<String> lines;
	private static String path = "job.txt";
	
	public JobFile(){
		this.lines = new ArrayList<>();
	}
	
	public void writeJobFile() {
		FileWriter writer = null;
	}
}
