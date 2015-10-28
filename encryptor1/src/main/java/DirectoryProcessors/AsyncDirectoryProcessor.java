package DirectoryProcessors;

import java.io.File;

import Encryptors.*;
import ExecutionAids.*;
import Keys.*;
import Logging.*;
import XML.*;

import com.google.inject.Singleton;
import com.google.inject.Inject;

@Singleton
public class AsyncDirectoryProcessor extends DirectoryProcessor {

	@Inject
	public AsyncDirectoryProcessor(Encryptor enc) {
		super(enc);
	}
	
	public AsyncDirectoryProcessor() {
		super();
	}

	public <T> void operate(String dirpath, String keypath, String action)
			throws Exception {
		File dir = new File(dirpath); //Create the instance of the directory.
		File dir_post; //This will be the directory with the results.
		File [] file_lst; //This is the list of the files in the directory.
		String outpath; //This is the path to the result for each file.
		String curr_path; //This is the path for the current file.
		int num_txt_files = 0; //This is the amount of .txt files.
		Key<T> key; //This is the Key instance.
		ExecThread runnable; //This is the Runnable instance.
		Thread operate; //This is used to create a new thread for each file.
		
		/* Name the directory with the output according to the action taken.
		   Read/create the keys and save them. */
		
		if (action.equals("encryption")) {
			dir_post = new File(dirpath + "\\encrypted");
			key = this.enc.readKey(keypath, "encryption");
		}
		
		else {
			dir_post = new File(dirpath + "\\decrypted");
			key = this.enc.readKey(keypath, "decryption");
		}
		
		file_lst = dir.listFiles(); //Get the list of files.
		
		//If the output directory doesn't exist already, create it.
		if (!dir_post.exists()) {
			dir_post.mkdir();
		}
		
		//Go over all of the directory's files.
		for (int i = 0; i < file_lst.length; i++) {
			curr_path = file_lst[i].getPath(); //Save the current file's path.
			
			//Only operate on FILES that are .TXT, otherwise dismiss.
			if (file_lst[i].isFile() && enc.getFO().is_txt(curr_path)) {
				num_txt_files++; //Increment the number of text files.
				
				//Create the path for the output file.
				outpath = dir_post.getPath() + "\\" + file_lst[i].getName();
				
				//Create the runnable instance and start a new thread.
				runnable = new ExecThread(action, curr_path, 
													key, outpath, this.enc);
				operate = new Thread(runnable);
				operate.start();
			}
		}
		
		//Wait until all threads have finished their task.
		while (EncryptionLogEventsArgs.getNumOfFilesDone() != num_txt_files);
		
		//In case of encryption, save the key.
		if (action.equals("encryption")) {
			keypath = dirpath + "\\key.txt";
			this.fo.writeIntoFile(keypath, key.toString());
		}
		
		//Inform the logger that the operation on the folder is over.
		if (action.equals("encryption")) {
			EncryptionEnded(dirpath, dir_post.getPath());
		}
		else {
			DecryptionEnded(dirpath, dir_post.getPath());
		}
		
		//Write the results into an XML file.
		XMLMethods.XMLWrite(dirpath, action, enc, enc.getAlgorithm(), 
												keypath, dir_post.getPath());
	}
	
	public String toString() {
		return "AsyncDirectoryProcessor";
	}

}
