package XML;

import java.io.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.validation.*;

public class SAXReader implements IXMLReadWrite {
	
	private SAXParserFactory factory;

	public InputData XMLRead(String xmlfile) throws Exception {
	    InputStream xmlInput; //This will be used to read input.
	    SAXParser saxParser; //This will be used to parse the input.
	    SAXHandler handler = new SAXHandler();
	    
	    this.factory = SAXParserFactory.newInstance();
	    xmlInput = new FileInputStream(xmlfile); //Read the input.
	    
	    //Validate.
	    this.validate();
	    
	    //Initiate the parser and parse.
	    saxParser = factory.newSAXParser();
	    saxParser.parse(xmlInput, handler);
	    
		return handler.getInputData();
	}

	public void validate() throws Exception {
		//Does not work...
		Schema schema = null;
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File("xml_input_schema.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setSchema(schema);

		SAXParser parser = spf.newSAXParser();
		
		InputStream xmlInput = new FileInputStream("xml_input_file.xml");

		parser.parse(xmlInput, new SAXHandler());
	}
	
	
}
