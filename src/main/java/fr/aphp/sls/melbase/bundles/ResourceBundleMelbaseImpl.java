package fr.aphp.sls.melbase.bundles;

import org.springframework.beans.factory.annotation.Value;

public class ResourceBundleMelbaseImpl implements ResourceBundleMelbase {
		
	private String tkAppName;
	private String hostName;
	private String inclusionAppName;
	private String clinicaAppName;
	
	@Value("${TK_APPNAME}") 
	public void setTkAppName(String t) {
		this.tkAppName = t;
	}
	
	@Value("${HOSTNAME}") 
	public void setHostName(String h) {
		this.hostName = h;
	}

	@Value("${INCLUSION_APPNAME}") 
	public void setInclusionAppName(String i) {
		this.inclusionAppName = i;
	}

	@Value("${CLINICA_APPNAME}") 
	public void setClinicaAppName(String c) {
		this.clinicaAppName = c;
	}

	@Override
	public String getHostName() {
		return hostName;
	}
	
	@Override
	public String getTkAppName() {
		return tkAppName;
	}
	
	@Override
	public String getInclusionAppName() {
		return inclusionAppName;
	}
	
	@Override
	public String getClinicaAppName() {
		return clinicaAppName;
	}
}
