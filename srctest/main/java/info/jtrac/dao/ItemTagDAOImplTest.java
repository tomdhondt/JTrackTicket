package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.ItemTag;
import main.java.info.jtrac.domain.Tag;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class ItemTagDAOImplTest {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Tag> iPersistenceTagDAOImpl = (IPersistenceDAOImpl<Tag>) context.getBean("iPersistenceTagDAOImpl");
	IPersistenceDAOImpl<ItemTag> iPersistenceItemTagDAOImpl = (IPersistenceDAOImpl<ItemTag>) context.getBean("iPersistenceItemTagDAOImpl");
	private Tag tag;
	@Before
	public void testBefore(){
		this.tag = new Tag();
		this.tag.setName("tag name");
		tag.setType(0);
		tag.setDescription("tag description");
		try {
			iPersistenceTagDAOImpl.persist(tag);
			Tag tagFound = iPersistenceTagDAOImpl.findByID(tag.getId());
			assertEquals("tag name", tagFound.getName());
			assertEquals("tag description", tagFound.getDescription());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

	@After
	public void testAfter(){
		try {
			iPersistenceTagDAOImpl.delete(this.tag.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

	@Test
	public void testScenario01() {
		ItemTag itemTag = new ItemTag();
		itemTag.setTag(this.tag);
		itemTag.setType(0);
		try {
			iPersistenceItemTagDAOImpl.persist(itemTag);
			ItemTag iTagFound = iPersistenceItemTagDAOImpl.findByID(itemTag.getId());
			assertNotNull(iTagFound);
			assertEquals(0, iTagFound.getType());
			assertNotNull(iTagFound.getTag());
			assertEquals("tag description", iTagFound.getTag().getDescription());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		try {
			iPersistenceItemTagDAOImpl.delete(itemTag.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

}
