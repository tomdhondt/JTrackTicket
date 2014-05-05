package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.domain.History;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.ItemItem;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class ItemDAOImplTest {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Attachment> iPersistenceAttachmentDAOImpl = (IPersistenceDAOImpl<Attachment>) context.getBean("iPersistenceAttachmentDAOImpl");
	IPersistenceDAOImpl<History> iPersistenceHistoryDAOImpl = (IPersistenceDAOImpl<History>) context.getBean("iPersistenceHistoryDAOImpl");
	IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
	IPersistenceDAOImpl<Space> iPersistenceSpaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("iPersistenceSpaceDAOImpl");
	IPersistenceDAOImpl<SpaceSequence> iPersistenceSpaceSequenceDAOImpl = (IPersistenceDAOImpl<SpaceSequence>) context.getBean("iPersistenceSpaceSequenceDAOImpl");
	IPersistenceDAOImpl<Item> iPersistenceItemDAOImpl = (IPersistenceDAOImpl<Item>) context.getBean("iPersistenceItemDAOImpl");
	IPersistenceDAOImpl<ItemItem> iPersistenceItemItemDAOImpl = (IPersistenceDAOImpl<ItemItem>) context.getBean("iPersistenceItemItemDAOImpl");
	private Space space;
	private SpaceSequence spaceSequence;
	private History history;
	private Attachment attachment00;
	private Attachment attachment01;
	private User loggedBy;
	private User assignedTo;
	private ItemItem itemItem00;
	private ItemItem itemItem01;
	@Before
	public void testBefore(){
		/* create ItemItem */
		this.itemItem00 = new ItemItem();
		this.itemItem00.setType(ItemItem.RELATED);
		this.itemItem01 = new ItemItem();
		this.itemItem01.setType(ItemItem.DUPLICATE_OF);
		try {
			iPersistenceItemItemDAOImpl.persist(this.itemItem00);
			iPersistenceItemItemDAOImpl.persist(this.itemItem01);
			assertNotNull(iPersistenceItemItemDAOImpl.findByID(this.itemItem00.getId()));
			assertNotNull(iPersistenceItemItemDAOImpl.findByID(this.itemItem01.getId()));
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* create attachment */
		this.attachment00 = new Attachment();
		this.attachment01 = new Attachment();
		this.attachment00.setFileName("filename");
		this.attachment01.setFileName("filename01");
		this.attachment00.setFilePrefix(0);
		this.attachment01.setFilePrefix(0);
		this.attachment00.setPrevious(null);
		this.attachment01.setPrevious(null);
		try {
			iPersistenceAttachmentDAOImpl.persist(attachment00);
			iPersistenceAttachmentDAOImpl.persist(attachment01);
			Attachment atFound = iPersistenceAttachmentDAOImpl.findByID(attachment00.getId());
			Attachment at01Found = iPersistenceAttachmentDAOImpl.findByID(attachment01.getId());
			assertNotNull(atFound);
			assertNotNull(at01Found);
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
		/* create History */
		this.history = new History();
		this.history.setType(1);
		this.history.setComment("Comment");
		this.history.setActualEffort(1.20);
		this.history.setLoggedBy(this.loggedBy);
		this.history.setAssignedTo(this.assignedTo);
		try {
			iPersistenceHistoryDAOImpl.persist(history);
			History hisFound = iPersistenceHistoryDAOImpl.findByID(history.getId());
			assertNotNull(hisFound);
			assertNotNull(hisFound.getLoggedBy());
			assertNotNull(hisFound.getAssignedTo());
			assertEquals("email@email.com", hisFound.getLoggedBy().getEmail());
			assertEquals("email-assignedTo@email.com", hisFound.getAssignedTo().getEmail());
			assertEquals("Comment", hisFound.getComment());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		/* create Space */
		this.spaceSequence = new SpaceSequence();
		this.space = new Space();
		space.setName("Space one");
		space.setGuestAllowed(true);
		space.setDescription("Description space one");
		space.setPrefixCode("prefixCode");
		space.setSpaceSequence(spaceSequence);
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
	}
	/**
	 * scenario 01 :
	 * 01 - create a new Item
	 * 02 - set the properties
	 * 03 - persist to the database
	 * 04 - check the properties
	 * 05 - delete the object out the database
	 */
	@Test
	public void test() {
		Item item = new Item();
		item.setType(0);
	    item.setSpace(this.space);
	    item.setSequenceNum(1);
	    item.add(history);
//	    private Set<Item> children;
	    item.add(this.attachment00);
	    item.add(this.attachment01);
	    item.setEditReason("test item persist");
	    try {
			iPersistenceItemDAOImpl.persist(item);
			Item itemFound = iPersistenceItemDAOImpl.findByID(item.getId());
			assertNotNull(itemFound);
			assertNotNull(itemFound.getSpace());
			assertEquals("Space one", itemFound.getSpace().getName());
//			assertEquals(1, itemFound.getHistory().size());
			assertEquals(2,itemFound.getAttachments().size());
//			assertEquals(1,itemFound.getSequenceNum());
		} catch (DataDAOException e) {
			assertNull(e);
		}
//	    try {
//			iPersistenceItemDAOImpl.delete(item.getId());
//		} catch (DataDAOException e) {
//			assertNull(e);
//		}
	}
	@After
	public void testAfter(){
//		try {
//			iPersistenceHistoryDAOImpl.delete(history.getId());
//			iPersistenceAttachmentDAOImpl.delete(this.attachment00.getId());
//			iPersistenceAttachmentDAOImpl.delete(this.attachment01.getId());
//			iPersistenceUserDAOImpl.delete(this.loggedBy.getId());
//			iPersistenceUserDAOImpl.delete(this.assignedTo.getId());
//			iPersistenceSpaceDAOImpl.delete(this.space.getId());
//			iPersistenceSpaceSequenceDAOImpl.delete(space.getSpaceSequence().getId());
//		} catch (DataDAOException e) {
//			assertNull(e);
//		}
	}

}
