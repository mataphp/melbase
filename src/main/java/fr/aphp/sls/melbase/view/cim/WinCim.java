/** 
 * Copyright ou © ou Copr. Ministère de la santé, FRANCE (01/09/2012)
 * mathieu.barthelemy@sls.aphp.fr
 * celeste.lebbe@sls.aphp.fr
 * anita.kowal
 * 
 * Ce logiciel est un programme informatique servant à la collecte 
 * de données clinico-biologiques dans le suivi de cancer. 
 *
 * Ce logiciel est régi par la licence CeCILL soumise au droit français
 * et respectant les principes de diffusion des logiciels libres. Vous 
 * pouvez utiliser, modifier et/ou redistribuer ce programme sous les 
 * conditions de la licence CeCILL telle que diffusée par le CEA, le 
 * CNRS et l'INRIA sur le site "http://www.cecill.info". 
 * En contrepartie de l'accessibilité au code source et des droits de   
 * copie, de modification et de redistribution accordés par cette 
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. 
 * Pour les mêmes raisons, seule une responsabilité restreinte pèse sur 
 * l'auteur du programme, le titulaire des droits patrimoniaux et les 
 * concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les 
 * risques asTermiés au chargement,  à l'utilisation,  à la modification 
 * et/ou au  développement et à la reproduction du logiciel par 
 * l'utilisateur étant donné sa spécificité de logiciel libre, qui peut 
 * le rendre complexe à manipuler et qui le réserve donc à des 	
 * développeurs et des professionnels  avertis possédant  des 
 * connaissances  informatiques approfondies.  Les utilisateurs sont 
 * donc invités à charger  et  tester  l'adéquation  du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs
 * systèmes et ou de leurs données et, plus généralement, à l'utiliser 
 * et l'exploiter dans les mêmes conditions de sécurité. 
 *	
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous 
 * avez pris connaissance de la licence CeCILL, et que vous en avez 
 * accepté les termes. 
 **/
package fr.aphp.sls.melbase.view.cim;

import java.util.Set;

import org.springframework.web.context.ContextLoader;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.TreeModel;
import fr.aphp.sls.melbase.dao.cim.CimDao;
import fr.aphp.sls.melbase.model.cim.Cim;
import fr.aphp.sls.melbase.model.cim.CimNode;
import fr.aphp.sls.melbase.model.cim.CimTreeModel;
import fr.aphp.sls.melbase.model.ctcae.Grade;

public class WinCim {
 
    private static final long serialVersionUID = 43014628867656917L;
    
    private Cim selectedCim;
    
    private CimTreeModel<Cim> _cimTreeModel;
     
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public TreeModel<CimNode<Cim>> getCimTreeModel () {
        CimNode root = new CimNode(null,
                new CimNode[] {new CimNode(getRoot())
        });
        if (_cimTreeModel == null) {
            _cimTreeModel = new CimTreeModel<Cim>(root);
        }
        return _cimTreeModel;
    }
	
    private Cim getRoot() {
    	
    	Cim root = ((CimDao) (ContextLoader
    			.getCurrentWebApplicationContext()).getBean("cimDao"))
    			.findById(932);
    	
    	return root;
    }
	
	/**
	 * Forwarded Event.
	 * Sélectionne le grade concerne
	 * @param event forwardé depuis le label grade cliquable
	 * (event.getData contient l'objet).
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void postToOC(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		Set s = event.getSelectedObjects();
		if (s != null && s.size() > 0) {
            selectedCim = ((CimNode<Cim>)s.iterator().next()).getData();
            System.out.println("selected: " + selectedCim.getCode());
        }
		Clients.evalJavaScript("postItToOc('" 
				+ selectedCim.getCode() 
				+ "')");
		
	}
	
//	public void onClick$goForIt() {
//		if (termBox.getValue() != null) {
//			populateTermGrades(((TermDao) (ContextLoader
//				.getCurrentWebApplicationContext()).getBean("termDao"))
//					.findByTermLike("%" + termBox.getValue() + "%"));
//		}
//	}
//	
//	public void onPressEnterKey() {
//		onClick$goForIt();
//	}
	
}

