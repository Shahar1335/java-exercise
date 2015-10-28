package Algorithms;

import ExecutionAids.InvalidEncryptionKeyException;

import com.google.inject.*;

@Singleton
public class ShiftUpEncryption extends EncryptionAlgorithm {

	public ShiftUpEncryption() {
		super(4);
	}
	
	//@Override
	public char compute_char(char current_char, int key, String action) 
			throws InvalidEncryptionKeyException {
		check_strength(key);

		/* Either add (decryption) or deduct (encryption) the key.
		   The only value possible besides "decryption" is "encryption". */
		char new_char = (action.equals("decryption") ? 
				(char)(current_char - key) : (char)(current_char + key));
		
		return new_char;
	}
	
	public String toString() {
		return "ShiftUpEncryption";
	}

}
