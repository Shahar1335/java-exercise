package Keys;

import java.util.ArrayList;

import Algorithms.*;
import ExecutionAids.*;

public class KeyDouble extends Key<ArrayList<Integer>> {

	public KeyDouble(int key1, int key2) {
		//Create a list of two keys.
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.add(key1);
		keys.add(key2);
		this.keys = keys;
	}
	
	//Get a key of your choice.
	public int getKey(int index) {
		return this.keys.get(index);
	}
	
	public String toString() {
		String key1 = Integer.toString(this.getKey(0));
		String key2 = Integer.toString(this.getKey(1));
		return key1 + System.getProperty("line.separator") + key2;
	}

	@Override
	public char change_char(char c, IEncryptionAlgorithm algo, String action) 
			throws InvalidEncryptionKeyException {
		/* Use the first key for the original character, then use the second key
		   on that computation's result. */
		int key1 = this.keys.get(0);
		int key2 = this.keys.get(1);
		char char_first = algo.compute_char(c, key1, action);
		return algo.compute_char(char_first, key2, action);
	}

}
