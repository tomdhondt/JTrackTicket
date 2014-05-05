package main.java.info.jtrac.service.manager;
import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.service.mapper.IMapper;

public class UserManager extends AbstractManager<UserDTO, User> implements IManager<UserDTO>{
	/**
	 * Default constructor for the Class
	 */
	public UserManager(){
		super.type = User.class;
	}
	/**
	 * Constructor for the Class UserManager
	 * @param daoImpl as IPersistenceDAOImpl
	 * @param mapper as IMapper
	 */
	public UserManager(IPersistenceDAOImpl<User> daoImpl, IMapper<User, UserDTO> mapper){
		super.type = User.class;
		super.daoImpl = daoImpl;
		super.mapper = mapper;
	}
}
