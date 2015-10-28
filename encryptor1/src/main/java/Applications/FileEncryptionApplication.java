package Applications;

import java.io.IOException;

import Encryptors.*;
import ExecutionAids.*;

import com.google.inject.*;

@Singleton
public class FileEncryptionApplication 
							implements IEncryptionPerformanceApplication {
	
	private Encryptor encryptor;
	
	@Inject
	public FileEncryptionApplication(Encryptor encryptor) {
		this.encryptor = encryptor;
	}
	
	public Encryptor getEncryptor() {
		return this.encryptor;
	}
	
	//En/decrypt with the encryptor's en/decryption methods.
	
	public void encryption(String filepath) 
			throws IOException, InvalidEncryptionKeyException, Exception {
		this.encryptor.encryption(filepath, null, "");
	}
	
	public void decryption(String filepath, String keyfilepath) 
			throws IOException, InvalidEncryptionKeyException, Exception {
		this.encryptor.decryption(filepath, null, keyfilepath, "");
	}

}
