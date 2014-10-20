package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.SerializationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XMLGenericMarshallerTest {

	private ResourcesLoader loader;
	private XMLGenericMarshaller<ProjectXMLAssembler> serializer;
	private final String RESOURCE_NAME = "resourceName";

	@Before
	public void setUp() throws Exception {
		serializer = new XMLGenericMarshaller<ProjectXMLAssembler>(ProjectXMLAssembler.class);
		loader = mock(ResourcesLoader.class);
	}

	@Test
	public void canInstantiateXMLMarshaller() throws Exception {
		assertNotNull(serializer);
	}

	@Test
	public void canUnmarshall() throws Exception {
		InputStream stream = mock(InputStream.class);
		when(loader.loadResource(ProjectXMLAssembler.class, RESOURCE_NAME)).thenReturn(stream);
		serializer.setResourcesLoader(loader);

		ProjectXMLAssembler dto = mock(ProjectXMLAssembler.class);
		Unmarshaller unmarshaller = mock(Unmarshaller.class);
		when(unmarshaller.unmarshal(stream)).thenReturn(dto);
		serializer.setUnmarshaller(unmarshaller);

		assertEquals(dto, serializer.unmarshall(RESOURCE_NAME));
	}

	@Test(expected = SerializationException.class)
	public void cannotUnmarshalInvalidResource() throws Exception {
		when(loader.loadResource(ProjectXMLAssembler.class, RESOURCE_NAME)).thenReturn(null);
		serializer.setResourcesLoader(loader);

		serializer.unmarshall(RESOURCE_NAME);
	}

	@Test
	public void canMarshall() throws Exception {
		ProjectXMLAssembler dto = mock(ProjectXMLAssembler.class);
		Marshaller marshaller = mock(Marshaller.class);
		OutputStream stream = mock(OutputStream.class);
		when(loader.loadResourceForWriting(RESOURCE_NAME)).thenReturn(stream);
		serializer.setResourcesLoader(loader);
		serializer.setMarshaller(marshaller);

		serializer.marshall(dto, RESOURCE_NAME);

		verify(loader).loadResourceForWriting(RESOURCE_NAME);
		verify(marshaller).marshal(dto, stream);
	}

}
