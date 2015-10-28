package encryptor1;

import java.io.IOException;

import org.junit.*;

import Algorithms.EncryptionAlgorithm;
import Algorithms.ShiftMultiplyEncryption;
import Algorithms.ShiftUpEncryption;
import Algorithms.XorEncryption;
import Applications.FileEncryptionApplication;
import Encryptors.DoubleEncryption;
import Encryptors.Encryptor;
import Encryptors.FileEncryptor;
import Encryptors.RepeatEncryption;
import ExecutionAids.FileOperations;
import ExecutionAids.InvalidEncryptionKeyException;

import com.google.inject.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EncryptionAlgorithmsTest {
	
	//A non-mocked FileOperations instance.
	private FileOperations fo_nomock = new FileOperations();

	@Test
	public void testAlgorithms() 
			throws Exception {
		//Create a mocked FileOperations instance.
		final FileOperations fo = mock(FileOperations.class);
		
		//Use this to inject a file encryption application.
		final Injector encryptor_injector;
		Injector fileapp_injector;
		
		/* Comment out the encryptor for the injected application.
		   Create an encryptor for each encryptor type and algorithm. */
		
		DoubleEncryption enc_mult_double = 
				new DoubleEncryption(new ShiftMultiplyEncryption(), fo);
		DoubleEncryption enc_up_double = 
				new DoubleEncryption(new ShiftUpEncryption(), fo);
		DoubleEncryption enc_xor_double = 
				new DoubleEncryption(new XorEncryption(), fo);
		
		//Create key arrays for mocking.
		String [] keys_one = {"3"};
		String [] keys_two = {"2", "4"};
		String text_enc = "";
		
		//Mock the FileOperations instance fo.
		when(fo.readKeys(anyString(),eq(1), 
                eq("encryption"), any(Encryptor.class))).thenReturn(keys_one);
		when(fo.readKeys(anyString(),eq(2), 
                eq("encryption"), any(Encryptor.class))).thenReturn(keys_two);
		when(fo.readTextFromFile(anyString())).thenReturn("012");
		when(fo.createNewFileName(anyString(), anyString())).
													   thenCallRealMethod();
		doCallRealMethod().when(fo).writeIntoFile(anyString(), anyString());
		
		//Inject the encryptor and then the file encryption application.
		
		encryptor_injector = 
				Guice.createInjector(new AbstractModule() {
            
           @Override
           protected void configure() {
        	   bind(EncryptionAlgorithm.class).to(ShiftUpEncryption.class);
        	   bind(FileOperations.class).toInstance(fo);
        	   bind(Integer.class).toInstance(2);
           }
        });
		
		fileapp_injector =
				Guice.createInjector(new AbstractModule() {
					
			@Override
			protected void configure() {
				RepeatEncryption repeat_enc = 
						encryptor_injector.getInstance(RepeatEncryption.class);
				bind(Encryptor.class).toInstance(repeat_enc);
			}
		});
		
		//Use the injector to get the file encryption application.
		FileEncryptionApplication file_app = 
				fileapp_injector.getInstance(FileEncryptionApplication.class);
		
		/* Encrypt with each encryptor, and then use the non-mocked
		   FileOperations instance in order to get the results.
		   Then compare to see if the test is successful. */
		
		text_enc = run_encryptor(enc_mult_double);
		assertEquals("ƀƈƐ", text_enc);
		text_enc = run_encryptor(enc_up_double);
		assertEquals("678", text_enc);
		text_enc = run_encryptor(file_app.getEncryptor());
		assertEquals("678", text_enc);
		text_enc = run_encryptor(enc_xor_double);
		assertEquals("674", text_enc);
		
	}
	
	public String run_encryptor(Encryptor encryptor) 
			throws IOException, InvalidEncryptionKeyException, Exception {
		encryptor.encryption("text.txt", null, "");
		return fo_nomock.readTextFromFile("text_encrypted.txt");
	}
	
	@Test (expected = InvalidEncryptionKeyException.class)
	public void testKeyException() 
			throws Exception {
		FileOperations fo = mock(FileOperations.class);
		
		//Create the encryptor with a mocked FileOperations instance.
		FileEncryptor enc_mult = 
				new FileEncryptor(new ShiftMultiplyEncryption(), fo);
		
		//The array contains a word instead of a number.
		String [] key = {"konnichiwa"};
		
		testKeyExecution(enc_mult, key);
	}
	
	@Test (expected = InvalidEncryptionKeyException.class)
	public void testKeyExceptionDouble() 
			throws Exception {
		FileOperations fo = mock(FileOperations.class);
		
		//Create the encryptor with a mocked FileOperations instance.
		DoubleEncryption enc_mult_double = 
				new DoubleEncryption(new ShiftMultiplyEncryption(), fo);
		
		//The array contains a key that is too strong for the algorithm.
		String [] keys = {"4231", "12"};
		
		testKeyExecution(enc_mult_double, keys);
	}
	
	
	public void testKeyExecution(Encryptor enc, String [] keys) 
			throws Exception {
		//Mock the encryptor's FileOperations instance.
		when(enc.getFO().readKeys(anyString(), eq(1), 
				  eq("encryption"), any(Encryptor.class))).thenReturn(keys);
		when(enc.getFO().readTextFromFile(anyString())).thenReturn("012");
		when(enc.getFO().createNewFileName(anyString(), anyString())).
													   thenCallRealMethod();
		doCallRealMethod().when(enc.getFO()).
		                            writeIntoFile(anyString(), anyString());
		
		//Try to encrypt with the invalid keys. Expect an exception.
		enc.encryption("text.txt", null, "");
	}
	
}
