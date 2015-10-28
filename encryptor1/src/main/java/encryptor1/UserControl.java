package encryptor1;

import java.io.IOException;
import java.util.*;

import Applications.*;
import DirectoryProcessors.*;
import Encryptors.*;
import ExecutionAids.*;
import Injectors.*;
import Logging.*;
import XML.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class UserControl {
	
	static Scanner reader = new Scanner(System.in);

	public static void main(String [] args) throws Exception {
		XML();
	}
	
	public static void inject()  throws Exception{
		//The user chooses whether they want to encrypt  or decrypt the text.
		System.out.println("Please choose between encryption and decryption:");
		String enc_or_dec = reader.next();
		System.out.println("Please enter the path to the file:");
		String filepath = reader.next();
		String keyfilepath;
		
		Injector injector_dir = Guice.createInjector(new EncryptionAppInjector());
		EncryptionApplication app =
				injector_dir.getInstance(EncryptionApplication.class);
			
			
		//Let the encryption logger observe the encryptor.
		EncryptionLogger enc_log = new EncryptionLogger();
		app.getApp().getEncryptor().addObserver(enc_log);
		
		if (app.getApp() instanceof DirectoryEncryptionApplication) {
			((DirectoryEncryptionApplication) 
					app.getApp()).getDirectoryProcessor().addObserver(enc_log);
		}
			
		try {
			if (enc_or_dec.equals("encryption")) {
				System.out.println("\n");
				
				app.encryption(filepath);
			}
			
			else if (enc_or_dec.equals("decryption")) {
				//Enter the path to the file with the key.
				System.out.println("Please enter the path to the key file:");
				keyfilepath = reader.next(); 
				System.out.println("\n");

				app.decryption(filepath, keyfilepath);
			}
		}
					
				
		catch (InvalidEncryptionKeyException e) {
			String msg = e.getMessage();
			String lvl = e.getLevel();
			EncryptionLog4JLogger.fatal_or_error(e, msg, lvl);
		}
				
		catch (IOException e) {
			String msg = "The paths that were passed are not valid.";
			EncryptionLog4JLogger.fatal_or_error(e, msg, "fatal");
		}
				
		catch (Exception e) {
			String msg = "The run had some problems.";
			EncryptionLog4JLogger.fatal_or_error(e, msg, "fatal");
		}
	}
	
	public static void XML() throws Exception {
		//The user chooses whether they want to encrypt  or decrypt the text.
		//System.out.println("Please choose between ENCRYPTION and DECRYPTION:");
		String enc_or_dec; // = reader.next();
		//System.out.println("Please enter the path to the file:");
		String filepath; // = reader.next();
		DirectoryProcessor processor = null;
		String keyfilepath;
		SAXReader xml_read = new SAXReader();
		InputData data = xml_read.XMLRead("xml_input_file.xml");
		
		enc_or_dec = data.getEncryptOrDecrypt();
		filepath = data.getPathGiven();
		
		//Determine which encryption algorithm will be used.
		Encryptor encryptor = data.getEncryptorUsed();
		encryptor.setAlgorithm(data.getAlgorithmUsed());
		
		if (data.getFileOrDir().equals("Directory")) {
			processor = data.getSyncOrAsync();
			processor.setEncryptor(encryptor);
		}
		//SyncDirectoryProcessor asdp = new SyncDirectoryProcessor(fe);
		
		//Let the encryption logger observe the encryptor.
		EncryptionLogger enc_log = new EncryptionLogger();
		encryptor.addObserver(enc_log);
		
		if (data.getFileOrDir().equals("Directory")) {
			processor.addObserver(enc_log);
		}
		
		try {
			if (enc_or_dec.equals("encryption")) {
				//System.out.println("\n");
				
				if (data.getFileOrDir().equals("Directory")) {
					processor.encryption(filepath);
				}
				else if (data.getFileOrDir().equals("File")) { 
					encryptor.encryption(filepath, null, "");
				}
			}
			
			else if (enc_or_dec.equals("decryption")) {
				//Enter the path to the file with the key.
				//System.out.println("Please enter the path to the key file:");
				keyfilepath = data.getPathToKey(); //reader.next(); 
				//System.out.println("\n");
				if (data.getFileOrDir().equals("Directory")) {
					processor.decryption(filepath, keyfilepath);
				}
				else if (data.getFileOrDir().equals("File")) { 
					encryptor.decryption(filepath, null, keyfilepath, "");
				}
			}
		}
			
		
		catch (InvalidEncryptionKeyException e) {
			String msg = e.getMessage();
			String lvl = e.getLevel();
			EncryptionLog4JLogger.fatal_or_error(e, msg, lvl);
		}
		
		catch (IOException e) {
			String msg = "The paths that were passed are not valid.";
			EncryptionLog4JLogger.fatal_or_error(e, msg, "fatal");
		}
		
		catch (Exception e) {
			String msg = "The run had some problems.";
			EncryptionLog4JLogger.fatal_or_error(e, msg, "fatal");
		}
	}
	
}
