package fr.aphp.sls.melbase.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.North;
import org.zkoss.zul.Window;

import fr.aphp.sls.melbase.bundles.ResourceBundleMelbase;

public class MainPortal extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(MainPortal.class);

	
	private Iframe iframe;
	private Iframe partenaires;
	private Iframe userInfoFrame;
	private String secure = "secure";
	private Hbox logoutDiv;
	private String host;
	private String inclusionAppName;
	private String clinicaAppName;
	private String tkAppName;
	private Groupbox appsGroup;
	private Caption caption;
	private North north;
	
	private Hbox apps;
	
	public String getSecure() {
		return secure;
	}

	public void setSecure(String secure) {
		this.secure = secure;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);	
		ResourceBundleMelbase bundle = (ResourceBundleMelbase) 
			ContextLoader
				.getCurrentWebApplicationContext()
				.getBean("resourceBundle");
		if (bundle != null) {
			host = bundle.getHostName();
			inclusionAppName = bundle.getInclusionAppName();
			clinicaAppName = bundle.getClinicaAppName();
			tkAppName = bundle.getTkAppName();
			iframe.setSrc("https://" + host + "/cas/login" );
		} else {
			throw new RuntimeException("Host configuration Error");
		}
		setConnected(false);
	}
	
	public void onSuccess(Event e) {
				
		if (!host.contains((String) e.getData())) {
			log.warn("cross-site scripting tentative from "
					+ e + " ?");
    	} else {
    		setConnected(true);
    	}
	}
	
	public void onClick$inclusionLink() {
		Executions.getCurrent().sendRedirect("https://" + host + "/" + inclusionAppName, "_blank");
	}
	
	public void onClick$clinicaLink() {
		Executions.getCurrent().sendRedirect("https://" + host + "/" + clinicaAppName, "_blank");
	}
	
	public void onClick$tumorotekLink() {
		Executions.getCurrent().sendRedirect("https://" + host + "/" + tkAppName, "_blank");
	}
	
	public void onClick$logoutDiv() {
		iframe.setSrc("https://" + host + "/cas/logout");
		setConnected(false);
	}
	
	public void setConnected(boolean connect) {
		logoutDiv.setVisible(connect);
		apps.setVisible(connect);
		appsGroup.setOpen(connect);
		appsGroup.setClosable(connect);
		north.setVisible(connect);
		
		if (connect) {
			userInfoFrame.setSrc("https://" + host + "/inclusion/admin/UserInformation");
			partenaires.setHeight("100px");
		} else {
			userInfoFrame.setSrc("");
			partenaires.setHeight("400px");
		}
		
	}
	
	public ResourceBundle getResourceBundle(String baseName) {
		InputStreamReader reader = null;
		FileInputStream fis = null;
		ResourceBundle bundle = null;

		if (baseName != null) {
			File file = new File(baseName);
			if (file.isFile()) { // Also checks for existance
				try {
					fis = new FileInputStream(file);
					reader = new InputStreamReader(fis, 
							Charset.forName("UTF-8"));
					bundle = new PropertyResourceBundle(reader);
				} catch (FileNotFoundException e) {
					log.error(e);
				} catch (IOException e) {
					log.error(e);
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						reader = null;
					}
					try {
						fis.close();
					} catch (IOException e) {
						fis = null;
					}
				}
			}
		}
		return bundle;
	}
	
	public void onClick$caption() {
		Executions.getCurrent().sendRedirect("https://" + host + "/cas/login", "_blank");
	}
		
	
    public void showModal() {
        //create a window programmatically and use it as a modal dialog.
        Window window = new Window();
        // ((Iframe) iframe.clone()).setParent(window);
        window.doModal();
    }
}
