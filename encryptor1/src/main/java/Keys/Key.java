package Keys;

import Algorithms.*;
import ExecutionAids.*;

public abstract class Key<T> {

	protected T keys;
	
	//Perform the given action on a character with the given key.
	public abstract char 
		change_char(char c, IEncryptionAlgorithm algo, String action)
			throws InvalidEncryptionKeyException;
	
}
