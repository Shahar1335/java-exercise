package Algorithms;

import ExecutionAids.InvalidEncryptionKeyException;

import com.google.inject.*;

@Singleton
public class ShiftMultiplyEncryption extends EncryptionAlgorithm {
	
	public ShiftMultiplyEncryption() {
		super(2);
	}

	//@Override
	public char compute_char(char current_char, int key, String action) 
			throws InvalidEncryptionKeyException {
		check_strength(key);
		
		/* Either divide (decryption) or multiply (encryption) by the key.
		   The only value possible besides "decryption" is "encryption". */
		return (action.equals("decryption") ? (char)(current_char / key) : 
			(char)(current_char * key));
	}
	
	public String toString() {
		return "ShiftMultiplyEncryption";
	}

}
