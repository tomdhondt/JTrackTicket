package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;

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
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setDetail("itemDetail");
		try {
			iItemManager.persist(itemDTO);
		} catch (ManagerException e) {
			assertNotNull(e.getCaption());
			assertEquals("itemdto.editreason.isRequired", e.getCaption());
		}
		SpaceDTO spacePersisted = null;
		SpaceDTO space = new SpaceDTO();
		space.setName("SpaceName");
		space.setDescription("Space description");
		try {
			iSpaceManager.persist(space);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name", "SpaceName"));
			List<SpaceDTO> result = iSpaceManager.findByCriteria(list,Space.NAMEDQUERY_FINDBYNAME);
			assertNotNull(result);
			spacePersisted = result.get(result.size()-1);
			assertNotNull(spacePersisted);
		} catch (ManagerException e) {
			assertNull(e);
		}		
		itemDTO.setSpace_Id(spacePersisted.getId());
		try {
			iItemManager.persist(itemDTO);
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"detail", "itemDetail"));
			List<ItemDTO> result = iItemManager.findByCriteria(list,Item.NAMEDQUERY_FINDBYDETAIL);
			
		} catch (ManagerException e) {
			assertNotNull(e.getCaption());
			assertEquals("itemdto.space.isrequired", e.getCaption());
		}
	}
}
