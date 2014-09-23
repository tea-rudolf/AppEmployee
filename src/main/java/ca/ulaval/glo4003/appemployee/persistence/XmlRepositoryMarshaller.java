package ca.ulaval.glo4003.appemployee.persistence;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlRepositoryMarshaller {
	
	private final static String XML_DATA_FILE_PATH = "./data/data.xml";
			
	private static XmlRepositoryMarshaller instance = null;
	
	private File file;
	private Boolean needsUnmarshalling = true;
	public XmlRootNode xmlRootNode;
	
	protected XmlRepositoryMarshaller() {
		file = new File(XML_DATA_FILE_PATH);
		if(file.exists() == false || file.isFile() == false) {
			throw new IllegalArgumentException(String.format("File '%s' was not found."));
		}
	}
	
	public synchronized static XmlRepositoryMarshaller getInstance() {
		if (instance == null) {
			instance = new XmlRepositoryMarshaller();
		}
		return instance;
	}
	
	public XmlRootNode getRootNode() {
		return xmlRootNode;
	}
	
	public void Marshall() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlRootNode.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			jaxbMarshaller.marshal(xmlRootNode, file);
			needsUnmarshalling = true;
		} catch(JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Unmarshall() {
		if (needsUnmarshalling == true) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(XmlRootNode.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				
				xmlRootNode = (XmlRootNode) jaxbUnmarshaller.unmarshal(file);
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
