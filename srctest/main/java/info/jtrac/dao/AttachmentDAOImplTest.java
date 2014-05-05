package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class AttachmentDAOImplTest {
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Attachment> iPersistenceAttachmentDAOImpl = (IPersistenceDAOImpl<Attachment>) context.getBean("iPersistenceAttachmentDAOImpl");
	Logger logger = Logger.getRootLogger();
	/*
	 * Scenario : 01
	 * 01 - Persist a null Object
	 * 02 - catch the nullPointer 
	 */
	@Test
	public void testAttachmentPersistScenario01() {
		Attachment object = null;
		try {
			iPersistenceAttachmentDAOImpl.persist(object);
		} catch (DataDAOException e) {
			assertNotNull(e);
		}
	}
	/*
	 * Scenario : 02
	 * 01 :	create a Attachment
	 * 02 : persist the attachment
	 * 03 :	check the database
	 * 04 : delete from the database
	 */
	public void testAttachmentPersistScenario02() {
		/* 01 :	create a Attachment */
		Attachment object = new Attachment("testFileName.txt");
		/* 02 : persist the attachment */
		try {
			this.iPersistenceAttachmentDAOImpl.persist(object);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 03 :	check the database */
		try {
			Attachment foundObject = this.iPersistenceAttachmentDAOImpl.findByID(object.getId());
			assertNotNull(foundObject);
			assertEquals("testFileName.txt", foundObject.getFileName());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 04 : delete from the database */
		try {
			this.iPersistenceAttachmentDAOImpl.delete(object.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	/*
	 * Scenario : 03
	 * 01 :	create a Attachment
	 * 02 :	persist the attachment
	 * 03 :	check the database
	 * 04 :	create a Attachment
	 * 05 :	add the first attachment
	 * 06 :	persist the second attachment in the database
	 * 07 : check the database
	 * 08 : delete from the database
	 */
	@Test
	public void testAttachmentPersistScenario03() {
		/* 01 :	create a Attachment */
		Attachment replacedFile = new Attachment("replacedFile.txt");
		/* 02 : persist the attachment */
		try {
			this.iPersistenceAttachmentDAOImpl.persist(replacedFile);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 03 :	check the database */
		try {
			Attachment foundObject = this.iPersistenceAttachmentDAOImpl.findByID(replacedFile.getId());
			assertNotNull(foundObject);
			assertEquals("replacedFile.txt", foundObject.getFileName());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 04 :	create a Attachment */
		Attachment object = new Attachment("testFileName.txt");
		/* 06 :	persist the second attachment in the database */
		try {
			this.iPersistenceAttachmentDAOImpl.persist(object);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 05 :	add the first attachment */
		object.setPrevious(replacedFile);
		try {
			this.iPersistenceAttachmentDAOImpl.update(object);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 07 : check the database */
		try {
			Attachment foundObject = this.iPersistenceAttachmentDAOImpl.findByID(object.getId());
			assertNotNull(foundObject);
			assertNotNull(foundObject.getPrevious());
			assertEquals("testFileName.txt", foundObject.getFileName());
			assertEquals("replacedFile.txt", foundObject.getPrevious().getFileName());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* 08 : delete from the database */
		try {
			this.iPersistenceAttachmentDAOImpl.delete(object.getId());
			this.iPersistenceAttachmentDAOImpl.delete(replacedFile.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	
}
