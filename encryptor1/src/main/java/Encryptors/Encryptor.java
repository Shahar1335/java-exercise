package Encryptors;

import java.io.IOException;

import Algorithms.*;
import ExecutionAids.*;
import Keys.*;
import Logging.*;

public abstract class Encryptor extends StartEnd {

	protected EncryptionAlgorithm enc_algo;
	protected FileOperations fo;
	protected boolean soloFile;
	
	
	public Encryptor(EncryptionAlgorithm enc_algo) {
			this.enc_algo = enc_algo;
			this.fo = new FileOperations();
	}
	   
	public Encryptor(EncryptionAlgorithm enc_algo, FileOperations fo) {
			this.enc_algo = enc_algo;
			this.fo = fo;
	}
	
	public EncryptionAlgorithm getAlgorithm() {
		return this.enc_algo;
	}
	
	public void setAlgorithm(EncryptionAlgorithm enc_algo) {
		this.enc_algo = enc_algo;
	}
	
	public void setFileOperations(FileOperations fo) {
		this.fo = fo;
	}

	/* When calling for en/decryption, call the operation method with the
	   appropriate arguments. */
	
	public <T> void encryption(String filepath, Key<T> key, String outpath) 
			throws IOException, InvalidEncryptionKeyException, Exception {
		operation(filepath, key, "", outpath, "encryption");
	}
	
	public <T> void decryption(String filepath, Key<T> key, String keypath, 
			String outpath) throws 
			IOException, InvalidEncryptionKeyException, Exception {
		operation(filepath, key, keypath, outpath, "decryption");
	}
	
	//This method performs the action on the given file.
	abstract <T> void operation(String filepath, Key<T> key, String keypath, 
			String outpath, String action) throws 
			IOException, InvalidEncryptionKeyException, Exception;
	
	//This method is used for reading/creating the keys.
	abstract public <T> Key<T> readKey(String filepath, String action) 
			throws InvalidEncryptionKeyException, IOException;

	public FileOperations getFO() {
		return this.fo;
	}
	
	//This method is used for creating a message that the operation has ended.
	public String endMessage(String action, String file, String resultFile) {
		String algo = this.enc_algo.toString();
		String message = action + " " + file + " " + algo + " " + resultFile;
		
		return message;
	}
	
	/* We check whether the file is empty as follows: If we have already
	   received the key, then we are operating a directory. Otherwise,
	   we are operating a single file and still need to compute the key. */
	public <T> void checkSoloFile(Key<T> key) {
		if (key == null) {
			this.soloFile = true;
		}
		
		else {
			this.soloFile = false;
		}
	}
	
}
