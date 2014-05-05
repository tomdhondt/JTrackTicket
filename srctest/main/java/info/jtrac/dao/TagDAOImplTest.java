package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.Tag;
import main.java.info.jtrac.exception.data.DataDAOException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class TagDAOImplTest {
	/**
	 * Instance member
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IPersistenceDAOImpl<Tag> iPersistenceTagDAOImpl = (IPersistenceDAOImpl<Tag>) context.getBean("iPersistenceTagDAOImpl");
	@Test
	public void testScenario01() {
		Tag tag = new Tag();
		tag.setName("tag name");
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
		try {
			iPersistenceTagDAOImpl.delete(tag.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
}
