package main.java.info.jtrac.service.manager;
import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.service.dto.SpaceSequenceDTO;
import main.java.info.jtrac.service.mapper.IMapper;
/**
 * Class SpaceSequenceManager
 * @author Tom Dhondt - created at : 3-mei-2014
 */
public class SpaceSequenceManager extends AbstractManager<SpaceSequenceDTO, SpaceSequence> implements IManager<SpaceSequenceDTO>{
	/**
	 * Default constructor for the Class
	 */
	public SpaceSequenceManager(){
		super.type = SpaceSequence.class;
	}
	/**
	 * Constructor for the Class SpaceSequenceManager
	 * @param daoImpl as IPersistenceDAOImpl
	 * @param mapper as IMapper
	 */
	public SpaceSequenceManager(IPersistenceDAOImpl<SpaceSequence> daoImpl, IMapper<SpaceSequence, SpaceSequenceDTO> mapper){
		super.type = SpaceSequence.class;
		super.daoImpl = daoImpl;
		super.mapper = mapper;
	}
}
