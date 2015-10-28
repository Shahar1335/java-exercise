package Injectors;

import Algorithms.*;
import ExecutionAids.*;

import com.google.inject.AbstractModule;

public class EncryptorInjector extends AbstractModule {

	@Override
	protected void configure() {
		//Bind the EncryptionAlgorithm class to the appropriate algorithm.
		bind(EncryptionAlgorithm.class).to(ShiftUpEncryption.class);
		//Bind the FileOperations (useful for testing).
		bind(FileOperations.class).toInstance(new FileOperations());
		bind(Integer.class).toInstance(4);
	}

}
