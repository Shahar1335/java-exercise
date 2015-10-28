package DirectoryProcessors;

import java.io.File;

import Encryptors.*;
import Keys.*;
import XML.*;

import com.google.inject.Singleton;
import com.google.inject.Inject;

@Singleton
public class SyncDirectoryProcessor extends DirectoryProcessor {

	@Inject
	public SyncDirectoryProcessor(Encryptor enc) {
		super(enc);
	}
	
	public SyncDirectoryProcessor() {
		super();
	}

	public <T> void operate(String dirpath, String keypath, String action)
			throws Exception {
		File dir = new File(dirpath); //Create the instance of the directory.
		File [] file_list; //This is the list of the files in the directory.
		File dir_post; //This will be the directory with the results.
		String outpath; //This is the path to the result for each file.
		String curr_path; //This is the path for the current file.
		Key<T> key; //This is the Key instance.
		
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
		
		file_list = dir.listFiles(); //Get the list of files.
		
		//If the output directory doesn't exist already, create it.
		if (!dir_post.exists()) {
			dir_post.mkdir();
		}
		
		//Go over all of the directory's files.
		for (int i = 0; i < file_list.length; i++) {
			curr_path = file_list[i].getPath(); //Save the current file's path.
			
			//Only operate on FILES that are .TXT, otherwise dismiss.
			if (file_list[i].isFile() && enc.getFO().is_txt(curr_path)) {
				//Create the path for the output file.
				outpath = dir_post.getPath() + "\\" + file_list[i].getName();
				
				//Perform the action on the file.
				if (action.equals("encryption")) {
					enc.encryption(curr_path, key, outpath);
				}
				else {
					enc.decryption(curr_path, key, null, outpath);
				}
			}
		}
		
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
		return "SyncDirectoryProcessor";
	}

}
