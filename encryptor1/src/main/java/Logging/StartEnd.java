package Logging;

import java.util.Observable;

public abstract class StartEnd extends Observable {

	public abstract String endMessage(String action, String file, String resultFile);
	
	//Upon starting, notify the logger with "start" and the file's name.
	public void Started(String filename) {
		setChanged();
		notifyObservers("start " + filename);
	}
	
	//Upon ending, notify the logger with the appropriate ending message.
	public void Ended(String action, String filename, String outfile) {
		String message = endMessage(action, filename, outfile);
		setChanged();
		notifyObservers(message);
	}
	
	/* When we start, we only need to give the name of the file/directory.
	   When we finish, we need to say what the action was, and what the
	   original and result files are.
	   Only a single thread may notify the logger at a time, in order to
	   avoid problems with databases.
	 */
	
	public synchronized void EncryptionStarted(String filename) {
		Started(filename);
	}
	
	public synchronized void EncryptionEnded(String filename, String encfile) {
		Ended("encryption", filename, encfile);
	}

	public synchronized void DecryptionStarted(String filename) {
		Started(filename);
	}
	
	public synchronized void DecryptionEnded(String filename, String decfile) {
		Ended("decryption", filename, decfile);
	}
	
}
