package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.ItemItem;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class ItemItemDAOImplTest {
	/**
	 * Instance member
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
	IPersistenceDAOImpl<ItemItem> iPersistenceItemItemDAOImpl = (IPersistenceDAOImpl<ItemItem>) context.getBean("iPersistenceItemItemDAOImpl");
	IPersistenceDAOImpl<Item> iPersistenceItemDAOImpl = (IPersistenceDAOImpl<Item>) context.getBean("iPersistenceItemDAOImpl");
	private Item item;
	private Item relatedItem;
	
	@Before
	public void testBefore() {
		this.item = new Item();
		this.item.setEditReason("item");
		this.relatedItem = new Item();
		this.relatedItem.setEditReason("relatedItem");
		try {
			iPersistenceItemDAOImpl.persist(item);
			iPersistenceItemDAOImpl.persist(relatedItem);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

	@After
	public void testAfter(){
		try {
			iPersistenceItemDAOImpl.delete(this.item.getId());
			iPersistenceItemDAOImpl.delete(this.relatedItem.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

	@Test
	public void testScenario01() {
		ItemItem iItem = new ItemItem();
		iItem.setItem(item);
		iItem.setRelatedItem(relatedItem);
		iItem.setType(ItemItem.RELATED);
		try {
			iPersistenceItemItemDAOImpl.persist(iItem);
			ItemItem fItem = iPersistenceItemItemDAOImpl.findByID(iItem.getId());
			assertNotNull(fItem);
			assertNotNull(fItem.getItem());
			assertNotNull(fItem.getRelatedItem());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		try {
			iPersistenceItemItemDAOImpl.delete(iItem.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

}
