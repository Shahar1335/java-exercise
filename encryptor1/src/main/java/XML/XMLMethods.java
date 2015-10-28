package XML;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

import Adapters.*;
import Algorithms.*;
import Encryptors.*;

public class XMLMethods {

	public static void XMLWrite(String input_path, String action, Encryptor encryptor,
			EncryptionAlgorithm enc_algo, String keypath, 
			String outputpath) throws Exception {
		//These two variables will be used to marshall the data
		JAXBContext jaxbContext;
		Marshaller jaxbMarshaller;
		
		//This is where we store the results of the run.
		RunResults data = new RunResults();
		
		data.setInputPath(input_path);
		data.setActionTaken(action);
		data.setEncryptorUsed(encryptor);
		data.setAlgorithmUsed(enc_algo);
		data.setKeyPath(keypath);
		data.setOutPutPath(outputpath);
		
		//Validate.
		JAXBValidate(data);
		
		//Initiate jaxb marshaler.
		jaxbContext = JAXBContext.newInstance(RunResults.class);
		jaxbMarshaller = jaxbContext.createMarshaller();

		//Set this flag to true to format the output.
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		//Write the results into an output file.
		jaxbMarshaller.marshal(data, new File("run_data.xml"));
	}
	
	public static void update_data(InputData data, String attr, String info) {
		//These variables will be to adapt the classes for en/decryption.
		DirectoryProcessorAdapter processor_adapt = 
				new DirectoryProcessorAdapter();
		EncryptorAdapter encryptor_adapt =
						new EncryptorAdapter();
		EncryptionAlgorithmAdapter enc_algo_adapt =
						new EncryptionAlgorithmAdapter();
		
		//Update the appropriate data's attribute.
		switch(attr) {
			case ("File_or_Dir"):
				data.setFileOrDir(info);
				break;
				
			/* For the directory processor / encryptor / algorithm, use
			   the appropriate adapter to get the appropriate instance
			   of the class. */
				
			case ("Sync_or_Async"):
				try {
					data.setSyncOrAsync(processor_adapt.unmarshal(info));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case ("Encryptor_Used"):
				try {
					data.setEncryptorUsed(encryptor_adapt.unmarshal(info));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case ("Algorithm_Used"):
				try {
					data.setAlgorithmUsed(
							enc_algo_adapt.unmarshal(info));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case ("Path_Given"):
				data.setPathGiven(info);
				break;
				
			case ("Encrypt_or_Decrypt"):
				data.setEncryptOrDecrypt(info);
				break;
				
			case ("Path_to_Key"):
				data.setPathToKey(info);
				break;
				
			default:
				break;
		}
	}
	
	public static void JAXBValidate(Data data) throws Exception {
		 //Create the schema.
	     SchemaFactory sf = 
	    		 SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	     Schema schema;

	     /* Create the context and use it to create sources for each 
	        of the data elements. */
	     JAXBContext jaxbContext = JAXBContext.newInstance(data.getClass());
	     JAXBSource sourceData = new JAXBSource(jaxbContext, data);
	     
	     Validator validator;
	     
	     //Use the appropriate XSD file.
	     if (data.toString().equals("InputData")) {
	    	 schema = sf.newSchema(new File("xml_input_schema.xsd"));
	     }
	     else {
	    	 schema = sf.newSchema(new File("xml_output_schema.xsd"));
	     }
	     
	     //Initialize the validator.
	     validator = schema.newValidator();
	     validator.setErrorHandler( new MyErrorHandler() );

	     //Validate.
	     try
	     {
	         validator.validate( sourceData );
	     }
	     catch( SAXException ex )
	     {
	         ex.printStackTrace();
	         System.out.println( "This XML file is invalid." );
	     }
	}
	
}
