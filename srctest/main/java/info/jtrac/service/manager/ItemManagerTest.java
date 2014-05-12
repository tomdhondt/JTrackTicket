package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.ItemDTO;

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
		ItemDTO dto = null;
		try {
			iItemManager.persist(dto);
		} catch (ManagerException e) {
			assertEquals("Object.isNull",e.getCaption());
		}
	}
	/*
	 * Scenario 02 :
	 * 01 - create a ItemDTO
	 * 02 - persist the ItemDTO
	 * 03 - get the ItemDTO findByCriteria
	 * 04 - compare the persisted and returned object
	 * 05 - delete the object out the database
	 */
//	@Test
	public void testScenario02() {
//		ItemDTO dto = new ItemDTO();
		
	}

}
