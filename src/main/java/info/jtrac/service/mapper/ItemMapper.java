package main.java.info.jtrac.service.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.util.MappingUtil;

@SuppressWarnings("unchecked")
public class ItemMapper extends AbstractMapper<Item, ItemDTO>{
	/**
	 * Method will map a DTO to a Object
	 * @return object as Item
	 * @param dto as ItemDTO 
	 */
	@Override
	public Item mapToObject(ItemDTO dto) throws MapperException {
	    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IPersistenceDAOImpl<Space> spaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("spaceDAOImpl");
		Item object = null;
		if(null != dto){
		    object = new Item();
			object.setType(MappingUtil.mapStringToInteger(dto.getType()));
		    /* Space */
			Space space = null;
			try {
				space = spaceDAOImpl.findByID(MappingUtil.mapStringToLong(dto.getSpace_Id()));
			} catch (DataDAOException e) {
			}
			object.setSpace(space);
			object.setSequenceNum(MappingUtil.mapStringToLong(dto.getSequenceNum()));
			object.setEditReason(dto.getEditReason());
//		    private Set<History> history;
//		    private Set<Item> children;
//		    private Set<Attachment> attachments;
		}
		return object;
	}
	/**
	 * Method will map a Object to a  DTO
	 * @return object as ItemDTO
	 * @param dto as Item
	 */
	@Override
	public ItemDTO mapToDTO(Item object) throws MapperException {
		ItemDTO dto = null;
		if(null != object){
		    dto = new ItemDTO();
			dto.setType(MappingUtil.mapIntegerToString(object.getType()));
			if(null != object.getSpace()){
				dto.setSpace_Id(MappingUtil.mapLongToString(object.getSpace().getId()));
				dto.setSpace_Type(MappingUtil.mapIntegerToString(object.getSpace().getType()));
				dto.setSpace_PrefixCode(object.getSpace().getPrefixCode());
				dto.setSpace_Name(object.getSpace().getName());
				dto.setSpace_Description(object.getSpace().getDescription());
				dto.setSpace_isGuestAllowed(object.getSpace().isGuestAllowed());
			}
			dto.setSequenceNum(MappingUtil.mapLongToString(object.getSequenceNum()));
			dto.setEditReason(object.getEditReason());
		}
		return dto;
	}

}
