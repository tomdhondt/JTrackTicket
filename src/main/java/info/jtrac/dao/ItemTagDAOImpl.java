package main.java.info.jtrac.dao;

import main.java.info.jtrac.domain.ItemTag;
/**
 * Class ItemTagDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class ItemTagDAOImpl extends AbstractDAOImpl<ItemTag> implements IPersistenceDAOImpl<ItemTag>{
	/**
	 * Default constructor for the class
	 */
	public ItemTagDAOImpl(){
		super.type = ItemTag.class;
	}
}
