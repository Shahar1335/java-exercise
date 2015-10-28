package Algorithms;

import java.util.Comparator;

public class CompareEncryptionAlgorithms 
                                implements Comparator<IEncryptionAlgorithm> {

	public int compare(IEncryptionAlgorithm alg1, IEncryptionAlgorithm alg2) {
		//We compare two encryption algorithms by their strengths.
		if (alg1.getKeyStrength() > alg1.getKeyStrength()) {
			return 1;
		}
		else if (alg1.getKeyStrength() == alg1.getKeyStrength()) {
			return 0;
		}
		else {
			return -1;
		}
	}

}
