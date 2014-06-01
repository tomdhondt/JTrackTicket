package main.java.info.jtrac.service.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.AttachmentDTO;
import main.java.info.jtrac.util.MappingUtil;

@SuppressWarnings("unchecked")
public class AttachmentMapper extends AbstractMapper<Attachment, AttachmentDTO>{
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<Attachment> iPersistenceAttachmentDAOImpl = (IPersistenceDAOImpl<Attachment>) context.getBean("iPersistenceAttachmentDAOImpl");
	@Override
	public Attachment mapToObject(AttachmentDTO dto) throws MapperException {
		Attachment object = null;
		if(null != dto){
			object = new Attachment();
			object.setFileName(dto.getFileName());
			if(null != dto.getFilePrefix()){
				object.setFilePrefix(MappingUtil.mapStringToLong(dto.getFilePrefix()));	
			}else{
				object.setFilePrefix(0);
			}
			if(null != dto.getId()){
				object.setId(MappingUtil.mapStringToLong(dto.getId()));	
			}else{
				object.setId(0);
			}
			if(null != dto.getPrevious_Att_Id()){
				long id = MappingUtil.mapStringToLong(dto.getPrevious_Att_Id());
				try {
					Attachment previous = iPersistenceAttachmentDAOImpl.findByID(id);
					object.setPrevious(previous);
				} catch (DataDAOException e) {
					object.setPrevious(null);
				}
			}else{
				object.setPrevious(null);	
			}
		}
		return object;
	}

	@Override
	public AttachmentDTO mapToDTO(Attachment object) throws MapperException {
		AttachmentDTO dto = null;
		if(null != object){
			dto = new AttachmentDTO();
			dto.setFileName(object.getFileName());
			dto.setFilePrefix(MappingUtil.mapLongToString(object.getFilePrefix()));	
			dto.setId(MappingUtil.mapLongToString(object.getId()));
			if(null != object.getPrevious()){
				dto.setPrevious_Att_Id(MappingUtil.mapLongToString(object.getPrevious().getId()));	
			}
		}
		return dto;
	}

}
