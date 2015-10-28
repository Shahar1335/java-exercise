package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	
	private InputData data = new InputData(); //This is where we store data.
	private String curr_section; //This is the current section's type.
	
	public InputData getInputData() {
		return this.data;
	}

	public void startElement(String uri, String localName,
            String qName, Attributes attributes)
    throws SAXException {
		this.curr_section = qName; //Store the new section's type.
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
    	this.curr_section = null; //Empty the variable until the next section.
    }

    public void characters(char ch[], int start, int length)
    throws SAXException {
    	String input = new String(ch, start, length);
    	
    	//Update the data according to what's written in the section.
    	if (this.curr_section != null) {
    		XMLMethods.update_data(data, this.curr_section, input);
    	}
    }
	
}
