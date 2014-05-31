package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.ItemUser;
/**
 * Class ItemDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class ItemUserDAOImpl extends AbstractDAOImpl<ItemUser> implements IPersistenceDAOImpl<ItemUser>{
	/**
	 * Default constructor for the class
	 */
	public ItemUserDAOImpl(){
		super.type = ItemUser.class;
	}
	/**
	 * Default constructor for the class
	 */
	public ItemUserDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = ItemUser.class;
	}
}
