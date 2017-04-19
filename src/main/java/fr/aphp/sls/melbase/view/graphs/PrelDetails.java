package fr.aphp.sls.melbase.view.graphs;

public class PrelDetails {

	private String typeEchan;
	private String modePrepaEchan;
	private Integer countEchan;
	
	public String getTypeEchan() {
		return typeEchan;
	}
	
	public void setTypeEchan(String t) {
		this.typeEchan = t;
	}
	
	public String getModePrepaEchan() {
		return modePrepaEchan;
	}
	
	public void setModePrepaEchan(String m) {
		this.modePrepaEchan = m;
	}
	
	public Integer getCountEchan() {
		return countEchan;
	}
	
	public void setCountEchan(Integer c) {
		this.countEchan = c;
	}
	
	public String getPrelDesc() {
		StringBuffer buf = new StringBuffer();
		buf.append("<li>" + typeEchan);
		if (modePrepaEchan != null) {
			buf.append(" " + modePrepaEchan);
		}
		buf.append(" : " + countEchan.toString() + "</li>");
		return buf.toString();
	}
}
