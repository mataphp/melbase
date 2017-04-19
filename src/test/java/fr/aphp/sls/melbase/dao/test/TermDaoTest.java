package fr.aphp.sls.melbase.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import fr.aphp.sls.melbase.dao.ctcae.TermDao;
import fr.aphp.sls.melbase.model.ctcae.Soc;

/**
 * 
 * Classe de test pour le DAO TermDao et le bean du domaine Term.
 * 
 * @author Mathieu BARTHELEMY.
 * @version 1.0
 *
 */
public class TermDaoTest extends AbstractDaoTest {

	@Autowired
	private TermDao termDao;
	
	public TermDaoTest() {
		super();
	}
	
	@Test
	public void testFindBySoc() {
		Soc cardiacDisorders = new Soc();
		cardiacDisorders.setSocId(2);
		cardiacDisorders.setSoc("Cardiac disorders");
		assertTrue(termDao.findBySoc(cardiacDisorders).size() == 36);
	}
	
	@Test
	public void testFindByTermLike() {
		assertTrue(termDao.findByTermLike("%platelet%").size() == 1);
		assertTrue(termDao.findByTermLike("%Anal%").size() == 7);
		assertTrue(termDao.findByTermLike("%Gold%").isEmpty());
		assertTrue(termDao.findByTermLike("%").size() == 790);
	}
}
