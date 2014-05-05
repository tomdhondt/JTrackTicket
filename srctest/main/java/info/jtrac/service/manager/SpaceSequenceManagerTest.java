package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;

import java.util.List;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.SpaceSequenceDTO;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class SpaceSequenceManagerTest {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<SpaceSequenceDTO> iSpaceSequenceManager = (IManager<SpaceSequenceDTO>) context.getBean("iSpaceSequenceManager");
	IPersistenceDAOImpl<SpaceSequence> spaceSequenceDAOImpl = (IPersistenceDAOImpl<SpaceSequence>) context.getBean("spaceSequenceDAOImpl");
	@Test
	public void testScenario01() {
		SpaceSequenceDTO dto = new SpaceSequenceDTO();
		dto.setNextSeqNum("10");
		assertEquals("10", dto.getNextSeqNum());
		assertEquals("11", dto.next());
		try {
			int cnt = iSpaceSequenceManager.findAll().size();
			iSpaceSequenceManager.persist(dto);
			int cntNew = iSpaceSequenceManager.findAll().size();
			assertTrue(cnt < cntNew);
		} catch (ManagerException e) {
			assertNull(e);
		}
		try {
			List<SpaceSequence> listSs = spaceSequenceDAOImpl.findAll();
			spaceSequenceDAOImpl.delete(listSs.get(listSs.size()-1).getId());
		} catch (DataDAOException e) {
			assertNull(e);
		}

	}
}
