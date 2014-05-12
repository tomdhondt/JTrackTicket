package main.java.info.jtrac.service.manager;
import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.service.mapper.IMapper;

public class ItemManager extends AbstractManager<ItemDTO, Item> implements IManager<ItemDTO>{
	/**
	 * Default constructor for the Class
	 */
	public ItemManager(){
		super.type = Item.class;
	}
	/**
	 * Constructor for the Class ItemManager
	 * @param daoImpl as IPersistenceDAOImpl
	 * @param mapper as IMapper
	 */
	public ItemManager(IPersistenceDAOImpl<Item> daoImpl, IMapper<Item, ItemDTO> mapper){
		super.type = Item.class;
		super.daoImpl = daoImpl;
		super.mapper = mapper;
	}
}
