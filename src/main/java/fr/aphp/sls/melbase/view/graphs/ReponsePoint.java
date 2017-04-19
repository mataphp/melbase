package fr.aphp.sls.melbase.view.graphs;

import java.util.Date;

import org.zkoss.json.JSONObject;

public class ReponsePoint {
	
	private Integer visitEvtId;
	private String reponse;
	private String progType;
	private Date dateVisit;
	private Integer task;
	private ProgLocalisation localisations;
	private String bilanRadio;
	
	public ReponsePoint(Integer i) {
		visitEvtId = i;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String r) {
		this.reponse = r;
	}

	public String getProgType() {
		return progType;
	}

	public void setProgType(String r) {
		this.progType = r;
	}

	public ProgLocalisation getLocalisations() {
		return localisations;
	}

	public void setLocalisations(ProgLocalisation ls) {
		this.localisations = ls;
	}
	
	public Integer getVisitEvtId() {
		return visitEvtId;
	}

	public void setVisitEvtId(Integer v) {
		this.visitEvtId = v;
	}

	public Date getDateVisit() {
		return dateVisit;
	}

	public void setDateVisit(Date d) {
		this.dateVisit = d;
	}
	
	public Integer getTask() {
		return task;
	}

	public void setTask(Integer t) {
		this.task = t;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ReponsePoint) {
			ReponsePoint iv = (ReponsePoint) o;
			return getVisitEvtId() != null && getVisitEvtId().equals(iv.getVisitEvtId());			
		}
		return false;			
	}
	
	@Override
	public int hashCode() {
		return getVisitEvtId().hashCode();
	}
	
	/** Formatte les informations de progression et de localisation
	 * sous la forme du tooltip HTML
	 * @return tooltip texte
	 */
	public String getReponseDesc() {
		StringBuffer buf = new StringBuffer();
		if (progType != null) {
			buf.append("<i>" + progType + "</i></br>");
		} 
		if (localisations != null) {
			buf.append(localisations.getDesc());
		}
		return buf.toString();
	}
	
	public JSONObject toJSONObject() {
		JSONObject milestone = new JSONObject();
		milestone.put("name", getMilestoneLabel());
		milestone.put("label", getVisitEvtId());
		milestone.put("time", getDateVisit().getTime());
		milestone.put("task", getTask());
		milestone.put("desc", getReponseDesc());
		
		JSONObject marker = new JSONObject();
		marker.put("symbol", "circle");
		marker.put("fillColor", "white");
		if (getReponse() != null) {
			if (getReponse().matches(".*réponse complète.*")) {
				if (isBilanRadio()) {
					marker.put("symbol", "url(/Melbase/images/icones/reponse_tot.png)");
				} else {
					marker.put("symbol", "url(/Melbase/images/icones/reponse_noradio.png)");
				}
			} else if (getReponse().matches(".*réponse partielle.*")) {
				if (isBilanRadio()) {
					marker.put("symbol", "url(/Melbase/images/icones/reponse_part.png)");
				} else {
					marker.put("symbol", "url(/Melbase/images/icones/reponse_noradio.png)");
				}
			} else if (getReponse().matches(".*stabilisation.*")) {
				if (isBilanRadio()) {
					marker.put("symbol", "url(/Melbase/images/icones/stabilisation.png)");
				} else {
					marker.put("symbol", "url(/Melbase/images/icones/stabil_noradio.png)");
				}
			} else if (getReponse().matches(".*progression.*")) {
				if (isBilanRadio()) {
					marker.put("symbol", "url(/Melbase/images/icones/progression.png)");
				} else {
					marker.put("symbol", "url(/Melbase/images/icones/progression_noradio.png)");
				}	
			} 
		}
		marker.put("lineWidth", 1);
		marker.put("lineColor", "black");
		marker.put("radius", 6);
		
		milestone.put("marker", marker);

		return milestone;
	}

	
	private String getMilestoneLabel() {
		return getReponse();
	}

	public String getBilanRadio() {
		return bilanRadio;
	}

	public void setBilanRadio(String v) {
		this.bilanRadio = v;
	}
	
	private boolean isBilanRadio() {
		return getBilanRadio() != null && getBilanRadio().equals("oui");
	}
	
}
