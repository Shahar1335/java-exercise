package Logging;

import java.util.*;

public class EncryptionLogger implements Observer {
	
	private EncryptionLogEventsArgs event_args;
	
	public EncryptionLogger() {
		this.event_args = new EncryptionLogEventsArgs();
	}
	
	public void update(Observable encryptor, Object o) {
		String printout; //This is where the massage we print is stored.
		String message = (String)o; //This is the input message for the method.
		
		//When we start an operation, start the timer for the file/directory.
		if (message.split(" ")[0].equals("start")) {
			event_args.startTimer(message.split(" ")[1]);
		}
		
		else {
			event_args.endTimer(); //End the timer.
			event_args.setDetails(message); //Get the details from the message.
			
			//Calculate the printout message.
			printout = "The " + event_args.getAction() + " for " +
			                    event_args.getFile() + " with algorithm " +
					            event_args.getAlgorithm() + " took " +
			                    event_args.getTime(event_args.getFile()) + 
			                    " milliseconds. ";
			
			//Determine the rest of the sentence according to the acion.
			if (event_args.getAction().equals("encryption")) {
				printout = printout + "The encrypted ";
			}
			else {
				printout = printout + "The decrypted ";
			}
				
			//Finish it with the location of the output file.
			printout = printout + "file is located in " +
								event_args.getOutputFile() + " .";
				
			//Send an info message to the logger.
			EncryptionLog4JLogger.info(printout);
		}
	}

}
