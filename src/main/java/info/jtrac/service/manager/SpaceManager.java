package main.java.info.jtrac.service.manager;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.mapper.IMapper;

public class SpaceManager extends AbstractManager<SpaceDTO, Space> implements IManager<SpaceDTO>{
	/**
	 * Default constructor for the Class
	 */
	public SpaceManager(){
		super.type = Space.class;
	}
	/**
	 * Constructor for the Class SpaceManager
	 * @param daoImpl as IPersistenceDAOImpl
	 * @param mapper as IMapper
	 */
	public SpaceManager(IPersistenceDAOImpl<Space> daoImpl, IMapper<Space, SpaceDTO> mapper){
		super.type = Space.class;
		super.daoImpl = daoImpl;
		super.mapper = mapper;
	}
}
