package ca.ulaval.glo4003.appemployee.persistence;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlRepositoryMarshaller {

	private static final String XML_DATA_FILE_PATH = "./data/data.xml";
	private static final Object XML_MARSHALL_LOCK = new Object();

	private static XmlRepositoryMarshaller instance = null;

	private File file;
	private Boolean needsUnmarshalling = true;
	private XmlRootNode xmlRootNode;

	protected XmlRepositoryMarshaller() {
		file = new File(XML_DATA_FILE_PATH);

		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException(String.format("File '%s' was not found."));
		}
	}

	public static synchronized XmlRepositoryMarshaller getInstance() {
		if (instance == null) {
			instance = new XmlRepositoryMarshaller();
		}

		return instance;
	}

	public XmlRootNode getRootNode() {
		return xmlRootNode;
	}

	public void marshall() {
		try {
			synchronized (XML_MARSHALL_LOCK) {
				JAXBContext jaxbContext = JAXBContext.newInstance(XmlRootNode.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				jaxbMarshaller.marshal(xmlRootNode, file);
				needsUnmarshalling = true;
			}
		} catch (JAXBException e) {
			throw new MarshallingException("Failed to marshall objects to XML repository.", e);
		}
	}

	public void unmarshall() {
		if (!needsUnmarshalling) {
			return;
		}

		try {
			synchronized (XML_MARSHALL_LOCK) {
				JAXBContext jaxbContext = JAXBContext.newInstance(XmlRootNode.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				xmlRootNode = (XmlRootNode) jaxbUnmarshaller.unmarshal(file);
			}
		} catch (JAXBException e) {
			throw new MarshallingException("Failed to unmarshall objects from XML repository.", e);
		}
	}
}
