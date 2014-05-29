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
public class AttachmentMapper  extends AbstractMapper<Attachment, AttachmentDTO>{
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IPersistenceDAOImpl<Attachment> attachmentDAOImpl = (IPersistenceDAOImpl<Attachment>) context.getBean("attachmentDAOImpl");
	@Override
	public Attachment mapToObject(AttachmentDTO dto) throws MapperException {
		Attachment object = null;
		if(null != dto){
			object = new Attachment();
			if(null != dto.getId()){
				object.setId(MappingUtil.mapStringToLong(dto.getId()));
			}
			object.setFileName(dto.getFileName());
			if(null != dto.getFilePrefix()){
				object.setFilePrefix(MappingUtil.mapStringToLong(dto.getFilePrefix()));
			}
			if(null != dto.getPrevious_Att_Id()){
				long prev_att_id = MappingUtil.mapStringToLong(dto.getPrevious_Att_Id());
				Attachment at;
				try {
					at = attachmentDAOImpl.findByID(prev_att_id);
					object.setPrevious(at);
				} catch (DataDAOException e) {
				}
			}
		}
		return object;
	}

	@Override
	public AttachmentDTO mapToDTO(Attachment object) throws MapperException {
		AttachmentDTO dto = null;
		if(null != object){
			dto = new AttachmentDTO();
			dto.setId(MappingUtil.mapLongToString(object.getId()));
			dto.setFileName(object.getFileName());
			dto.setFilePrefix(MappingUtil.mapLongToString(object.getFilePrefix()));
			if(null != object.getPrevious()){
				dto.setPrevious_Att_Id(MappingUtil.mapLongToString(object.getPrevious().getId()));
			}
		}
		return dto;
	}

}
