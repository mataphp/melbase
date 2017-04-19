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
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.North;
import org.zkoss.zul.Window;

import fr.aphp.sls.melbase.bundles.ResourceBundleMelbase;

public class Logout extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(Logout.class);

	
	public void onClick$logoutBtn() {
		ResourceBundleMelbase bundle = (ResourceBundleMelbase) 
		ContextLoader
			.getCurrentWebApplicationContext()
			.getBean("resourceBundle");
		Executions.getCurrent().sendRedirect("https://" + bundle.getHostName() + "/cas/logout");
	}
		
}
