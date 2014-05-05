package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class SpaceSequenceDAOImplTest {
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<SpaceSequence> iPersistenceSpaceSequenceDAOImpl = (IPersistenceDAOImpl<SpaceSequence>) context.getBean("iPersistenceSpaceSequenceDAOImpl");
	Logger logger = Logger.getRootLogger();
	/**
	 * Scenario 01 :
	 * 01 - create a SpaceSequence
	 * 02 - persist the SpaceSequence
	 * 03 - check the object in the database
	 * 04 - delete the object in the database
	 */
	@Test
	public void testScenario01() {
		SpaceSequence sSq = new SpaceSequence();
		try {
			iPersistenceSpaceSequenceDAOImpl.persist(sSq);
			assertEquals(1,iPersistenceSpaceSequenceDAOImpl.findAll().size());
			SpaceSequence sSqFound = iPersistenceSpaceSequenceDAOImpl.findByID(sSq.getId());
			assertEquals(1, sSqFound.getNextSeqNum());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		try {
			iPersistenceSpaceSequenceDAOImpl.delete(sSq.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

}
