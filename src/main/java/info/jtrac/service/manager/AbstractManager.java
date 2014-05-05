package main.java.info.jtrac.service.manager;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.mapper.IMapper;

public abstract class AbstractManager<D,T> implements IManager<D> {	
	/* 
	 * Instance Members
	 */
	protected Class<T> type;
	protected IPersistenceDAOImpl<T> daoImpl;
	protected IMapper<T,D> mapper;
    /**
	 * Default constructor for the Class
	 */
	public AbstractManager(){
	}
	/**
	 * Method will return all the objects<T>
	 * @param type as Class<T>
	 * @return objects as List<T>
	 * @throws  ManagerException
	 */
	@Override
	public List<D> findAll() throws ManagerException{
		List<D> resultList = null;
		try{
			/* initiate the List<D> */
			resultList = new ArrayList<D>(); 
			/* get the object out the Database */
			List<T> objectList = this.daoImpl.findAll();
			/* iterate the list */
			for(T obj : objectList){
				/* map the Object to DTO and add to the resultList */
				try{
					resultList.add(this.mapper.mapToDTO(obj));
				}catch(MapperException mXe){
					throw new ManagerException(mXe.getCaption());
				}
			}
		}catch(Exception e){
			new ManagerException("manager.findAll.error");
		}
		return resultList;
	}
	/**
	 * Method will find the object T by id
	 * @param id as int
	 * @return T as found object
	 */
	@Override
	public D findByID(long id) throws ManagerException{
		/* get the object out the database */
		T object = null;
		try{
			object = this.daoImpl.findByID(id);
		}catch(DataDAOException dXe){
			throw new ManagerException(dXe.getCaption());
		}
		/* map t he object to a DTO */
		D objectDTO = null;
		try{
			objectDTO = this.mapper.mapToDTO(object);
		}catch(MapperException mXe){
			throw new ManagerException(mXe.getCaption());
		}
		return objectDTO;
	}
	/**
	 * Method will remove the object out the database
	 * @param entity as T
	 * @return boolean as success rate
	 */
	@Override
	public boolean remove(long id) throws ManagerException{
		/* delete the object out the database */
		boolean success = false;
		try{
			success = this.daoImpl.remove(id);
		}catch(DataDAOException dXe){
			throw new ManagerException(dXe.getCaption());
		}
		return success;
	}
	/**
	 * Method will persist the object in the database
	 * @param object as T
	 * @return 
	 * @return T as object
	 */
	@Override
	public void persist(D dto) throws ManagerException{
		/* map the DTO to a object */
		T object  = null;
		try{
			object = this.mapper.mapToObject(dto);
		}catch(MapperException mXe){
			throw new ManagerException(mXe.getCaption());
		}
		/* persist the object in the database */
		try{
			this.daoImpl.persist(object);
		}catch(DataDAOException dXe){
			throw new ManagerException(dXe.getCaption());
		}
	}
	/**
	 * Method will find a object based on the criteria of the object
	 * @param Object as T
	 * @return T as result
	 */
	@Override
	public List<D> findByCriteria(List<NameQueryParam> list , String namedQuery) throws ManagerException{
		List<D> listResult = new ArrayList<>();
		List<T> listQueryResult = null;
		try {
			listQueryResult = (List<T>) this.daoImpl.findByCriteria(list, namedQuery);
		} catch (DataDAOException dXe) {
			throw new ManagerException(dXe.getCaption());
		}
		for(T object : listQueryResult){
			try {
				listResult.add(this.mapper.mapToDTO(object));
			} catch (MapperException dXe) {
				throw new ManagerException(dXe.getCaption());
			}
		}
		return listResult;
	}
	/**
	 * Method will update the object in the database
	 * @param object as T
	 */
	@Override
	public void update(D dto) throws ManagerException{
		/* map the DTO to a object */
		T object  = null;
		try{
			object = this.mapper.mapToObject(dto);
		}catch(MapperException mXe){
			throw new ManagerException(mXe.getCaption());
		}
		/* persist the object in the database */
		try{
			this.daoImpl.update(object);
		}catch(DataDAOException dXe){
			throw new ManagerException(dXe.getCaption());
		}
	}
}
