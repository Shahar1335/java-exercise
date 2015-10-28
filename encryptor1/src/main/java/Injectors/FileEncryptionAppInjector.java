package Injectors;

import Encryptors.*;

import com.google.inject.*;

public class FileEncryptionAppInjector extends AbstractModule {

	@Override
	protected void configure() {
		//Inject an encryptor for the application to use.
		Injector injector = Guice.createInjector(new EncryptorInjector()); 
		FileEncryptor encryptor = 
				injector.getInstance(FileEncryptor.class);
		
		bind(Encryptor.class).toInstance(encryptor);
	}

}
