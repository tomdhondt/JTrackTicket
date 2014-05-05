package main.java.info.jtrac.dao;

import main.java.info.jtrac.domain.Tag;
/**
 * Class TagDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class TagDAOImpl extends AbstractDAOImpl<Tag> implements IPersistenceDAOImpl<Tag>{
	/**
	 * Default constructor for the class
	 */
	public TagDAOImpl(){
		super.type = Tag.class;
	}
}
