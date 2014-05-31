package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.UserSpaceRole;
/**
 * Class UserSpaceRoleDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class UserSpaceRoleDAOImpl extends AbstractDAOImpl<UserSpaceRole> implements IPersistenceDAOImpl<UserSpaceRole>{
	/**
	 * Default constructor for the class
	 */
	public UserSpaceRoleDAOImpl(){
		super.type = UserSpaceRole.class;
	}
	/**
	 * Default constructor for the class
	 */
	public UserSpaceRoleDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = UserSpaceRole.class;
	}
}
