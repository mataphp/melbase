package fr.aphp.sls.melbase.view.graphs;

import java.util.Date;

import org.zkoss.json.JSONObject;

public class VisiteMilestone {
	
	private String visitEvtId;
	private String visitNb;
	private Date dateVisit;
	private Integer task;
	private String type;

	public VisiteMilestone(String i) {
		visitEvtId = i;
	}
	
	public String getVisitEvtId() {
		return visitEvtId;
	}

	public void setVisitEvtId(String v) {
		this.visitEvtId = v;
	}

	public Date getDateVisit() {
		return dateVisit;
	}

	public void setDateVisit(Date d) {
		this.dateVisit = d;
	}
	
	public String getVisitNb() {
		return visitNb;
	}

	public void setVisitNb(String v) {
		this.visitNb = v;
	}
	
	public Integer getTask() {
		return task;
	}

	public void setTask(Integer t) {
		this.task = t;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof VisiteMilestone) {
			VisiteMilestone iv = (VisiteMilestone) o;
			return getVisitEvtId() != null && getVisitEvtId().equals(iv.getVisitEvtId());			
		}
		return false;			
	}
	
	@Override
	public int hashCode() {
		return getVisitEvtId().hashCode();
	}
	
	public JSONObject toJSONObject() {
		JSONObject milestone = new JSONObject();
		milestone.put("name", getMilestoneLabel());
		milestone.put("label", getVisitEvtId());
		milestone.put("time", getDateVisit().getTime());
		milestone.put("task", getTask());
		
		JSONObject marker = new JSONObject();
		marker.put("symbol", "circle");
		marker.put("fillColor", "white");
		if (getType() != null) {
			if (getType().equals("INCLUSION")) {
				marker.put("symbol", "triangle");
			} else if (getType().equals("DECES")) {
				marker.put("symbol", "url(/Melbase/images/icones/death_cross_even.gif)");
			}
		}
		marker.put("lineWidth", 1);
		marker.put("lineColor", "black");
		marker.put("radius", 6);
		
		milestone.put("marker", marker);

		return milestone;
	}

	public String getType() {
		return type;
	}

	public void setType(String t) {
		this.type = t;
	}
	
	private String getMilestoneLabel() {
		if (getType().equals("INCLUSION")) {
			return "Inclusion";
		} else if (getType().equals("VISITE")) {
			return "Visite " + getVisitNb();
		} else { // Deces
			return "Cause: " + getVisitNb();
		}
	}
	
}
