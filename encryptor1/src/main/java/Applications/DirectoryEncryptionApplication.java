package Applications;

import DirectoryProcessors.*;
import Encryptors.*;

import com.google.inject.*;

@Singleton
public class DirectoryEncryptionApplication 
					implements IEncryptionPerformanceApplication {

	private DirectoryProcessor dp;
	
	@Inject
	public DirectoryEncryptionApplication(DirectoryProcessor dp) {
		this.dp = dp;
	}
	
	public DirectoryProcessor getDirectoryProcessor() {
		return this.dp;
	}
	
	public Encryptor getEncryptor() {
		return this.dp.getEncryptor();
	}
	
	//En/decrypt with the directory processor's en/decryption methods.
	
	public void encryption(String dirpath) 
			throws Exception {
		dp.encryption(dirpath);
	}
	
	public void decryption(String dirpath, String keypath)  
			throws Exception {
		dp.decryption(dirpath, keypath);
	}
	
}
