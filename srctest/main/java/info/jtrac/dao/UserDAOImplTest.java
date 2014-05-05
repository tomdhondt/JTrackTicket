package main.java.info.jtrac.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserRole;
import main.java.info.jtrac.domain.UserSpaceRole;
import main.java.info.jtrac.exception.data.DataDAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class UserDAOImplTest {
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
	IPersistenceDAOImpl<Metadata> iPersistenceMetadataDAOImpl = (IPersistenceDAOImpl<Metadata>) context.getBean("iPersistenceMetadataDAOImpl");
	IPersistenceDAOImpl<UserSpaceRole> iPersistenceUserSpaceRoleDAOImpl = (IPersistenceDAOImpl<UserSpaceRole>) context.getBean("iPersistenceUserSpaceRoleDAOImpl");
	IPersistenceDAOImpl<Space> iPersistenceSpaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("iPersistenceSpaceDAOImpl");
	IPersistenceDAOImpl<SpaceSequence> iPersistenceSpaceSequenceDAOImpl = (IPersistenceDAOImpl<SpaceSequence>) context.getBean("iPersistenceSpaceSequenceDAOImpl");
	private UserSpaceRole userSpaceRole01;
	private UserSpaceRole userSpaceRole02;
	private UserSpaceRole userSpaceRole03;
	private Space space;
	private SpaceSequence spaceSequence;
	
	@Before
	public void testBefore(){
		this.spaceSequence = new SpaceSequence();
		this.space = new Space();
		this.space.setName("Space one");
		this.space.setGuestAllowed(true);
		this.space.setDescription("Description space one");
		this.space.setPrefixCode("prefixCode");
		this.space.setSpaceSequence(spaceSequence);
		try {
			iPersistenceSpaceDAOImpl.persist(space);
			assertNotNull(iPersistenceSpaceDAOImpl.findByID(space.getId()));
		} catch (DataDAOException e) {
			assertNull(e);
		}
		this.userSpaceRole01 = new UserSpaceRole();
		this.userSpaceRole02 = new UserSpaceRole();
		this.userSpaceRole03 = new UserSpaceRole();
		this.userSpaceRole01.setRoleKey("roleKey 01");
		this.userSpaceRole02.setRoleKey("roleKey 02");
		this.userSpaceRole03.setRoleKey("roleKey 03");
		this.userSpaceRole01.setSpace(space);
		this.userSpaceRole02.setSpace(space);
		this.userSpaceRole03.setSpace(space);
		this.userSpaceRole01.setUser(null);
		this.userSpaceRole02.setUser(null);
		this.userSpaceRole03.setUser(null);
		try {
			iPersistenceUserSpaceRoleDAOImpl.persist(this.userSpaceRole01);
			iPersistenceUserSpaceRoleDAOImpl.persist(this.userSpaceRole02);
			iPersistenceUserSpaceRoleDAOImpl.persist(this.userSpaceRole03);
			assertTrue(3 == iPersistenceUserSpaceRoleDAOImpl.findAll().size());
			assertNotNull(iPersistenceUserSpaceRoleDAOImpl.findByID(this.userSpaceRole01.getId()));
			assertNotNull(iPersistenceUserSpaceRoleDAOImpl.findByID(this.userSpaceRole02.getId()));
			assertNotNull(iPersistenceUserSpaceRoleDAOImpl.findByID(this.userSpaceRole03.getId()));
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	/**
	 * Scenario 01 :
	 * 01 - Create a User
	 * 02 - persist the User to the database
	 * 03 - catch the exception
	 */
	@Test
	public void testScenario01() {
		User user = null;
		try {
			iPersistenceUserDAOImpl.persist(user);
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
	public void testScenario02(){
		Metadata metaDataParent = new Metadata();
		Metadata metaData = new Metadata(); 
		metaDataParent.setType(1);
		metaData.setType(2);
		metaDataParent.setName("MetaData Parent Name");
		metaData.setName("MetaData Name");
		metaDataParent.setDescription("Metadata Parent Description");    
	    metaData.setDescription("Metadata Description");
	    metaData.setParent(metaDataParent);
	    try {
			iPersistenceMetadataDAOImpl.persist(metaData);
			assertNotNull(iPersistenceMetadataDAOImpl.findByID(metaDataParent.getId()));
			assertNotNull(iPersistenceMetadataDAOImpl.findByID(metaData.getId()));
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    User parent = new User();
	    parent.setType(1);
	    parent.setLoginName("loginname parent");
	    parent.setName("name parent");
	    parent.setPassword("12345678");
	    parent.setEmail("email.parent@test.be");
		User user = new User();
	    user.setType(1);
	    user.setParent(parent);
	    user.setLoginName("loginname");
	    user.setName("name");
	    user.setPassword("1234");
	    user.setEmail("email@test.be");
	    user.setLocale("en");
	    user.setLocked(false);
	    try {
			iPersistenceUserDAOImpl.persist(user);
			User u = iPersistenceUserDAOImpl.findByID(user.getId());
			u.setMetadata(metaData);
			iPersistenceUserDAOImpl.update(u);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			User userFound = iPersistenceUserDAOImpl.findByID(user.getId());
			assertNotNull(userFound);
			assertNotNull(userFound.getMetadata());
			assertEquals("loginname", userFound.getLoginName());
			assertEquals("name",userFound.getName());
			assertEquals("1234",userFound.getPassword());
			assertEquals("email@test.be",userFound.getEmail());
			assertEquals("en",userFound.getLocale());
			assertEquals(false,userFound.isLocked());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			iPersistenceUserDAOImpl.delete(user.getId());
			iPersistenceUserDAOImpl.delete(parent.getId());
			iPersistenceMetadataDAOImpl.delete(metaData.getId());
			iPersistenceMetadataDAOImpl.delete(metaDataParent.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	@After
	public void testAfter(){
		try {
			iPersistenceUserSpaceRoleDAOImpl.delete(this.userSpaceRole03.getId());
			iPersistenceUserSpaceRoleDAOImpl.delete(this.userSpaceRole02.getId());
			iPersistenceUserSpaceRoleDAOImpl.delete(this.userSpaceRole01.getId());
			iPersistenceSpaceDAOImpl.delete(space.getId());
			iPersistenceSpaceSequenceDAOImpl.delete(space.getSpaceSequence().getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	/**
	 * Scenario 02 : namedQuery
	 * 01 - create user
	 * 02 - persist user
	 * 03 - findByCriteria
	 * 04 - check equals user
	 * 05 - delete from the database
	 */
	@Test
	public void testScenario03(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
		User user = new User();
	    user.setType(1);
	    user.setLoginName("user.login.name");
	    user.setName("name");
	    user.setPassword("1234");
	    user.setEmail("email@test.be");
	    user.setLocale("en");
	    user.setLocked(false);
	    try {
			iPersistenceUserDAOImpl.persist(user);
			User u = iPersistenceUserDAOImpl.findByID(user.getId());
			iPersistenceUserDAOImpl.update(u);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		List<NameQueryParam> list = new ArrayList<NameQueryParam>();
		list.add(new NameQueryParam(1,"loginName","user.login.name"));
		try {
			List<User> result = iPersistenceUserDAOImpl.findByCriteria(list, "findByLoginName");
			assertEquals(1, result.size());
			assertEquals("name",result.get(0).getName());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			iPersistenceUserDAOImpl.delete(user.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	/**
	 * Scenario 02 : namedQuery
	 * 01 - create user
	 * 02 - persist user
	 * 03 - findByCriteria
	 * 04 - remove user
	 * 05 - check the state of the object in the database
	 * 05 - delete from the database
	 */
	@Test
	public void  testScenario04(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IPersistenceDAOImpl<User> iPersistenceUserDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("iPersistenceUserDAOImpl");
		User user = new User();
	    user.setType(1);
	    user.setLoginName("user.login.name");
	    user.setName("name");
	    user.setPassword("1234");
	    user.setEmail("email@test.be");
	    user.setLocale("en");
	    user.setLocked(false);
	    user.setUserRole(UserRole.ADMIN);
	    try {
			iPersistenceUserDAOImpl.persist(user);
			User u = iPersistenceUserDAOImpl.findByID(user.getId());
			iPersistenceUserDAOImpl.update(u);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		List<NameQueryParam> list = new ArrayList<NameQueryParam>();
		list.add(new NameQueryParam(1,"loginName","user.login.name"));
		User userFound = null;
		try {
			List<User> result = iPersistenceUserDAOImpl.findByCriteria(list, "findByLoginName");
			assertEquals(1, result.size());
			assertEquals("name",result.get(0).getName());
			userFound = result.get(0);
		} catch (DataDAOException e) {
			assertNull(e);
		}
		try {
			iPersistenceUserDAOImpl.remove(userFound.getId());
			List<User> result = iPersistenceUserDAOImpl.findByCriteria(list, "findByLoginName");
			assertEquals(1, result.size());
			assertEquals("name",result.get(0).getName());
			assertEquals(false, result.get(0).isActive());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	    try {
			iPersistenceUserDAOImpl.delete(user.getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
}
