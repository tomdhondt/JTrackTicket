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
				/**
				 * Serial version ID
				 */
				private static final long serialVersionUID = 3582922532882713738L;
				@Override
				public Space getSpace() {
					return null;
				}
				@Override
				public String getRefId() {
					return null;
				}
			};
			object.setId(MappingUtil.mapStringToLong(dto.getId()));
			object.setParent(AbstractItemMapper.getItem(MappingUtil.mapStringToLong(dto.getParent_Item_Id())));
			object.setSummary(dto.getSummary());
			object.setDetail(dto.getDetail());
			object.setLoggedBy(AbstractItemMapper.getUser(MappingUtil.mapStringToLong(dto.getUser_loggedBy_Id())));
			object.setAssignedTo(AbstractItemMapper.getUser(MappingUtil.mapStringToLong(dto.getUser_assignedTo_Id())));
			object.setPlannedEffort(MappingUtil.mapStringToDouble(dto.getPlannedEffort()));
			object.setStatus(dto.getStatus());
			object.setTimeStamp(dto.getTimeStamp());
			object.setSeverity(MappingUtil.mapStringToInteger(dto.getSeverity()));				// Integer
			object.setPriority(MappingUtil.mapStringToInteger(dto.getPriority()));				// Integer
			object.setCusInt01(MappingUtil.mapStringToInteger(dto.getCusInt01()));				// Integer
			object.setCusInt02(MappingUtil.mapStringToInteger(dto.getCusInt02()));				// Integer
			object.setCusInt03(MappingUtil.mapStringToInteger(dto.getCusInt03()));				// Integer
			object.setCusInt04(MappingUtil.mapStringToInteger(dto.getCusInt04()));				// Integer
			object.setCusInt05(MappingUtil.mapStringToInteger(dto.getCusInt05()));				// Integer
			object.setCusInt06(MappingUtil.mapStringToInteger(dto.getCusInt06()));				// Integer
			object.setCusInt07(MappingUtil.mapStringToInteger(dto.getCusInt07()));				// Integer
			object.setCusInt08(MappingUtil.mapStringToInteger(dto.getCusInt08()));				// Integer
			object.setCusInt09(MappingUtil.mapStringToInteger(dto.getCusInt09()));				// Integer
			object.setCusInt10(MappingUtil.mapStringToInteger(dto.getCusInt10()));				// Integer
			object.setCusDbl01(MappingUtil.mapStringToDouble(dto.getCusDbl01()));				// Double
			object.setCusDbl02(MappingUtil.mapStringToDouble(dto.getCusDbl01()));				// Double
			object.setCusDbl03(MappingUtil.mapStringToDouble(dto.getCusDbl01()));				// Double
			object.setCusStr01(dto.getCusStr01());												// String
			object.setCusStr02(dto.getCusStr01());												// String
			object.setCusStr03(dto.getCusStr01());												// String
			object.setCusStr04(dto.getCusStr01());												// String
			object.setCusStr05(dto.getCusStr01());												// String
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
		/*
		 * Instance members
		 */
		AbstractItemDTO dto = null;
		if(null != object){
			dto = new AbstractItemDTO();
			dto.setId(MappingUtil.mapLongToString(object.getId()));
			dto.setParent_Item_Id(MappingUtil.mapLongToString(object.getParent().getId()));
			dto.setParent_Item_EditReason(object.getParent().getEditReason());
			dto.setSummary(object.getSummary());
			dto.setDetail(object.getDetail());
			dto.setTimeStamp(object.getTimeStamp());
			dto.setUser_loggedBy_Id(MappingUtil.mapLongToString(object.getLoggedBy().getId()));
			dto.setUser_loggedBy_name(object.getLoggedBy().getName());
			dto.setUser_loggedBy_loginName(object.getLoggedBy().getLoginName());
			dto.setUser_loggedBy_email(object.getLoggedBy().getEmail());
			dto.setUser_assignedTo_Id(MappingUtil.mapLongToString(object.getAssignedTo().getId()));
			dto.setUser_assignedTo_name(object.getAssignedTo().getName());
			dto.setUser_assignedTo_loginName(object.getAssignedTo().getLoginName());
			dto.setUser_assignedTo_email(object.getAssignedTo().getEmail());
			dto.setPlannedEffort(MappingUtil.mapDoubleToString(object.getPlannedEffort()));
			dto.setStatus(object.getStatus());
			dto.setSeverity(MappingUtil.mapIntegerToString(object.getSeverity()));
			dto.setPriority(MappingUtil.mapIntegerToString(object.getPriority()));				// Integer
			dto.setCusInt01(MappingUtil.mapIntegerToString(object.getCusInt01()));				// Integer
			dto.setCusInt02(MappingUtil.mapIntegerToString(object.getCusInt02()));				// Integer
			dto.setCusInt03(MappingUtil.mapIntegerToString(object.getCusInt03()));				// Integer
			dto.setCusInt04(MappingUtil.mapIntegerToString(object.getCusInt04()));				// Integer
			dto.setCusInt05(MappingUtil.mapIntegerToString(object.getCusInt05()));				// Integer
			dto.setCusInt06(MappingUtil.mapIntegerToString(object.getCusInt06()));				// Integer
			dto.setCusInt07(MappingUtil.mapIntegerToString(object.getCusInt07()));				// Integer
			dto.setCusInt08(MappingUtil.mapIntegerToString(object.getCusInt08()));				// Integer
			dto.setCusInt09(MappingUtil.mapIntegerToString(object.getCusInt09()));				// Integer
			dto.setCusInt10(MappingUtil.mapIntegerToString(object.getCusInt10()));				// Integer
			dto.setCusDbl01(MappingUtil.mapDoubleToString(object.getCusDbl01()));				// Double
			dto.setCusDbl02(MappingUtil.mapDoubleToString(object.getCusDbl02()));				// Double
			dto.setCusDbl03(MappingUtil.mapDoubleToString(object.getCusDbl03()));				// Double
			dto.setCusStr01(object.getCusStr01());												// String
			dto.setCusStr02(object.getCusStr02());												// String
			dto.setCusStr03(object.getCusStr03());												// String
			dto.setCusStr04(object.getCusStr04());												// String
			dto.setCusStr05(object.getCusStr05());												// String
	//		dto.setCusTim01();				// Date
	//		dto.setCusTim02();				// Date
	//		dto.setCusTim03();    			// Date
		}
		return dto;
	}
	/*
	 * Method will get the reference user out the database
	 */
	public static User getUser(long id){
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
	public static Item getItem(long id){
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
