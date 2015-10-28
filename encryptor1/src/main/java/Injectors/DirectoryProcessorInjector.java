package Injectors;

import Encryptors.*;

import com.google.inject.*;

public class DirectoryProcessorInjector extends AbstractModule {

	@Override
	protected void configure() {
		//Inject an encryptor and use it for the directory processor.
		Injector injector = Guice.createInjector(new EncryptorInjector()); 
		RepeatEncryption encryptor = 
				injector.getInstance(RepeatEncryption.class);
		
		bind(Encryptor.class).toInstance(encryptor);
	}

}
