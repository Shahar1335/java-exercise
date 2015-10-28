package Injectors;

import DirectoryProcessors.*;

import com.google.inject.*;

public class DirectoryEncryptionAppInjector extends AbstractModule {

	@Override
	protected void configure() {
		//Inject a directory processor for this application to use.
		Injector injector = 
				Guice.createInjector(new DirectoryProcessorInjector()); 
		DirectoryProcessor asdp = 
				injector.getInstance(AsyncDirectoryProcessor.class);
		
		bind(DirectoryProcessor.class).toInstance(asdp);
	}

}
