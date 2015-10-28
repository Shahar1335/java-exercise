package Applications;

import Encryptors.*;

public interface IEncryptionPerformanceApplication {

	//This method encrypts the given path.
	abstract public void encryption(String filepath) throws Exception;
	
	//This method decrypts the given path.
	abstract public void 
			decryption(String filepath, String keypath) throws Exception;

	//This method is used to get the encryptor used by the application.
	abstract public Encryptor getEncryptor();
	
}
