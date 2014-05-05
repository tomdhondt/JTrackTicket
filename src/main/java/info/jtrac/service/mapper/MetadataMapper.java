package main.java.info.jtrac.service.mapper;

import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.MetadataDTO;
import main.java.info.jtrac.util.MappingUtil;

public class MetadataMapper extends AbstractMapper<Metadata, MetadataDTO>{
	/**
	 * Method will map a DTO to a Object
	 * @return object as Metadata
	 * @param dto as MetadataDTO
	 */
	@Override
	public Metadata mapToObject(MetadataDTO dto) throws MapperException {
		Metadata object = null;
		try{
			if(null != dto){
				Metadata objectP = null;
				if(null != dto.getParent_Id()){
					objectP = new Metadata();
					objectP.setId(MappingUtil.mapStringToLong(dto.getParent_Id()));
					objectP.setName(dto.getParent_Name());
					objectP.setType(MappingUtil.parseInt(dto.getParent_Type()));
					objectP.setDescription(dto.getParent_Description());
					objectP.setXmlString(dto.getParent_XmlString());
				}
				object = new Metadata();
				object.setId(MappingUtil.mapStringToLong(dto.getId()));
				object.setName(dto.getName());
				object.setType(MappingUtil.parseInt(dto.getType()));
				object.setDescription(dto.getDescription());
				object.setXmlString(dto.getXmlString());
				object.setParent(objectP);
			}
		}catch(Exception e){
			throw new MapperException("mapperException.object");
		}
		return object;
	}
	/**
	 * Method will map a object to a DTO
	 * @return dto as MetadataDTO
	 * @param object as Metadata
	 */
	@Override
	public MetadataDTO mapToDTO(Metadata object) throws MapperException {
		MetadataDTO dto = null;
		try{
		if(null != object){
			dto = new MetadataDTO();
			dto.setId(MappingUtil.mapLongToString(object.getId()));
			dto.setName(object.getName());
			dto.setType(MappingUtil.parseInt(object.getType()));
			dto.setDescription(object.getDescription());
			dto.setXmlString(object.getXmlString());
			if(null != object.getParent()){
				dto.setParent_Id(MappingUtil.mapLongToString(object.getParent().getId()));
				dto.setParent_Name(object.getParent().getName());
				dto.setParent_Type(MappingUtil.parseInt(object.getParent().getType()));
				dto.setParent_Description(object.getParent().getDescription());
				dto.setParent_XmlString(object.getParent().getXmlString());
			}
		}
		}catch(Exception e){
			throw new MapperException("mapperException.dto");
		}
		return dto;
	}

}
