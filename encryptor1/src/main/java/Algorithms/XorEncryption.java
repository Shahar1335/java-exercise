package Algorithms;

import ExecutionAids.InvalidEncryptionKeyException;

import com.google.inject.*;

@Singleton
public class XorEncryption extends EncryptionAlgorithm {

	public XorEncryption() {
		super(3);
	}
	
	//@Override
	public char compute_char(char current_char, int key, String action) 
			throws InvalidEncryptionKeyException {
		check_strength(key);
		
		//Do XOR with the character and the key (decryption and encryption).
		return (char)(current_char ^ key);
	}

	public String toString() {
		return "XorEncryption";
	}
	
}
