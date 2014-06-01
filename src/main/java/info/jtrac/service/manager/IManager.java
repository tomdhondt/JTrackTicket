package main.java.info.jtrac.service.manager;

import java.util.List;

import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.exception.manager.ManagerException;


public interface IManager <T>{
	/**
	 * Method will find the object T by id
	 * @param id as long
	 * @return T as found object
	 */
	public T findByID(long id) throws ManagerException;
	/**
	 * Method will return all the objects<T>
	 * @param type as Class<T>
	 * @return objects as List<T>
	 * @throws  
	 */
	public List<T> findAll() throws ManagerException;
	/**
	 * Method will return all the objects<T>
	 * @param type as Class<T>
	 * @param count as Integer
	 * @return objects as List<T>
	 * @throws  
	 */
	public List<T> findAll(int count) throws ManagerException;
	/**
	 * Method will remove the object out the database
	 * @param entity as T
	 * @return boolean as success rate
	 */
	public boolean remove(long id) throws ManagerException;
	/**
	 * Method will persist the object in the database
	 * @param object as T
	 * @return 
	 * @return T as object
	 */
	public void persist(T object) throws ManagerException;
	/**
	 * Method will find a object based on the criteria of the object
	 * @param Object as T
	 * @return T as result
	 */
	public List<T> findByCriteria(List<NameQueryParam> list , String namedQuery) throws ManagerException;
	/**
	 * Method will find a object based on the criteria of the object
	 * @param Object as T
	 * @param count as Integer
	 * @return T as result
	 */
	public List<T> findByCriteria(List<NameQueryParam> list , String namedQuery, int count) throws ManagerException;
	/**
	 * Method will find a object based on the criteria of the object
	 * @param namedQuery as String
	 * @return List<Object> as result
	 */
	public List<Object> findByCriteria(String namedQuery) throws ManagerException;
	/**
	 * Method will update the object in the database
	 * @param object as T
	 * @return T as updated object
	 */
	public void update(T object) throws ManagerException;
}
