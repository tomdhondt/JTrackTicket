package main.java.info.jtrac.service.mapper;

import main.java.info.jtrac.domain.AbstractItem;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.AbstractItemDTO;

public class AbstractItemMapper extends AbstractMapper<AbstractItem, AbstractItemDTO>{
	@Override
	/**
	 * Method will map a DTO to a Object
	 * @return object as AbstractItem
	 * @param dto as AbstractItemDTO 
	 */
	public AbstractItem mapToObject(AbstractItemDTO dto) throws MapperException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Method will map a Object to a DTO
	 * @param object a AbstractItem
	 * @return dto as AbstractItemDTO
	 */
	@Override
	public AbstractItemDTO mapToDTO(AbstractItem object) throws MapperException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
