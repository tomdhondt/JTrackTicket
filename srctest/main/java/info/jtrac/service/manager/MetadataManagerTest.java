package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.MetadataDTO;
import main.java.info.jtrac.util.MappingUtil;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class MetadataManagerTest {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<MetadataDTO> iMetadataManager = (IManager<MetadataDTO>) context.getBean("iMetadataManager");
	IPersistenceDAOImpl<Metadata> metadataDAOImpl = (IPersistenceDAOImpl<Metadata>) context.getBean("metadataDAOImpl");
	@Test
	public void testMetadataPersist(){
		MetadataDTO dto = new MetadataDTO("default Metadata", "default description MetadataDTO", "<metadata><fields></fields></metadata>");
		try {
			iMetadataManager.persist(dto);
		} catch (ManagerException e) {
			System.out.println(e);
		}
	}
	/*
	 * Scenario 01 :
	 * 01 - create Metadata
	 * 02 - persist the Metadata to the database
	 * 03 - check the object in the database
	 * 04 - delete the object in the database 
	 */
//	@Test
	public void test() {
		MetadataDTO parentObject = new MetadataDTO();
		parentObject.setId("");
		parentObject.setType("2");
		parentObject.setName("Name parent metadata");
		parentObject.setDescription("Description about the parent metadata");
		parentObject.setXmlString("<metadata><fields>" 
                + "<field name='cusInt01' label='Test Label 1'/>"
                + "<field name='cusInt02' label='Test Label 2'/>"
                + "</fields></metadata>");
		try {
			this.iMetadataManager.persist(parentObject);
		} catch (ManagerException e) {
			assertNull(e);
		}
		MetadataDTO object = new MetadataDTO();
		object.setId("");
		object.setType("2");
		object.setName("Name metadata");
		object.setDescription("Description about the metadata");
		object.setXmlString("<metadata><fields>" 
                + "<field name='cusInt03' label='Test Label 3'/>"
                + "<field name='cusInt04' label='Test Label 4'/>"
                + "</fields></metadata>");
		try {
			this.iMetadataManager.persist(object);
		} catch (ManagerException e) {
			assertNull(e);
		}
		List<NameQueryParam> list = new ArrayList<NameQueryParam>();
		List<NameQueryParam> listParent = new ArrayList<NameQueryParam>();
		list.add(new NameQueryParam(1, "name", "Name metadata"));
		listParent.add(new NameQueryParam(1, "name", "Name parent metadata"));
		List<MetadataDTO> result = null;
		List<MetadataDTO> resultParent = null;
		try {
			result = this.iMetadataManager.findByCriteria(list, "findByName");
			resultParent = this.iMetadataManager.findByCriteria(listParent, "findByName");
			assertNotNull(result);
			assertNotNull(resultParent);
			assertTrue(result.size() > 0);
			assertTrue(resultParent.size() > 0);
			assertEquals("Description about the metadata", result.get(result.size()-1).getDescription());
			assertEquals("Description about the parent metadata", resultParent.get(resultParent.size()-1).getDescription());
		} catch (ManagerException e) {
			assertNull(e);
		}
		MetadataDTO dtoFound = result.get(result.size()-1);
		dtoFound.setParent_Id(resultParent.get(resultParent.size()-1).getId());
		dtoFound.setParent_Name(resultParent.get(resultParent.size()-1).getName());
		dtoFound.setParent_Type(resultParent.get(resultParent.size()-1).getType());
		dtoFound.setParent_XmlString(resultParent.get(resultParent.size()-1).getXmlString());
		dtoFound.setParent_Description(resultParent.get(resultParent.size()-1).getDescription());
		try {
			iMetadataManager.update(dtoFound);
		} catch (ManagerException e) {
			assertNull(e);
		}
		try {
			this.metadataDAOImpl.delete(MappingUtil.mapStringToLong(resultParent.get(resultParent.size()-1).getId()));
			this.metadataDAOImpl.delete(MappingUtil.mapStringToLong(result.get(result.size()-1).getId()));
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
}
