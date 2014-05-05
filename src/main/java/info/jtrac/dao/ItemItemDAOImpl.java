package main.java.info.jtrac.dao;

import main.java.info.jtrac.domain.ItemItem;
/**
 * Class ItemItemDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class ItemItemDAOImpl extends AbstractDAOImpl<ItemItem> implements IPersistenceDAOImpl<ItemItem>{
	/**
	 * Default constructor for the class
	 */
	public ItemItemDAOImpl(){
		super.type = ItemItem.class;
	}
}
