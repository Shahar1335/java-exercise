package XML;

public interface IXMLReadWrite {

	//This method reads the data from an XML file.
	abstract InputData XMLRead(String xmlfile) throws Exception;
	
}
