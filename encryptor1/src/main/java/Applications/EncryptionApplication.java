package Applications;

import java.io.IOException;

import ExecutionAids.*;

import com.google.inject.Inject;

public class EncryptionApplication {

	private IEncryptionPerformanceApplication enc_app;
	
	@Inject
	public EncryptionApplication(IEncryptionPerformanceApplication enc_app) {
		this.enc_app = enc_app;
	}
	
	public IEncryptionPerformanceApplication getApp() {
		return this.enc_app;
	}
	
	/* When en/decrypting a file, call the en/decryption method of the
	   file/directory application that the main application is using. */
	
	public void encryption(String filepath) 
			throws IOException, InvalidEncryptionKeyException, Exception {
		this.enc_app.encryption(filepath);
	}
	
	public void decryption(String filepath, String keyfilepath) 
			throws IOException, InvalidEncryptionKeyException, Exception {
		this.enc_app.decryption(filepath, keyfilepath);
	}
	
}
