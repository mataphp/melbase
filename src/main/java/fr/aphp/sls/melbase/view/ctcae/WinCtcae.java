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
package fr.aphp.sls.melbase.view.ctcae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

import fr.aphp.sls.melbase.dao.ctcae.SocDao;
import fr.aphp.sls.melbase.dao.ctcae.TermDao;
import fr.aphp.sls.melbase.model.ctcae.Grade;
import fr.aphp.sls.melbase.model.ctcae.Soc;
import fr.aphp.sls.melbase.model.ctcae.Term;
import fr.aphp.sls.melbase.model.ctcae.TermGrades;

public class WinCtcae extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	private List<Soc> socs = new ArrayList<Soc>();
	private Soc selectedSoc;
	private List<TermGrades> termGrades = new ArrayList<TermGrades>();
	private TermGradesRenderer renderer = new TermGradesRenderer();
	
	private Textbox termBox;
	
	public List<Soc> getSocs() {
		return socs;
	}

	public void setSocs(List<Soc> s) {
		this.socs = s;
	}

	
	public Soc getSelectedSoc() {
		return selectedSoc;
	}

	public void setSelectedSoc(Soc s) {
		this.selectedSoc = s;
		populateTermGrades(((TermDao) (ContextLoader
			.getCurrentWebApplicationContext()).getBean("termDao"))
				.findBySoc(getSelectedSoc()));
	}

	public List<TermGrades> getTermGrades() {
		return termGrades;
	}

	public void setTermGrades(List<TermGrades> t) {
		this.termGrades = t;
	}

	public TermGradesRenderer getRenderer() {
		return renderer;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		socs.addAll(((SocDao) (ContextLoader
				.getCurrentWebApplicationContext()).getBean("socDao"))
					.findAll());
	
	}
	
	/**
	 * Peuple la liste de terms et leurs grades associés à partir 
	 * d'une liste de terms.
	 */
	private void populateTermGrades(List<Term> terms) {
		termGrades.clear();
		Iterator<Term> termsIt = terms.iterator();
		Term t;
		TermGrades tG;
		while (termsIt.hasNext()) {		
			t = termsIt.next();
			tG = new TermGrades(t);
			termGrades.add(tG);
		}
	}
	
	/**
	 * Forwarded Event.
	 * Sélectionne le grade concerné
	 * @param event forwardé depuis le label grade cliquable
	 * (event.getData contient l'objet).
	 */
	public void onClickGrade(Event event) {
		Grade grade = (Grade) event.getData();
		Clients.evalJavaScript("postItToOc('" 
				+ grade.getTerm().getTerm() 
				+ " [grade " + grade.getGrade() + "]"
				+ "')");
		
	}
	
	public void onClick$goForIt() {
		if (termBox.getValue() != null) {
			populateTermGrades(((TermDao) (ContextLoader
				.getCurrentWebApplicationContext()).getBean("termDao"))
					.findByTermLike("%" + termBox.getValue() + "%"));
		}
	}
	
	public void onPressEnterKey() {
		onClick$goForIt();
	}
	
}

