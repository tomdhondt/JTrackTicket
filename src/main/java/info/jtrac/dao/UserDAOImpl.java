package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.User;
/**
 * Class UserDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class UserDAOImpl extends AbstractDAOImpl<User> implements IPersistenceDAOImpl<User>{
	/**
	 * Default constructor for the class
	 */
	public UserDAOImpl(){
		super.type = User.class;
	}
	/**
	 * Default constructor for the class
	 */
	public UserDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = User.class;
	}
}
