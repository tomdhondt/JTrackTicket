package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.Space;
/**
 * Class SpaceDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class SpaceDAOImpl extends AbstractDAOImpl<Space> implements IPersistenceDAOImpl<Space>{
	/**
	 * Default constructor for the class
	 */
	public SpaceDAOImpl(){
		super.type = Space.class;
	}
	/**
	 * Default constructor for the class
	 */
	public SpaceDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = Space.class;
	}
}
