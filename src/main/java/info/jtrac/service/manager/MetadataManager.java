package main.java.info.jtrac.service.manager;
import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.service.dto.MetadataDTO;
import main.java.info.jtrac.service.mapper.IMapper;
/**
 * 
 * Class MetadataManager
 * @author Tom Dhondt - created at : 4-mei-2014
 *
 */
public class MetadataManager extends AbstractManager<MetadataDTO, Metadata> implements IManager<MetadataDTO>{
	/**
	 * Default constructor for the Class
	 */
	public MetadataManager(){
		super.type = Metadata.class;
	}
	/**
	 * Constructor for the Class MetadataManager
	 * @param daoImpl as IPersistenceDAOImpl
	 * @param mapper as IMapper
	 */
	public MetadataManager(IPersistenceDAOImpl<Metadata> daoImpl, IMapper<Metadata, MetadataDTO> mapper){
		super.type = Metadata.class;
		super.daoImpl = daoImpl;
		super.mapper = mapper;
	}
}
