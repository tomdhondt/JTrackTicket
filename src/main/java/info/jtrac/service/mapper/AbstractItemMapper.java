package main.java.info.jtrac.service.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.AbstractItem;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.AbstractItemDTO;
import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.service.manager.IManager;
import main.java.info.jtrac.util.MappingUtil;

@SuppressWarnings("unchecked")
public class AbstractItemMapper extends AbstractMapper<AbstractItem, AbstractItemDTO>{
	@Override
	/**
	 * Method will map a DTO to a Object
	 * @return object as AbstractItem
	 * @param dto as AbstractItemDTO 
	 */
	public AbstractItem mapToObject(AbstractItemDTO dto) throws MapperException {
		/*
		 * Instance members
		 */

		AbstractItem object = null;
		if(null != dto){
			object = new AbstractItem() {
				@Override
				public Space getSpace() {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				public String getRefId() {
					// TODO Auto-generated method stub
					return null;
				}
			};
			object.setId(MappingUtil.mapStringToLong(dto.getId()));
			object.setParent(this.getItem(MappingUtil.mapStringToLong(dto.getParent_Item_Id())));
			object.setSummary(dto.getSummary());
			object.setDetail(dto.getDetail());
			object.setLoggedBy(this.getUser(MappingUtil.mapStringToLong(dto.getUser_loggedBy_Id())));
			object.setAssignedTo(this.getUser(MappingUtil.mapStringToLong(dto.getUser_assignedTo_Id())));
			object.setPlannedEffort(MappingUtil.mapStringToDouble(dto.getPlannedEffort()));
			object.setStatus(MappingUtil.mapStringToLong(dto.getStatus()));
//			object.setSeverity();				// Integer
//			object.setPriority();				// Integer
//			object.setCusInt01();				// Integer
//			object.setCusInt02();				// Integer
//			object.setCusInt03();				// Integer
//			object.setCusInt04();				// Integer
//			object.setCusInt05();				// Integer
//			object.setCusInt06();				// Integer
//			object.setCusInt07();				// Integer
//			object.setCusInt08();				// Integer
//			object.setCusInt09();				// Integer
//			object.setCusInt10();				// Integer
//			object.setCusDbl01();				// Double
//			object.setCusDbl02();				// Double
//			object.setCusDbl03();				// Double
//			object.setCusStr01();				// String
//			object.setCusStr02();				// String
//			object.setCusStr03();				// String
//			object.setCusStr04();				// String
//			object.setCusStr05();				// String
//			object.setCusTim01();				// Date
//			object.setCusTim02();				// Date
//			object.setCusTim03();    			// Date
		
		}
		
		return object;
	}
	/**
	 * Method will map a Object to a DTO
	 * @param object a AbstractItem
	 * @return dto as AbstractItemDTO
	 */
	@Override
	public AbstractItemDTO mapToDTO(AbstractItem object) throws MapperException {
	//		object.setSeverity();				// Integer
	//		object.setPriority();				// Integer
	//		object.setCusInt01();				// Integer
	//		object.setCusInt02();				// Integer
	//		object.setCusInt03();				// Integer
	//		object.setCusInt04();				// Integer
	//		object.setCusInt05();				// Integer
	//		object.setCusInt06();				// Integer
	//		object.setCusInt07();				// Integer
	//		object.setCusInt08();				// Integer
	//		object.setCusInt09();				// Integer
	//		object.setCusInt10();				// Integer
	//		object.setCusDbl01();				// Double
	//		object.setCusDbl02();				// Double
	//		object.setCusDbl03();				// Double
	//		object.setCusStr01();				// String
	//		object.setCusStr02();				// String
	//		object.setCusStr03();				// String
	//		object.setCusStr04();				// String
	//		object.setCusStr05();				// String
	//		object.setCusTim01();				// Date
	//		object.setCusTim02();				// Date
	//		object.setCusTim03();    			// Date
		return null;
	}
	/*
	 * Method will get the reference user out the database
	 */
	private User getUser(long id){
		User user = null;
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IPersistenceDAOImpl<User> userDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("userDAOImpl");
		try {
			 user = userDAOImpl.findByID(id);
		} catch (DataDAOException e) {
		}
		return user;
	}
	/*
	 * Method will get the reference Item out the database
	 */
	private Item getItem(long id){
		Item item = null;
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IPersistenceDAOImpl<Item> itemDAOImpl = (IPersistenceDAOImpl<Item>) context.getBean("itemDAOImpl");
		try {
			 item = itemDAOImpl.findByID(id);
		} catch (DataDAOException e) {
		}
		return item;
	}
}
