package ca.ulaval.glo4003.appemployee.persistence;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.SerializationException;

public class XMLGenericMarshaller<T> {

	private Class<T> type;

	@Inject
	private ResourcesLoader resourcesLoader;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public XMLGenericMarshaller(Class<T> type) throws JAXBException {
		this.type = type;
		JAXBContext context = JAXBContext.newInstance(type);
		marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		unmarshaller = context.createUnmarshaller();
		resourcesLoader = new ResourcesLoader();
	}

	@SuppressWarnings("unchecked")
	public T unmarshall(String resourceName) throws JAXBException, SerializationException {
		InputStream stream = resourcesLoader.loadResource(type, resourceName);

		if (stream != null) {
			return (T) unmarshaller.unmarshal(stream);
		} else {
			throw new SerializationException("Invalid resource name : " + resourceName);
		}
	}

	public void marshall(T element, String resourceName) throws JAXBException, URISyntaxException, FileNotFoundException {
		OutputStream stream = resourcesLoader.loadResourceForWriting(resourceName);
		marshaller.marshal(element, stream);
	}

	public void setResourcesLoader(ResourcesLoader loader) {
		this.resourcesLoader = loader;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
}
