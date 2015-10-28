package ExecutionAids;

import java.io.IOException;
import java.net.UnknownHostException;

import Encryptors.*;
import Keys.*;
import Logging.*;

public class ExecThread implements Runnable {

	private String action; //The action we are taking.
	private String filepath; //The file we are operating on.
	private Key<Object> key; //The key we are using.
	private String outpath; //The path we are writing into.
	private Encryptor enc; //The encryptor we are using.
	
	@SuppressWarnings("unchecked")
	public <T> ExecThread(String action, String filepath, 
								Key<T> key, String outpath, Encryptor enc) {
		this.action = action;
		this.filepath = filepath;
		this.key = (Key<Object>)key;
		this.outpath = outpath;
		this.enc = enc;
	}
	
	public void run() {
		
		//Use the encryptor's method that responds to the action we are taking.
		try {
			if (this.action.equals("encryption")) {
				this.enc.encryption(this.filepath, this.key, this.outpath);
			}
			else {
				this.enc.decryption(this.filepath, this.key, 
											   null, this.outpath);
			}
		}
			
		//Handle IO and InvalidKey exceptions, as well as others.
		catch (IOException e) {
			String msg = "The paths that were passed are not valid.";
			
			try {
				EncryptionLog4JLogger.fatal_or_error(e, msg, "fatal");
			} 
			
			catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			
		} 
		
		catch (InvalidEncryptionKeyException e) {
			String msg = e.getMessage();
			String lvl = e.getLevel();
			
			try {
				EncryptionLog4JLogger.fatal_or_error(e, msg, lvl);
			} 
			
			catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

