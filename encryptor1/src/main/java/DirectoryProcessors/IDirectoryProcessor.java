package DirectoryProcessors;

import java.io.IOException;

import ExecutionAids.*;

public interface IDirectoryProcessor {

	//This methods encrypts a directory.
	abstract void encryption(String dirpath) 
			throws IOException, InvalidEncryptionKeyException, Exception;
	
	//This methods decrypts a directory.
	abstract void decryption(String dirpath, String keypath) 
			throws IOException, InvalidEncryptionKeyException, Exception;
	
	//This method operates the given action on a directory.
	abstract <T> void operate(String dirpath, String keypath, String action) 
			throws IOException, InvalidEncryptionKeyException, Exception;
	
}
