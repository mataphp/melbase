package fr.aphp.sls.melbase.view.graphs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import fr.aphp.sls.melbase.view.graphs.ProgLocalisation.ProgCerebrale;

public class PatientViewVM {

	private Integer centreNum = -1;
	private Integer centreId = -1;
	
	private Date minDate;
	private Date dateFin;
	private String inclusion = "";

	private LinkedHashSet<String> categories = new LinkedHashSet<String>();

	private String initiales;
	private List<VisiteMilestone> visitMs = new ArrayList<VisiteMilestone>();
	private List<Interval> intervals = new ArrayList<Interval>();
	private List<Interval> toxVals = new ArrayList<Interval>();
	private List<Interval> hospVals = new ArrayList<Interval>();
	private List<ReponsePoint> reponsePoints = new ArrayList<ReponsePoint>();
	private List<PrelMilestone> prelMs = new ArrayList<PrelMilestone>();
	
	private List<PrelMilestone> excludedPrelMs = new ArrayList<PrelMilestone>();
	
	private List<String> availSubjectIds = new ArrayList<String>();
	
//	@AfterCompose
//    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
//        Selectors.wireComponents(view, this, false);
//    }
	
	
	@Init
	public void initUserCenter() {
		Object principal = SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}	
		
		Connection connection = null;
		Statement stt = null;
		ResultSet set = null;
		ResultSet set2 = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = null;
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/melbase","tumo","tumo");
			stt = connection.createStatement();
		
			set = stt.executeQuery("select c.center_id, c.number from CENTER c join USER u on u.center_id = c.center_id " +
					"where u.username = '" + username + "'");
		
			centreNum = null;
			centreId = null;
			while (set.next()) {
				centreId = set.getInt(1);
				centreNum = set.getInt(2);
			}
			
			availSubjectIds.clear();
			
			if (centreNum == null || centreNum == 1) {
				set2 = stt.executeQuery("select numero_inclusion from PATIENT order by numero_inclusion");
			} else {
				set2 = stt.executeQuery("select numero_inclusion from PATIENT where center_id = " 
											+ centreId.toString() + " order by numero_inclusion");
			}
			while (set2.next()) {
				availSubjectIds.add(set2.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		   if (connection != null){
			   try{ connection.close();} catch(Exception e){ connection = null;}
		   }
		   if (stt != null){
			   try{ stt.close();} catch(Exception e){ stt = null;}
		   }
		   if (set != null){
			   try{ set.close();} catch(Exception e){ set = null;}
		   }
		   if (set2 != null){
			   try{ set2.close();} catch(Exception e){ set2 = null;}
		   }
		}
	}

	
	@Command("graph")
	public void createGraphe() throws ParseException {
		// restriction par centre
		try {
			if (inclusion.matches("[0-9]{2}-[0-9]{5}") 
				&& (centreNum == null || centreNum.intValue() == 1
					|| centreNum.equals(new 
							Integer(inclusion.substring(0, inclusion.indexOf("-")))))) {
				initiales = null;
				// inclusion = "";
				dateFin = null;
				minDate = null;
				categories.clear();
				visitMs.clear();
				intervals.clear();
				toxVals.clear();
				hospVals.clear();
				reponsePoints.clear();
				prelMs.clear();
				
				extractTraitVisitsIntervals(getInclusion());
				
				categories.add("Biologie");
				categories.add("Suivi");
				
				Clients.evalJavaScript("createGraph("
						   + formatStringToJSParamater(inclusion) + "," 
						   + formatStringToJSParamater(initiales != null ? initiales : "") + ","
						   + formatVisitsIntervalJSON(intervals, "VISITE") + ","
						   + formatVisitsMilestonesJSON(visitMs) + "," 
						   + formatVisitsIntervalJSON(toxVals, "TOXICITE") + ","
						   + formatVisitsIntervalJSON(hospVals, "HOSPITALISATION") + ","
						   + formatCategories(categories) + "," 
						   + dateFin.getTime() + ","
						   + formatReponsesPointsJSON(reponsePoints) + ","
						   + formatPrelMilestonesJSON(prelMs)
						   + ")");	
				} else {
					Messagebox.show("Recherche impossible", "Error", Messagebox.OK, Messagebox.ERROR);
				}
			// clear combobox
			// inclusion = null;
		} catch (RuntimeException re) {
			// Clients.evalJavaScript("clearG
			Messagebox.show(re.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void extractTraitVisitsIntervals(String incl) {
		Connection connection = null;
		Statement stt = null;
		ResultSet set = null;
		ResultSet set2 = null;
		ResultSet set3 = null;
		ResultSet set4 = null;
		
		String evenement = null;
		Integer evtNb = null;
		String decision = null;
		String desc = null;
		String traitement = null;
		Date dateV = null;
		Integer evtId = null;
		Date dateV2 = null;
				
		List<Interval> curVals = new ArrayList<Interval>();
		
		inclusion = incl;
		
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/clinica";
			connection = DriverManager.getConnection(url,"clinica","clinica");
			stt = connection.createStatement();
			
			// deces
			set3 = stt.executeQuery("SELECT DISTINCT * FROM deux_items2('" + inclusion 
					+ "', 'DECES','DECES','DECES', 'DATE','CAUSE')");
			
			while (set3.next()) {
				if (set3.getObject(5) != null && !set3.getObject(5).equals("")) {
					dateFin = set3.getDate(5);
					VisiteMilestone vMs = new VisiteMilestone(set3.getString(9));
					vMs.setVisitNb(set3.getString(6));
					vMs.setDateVisit(set3.getDate(5));
					vMs.setType("DECES");
					visitMs.add(vMs);
				}
			}
			
			// si encore en vie
			if (dateFin == null) {
				dateFin = Calendar.getInstance().getTime();
			}
			
			String queryTv = "SELECT DISTINCT * FROM  ttt_dates_graphes2('" + inclusion 
					+ "') WHERE ttt_nom not like 'autre' order by numero_patient, date_visite; ";
			set = stt.executeQuery(queryTv);
			
			//int j = 0;
		        		   
			// visites - traitements
			while (set.next()) {
				
				// j++;
				
				if (initiales == null) {
					initiales = set.getString(2);
				}
				evtNb = set.getInt(3);
				dateV = set.getDate(4);
				evenement = set.getString(5);
				decision = set.getString(6);
				desc = set.getString(7);
				traitement = set.getString(9);
				evtId = set.getInt(10);
				
				// minDate
				if (dateV != null) {
					if (minDate == null || minDate.after(dateV)) {
						minDate = dateV;
					}
				}
				
				// milestones visites
				if (evenement != null && evenement.equals("INCLUSION")) {
					evtNb = 0;
				}
				if (dateV != null) {
					VisiteMilestone vMs = new VisiteMilestone(evtId.toString());
					vMs.setVisitNb(evtNb.toString());
					vMs.setDateVisit(dateV);
					vMs.setType(evtNb == 0 ? "INCLUSION" : "VISITE");
					// teste si doublons date visite
					for (VisiteMilestone vM : visitMs) {
						if (vMs.getType().equals(vM.getType()) 
								&& !vMs.getVisitEvtId().equals(vM.getVisitEvtId())
								&& dateV.equals(vM.getDateVisit())) {
							throwDoublonVisiteException(evtNb, dateV, vM);
						}
					}
					visitMs.add(vMs);
				}
				
				String[] decisions = extractDecision(decision, desc, traitement);
				
				if (decisions != null && dateV != null) {
					
					// ajoute les intervals nouveaux
					for (int i = 0; i < decisions.length; i++) {
						if (!curVals.contains(new Interval(decisions[i]))) {
							Interval iv = new Interval(decisions[i]);
							iv.setLabel(evtId.toString());
							iv.setFrom(dateV);
							curVals.add(iv);
						} else {
							curVals.get(curVals.indexOf(new Interval(decisions[i])))
								.setLabel(evtId.toString());
						}
					}
					
					// complete les intervals dont la decision n'est pas presente
					for (Interval iv : curVals) {
						if (!iv.getLabel().equals(evtId.toString()) 
							&& !Arrays.asList(decisions).contains(iv.getCategorie())) {
							iv.setTo(dateV);
						}
					}
				}
				
				// extract complete
				Iterator<Interval> ivIt = curVals.iterator();
				Interval cIt;
				while (ivIt.hasNext()) {
					cIt = ivIt.next();
					if (cIt.isComplete()) {
						intervals.add(cIt);
						ivIt.remove();
					}
				}
			} 
			// add incompletes
			for (Interval iv : curVals) {
				iv.setTo(dateFin);
				intervals.add(iv);
			}
			
			curVals.clear();
			
			// toxicites
			String queryTox = "SELECT DISTINCT * FROM  tox('" + 
					inclusion + "') order by tx_date_debut";
		        					
			set2 = stt.executeQuery(queryTox);
							        		        
			while (set2.next()) {
				
				dateV = null;
				dateV2 = null;
				desc = null;
				evtId = null;
				
				if (set2.getObject(5) != null && !set2.getObject(5).equals("")) {
					dateV = set2.getDate(5);
				}
				if (set2.getObject(8) != null && !set2.getObject(8).equals("")) {
					dateV2 = set2.getDate(8);
				} else {
					dateV2 = dateFin;
				}
				desc = set2.getString(4);
				traitement = ""; 
				if (set2.getString(11) != null) {
					traitement = traitement + set2.getString(11);
				}
				if (set2.getString(12) != null) {
					traitement = traitement + set2.getString(12);
				}
				if (set2.getString(13) != null) {
					traitement = traitement + set2.getString(13);
				}
				if (set2.getString(14) != null) {
					traitement = traitement + set2.getString(14);
				}

				evtId = set2.getInt(16);
				
				String[] tts = extractTraitement(traitement);
				if (dateV != null) {
					Interval iv = new Interval(desc);
					iv.setLabel(evtId.toString());
					iv.setFrom(dateV);
					iv.setTo(dateV2);
					iv.setTox(true);
					iv.setDesc("imputée à : " + Arrays.toString(tts));
					toxVals.add(iv);
				}
			} 
			
			// hosp
			String queryHosp = "SELECT DISTINCT * FROM  hosp('"
					+ inclusion + "') ORDER BY hosp_DATE_ADMISSION;";
		        					
			set4 = stt.executeQuery(queryHosp);
							        		        
			while (set4.next()) {
				
				dateV = null;
				dateV2 = null;
				desc = null;
				evtId = null;
				
				if (set4.getObject(5) != null && !set4.getObject(5).equals("")) {
					dateV = set4.getDate(5);
				}
				if (set4.getObject(6) != null && !set4.getObject(6).equals("")) {
					dateV2 = set4.getDate(6);
				} else {
					dateV2 = dateFin;
				}
				desc = set4.getString(8);
				evtId = set4.getInt(15);
				
				String[] raisons = extractTraitement(desc);
				
				if (dateV != null) {
					Interval iv = new Interval(set4.getString(7));
					iv.setLabel(evtId.toString());
					iv.setFrom(dateV);
					iv.setTo(dateV2);
					iv.setDesc("Raison : " + Arrays.toString(raisons));
					hospVals.add(iv);
				}

			}
			
			// reponses
			populateReponsePoints(connection);
			
			// biol
			populatePrelMilestones();
			
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw(re);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		   if (connection != null){
			   try{ connection.close();} catch(Exception e){ connection = null;}
		   }
		   if (stt != null){
			   try{ stt.close();} catch(Exception e){ stt = null;}
		   }
		   if (set != null){
			   try{ set.close();} catch(Exception e){ set = null;}
		   }
		   if(set2 != null){
			   try{ set2.close();} catch(Exception e){ set2 = null;}
		   }
		   if(set3 != null){
			   try{ set3.close();} catch(Exception e){ set3 = null;}
		   }
		   if(set4 != null){
			   try{ set4.close();} catch(Exception e){ set4 = null;}
		   }
		}		
	}
		
	public void populateReponsePoints(Connection conn) {
		// progression
		String queryProg = "SELECT DISTINCT	* FROM reponses('" + inclusion  
			+ "') AS (numero_patient character varying, initiales character varying,  nro_visite integer, "
			+ "dt_visite character varying, REPONSE character varying,REPONSE_PROGR character varying, "
			+ "PROGR_PEAU character varying, PROGR_GG character varying, PROGR_OS character varying, "
			+ "PROGR_POUMONS character varying,PROGR_FOIE character varying, PROGR_RATE character varying, "
			+ "PROGR_SURR character varying, PROGR_DIG character varying, PROGR_PANCR character varying, "
			+ "PROGR_CEREBR character varying,PROGR_CEREB_NB character varying, PROGR_CEREB_TAILLE character varying, "
			+ "PROGR_CEREB_SYMPTO character varying, PROGR_CEREB_LEPTO character varying, "
			+ "PROGR_EPIDURITE character varying, PROGR_METASTASE_OP character varying, PROGR_TOPO_CRAN character varying, "
			+ "bil_radio character varying, se_id integer)	ORDER BY numero_patient , dt_visite";
		Statement stt = null;
		ResultSet rs = null;
		
		ReponsePoint rPoint; 
		ProgLocalisation loc;
		try {  				
			stt = conn.createStatement();
			rs = stt.executeQuery(queryProg);
			while (rs.next()) {
				
				// evtId
				rPoint = new ReponsePoint(rs.getInt(25));
				loc = new ProgLocalisation();
					
				// date visite
				if (rs.getObject(4) != null && !rs.getObject(4).equals("")) {
					rPoint.setDateVisit(rs.getDate(4));
				} else { // throw no-visit exception
					throwNoVisiteException(rs.getInt(3));
				}
				
				// reponse
				if (rs.getObject(5) != null && !rs.getObject(5).equals("")) {
					rPoint.setReponse(rs.getString(5).replace("\"", "").replace("{", "").replace("}", ""));
				}
				
				// progtype
				if (rs.getObject(6) != null && !rs.getObject(6).equals("")) {
					rPoint.setProgType(rs.getString(6).replace("{", "").replace("}", ""));
				}
				
				// localisations
				loc = new ProgLocalisation();
				// peau
				if (rs.getString(7) != null && !rs.getObject(7).equals("")) {
					loc.setPeau(rs.getString(7));
				}
				// gg
				if (rs.getString(8) != null && !rs.getObject(8).equals("")) {
					loc.setGanglions(rs.getString(8));
				}
				// os
				if (rs.getString(9) != null && !rs.getObject(9).equals("")) {
					loc.setOs(rs.getString(9));
				}
				// poumon
				if (rs.getString(10) != null && !rs.getObject(10).equals("")) {
					loc.setPoumons(rs.getString(10));
				}
				// foie
				if (rs.getString(11) != null && !rs.getObject(11).equals("")) {
					loc.setFoie(rs.getString(11));
				}
				// rate
				if (rs.getString(12) != null && !rs.getObject(12).equals("")) {
					loc.setRate(rs.getString(12));
				}
				// surr
				if (rs.getString(13) != null && !rs.getObject(13).equals("")) {
					loc.setSurrenale(rs.getString(13));
				}
				// dig
				if (rs.getString(14) != null && !rs.getObject(14).equals("")) {
					loc.setDig(rs.getString(14));
				}
				// pancreas
				if (rs.getString(15) != null && !rs.getObject(15).equals("")) {
					loc.setPancreas(rs.getString(15));
				}
				// cerebrale
				if (rs.getString(16) != null && !rs.getObject(16).equals("")) {
					ProgCerebrale cereb = new ProgLocalisation.ProgCerebrale(rs.getString(16).equals("oui"));
					// nb
					if (rs.getObject(17) != null && !rs.getObject(17).equals("")) {
						cereb.setNb(rs.getInt(17));
					}
					// taille max
					if (rs.getObject(18) != null && !rs.getObject(18).equals("")) {
						cereb.setTaille(rs.getInt(18));
					}
					// sympto
					if (rs.getString(19) != null && !rs.getObject(19).equals("")) {
						cereb.setSympto(rs.getString(19));
					}
					// lepto
					if (rs.getString(20) != null && !rs.getObject(20).equals("")) {
						cereb.setLepto(rs.getString(20));
					}
					// epidurite
					if (rs.getString(21) != null && !rs.getObject(21).equals("")) {
						cereb.setEpidurite(rs.getString(21));
					}
					// meta
					if (rs.getString(22) != null && !rs.getObject(22).equals("")) {
						cereb.setMeta_op(rs.getString(22));
					}
					// topo_cran
					if (rs.getObject(23) != null && !rs.getObject(23).equals("")) {
						cereb.setTopo_cran(rs.getString(23).replace("{", "").replace("}", ""));
					}				
					loc.setCerebrale(cereb);
				}
				rPoint.setLocalisations(loc);
				if (rPoint.getReponse() != null) {
					getReponsePoints().add(rPoint);
				}
				
				// bilan radio
				// dig
				if (rs.getString(24) != null && !rs.getObject(24).equals("")) {
					rPoint.setBilanRadio(rs.getString(24));
				}
			} 
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw(re);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {rs.close();} catch (Exception e) { rs = null; } 
			}
			if (stt != null) {
				try {stt.close();} catch (Exception e) { stt = null; } 
			}
		}
	}

	private void throwDoublonVisiteException(Integer evtNb, Date dateV, VisiteMilestone vM) {
		throw new RuntimeException(
				"Les visites n° " 
				+ evtNb.toString() + " et " 
				+ vM.getVisitNb() + " à la même date "
				+ dateV.toString());
	}
	
	private void throwNoVisiteException(Integer evtNb) {
		throw new RuntimeException("La visites n° " + evtNb.toString() 
				+ " donne lieu à une évaluation tumorale " 
				+ " mais aucune n'est renseignée dans le formulaire VISITE");
	}
	
	public void populatePrelMilestones() {
		Connection connection = null;
		Statement stt = null;
		ResultSet set = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = null;
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/melbasetk","tumo","tumo");
			stt = connection.createStatement();
		
			set = stt.executeQuery("select a.nom, p.date_prelevement, t.type, d.nom, count(e.echantillon_id) "
				+ "from PRELEVEMENT p join MALADIE m on m.maladie_id = p.maladie_id join PATIENT a on a.patient_id = m.patient_id "
				+ "join ECHANTILLON e on e.prelevement_id = p.prelevement_id "
				+ "join ECHANTILLON_TYPE t on t.echantillon_type_id=e.echantillon_type_id left join MODE_PREPA d on d.mode_prepa_id = e.mode_prepa_id "
				+ "where a.nom = '" + inclusion + "' group by p.date_prelevement, t.type, d.nom order by p.date_prelevement");
		
			PrelDetails prelD;
			PrelMilestone prelMile = null;
			Date curr_date = null;
			Date prev_date = null;
			while (set.next()) {
				curr_date = set.getDate(2);
				// new milestone
				if (!curr_date.equals(prev_date)) {
					prelMile = new PrelMilestone();
					prelMile.setDateprel(curr_date);
					getPrelMs().add(prelMile);

				}
				prelD = new PrelDetails();
				
				// type echan
				if (set.getString(3) != null && !set.getString(3).equals("")) {
					prelD.setTypeEchan(set.getString(3));
				}
				// mode prepa echan
				if (set.getString(4) != null && !set.getString(4).equals("")) {
					prelD.setModePrepaEchan(set.getString(4));
				}
				// count echan
				if (set.getObject(5) != null && !set.getString(5).equals("")) {
					prelD.setCountEchan(set.getInt(5));
				}
				prelMile.getPrels().add(prelD);
				
				
				prev_date = curr_date;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		   if (connection != null){
			   try{ connection.close();} catch(Exception e){ connection = null;}
		   }
		   if (stt != null){
			   try{ stt.close();} catch(Exception e){ stt = null;}
		   }
		   if (set != null){
			   try{ set.close();} catch(Exception e){ set = null;}
		   }
		}
	}
	
	public String formatStringToJSParamater(String s) {
		return "\'" + s + "\'";
	}
		
	public String formatVisitsIntervalJSON(List<Interval> intervals, String ivType) {

		List<JSONObject> serieList = new ArrayList<JSONObject>();
		
		// prepare Map
		Map<String,List<Interval>> serieMap = new LinkedHashMap<String, List<Interval>>();
		for (Interval iv : intervals) {
			if (!serieMap.containsKey(iv.getCategorie())) {
				serieMap.put(iv.getCategorie(), new ArrayList<Interval>());
			}
			serieMap.get(iv.getCategorie()).add(iv);
		}
		
		categories.addAll(serieMap.keySet());
			
		Iterator<String> serieMapKeyIt = serieMap.keySet().iterator();
		String key;
		List<Interval> ivs;
		JSONObject serieItem;
		List<JSONObject> lso = new ArrayList<JSONObject>();
		
		while (serieMapKeyIt.hasNext()) {
			key = serieMapKeyIt.next();
			serieItem = new JSONObject();
			serieItem.put("name", key);
			if (ivType.equals("VISITE")) {
				if (key.equals("Essai thérapeutique")) {
					serieItem.put("color","yellow");
				} else if (key.equals("Surveillance sans traitement")) {
					serieItem.put("color","green");
				} else if (key.equals("Soins palliatifs")) {
					serieItem.put("color","pink");
				} else if (key.equals("Bisphosphonates")) {
					serieItem.put("color","purple");
				} else {
					serieItem.put("color","blue");
				} 
			} else if (ivType.equals("TOXICITE")) {
				serieItem.put("color","red");
			} else {
				serieItem.put("color","grey");
			}
		    
			ivs = serieMap.get(key);
			lso = new ArrayList<JSONObject>();
			for (Interval iv : ivs) {
				lso.add(iv.toJSONObject(categories));
			}
			serieItem.put("intervals", lso);	
			
			serieList.add(serieItem);
		}
		
		serieItem = new JSONObject();
		if (ivType.equals("VISITE")) {
			serieItem.put("name", "Visites");
			categories.add("Réponses");
		} else if (ivType.equals("TOXICITE")) {
			serieItem.put("name", "Toxicites");
		} else if (ivType.equals("HOSPITALISATION")) {
			serieItem.put("name", "Hospitalisations");
		}
		serieItem.put("intervals", new ArrayList<JSONObject>());
		//serieList.add(serieItem);
			    
		return serieList.toString();
	}
	
	public String formatVisitsMilestonesJSON(List<VisiteMilestone> milestones) {
		List<JSONObject> msList = new ArrayList<JSONObject>();
		for (VisiteMilestone ms : milestones) {
			ms.setTask(1);
			msList.add(ms.toJSONObject());
		}
		return msList.toString();
	}
	
	public String formatReponsesPointsJSON(List<ReponsePoint> points) {
		List<JSONObject> msList = new ArrayList<JSONObject>();
		for (ReponsePoint rp : points) {
			rp.setTask(new ArrayList<String>(categories).indexOf("Réponses"));
			msList.add(rp.toJSONObject());
		}
		return msList.toString();
	}
	
	public String formatPrelMilestonesJSON(List<PrelMilestone> prels) {
		List<JSONObject> msList = new ArrayList<JSONObject>();
		for (PrelMilestone pM : prels) {
			pM.setTask(0);
			msList.add(pM.toJSONObject());
		}
		return msList.toString();
	}
	
	public String formatCategories(Set<String> categories) {
		Iterator<String> catIt = categories.iterator();
		String out = "[";
		while (catIt.hasNext()) {
			out = out + "'" + catIt.next() + "'";
			if (catIt.hasNext()) {
				out = out + ",";
			} 
		}
		out = out + "]";
		return out;
	}
	
	private String[] extractDecision(String decision, String desc, String traitement) {
		if (decision != null && !decision.equals("")) {
			if (decision.equals("CHIMIOTHERAPIE") 
					|| decision.equals("IMMUNOTHERAPIE") 
					|| decision.equals("CIBLEE")) {
				if (traitement != null && !traitement.equals("")) {
					return extractTraitement(traitement);
				}
			} else {
				return new String[]{desc};
			}
		}
		return null;
	}
	
	private String[] extractTraitement(String tt) {
		String[] tts = new String[] {};
		if (tt != null) {
			tt = tt.replace("{", "");
			tt = tt.replace("}", "");
			tts = tt.split(",");
			for (int i = 0; i < tts.length; i++) {
				tts[i] = tts[i].trim();
			}
		}
		return tts;
	}

	public List<VisiteMilestone> getVisitMs() {
		return visitMs;
	}

	public void setVisitMs(List<VisiteMilestone> vMs) {
		this.visitMs = vMs;
	}

	public List<Interval> getIntervals() {
		return intervals;
	}

	public void setIntervals(List<Interval> ivs) {
		this.intervals = ivs;
	}
	
	public String getInclusion() {
		return inclusion;
	}
	
	public void setInclusion(String i) {
		this.inclusion = i;
	}

	public String getInitiales() {
		return initiales;
	}

	public List<ReponsePoint> getReponsePoints() {
		return reponsePoints;
	}

	public List<PrelMilestone> getPrelMs() {
		return prelMs;
	}


	public List<PrelMilestone> getExcludedPrelMs() {
		return excludedPrelMs;
	}


	public List<String> getAvailSubjectIds() {
		return availSubjectIds;
	}
}
