package fr.aphp.sls.melbase.dao.test.json;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.zkoss.json.JSONObject;

import fr.aphp.sls.melbase.view.graphs.Interval;
import fr.aphp.sls.melbase.view.graphs.PatientViewVM;

/**
 * 
 * Classe de test pour le DAO SocDao et le bean du domaine Soc.
 * 
 * @author Mathieu BARTHELEMY.
 * @version 1.0
 *
 */
public class JsonTest {

	public JsonTest() {
		super();
	}
	
	@Test
	public void testMapToJson() throws ParseException {

		JSONObject fromto1 = new JSONObject();
		fromto1.put("from", ((Date) new SimpleDateFormat("dd/mm/yyyy")
			.parseObject("12/12/2012")).getTime());
		fromto1.put("to", ((Date) new SimpleDateFormat("dd/mm/yyyy")
								.parseObject("13/12/2012")).getTime());
		
		JSONObject fromto2 = new JSONObject();
		fromto2.put("from", ((Date) new SimpleDateFormat("dd/mm/yyyy")
										.parseObject("12/12/2013")).getTime());
		fromto2.put("to", ((Date) new SimpleDateFormat("dd/mm/yyyy")
								.parseObject("13/12/2013")).getTime());
		
		List<JSONObject> ftLst = new ArrayList<JSONObject>();
		ftLst.add(fromto1);
		ftLst.add(fromto2);
		
		JSONObject main = new JSONObject();
	    main.put("name", "Sleep");
	    main.put("intervals", ftLst);
	    
		List<JSONObject> tasks = new ArrayList<JSONObject>();
		tasks.add(main);

	    System.out.print(tasks);
	}
	
	@Test
	public void testExtractAndJSON() {
		PatientViewVM pVM = new PatientViewVM();
		pVM.extractTraitVisitsIntervals("01-00004");
		String jsonOutput = pVM.formatVisitsIntervalJSON(pVM.getIntervals(), "VISITE");
		System.out.println(jsonOutput);
		jsonOutput = pVM.formatVisitsMilestonesJSON(pVM.getVisitMs());
		System.out.println(jsonOutput);
	}
	
	@Test
	public void testExtractIntervals() throws SQLException, ParseException {
		PatientViewVM pVM = new PatientViewVM();
		pVM.extractTraitVisitsIntervals("01-00004");
		assertTrue(pVM.getInclusion().equals("01-00004"));
		assertTrue(pVM.getInitiales().equals("J K"));
		List<Interval> ivs = pVM.getIntervals();
		assertTrue(ivs.size() == 3);
		assertTrue(ivs.contains(new Interval("ipilimumab")));
		assertTrue(ivs.contains(new Interval("Essai thérapeutique")));
		assertTrue(ivs.contains(new Interval("fotémusine")));
		
		Interval essai = ivs.get(ivs.indexOf(new Interval("Essai thérapeutique")));
		assertTrue(essai.getFrom().equals(new SimpleDateFormat("dd/MM/yyyy").parseObject("12/04/2013")));
		assertTrue(essai.getTo().equals(new SimpleDateFormat("dd/MM/yyyy").parseObject("15/05/2013")));
		assertTrue(essai.getLabel().equals("88"));
		
		Interval immuno = ivs.get(ivs.indexOf(new Interval("ipilimumab")));
		assertTrue(immuno.getFrom().equals(new SimpleDateFormat("dd/MM/yyyy").parseObject("15/05/2013")));
		assertTrue(immuno.getTo().equals(new SimpleDateFormat("dd/MM/yyyy").parseObject("15/10/2013")));
		assertTrue(immuno.getCategorie().equals("859"));
		
		Interval chimio = ivs.get(ivs.indexOf(new Interval("fotémusine")));
		assertTrue(chimio.getFrom().equals(new SimpleDateFormat("dd/MM/yyyy").parseObject("15/10/2013")));
		assertTrue(chimio.getTo() == null);
		assertTrue(chimio.getCategorie().equals("868"));
	}
	
	@Test
	public void testExtractReponsesAndJSON() {
		PatientViewVM pVM = new PatientViewVM();
		pVM.extractTraitVisitsIntervals("01-00019");
		String jsonOutput = pVM.formatReponsesPointsJSON(pVM.getReponsePoints());
		System.out.println(jsonOutput);
	}
}
