package main.java.info.jtrac.dao;

import main.java.info.jtrac.domain.Item;
/**
 * Class ItemDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class ItemDAOImpl extends AbstractDAOImpl<Item> implements IPersistenceDAOImpl<Item>{
	/**
	 * Default constructor for the class
	 */
	public ItemDAOImpl(){
		super.type = Item.class;
	}
}
