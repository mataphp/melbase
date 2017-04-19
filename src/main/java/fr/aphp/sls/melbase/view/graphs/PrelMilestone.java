package fr.aphp.sls.melbase.view.graphs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.json.JSONObject;

public class PrelMilestone {

	private Date dateprel;
	private List<PrelDetails> prels = new ArrayList<PrelDetails>();
	private Integer task;

	public Date getDateprel() {
		return dateprel;
	}
	
	public void setDateprel(Date d) {
		this.dateprel = d;
	}
	
	public List<PrelDetails> getPrels() {
		return prels;
	}
	
	public void setPrels(List<PrelDetails> prels) {
		this.prels = prels;
	}
	
	public Integer getTask() {
		return task;
	}

	public void setTask(Integer t) {
		this.task = t;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PrelMilestone) {
			PrelMilestone iv = (PrelMilestone) o;
			return getDateprel() != null && getDateprel().equals(iv.getDateprel());			
		}
		return false;			
	}
	
	@Override
	public int hashCode() {
		return getDateprel().hashCode();
	}
	
	/** Formatte les informations des details prelevements sous la forme
	 * tooltip HTML
	 * @return tooltip texte
	 */
	public String getDesc() {
		StringBuffer buf = new StringBuffer();
		buf.append("<ul>");
		for (PrelDetails p : prels) {
			buf.append(p.getPrelDesc());
		}
		buf.append("</ul>");
		return buf.toString();
	}
	
	public JSONObject toJSONObject() {
		JSONObject milestone = new JSONObject();
		milestone.put("name", getMilestoneLabel());
		milestone.put("label", getMilestoneLabel());
		milestone.put("time", getDateprel().getTime());
		milestone.put("task", getTask());
		milestone.put("desc", getDesc());
		
		JSONObject marker = new JSONObject();
		marker.put("symbol", "circle");
		marker.put("fillColor", "white");
		if (!getPrels().isEmpty()) {
			marker.put("symbol", "url(/Melbase/images/icones/prels.png)");
		}
		marker.put("lineWidth", 1);
		marker.put("lineColor", "black");
		marker.put("radius", 6);
		
		milestone.put("marker", marker);

		return milestone;
	}

	
	private String getMilestoneLabel() {
		return new SimpleDateFormat("dd/MM/yyyy").format(dateprel);
	}
	
}
