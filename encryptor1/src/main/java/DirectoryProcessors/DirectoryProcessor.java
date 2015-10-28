package DirectoryProcessors;

import Encryptors.*;
import ExecutionAids.*;
import Logging.*;

public abstract class DirectoryProcessor 
					extends StartEnd implements IDirectoryProcessor {

	protected Encryptor enc;
	protected FileOperations fo = new FileOperations();
	
	public DirectoryProcessor(Encryptor enc) {
		this.enc = enc;
	}
	
	public DirectoryProcessor() {}
	
	public void setEncryptor(Encryptor encryptor) {
		this.enc = encryptor;
	}
	
	public Encryptor getEncryptor() {
		return this.enc;
	}
	
	/* When en/decrypting, inform that the operation has started, and then
	   perform it with the "operate" method. */
	
	public void encryption(String dirpath) 
			throws Exception {
		EncryptionStarted(dirpath);
		this.operate(dirpath, "", "encryption");
	}

	public void decryption(String dirpath, String keypath)  
			throws Exception {
		DecryptionStarted(dirpath);
		this.operate(dirpath, keypath, "decryption");
	}
	
	@Override
	public String endMessage(String action, String file, String resultFile) {
		return this.enc.endMessage(action, file, resultFile);
	}
	
}
