package Algorithms;

import ExecutionAids.InvalidEncryptionKeyException;
import Keys.Key;

public abstract class EncryptionAlgorithm implements IEncryptionAlgorithm {
	
	protected int strength;
	
	public EncryptionAlgorithm(int strength) {
		this.strength = strength;
	}

	public <T> String enc_dec_alg(String text, Key<T> key, String action) 
			throws InvalidEncryptionKeyException {
		String text_enc = "";
		
		//Perform the algorithm on each character and add it to the new text.
		for (int i = 0; i < text.length(); i++) {
			text_enc += key.change_char(text.charAt(i), this, action);
		}
		
		return text_enc;
	}
	
	public void check_strength(int key) throws InvalidEncryptionKeyException {
		String err_msg;
		
		//If the key is too strong, throw an exception.
		if (key >= Math.pow(10, this.strength) | key <= 0) {
			err_msg = "The key is too strong for this algorithm.";
			throw new InvalidEncryptionKeyException(err_msg, "error");
		}
	}
	
	public int getKeyStrength() {
		return this.strength;
	}
	
}
