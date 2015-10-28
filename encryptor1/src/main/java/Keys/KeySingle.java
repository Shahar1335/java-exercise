package Keys;

import Algorithms.*;
import ExecutionAids.*;

public class KeySingle extends Key<Integer> {

	public KeySingle(int keys) {
		this.keys = keys;
	}
	
	public String toString() {
		return Integer.toString(this.keys);
	}

	@Override
	public char change_char(char c, IEncryptionAlgorithm algo, String action) 
			throws InvalidEncryptionKeyException {
		//Perform the action on the given character with the given key.
		return algo.compute_char(c, keys, action);
	}

}
