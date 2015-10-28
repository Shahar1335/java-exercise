package Encryptors;

import Algorithms.*;
import ExecutionAids.*;

import com.google.inject.*;

@Singleton
public class FileEncryptor extends RepeatEncryption {
	
	
	public FileEncryptor(EncryptionAlgorithm enc_algo) {
		//We perform the algorithm only once, therefore n = 1.
		super(enc_algo, 1);
	}
	
	@Inject
	public FileEncryptor(EncryptionAlgorithm enc_algo, FileOperations fo) {
		//We perform the algorithm only once, therefore n = 1.
		super(enc_algo, fo, 1);
	}
	
	public String toString() {
		return "FileEncryptor";
	}

}
