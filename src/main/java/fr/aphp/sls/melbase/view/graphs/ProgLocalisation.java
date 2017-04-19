package fr.aphp.sls.melbase.view.graphs;

public class ProgLocalisation {
	
	private String peau;
	private String ganglions;
	private String os;
	private String poumons;
	private String foie;
	private String rate;
	private String surrenale;
	private String dig;
	private String pancreas;
	private ProgLocalisation.ProgCerebrale cerebrale;
	
	public String getPeau() {
		return peau;
	}

	public void setPeau(String p) {
		this.peau = p;
	}

	public String getGanglions() {
		return ganglions;
	}

	public void setGanglions(String g) {
		this.ganglions = g;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String o) {
		this.os = o;
	}

	public String getPoumons() {
		return poumons;
	}

	public void setPoumons(String p) {
		this.poumons = p;
	}

	public String getFoie() {
		return foie;
	}

	public void setFoie(String f) {
		this.foie = f;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String r) {
		this.rate = r;
	}

	public String getSurrenale() {
		return surrenale;
	}

	public void setSurrenale(String s) {
		this.surrenale = s;
	}

	public String getDig() {
		return dig;
	}

	public void setDig(String d) {
		this.dig = d;
	}

	public String getPancreas() {
		return pancreas;
	}

	public void setPancreas(String p) {
		this.pancreas = p;
	}
	
	public ProgLocalisation.ProgCerebrale getCerebrale() {
		return cerebrale;
	}

	public void setCerebrale(ProgLocalisation.ProgCerebrale c) {
		this.cerebrale = c;
	}


	public static class ProgCerebrale {
		
		private Boolean prog;
		private Integer nb;
		private Integer taille;
		private String lepto;
		private String sympto;
		private String epidurite;
		private String meta_op;
		private String topo_cran;
		
		public ProgCerebrale(boolean p) {
			prog = p;
		}
		
		public Integer getNb() {
			return nb;
		}
		
		public void setNb(Integer n) {
			this.nb = n;
		}
		
		public Integer getTaille() {
			return taille;
		}
		
		public void setTaille(Integer t) {
			this.taille = t;
		}
		
		public String getLepto() {
			return lepto;
		}
		
		public void setLepto(String l) {
			this.lepto = l;
		}
		
		public String getSympto() {
			return sympto;
		}
		
		public void setSympto(String s) {
			this.sympto = s;
		}
		
		public String getEpidurite() {
			return epidurite;
		}
		
		public void setEpidurite(String e) {
			this.epidurite = e;
		}
		
		public String getMeta_op() {
			return meta_op;
		}
		
		public void setMeta_op(String m) {
			this.meta_op = m;
		}
		
		public String getTopo_cran() {
			return topo_cran;
		}
		
		public void setTopo_cran(String t) {
			this.topo_cran = t;
		}

		public Boolean getProg() {
			return prog;
		}

		public void setProg(Boolean prog) {
			this.prog = prog;
		}

		public String getDesc() {
			StringBuffer buf = new StringBuffer();
			if (getProg() != null) {
				buf.append("<li>Cerebrale: " 
				+ getValueStyled((getProg() ? "oui" : "non")) 
				+ "</li>");
			}
			buf.append("<ul title=\"Details\">");
			if (getNb() != null) {
				buf.append("<li>Nombres: " + getNb().toString() + "</li>");
			}
			if (getTaille() != null) {
				buf.append("<li>Taille: " + getTaille().toString() + "</li>");
			}
			if (getSympto() != null) {
				buf.append("<li>Symptomatique: " + getValueStyled(getSympto()) + "</li>");
			}
			if (getLepto() != null) {
				buf.append("<li>Leptoméningée: " + getValueStyled(getLepto()) + "</li>");
			}
			if (getEpidurite() != null) {
				buf.append("<li>Epidurite: " + getValueStyled(getEpidurite()) + "</li>");
			}
			if (getMeta_op() != null) {
				buf.append("<li>Metastase Opérable: " + getValueStyled(getMeta_op()) + "</li>");
			}
			if (getTopo_cran() != null) {
				buf.append("<li>Topographie: " + getTopo_cran() + "</li>");
			}
			
			buf.append("</ul>");
			return buf.toString();
		}
		
	}
	
	private static String getValueStyled(String value) {
		if (value != null && value.equals("oui")) {
			return "<span style=\"color: red; font-weight: bold\">oui</span>";
		}
		return value;
	}


	public String getDesc() {
		StringBuffer buf = new StringBuffer();
		buf.append("<ul title=\"Localisations\">");
		if (getPeau() != null) {
			buf.append("<li>Peau: " + getValueStyled(getPeau()) + "</li>");
		}
		if (getGanglions() != null) {
			buf.append("<li>Ganglions: " + getValueStyled(getGanglions()) + "</li>");
		}
		if (getOs() != null) {
			buf.append("<li>Os: " + getValueStyled(getOs()) + "</li>");
		}
		if (getPoumons() != null) {
			buf.append("<li>Poumons: " + getValueStyled(getPoumons()) + "</li>");
		}
		if (getFoie() != null) {
			buf.append("<li>Foie: " + getValueStyled(getFoie()) + "</li>");
		}
		if (getRate() != null) {
			buf.append("<li>Rate: " + getValueStyled(getRate()) + "</li>");
		}
		if (getSurrenale() != null) {
			buf.append("<li>Surrenale: " + getValueStyled(getSurrenale()) + "</li>");
		}
		if (getDig() != null) {
			buf.append("<li>Digestive: " + getValueStyled(getDig()) + "</li>");
		}
		if (getPancreas() != null) {
			buf.append("<li>Pancreas: " + getValueStyled(getPancreas()) + "</li>");
		}
		if (getCerebrale() != null) {
			buf.append(getCerebrale().getDesc());
		}
		buf.append("</ul>");
		return buf.toString();
	}
}
