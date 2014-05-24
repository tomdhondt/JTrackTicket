package main.java.info.jtrac.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class SpaceDAOImplTest {
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Space> iPersistenceSpaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("iPersistenceSpaceDAOImpl");
	IPersistenceDAOImpl<SpaceSequence> iPersistenceSpaceSequenceDAOImpl = (IPersistenceDAOImpl<SpaceSequence>) context.getBean("iPersistenceSpaceSequenceDAOImpl");
	IPersistenceDAOImpl<Metadata> iPersistenceMetadataDAOImpl = (IPersistenceDAOImpl<Metadata>) context.getBean("iPersistenceMetadataDAOImpl");
	Logger logger = Logger.getRootLogger();
	private Metadata metaData;
	private Metadata metaDataParent;
	//@Before
	public void testBefore(){
		this.metaDataParent = new Metadata();
		this.metaData = new Metadata(); 
		this.metaDataParent.setType(1);
		this.metaData.setType(2);
		this.metaDataParent.setName("MetaData Parent Name");
		this.metaData.setName("Name");
		this.metaDataParent.setDescription("Metadata Parent Description");    
		this.metaData.setDescription("Metadata Description");
		this.metaData.setParent(metaDataParent);
		try {
			iPersistenceMetadataDAOImpl.persist(this.metaData);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1, "name", this.metaData.getName()));
			List<Metadata> listResult = iPersistenceMetadataDAOImpl.findByCriteria(list, "findByName");
			assertEquals(1, listResult.size());
		} catch (DataDAOException e) {
			assertNull(e);
		}	
	}
	/**
	 * Scenario : 01
	 * 01 - create a Space = null
	 * 02 - persist to the database and catch the exception
	 */
	//@Test
	public void testScenario01() {
		Space space = null;
		try {
			iPersistenceSpaceDAOImpl.persist(space);
		} catch (DataDAOException e) {
			assertNotNull(e.getCaption());
			assertEquals("Object.isNull", e.getCaption());
		}
	}
	/**
	 * Scenario : 02
	 * 01 - create a new Space
	 * 02 - set the properties
	 * 03 - persist to the Database
	 * 04 - find the persisted object in the database
	 * 05 - check the state of the object in the database
	 * 06 - delete the persisted object out the database
	 */
	@Test
	public void testScenario02(){
		SpaceSequence sSq = new SpaceSequence();
		Space space = new Space();
		space.setName("Space one");
		space.setGuestAllowed(true);
		space.setDescription("Description space one");
		space.setPrefixCode("prefixCode");
		space.setSpaceSequence(sSq);
		space.setMetadata(this.metaData);
		try {
			iPersistenceSpaceDAOImpl.persist(space);
			Space spaceFound = iPersistenceSpaceDAOImpl.findByID(space.getId());
			assertNotNull(spaceFound.getSpaceSequence());
			assertEquals(spaceFound.getName(),"Space one");
			assertTrue(spaceFound.isGuestAllowed());
			assertEquals(spaceFound.getDescription(),"Description space one");
			assertEquals(spaceFound.getPrefixCode(),"prefixCode");
		} catch (DataDAOException e) {
			assertNull(e);
		}
//		try {
//			iPersistenceSpaceDAOImpl.delete(space.getId());
//			iPersistenceSpaceSequenceDAOImpl.delete(space.getSpaceSequence().getId());
//		} catch (DataDAOException e) {
//			assertNull(e);
//		}
	}
	//@After
	public void testAfter(){
		try {
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1, "name", this.metaData.getName()));
			List<Metadata> listResult = iPersistenceMetadataDAOImpl.findByCriteria(list, "findByName");
			iPersistenceMetadataDAOImpl.delete(listResult.get(0).getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	
}
