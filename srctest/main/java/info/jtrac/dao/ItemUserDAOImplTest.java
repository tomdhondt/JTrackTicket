package main.java.info.jtrac.dao;

import static org.junit.Assert.*;
import main.java.info.jtrac.domain.ItemUser;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unchecked")
public class ItemUserDAOImplTest {
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
	IPersistenceDAOImpl<ItemUser> iPersistenceItemUserDAOImpl = (IPersistenceDAOImpl<ItemUser>) context.getBean("iPersistenceItemUserDAOImpl");
	private User user; 
	@Before
	public void testBefore(){
		this.user = new User();
		this.user.setType(1);
		this.user.setLoginName("loginname");
		this.user.setName("name");
		this.user.setPassword("1234");
		this.user.setEmail("email@test.be");
		this.user.setLocale("en");
		this.user.setLocked(false);
	    try {
			iPersistenceUserDAOImpl.persist(user);
			User u = iPersistenceUserDAOImpl.findByID(user.getId());
			assertNotNull(u);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	@Test
	public void testScenario01() {
		ItemUser iUser = new ItemUser();
		iUser.setType(0);
		iUser.setUser(this.user);
		try {
			iPersistenceItemUserDAOImpl.persist(iUser);
			ItemUser iUserFound = iPersistenceItemUserDAOImpl.findByID(iUser.getId());
			assertNotNull(iUserFound);
			assertNotNull(iUserFound.getUser());
			assertEquals(0, iUserFound.getType());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		try {
			iPersistenceItemUserDAOImpl.delete(iUser.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	@After
	public void testAfter(){
		try {
			iPersistenceUserDAOImpl.delete(this.user.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
}
