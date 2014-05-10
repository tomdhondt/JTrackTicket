package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.MetadataDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.dto.SpaceSequenceDTO;
import main.java.info.jtrac.util.MappingUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class SpaceManagerTest {
	/* Instance Members */
	private MetadataDTO metadataDTO;
	private MetadataDTO metadataParentDTO;
	private SpaceSequenceDTO spaceSeqDTO;
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<MetadataDTO> iMetadataManager = (IManager<MetadataDTO>) context.getBean("iMetadataManager");
	IPersistenceDAOImpl<Metadata> metadataDAOImpl = (IPersistenceDAOImpl<Metadata>) context.getBean("metadataDAOImpl");
	IPersistenceDAOImpl<Space> spaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("spaceDAOImpl");
	IManager<SpaceSequenceDTO> iSpaceSequenceManager = (IManager<SpaceSequenceDTO>) context.getBean("iSpaceSequenceManager");
	IManager<SpaceDTO> iSpaceManager = (IManager<SpaceDTO>) context.getBean("iSpaceManager");
	IPersistenceDAOImpl<SpaceSequence> spaceSequenceDAOImpl = (IPersistenceDAOImpl<SpaceSequence>) context.getBean("spaceSequenceDAOImpl");
	@Before
	public void beforeSpaceManager(){
		metadataParentDTO = new MetadataDTO();
		metadataParentDTO.setId("");
		metadataParentDTO.setType("2");
		metadataParentDTO.setName("Name parent metadata");
		metadataParentDTO.setDescription("Description about the parent metadata");
		metadataParentDTO.setXmlString("<metadata><fields>" 
                + "<field name='cusInt01' label='Test Label 1'/>"
                + "<field name='cusInt02' label='Test Label 2'/>"
                + "</fields></metadata>");
		try {
			this.iMetadataManager.persist(metadataParentDTO);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name","Name parent metadata"));
			List<MetadataDTO> listResult = this.iMetadataManager.findByCriteria(list, "findByName");
			metadataParentDTO = listResult.get(0);
		} catch (ManagerException e) {
			assertNull(e);
		}
		metadataDTO = new MetadataDTO();
		metadataDTO.setId("");
		metadataDTO.setType("2");
		metadataDTO.setName("Name metadata");
		metadataDTO.setDescription("Description about the metadata");
		metadataDTO.setXmlString("<metadata><fields>" 
                + "<field name='cusInt03' label='Test Label 3'/>"
                + "<field name='cusInt04' label='Test Label 4'/>"
                + "</fields></metadata>");
		try {
			this.iMetadataManager.persist(metadataDTO);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name","Name metadata"));
			List<MetadataDTO> listResult = this.iMetadataManager.findByCriteria(list, "findByName");
			metadataDTO = listResult.get(0);
		} catch (ManagerException e) {
			assertNull(e);
		}
		spaceSeqDTO = new SpaceSequenceDTO();
		try {
			iSpaceSequenceManager.persist(spaceSeqDTO);
		} catch (ManagerException e) {
			assertNull(e);
		}
	}
	@Test
	public void testScenario01() {
		SpaceDTO dTo = new SpaceDTO();
		dTo.setDescription("Description Space");
		dTo.setName("SpaceName");
		dTo.setGuestAllowed(true);
		dTo.setMd_Id(metadataDTO.getId());
		dTo.setMd_type(metadataDTO.getType());
		dTo.setMd_name(metadataDTO.getName());
		dTo.setMd_description(metadataDTO.getDescription());
		dTo.setMd_xmlString(metadataDTO.getXmlString());
		dTo.setSpaceSequence_id(this.spaceSeqDTO.getId());
		dTo.setSpaceSequence_nextSeqNum(this.spaceSeqDTO.getNextSeqNum());
		try {
			iSpaceManager.persist(dTo);
		} catch (ManagerException e) {
			assertNull(e);
		}
		try {
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name","SpaceName"));
			List<SpaceDTO> listResult = this.iSpaceManager.findByCriteria(list, "Space_findByName");
			SpaceDTO spaceDTO = listResult.get(0);
			assertNotNull(spaceDTO);
			assertEquals("SpaceName", spaceDTO.getName());
			assertEquals("Description Space", spaceDTO.getDescription());
		} catch (ManagerException e) {
			assertNull(e);
		}
		try {
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name","SpaceName"));
			List<SpaceDTO> listResult;
			listResult = this.iSpaceManager.findByCriteria(list, "Space_findByName");
			SpaceDTO spaceDTO = listResult.get(0);
			this.spaceDAOImpl.delete(MappingUtil.parseInt(spaceDTO.getId()));
		} catch (ManagerException e) {
			assertNull(e);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
	@After
	public void afterSpaceManager(){
		List<NameQueryParam> list = new ArrayList<NameQueryParam>();
		List<NameQueryParam> listParent = new ArrayList<NameQueryParam>();
		list.add(new NameQueryParam(1, "name", "Name metadata"));
		listParent.add(new NameQueryParam(1, "name", "Name parent metadata"));
		List<MetadataDTO> result = null;
		List<MetadataDTO> resultParent = null;
		try {
			result = this.iMetadataManager.findByCriteria(list, "findByName");
			resultParent = this.iMetadataManager.findByCriteria(listParent, "findByName");
			/* delete the Metadata */
			this.metadataDAOImpl.delete(MappingUtil.mapStringToLong(resultParent.get(resultParent.size()-1).getId()));
			this.metadataDAOImpl.delete(MappingUtil.mapStringToLong(result.get(result.size()-1).getId()));
			/* delete the spaceSequence */
			List<SpaceSequence> listSs = spaceSequenceDAOImpl.findAll();
			spaceSequenceDAOImpl.delete(listSs.get(listSs.size()-1).getId());
		} catch (ManagerException e) {
			assertNull(e);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}

}
