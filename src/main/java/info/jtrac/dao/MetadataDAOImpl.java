package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.Metadata;
/**
 * Class MetadataDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class MetadataDAOImpl extends AbstractDAOImpl<Metadata> implements IPersistenceDAOImpl<Metadata>{
	/**
	 * Default constructor for the class
	 */
	public MetadataDAOImpl(){
		super.type = Metadata.class;
	}
	/**
	 * Default constructor for the class
	 */
	public MetadataDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = Metadata.class;
	}
}
