package main.java.info.jtrac.service.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.MetadataDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.dto.SpaceSequenceDTO;
import main.java.info.jtrac.service.manager.IManager;
import main.java.info.jtrac.util.MappingUtil;

@SuppressWarnings("unchecked")
public class SpaceMapper extends AbstractMapper<Space, SpaceDTO>{
	/**
	 * Method will map a DTO to a Object
	 * @return object as Space
	 * @param dto as SpaceDTO 
	 */
	@Override
	public Space mapToObject(SpaceDTO dto) throws MapperException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
		IManager<MetadataDTO> iMetadataManager = (IManager<MetadataDTO>) context.getBean("iMetadataManager");
		IMapper<Metadata,MetadataDTO> iMetadataMapper = (IMapper<Metadata,MetadataDTO>) context.getBean("iMetadataMapper");
		IManager<SpaceSequenceDTO> iSpaceSequenceManager = (IManager<SpaceSequenceDTO>) context.getBean("iSpaceSequenceManager");
		IMapper<SpaceSequence,SpaceSequenceDTO> iSpaceSequenceMapper = (IMapper<SpaceSequence,SpaceSequenceDTO>) context.getBean("iSpaceSequenceMapper");
		Space object = null;
		if(null != dto){
			object = new Space();
			object.setId(MappingUtil.parseInt(dto.getId()));
			object.setDescription(dto.getDescription());
			object.setGuestAllowed(dto.isGuestAllowed());
			object.setName(dto.getName());
			object.setPrefixCode(dto.getPrefixCode());
			object.setType(MappingUtil.parseInt(dto.getType()));
			/* Map the Metadata */
			try{
				MetadataDTO mDTO = iMetadataManager.findByID(MappingUtil.parseInt(dto.getMd_Id()));
				if(null!= mDTO){
					Metadata mObject = iMetadataMapper.mapToObject(mDTO);
					object.setMetadata(mObject);
				};
			}catch(ManagerException mEx){
				object.setMetadata(null);
				throw new MapperException(mEx.getMessage());
			}
			/* Map the SpaceSequence */
			try {
				SpaceSequenceDTO sDTO = iSpaceSequenceManager.findByID(MappingUtil.parseInt(dto.getSpaceSequence_id()));
				if(null != sDTO){
					SpaceSequence sObject = iSpaceSequenceMapper.mapToObject(sDTO);
					object.setSpaceSequence(sObject);
				};
			} catch (ManagerException mEx) {
				object.setSpaceSequence(null);
				throw new MapperException(mEx.getMessage());
			}
		}
		return object;
	}
	/**
	 * Method will map a Object to a DTO
	 * @param object a Space
	 * @return dto as SpaceDTO
	 */
	@Override
	public SpaceDTO mapToDTO(Space object) throws MapperException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IMapper<Metadata,MetadataDTO> iMetadataMapper = (IMapper<Metadata,MetadataDTO>) context.getBean("iMetadataMapper");
		IMapper<SpaceSequence,SpaceSequenceDTO> iSpaceSequenceMapper = (IMapper<SpaceSequence,SpaceSequenceDTO>) context.getBean("iSpaceSequenceMapper");
		SpaceDTO dto = null;
		if(null != object){
			dto = new SpaceDTO();
			dto.setId(MappingUtil.mapLongToString(object.getId()));
			dto.setDescription(object.getDescription());
			dto.setGuestAllowed(object.isGuestAllowed());
			dto.setName(object.getName());
			dto.setPrefixCode(object.getPrefixCode());
			dto.setType(MappingUtil.parseInt(object.getType()));
			/* Map the MetadataDTO */
			try{
				MetadataDTO mObject = iMetadataMapper.mapToDTO(object.getMetadata());
				if(null!= mObject){
					dto.setMd_Id(mObject.getId());
					dto.setMd_type(mObject.getType());
					dto.setMd_name(mObject.getName());
					dto.setMd_description(mObject.getDescription());
					dto.setMd_xmlString(mObject.getXmlString());
					dto.setMdp_Id(mObject.getParent_Id());
					dto.setMdp_type(mObject.getParent_Type());
					dto.setMdp_name(mObject.getParent_Name());
					dto.setMdp_description(mObject.getParent_Description());
					dto.setMdp_xmlString(mObject.getParent_XmlString());
				}
			}catch(MapperException mEx){
				throw new MapperException(mEx.getMessage());
			}
			/* Map the SpaceSequenceDTO */
			try {
				SpaceSequenceDTO sDTO = iSpaceSequenceMapper.mapToDTO(object.getSpaceSequence());
				if(null != sDTO){
					dto.setSpaceSequence_id(sDTO.getId());
					dto.setSpaceSequence_nextSeqNum(sDTO.getNextSeqNum());
				}
			} catch (MapperException mEx) {
				throw new MapperException(mEx.getMessage());
			}
		}
		return dto;
	}
	
}	
