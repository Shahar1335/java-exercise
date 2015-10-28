package Algorithms;

import ExecutionAids.InvalidEncryptionKeyException;
import Keys.Key;

public interface IEncryptionAlgorithm {
	
	//This method performs the algorithm on the given text using the given key.
	abstract <T> String enc_dec_alg(String text, Key<T> key, String action) 
			throws InvalidEncryptionKeyException;
	
	//This method computes a single character with the given key.
	abstract char compute_char(char current_char, int key, String action) 
			throws InvalidEncryptionKeyException;
	
	//This method checks the key's strength.
	abstract void check_strength(int key) throws InvalidEncryptionKeyException;
	
	abstract int getKeyStrength();
}
