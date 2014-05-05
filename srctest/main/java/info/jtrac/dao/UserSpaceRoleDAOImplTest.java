package main.java.info.jtrac.dao;

import static org.junit.Assert.*;

import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserSpaceRole;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class UserSpaceRoleDAOImplTest {
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<UserSpaceRole> iPersistenceUserSpaceRoleDAOImpl = (IPersistenceDAOImpl<UserSpaceRole>) context.getBean("iPersistenceUserSpaceRoleDAOImpl");
	IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
	IPersistenceDAOImpl<Space> iPersistenceSpaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("iPersistenceSpaceDAOImpl");
	/**
	 * Scenario 01 :
	 * 01 - Create a UserSpaceRole
	 * 02 - persist the UserSpaceRole to the database
	 * 03 - catch the exception
	 */
	@Test
	public void testScenario01() {
		UserSpaceRole object = null;
		try {
			iPersistenceUserSpaceRoleDAOImpl.persist(object);
		} catch (DataDAOException e) {
			assertNotNull(e);
		}
	}
	/**
	 * Scenario 01 :
	 * 01 - Create a Matadata
	 * 02 - Persist the Matadata to the database
	 * 03 - Create User
	 * 04 - Persist the user to the database
	 * 05 - check the state of the user in the database
	 * 06 - delete the Metadata out the database
	 * 07 - delete the User out the database
	 */
	@Test
	public void testScenario02() {
		SpaceSequence sSq = new SpaceSequence();
		Space space = new Space();
		space.setName("Space one");
		space.setGuestAllowed(true);
		space.setDescription("Description space one");
		space.setPrefixCode("prefixCode");
		space.setSpaceSequence(sSq);
		try {
			iPersistenceSpaceDAOImpl.persist(space);
			assertNotNull(iPersistenceSpaceDAOImpl.findByID(space.getId()));
			assertEquals("Space one", iPersistenceSpaceDAOImpl.findByID(space.getId()).getName());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    User user = new User();
	    user.setType(1);
	    user.setLoginName("loginname parent");
	    user.setName("name parent");
	    user.setPassword("12345678");
	    user.setEmail("email.parent@test.be");
	    try {
			iPersistenceUserDAOImpl.persist(user);
			assertNotNull(iPersistenceUserDAOImpl.findByID(user.getId()));
			assertEquals("name parent", iPersistenceUserDAOImpl.findByID(user.getId()).getName());
		} catch (DataDAOException e) {
			assertNull(e);
		}
		UserSpaceRole object = new UserSpaceRole();
	    object.setUser(user);
	    object.setSpace(space);
	    object.setRoleKey("RoleKey");
	    try {
			iPersistenceUserSpaceRoleDAOImpl.persist(object);
			UserSpaceRole uspFound = iPersistenceUserSpaceRoleDAOImpl.findByID(object.getId());
			assertNotNull(uspFound);
			assertNotNull(uspFound.getUser());
			assertNotNull(uspFound.getSpace());
			assertEquals("name parent", uspFound.getUser().getName());
			assertEquals(null, uspFound.getUser().getParent());
			assertEquals("Description space one", uspFound.getSpace().getDescription());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			iPersistenceUserSpaceRoleDAOImpl.delete(object.getId());
			iPersistenceUserDAOImpl.delete(user.getId());
			iPersistenceSpaceDAOImpl.delete(space.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

}
