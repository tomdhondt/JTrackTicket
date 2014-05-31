package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.History;
/**
 * Class UserSpaceRoleDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class HistoryDAOImpl extends AbstractDAOImpl<History> implements IPersistenceDAOImpl<History>{
	/**
	 * Default constructor for the class
	 */
	public HistoryDAOImpl(){
		super.type = History.class;
	}
	/**
	 * Constructor for the class
	 */
	public HistoryDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = History.class;
	}
}
