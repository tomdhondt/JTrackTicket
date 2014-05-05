package main.java.info.jtrac.service.mapper;

import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.SpaceSequenceDTO;
import main.java.info.jtrac.util.MappingUtil;

public class SpaceSequenceMapper extends AbstractMapper<SpaceSequence, SpaceSequenceDTO> {
	/**
	 * Method will map a DTO to a Object
	 * @return object as SpaceSequence
	 * @param dto as SpaceSequenceDTO 
	 */
	@Override
	public SpaceSequence mapToObject(SpaceSequenceDTO dto) throws MapperException {
		SpaceSequence object = null;
		if(null!=dto){
			object = new SpaceSequence();
			object.setId(MappingUtil.mapStringToLong(dto.getId()));
			object.setNextSeqNum(MappingUtil.mapStringToLong(dto.getNextSeqNum()));
		}
		return object;
	}
	/**
	 * Method will map a Object to a DTO
	 * @param object a SpaceSequence
	 * @return dto as SpaceSequenceDTO
	 */
	@Override
	public SpaceSequenceDTO mapToDTO(SpaceSequence object) throws MapperException {
		SpaceSequenceDTO dto = null;
		if(null!=object){
			dto = new SpaceSequenceDTO();
			dto.setId(MappingUtil.mapLongToString(object.getId()));
			dto.setNextSeqNum(MappingUtil.mapLongToString(object.getNextSeqNum()));
		}
		return dto;
	}

}
