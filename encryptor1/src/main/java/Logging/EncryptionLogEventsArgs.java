package Logging;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;

public class EncryptionLogEventsArgs {

	//This is where all the starting times are stored.
	private HashMap<String, Long> starting_time;
	//This is where we store the ending time.
	private long ending_time;
	//This is where the run's details are stored.
	private String [] details;
	//This is where we count how many files are finished.
	private static int num_of_files_done = 0;
	
	//We only need to initiate the starting times' HashMap.
	public EncryptionLogEventsArgs() {
		this.starting_time = new HashMap<String, Long>();
	}
	
	public static int getNumOfFilesDone() {
		return num_of_files_done;
	}
	
	public static void incrementNumOfFilesDone() {
		num_of_files_done++;
	}
	
	//Get the current time in milliseconds (switch from nano to milli).
	public long getTimeMillis() {
		long nanoTime = System.nanoTime();
		return TimeUnit.NANOSECONDS.toMillis(nanoTime);
	}
	
	//Set the starting time and the ending time.
	
	public void startTimer(String file) {
		//Add the starting time to the HashMap for the appropriate file.
		starting_time.put(file, getTimeMillis());
	}
	
	public HashMap<String, Long> getStartingTime() {
		return starting_time;
	}
	
	public void endTimer() {
		ending_time = getTimeMillis(); 
	}
	
	//Get the total time.
	public long getTime(String file) {
		long total_time = ending_time - starting_time.get(file);
		return total_time;
	}
	
	//Split the string containing the details we need for the user.
	public void setDetails(String details_in) {
		String [] details_split = details_in.split(" ");
		details = details_split;
	}
	
	public String [] getDetails() {
		return details;
	}
	
	public String getAction() {
		return details[0];
	}
	
	public String getFile() {
		return details[1];
	}
	
	public String getAlgorithm() {
		return details[2];
	}
	
	public String getOutputFile() {
		return details[3];
	}
	
	public Timestamp getTimeStamp() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}
	
	public boolean equals(EncryptionLogEventsArgs args) {
		//Compare the starting times HashMaps and the ending times.
		boolean times_equal = starting_time.equals(args.starting_time)
								&& ending_time == args.ending_time;
		boolean details_equal = true;
		
		//Check for each of details's values, whether they are equal.
		for (int i = 0; i < details.length; i++) {
			if (!details[i].equals(args.getDetails()[i])) {
				details_equal = false;
			}
		}
		
		//Return true iff both the times and the details are equal.
		return times_equal && details_equal;	
	}
	
	public int hashCode() {
		int hash = 1;
		
		//Compute the hash using the times and the details.
		
		hash *= this.starting_time.hashCode() + this.ending_time;
		
		for (int i = 0; i < details.length; i++) {
			hash *= details[i].hashCode();
		}
		
		return hash;
	}
	
}
