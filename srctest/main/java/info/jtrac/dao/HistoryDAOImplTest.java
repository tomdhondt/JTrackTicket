package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.domain.History;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class HistoryDAOImplTest {
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Attachment> iPersistenceAttachmentDAOImpl = (IPersistenceDAOImpl<Attachment>) context.getBean("iPersistenceAttachmentDAOImpl");
	IPersistenceDAOImpl<History> iPersistenceHistoryDAOImpl = (IPersistenceDAOImpl<History>) context.getBean("iPersistenceHistoryDAOImpl");
	IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
	private Attachment attachment;
	private User loggedBy;
	private User assignedTo;
	@Before
	public void testBefore(){
		/* create attachment */
		this.attachment = new Attachment();
		this.attachment.setFileName("filename");
		this.attachment.setFilePrefix(0);
		this.attachment.setPrevious(null);
		try {
			iPersistenceAttachmentDAOImpl.persist(attachment);
			Attachment atFound = iPersistenceAttachmentDAOImpl.findByID(attachment.getId());
			assertNotNull(atFound);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* create User */
		this.assignedTo = new User();
		this.assignedTo.setEmail("email-assignedTo@email.com");
		this.assignedTo.setLocale("nl");
		this.assignedTo.setLocked(false);
		this.assignedTo.setLoginName("loginName assignedTo");
		this.assignedTo.setName("name assignedTo");
		this.assignedTo.setPassword("paswoord assignedTo");
		this.assignedTo.setType(1);
		this.loggedBy = new User();
		this.loggedBy.setEmail("email@email.com");
		this.loggedBy.setLocale("en");
		this.loggedBy.setLocked(false);
		this.loggedBy.setLoginName("loginName");
		this.loggedBy.setName("name");
		this.loggedBy.setPassword("paswoord");
		this.loggedBy.setType(0);
		try {
			this.iPersistenceUserDAOImpl.persist(this.loggedBy);
			this.iPersistenceUserDAOImpl.persist(this.assignedTo);
			User uFound = this.iPersistenceUserDAOImpl.findByID(this.loggedBy.getId());
			User uAFound = this.iPersistenceUserDAOImpl.findByID(this.assignedTo.getId());
			assertNotNull(uFound);
			assertNotNull(uAFound);
			assertEquals("en", uFound.getLocale());
			assertEquals("nl", uAFound.getLocale());
			assertEquals("email@email.com",uFound.getEmail());
			assertEquals("email-assignedTo@email.com",uAFound.getEmail());
			assertEquals(false,uFound.isLocked());
			assertEquals(false,uAFound.isLocked());
			assertEquals("loginName", uFound.getLoginName());
			assertEquals("loginName assignedTo", uAFound.getLoginName());
			assertEquals("name",uFound.getName());
			assertEquals("name assignedTo",uAFound.getName());
			assertEquals("paswoord",uFound.getPassword());
			assertEquals("paswoord assignedTo",uAFound.getPassword());
			assertTrue(0==uFound.getType());
			assertTrue(1==uAFound.getType());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	/**
	 * Scenario 01:
	 * 01 - Create Attachment
	 * 02 - Persist Attachment
	 * 03 - create History
	 * 04 - persist to the database
	 * 05 - check the state
	 * 06 - delete from the database
	 */
	@Test
	public void testScenario01() {
		History history = new History();
		history.setAttachment(this.attachment);
		history.setType(1);
		history.setComment("Comment");
		history.setActualEffort(1.20);
		history.setLoggedBy(this.loggedBy);
		history.setAssignedTo(this.assignedTo);
		try {
			iPersistenceHistoryDAOImpl.persist(history);
			History hisFound = iPersistenceHistoryDAOImpl.findByID(history.getId());
			assertNotNull(hisFound);
			assertNotNull(hisFound.getAttachment());
			assertEquals("filename", hisFound.getAttachment().getFileName());
			assertNotNull(hisFound.getLoggedBy());
			assertNotNull(hisFound.getAssignedTo());
			assertEquals("email@email.com", hisFound.getLoggedBy().getEmail());
			assertEquals("email-assignedTo@email.com", hisFound.getAssignedTo().getEmail());
			assertEquals("Comment", hisFound.getComment());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		try {
			iPersistenceHistoryDAOImpl.delete(history.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	@After
	public void testAfter(){
		/* delete the objects out the database */
		try {
			iPersistenceAttachmentDAOImpl.delete(this.attachment.getId());
			iPersistenceUserDAOImpl.delete(this.loggedBy.getId());
			iPersistenceUserDAOImpl.delete(this.assignedTo.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
}
