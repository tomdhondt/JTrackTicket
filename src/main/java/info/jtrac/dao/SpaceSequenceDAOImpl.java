package main.java.info.jtrac.dao;

import main.java.info.jtrac.domain.SpaceSequence;
/**
 * Class SpaceSequenceDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class SpaceSequenceDAOImpl extends AbstractDAOImpl<SpaceSequence> implements IPersistenceDAOImpl<SpaceSequence>{
	/**
	 * Default constructor for the class
	 */
	public SpaceSequenceDAOImpl(){
		super.type = SpaceSequence.class;
	}
}
