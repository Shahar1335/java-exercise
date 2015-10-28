package Adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import Algorithms.*;

public class EncryptionAlgorithmAdapter 
				extends XmlAdapter<String, EncryptionAlgorithm> {

	@Override
	public EncryptionAlgorithm unmarshal(String algorithm) throws Exception {
		EncryptionAlgorithm enc_algo = null;
		
		//Return an algorithm according to what's written in the file.
		switch (algorithm) {
		
		case ("ShiftUpEncryption"): 
			enc_algo = new ShiftUpEncryption();
			break;
		
		case ("ShiftMultiplyEncryption"):
			enc_algo = new ShiftMultiplyEncryption();
			break;
			
		case ("XorEncryption"):
			enc_algo = new XorEncryption();
			break;
			
		default:
			break;
		
		}
		
		return enc_algo;
	}

	@Override
	public String marshal(EncryptionAlgorithm enc_algo) throws Exception {
		return enc_algo.toString();
	}

}
