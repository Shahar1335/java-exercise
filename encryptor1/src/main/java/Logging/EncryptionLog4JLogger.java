package Logging;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.management.*;

import org.apache.log4j.*;

public class EncryptionLog4JLogger {
	
	static Logger log = 
			Logger.getLogger(EncryptionLog4JLogger.class.getName());
	
	static EncryptionLogEventsArgs args = new EncryptionLogEventsArgs();
	
	//This method returns the PID for the message.
	private static long getPID() {
	    String PName = ManagementFactory.getRuntimeMXBean().getName();
	    return Long.parseLong(PName.split("@")[0]);
	  }
	
	public static void fatal_or_error(Exception e, String msg, String lvl) 
			throws UnknownHostException {
		//Store the thread ID, the PID and the host name for the message.
		long threadID = Thread.currentThread().getId();
		long PID = getPID();
		String hostname = InetAddress.getLocalHost().getHostName();
		
		//Create the string for the message, while also getting the time stamp.
		String log_msg = msg + "\n" +
						 "The time stamp is " + args.getTimeStamp() + "\n" +
						 "The host name is " + hostname + "\n" +
						 "The thread ID is " + threadID + "\n" +
						 "The process ID is " + PID;
		
		//Send the message with the appropriate level.
		
		if (lvl.equals("fatal")) {
			log.fatal(log_msg, e);
		}
		
		else if (lvl.equals("error")) {
			log.error(log_msg, e);
		}
	}
	
	//Send info messages with the time stamp.
	public static void info(String msg) {
		log.info(msg + " The time stamp is " + args.getTimeStamp());
	}
	
	//Send debug messages.
	public static void debug(String msg) {
		log.debug(msg);
	}

}
