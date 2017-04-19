package fr.aphp.sls.melbase.view.graphs;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import org.zkoss.json.JSONObject;

public class Interval {
	
	private String categorie;
	private Date from;
	private Date to;
	private String label;
	private Boolean tox = false;
	private String desc;
	
	public Interval(String deci) {
		categorie = deci;
	}
	
	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String d) {
		this.categorie = d;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date f) {
		this.from = f;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date t) {
		this.to = t;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Interval) {
			Interval iv = (Interval) o;
			return getCategorie() != null && getCategorie().equals(iv.getCategorie());			
		}
		return false;			
	}
	
	@Override
	public int hashCode() {
		return getCategorie().hashCode();
	}
	
	public boolean isComplete() {
		return getFrom() != null && getTo() != null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String l) {
		this.label = l;
	}
	
	public Boolean getTox() {
		return tox;
	}

	public void setTox(Boolean t) {
		this.tox = t;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public JSONObject toJSONObject(LinkedHashSet<String> categories) {
		JSONObject fromTo = new JSONObject();
		fromTo.put("categorie", new ArrayList<String>(categories).indexOf(getCategorie()));
		fromTo.put("label", getLabel());
		fromTo.put("desc", getDesc());
		if (getFrom() != null) {
			fromTo.put("from", getFrom().getTime());
		}
		if (getTo() != null) {
			fromTo.put("to", getTo().getTime());
		}
		return fromTo;
	}
	
}
