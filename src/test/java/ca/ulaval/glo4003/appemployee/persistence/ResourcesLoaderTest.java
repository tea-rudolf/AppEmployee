package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class ResourcesLoaderTest {

	private final String VALID_DUMMY_RESOURCE = "/dummyResource.xml";
	private final String INVALID_DUMMY_RESOURCE = "DUMMYdummyDUMMY.xml";

	private ResourcesLoader loader;

	@Before
	public void setUp() {
		loader = new ResourcesLoader();
	}

	@Test
	public void givenValidResourceLoadsResource() {
		assertNotNull(loader.loadResource(ProjectXMLAssembler.class, VALID_DUMMY_RESOURCE));
	}

	@Test
	public void canLoadExistingResourceForWriting() throws Exception {
		assertNotNull(loader.loadResourceForWriting(VALID_DUMMY_RESOURCE));
	}

	@Test
	public void cantLoadUnexistingResource() {
		assertNull(loader.loadResource(ProjectXMLAssembler.class, INVALID_DUMMY_RESOURCE));
	}

	@Test(expected = FileNotFoundException.class)
	public void cantLoadUnexistingResourceForWriting() throws Exception {
		loader.loadResourceForWriting(INVALID_DUMMY_RESOURCE);
	}

}
