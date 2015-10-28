package Adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import DirectoryProcessors.*;

public class DirectoryProcessorAdapter 
				extends XmlAdapter<String, DirectoryProcessor> {

	@Override
	public String marshal(DirectoryProcessor processor) 
			throws Exception {
		return processor.toString();
	}

	@Override
	public DirectoryProcessor unmarshal(String processor_type) 
			throws Exception {
		DirectoryProcessor processor = null;
		
		//Return a directory processor according to what's written in the file.
		
		if (processor_type.equals("SyncDirectoryProcessor")) {
			processor = new SyncDirectoryProcessor();
		}
		
		else if (processor_type.equals("AsyncDirectoryProcessor")) {
			processor = new AsyncDirectoryProcessor();
		}
		
		return processor;
	}

}
