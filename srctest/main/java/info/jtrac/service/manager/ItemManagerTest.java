package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.util.MappingUtil;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ItemManagerTest {
	/*
	 * Scenario 01:
	 * 01 - create a null object
	 * 02 - persist to the database
	 * 03 - catch the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testScenario01(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IManager<ItemDTO> iItemManager = (IManager<ItemDTO>) context.getBean("iItemManager");
		ItemDTO itemDTO = null;
		try {
			iItemManager.persist(itemDTO);
		} catch (ManagerException e) {
			assertEquals("Object.isNull",e.getCaption());
		}
	}
	/*
	 * Scenario 02 : JValidate
	 * 01 - create a ItemDTO
	 * 02 - persist to the database
	 * 03 - catch the JValidation error for the SpaceID
	 * 04 - create a SpaceDTO
	 * 05 - Set the SpaceID
	 * 06 - Persist to the database
	 * 07 - catch the JValidation error for editReason
	 * 08 - set the EditReason
	 * 09 - persist to the database
	 * 10 - check persisted object in the database
	 * 11 - compare the persisted object in the database
	 * 12 - delete the persisted object in the database
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testScenario02() {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
		IManager<ItemDTO> iItemManager = (IManager<ItemDTO>) context.getBean("iItemManager");
		IManager<SpaceDTO> iSpaceManager = (IManager<SpaceDTO>) context.getBean("iSpaceManager");
		IPersistenceDAOImpl<Item> itemDAOImpl = (IPersistenceDAOImpl<Item>) context.getBean("itemDAOImpl");
		IPersistenceDAOImpl<Space> spaceDAOImpl = (IPersistenceDAOImpl<Space>) context.getBean("spaceDAOImpl");
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setDetail("itemDetail");
		try {
			iItemManager.persist(itemDTO);
		} catch (ManagerException e) {
			assertNotNull(e.getCaption());
			assertEquals("itemdto.editreason.isRequired", e.getCaption());
		}
		SpaceDTO spaceDTOFound = null;
		SpaceDTO space = new SpaceDTO();
		space.setName("SpaceName");
		space.setDescription("Space description");
		try {
			iSpaceManager.persist(space);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name", "SpaceName"));
			List<SpaceDTO> result = iSpaceManager.findByCriteria(list,Space.NAMEDQUERY_FINDBYNAME);
			assertNotNull(result);
			spaceDTOFound = result.get(result.size()-1);
			assertNotNull(spaceDTOFound);
		} catch (ManagerException e) {
			assertNull(e);
		}		
		itemDTO.setSpace_Id(spaceDTOFound.getId());
		ItemDTO itemDTOFound = null;
		try {
			iItemManager.persist(itemDTO);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"detail", "itemDetail"));
			List<ItemDTO> result = iItemManager.findByCriteria(list,Item.NAMEDQUERY_FINDBYDETAIL);
			assertNotNull(result);
			assertNotNull(result.get(result.size()-1));
			itemDTOFound = result.get(result.size()-1);
			assertEquals("detail", result.get(result.size()-1).getDetail());
			assertEquals(spaceDTOFound.getId(), result.get(result.size()-1).getSpace_Id());
			assertNotNull(result);
		} catch (ManagerException e) {
			assertNotNull(e.getCaption());
			assertEquals("itemdto.space.isrequired", e.getCaption());
		}
//		try {
//			spaceDAOImpl.delete(MappingUtil.mapStringToLong(spaceDTOFound.getId()));
//			itemDAOImpl.delete(MappingUtil.mapStringToLong(itemDTOFound.getId()));
//		} catch (DataDAOException e) {
//			assertNull(e);
//		}
	}
}
