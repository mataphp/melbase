package fr.aphp.sls.melbase.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import fr.aphp.sls.melbase.dao.ctcae.SocDao;
import fr.aphp.sls.melbase.dao.test.AbstractDaoTest;

/**
 * 
 * Classe de test pour le DAO SocDao et le bean du domaine Soc.
 * 
 * @author Mathieu BARTHELEMY.
 * @version 1.0
 *
 */
public class SocDaoTest extends AbstractDaoTest {

	@Autowired
	private SocDao socDao;
	
	public SocDaoTest() {
		super();
	}
	
	@Test
	public void testFindBySocLike() {
		assertTrue(socDao.findBySocLike("%").size() == 26);
	}
}
