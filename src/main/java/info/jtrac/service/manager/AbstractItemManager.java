package main.java.info.jtrac.service.manager;
import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.AbstractItem;
import main.java.info.jtrac.service.dto.AbstractItemDTO;
import main.java.info.jtrac.service.mapper.IMapper;
/**
 * 
 * Class AbstractItem
 * @author Tom Dhondt - created at : 11-mei-2014
 *
 */
public class AbstractItemManager extends AbstractManager<AbstractItemDTO, AbstractItem> implements IManager<AbstractItemDTO>{
	/**
	 * Default constructor for the Class
	 */
	public AbstractItemManager(){
		super.type = AbstractItem.class;
	}
	/**
	 * Constructor for the Class AbstractItemManager
	 * @param daoImpl as IPersistenceDAOImpl
	 * @param mapper as IMapper
	 */
	public AbstractItemManager(IPersistenceDAOImpl<AbstractItem> daoImpl, IMapper<AbstractItem, AbstractItemDTO> mapper){
		super.type = AbstractItem.class;
		super.daoImpl = daoImpl;
		super.mapper = mapper;
	}
}
