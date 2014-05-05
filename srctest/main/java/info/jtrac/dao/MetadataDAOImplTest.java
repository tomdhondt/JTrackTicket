package main.java.info.jtrac.dao;

import static org.junit.Assert.*;

import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class MetadataDAOImplTest {
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Metadata> iPersistenceMetadataDAOImpl = (IPersistenceDAOImpl<Metadata>) context.getBean("iPersistenceMetadataDAOImpl");
	/**
	 * Scenario 01 :
	 * 01 - create a Metadata null
	 * 02 - persist it to the database
	 * 03 - catch the nullPointer
	 */
//	@Test
	public void testScenario01() {
		Metadata metaData = null;
		try {
			iPersistenceMetadataDAOImpl.persist(metaData);
		} catch (DataDAOException e) {
			assertNotNull(e);
		}
	}
	/**
	 * Scenario 02 :
	 * 01 - Create a Metadata
	 * 02 - persist it to the database
	 * 03 - check the object in the database
	 * 04 - delete the object in the database
	 */
	@Test
	public void testScenario02() {
		Metadata metaDataParent = new Metadata();
		Metadata metaData = new Metadata(); 
		metaDataParent.setType(1);
		metaData.setType(2);
		metaDataParent.setName("MetaData Parent Name");
		metaData.setName("MetaData Name");
		metaDataParent.setDescription("Metadata Parent Description");    
	    metaData.setDescription("Metadata Description");
	    metaData.setXmlString("<metadata><fields>" 
                + "<field name='cusInt03' label='Test Label 3'/>"
                + "<field name='cusInt04' label='Test Label 4'/>"
                + "</fields></metadata>");
	    metaData.setParent(metaDataParent);
	    try {
			iPersistenceMetadataDAOImpl.persist(metaData);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			assertEquals(2, iPersistenceMetadataDAOImpl.findAll().size());
			Metadata mdFound = iPersistenceMetadataDAOImpl.findByID(metaData.getId());
			assertNotNull(mdFound);
			assertNotNull(mdFound.getParent());
			assertEquals("MetaData Name", mdFound.getName());
			assertEquals("Metadata Description", mdFound.getDescription());
			assertTrue(2 == mdFound.getType());
			assertEquals("MetaData Parent Name", mdFound.getParent().getName());
			assertEquals("Metadata Parent Description", mdFound.getParent().getDescription());
			assertTrue(1 == mdFound.getParent().getType());
			mdFound.getXmlString();
			System.out.println("test");
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			iPersistenceMetadataDAOImpl.delete(metaData.getId());
			iPersistenceMetadataDAOImpl.delete(metaDataParent.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

}
