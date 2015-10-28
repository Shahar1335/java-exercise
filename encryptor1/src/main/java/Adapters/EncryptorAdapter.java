package Adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import Encryptors.*;

public class EncryptorAdapter  
			extends XmlAdapter<String, Encryptor> {

	@Override
	public String marshal(Encryptor encryptor) throws Exception {
		return encryptor.toString();
	}

	@Override
	public Encryptor unmarshal(String encryptor) throws Exception {
		String [] encryptor_split = encryptor.split(" ");
		Encryptor enc = null;
		int n;
		
		/*Return an encryptor according to what's written in the XML. 
		  We do not know what the algorithm is yet. */
		switch (encryptor_split[0]) {
			
			/* In case of RepeatEncryption, after the space is the number of
			   repetitions. */
			case ("RepeatEncryption"): 
				n = Integer.parseInt(encryptor_split[1]);
				enc = new RepeatEncryption(n);
				break;
			
			case ("DoubleEncryption"):
				enc = new DoubleEncryption(null);
				break;
				
			case ("FileEncryptor"):
				enc = new FileEncryptor(null);
				break;
				
			default:
				break;
			
		}
		
		return enc;
	}

}
