package Injectors;

import Applications.*;

import com.google.inject.*;

public class EncryptionAppInjector extends AbstractModule {

	@Override
	protected void configure() {
		//Inject a file/directory application for the main application to use.
		Injector injector_dir = Guice.createInjector(new DirectoryEncryptionAppInjector());
		DirectoryEncryptionApplication dir_app =
				injector_dir.getInstance(DirectoryEncryptionApplication.class);
		
		bind(IEncryptionPerformanceApplication.class).toInstance(dir_app);
	}

}
