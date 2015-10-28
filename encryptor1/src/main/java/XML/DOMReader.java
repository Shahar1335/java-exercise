package XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMReader implements IXMLReadWrite {
	
	public InputData XMLRead(String xmlfile) throws SAXException, IOException {
		//We store the XML file's data in the variable "data".
		InputData data = new InputData();
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null; //Here we store the paring's results.
		Element rootElement; //This is where we store the root element.
		NodeList nodes; //This is where the root's children are stored.
		Node node; //This is how we iterate over the children.
		Schema schema = null; //This is where the schema will be stored.
		
		//Create the builder.
		try {
		    builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		}
		
		//Parse the file.
		try {
		    document = builder.parse(new FileInputStream(xmlfile));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		//Get the root element and its children.
		
		rootElement = document.getDocumentElement();
		
		nodes = rootElement.getChildNodes();

		//Go over all of the children.
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
	
			if(node instanceof Element){
				//a child element to process
				Element child = (Element) node;
				
				XMLMethods.update_data(data, 
						child.getTagName(), child.getTextContent());
			}
		}
		
		//Create the schema.
		try {
		  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		  SchemaFactory factory = SchemaFactory.newInstance(language);
		  schema = factory.newSchema(new File("xml_input_schema.xsd"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		//Validate the file.
		
		Validator validator = schema.newValidator();

		validator.validate(new DOMSource(document));
		
		return data;
	}

}
