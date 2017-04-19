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

import org.zkoss.zul.Detail;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import fr.aphp.sls.melbase.model.ctcae.Term;
import fr.aphp.sls.melbase.model.ctcae.TermGrades;

public class TermGradesRenderer implements RowRenderer<TermGrades> {
    
	private int id = 0;
	
	@Override
	public void render(Row row, TermGrades tG, int arg2) throws Exception {
		// Detail detail = new Detail();
		// row.appendChild(detail);
        row.appendChild(drawTermDef(tG.getTerm(), row));
        Label grade1Label = new Label(tG.getGrade1().getDescr());
        grade1Label.setSclass("link");
        grade1Label.addForward(null, grade1Label.getParent(), 
								"onClickGrade", tG.getGrade1());
        row.appendChild(grade1Label);
        Label grade2Label = new Label(tG.getGrade2().getDescr());
        grade2Label.setSclass("link");
        grade2Label.addForward(null, grade2Label.getParent(), 
				"onClickGrade", tG.getGrade2());
        row.appendChild(grade2Label);
        Label grade3Label = new Label(tG.getGrade3().getDescr());
        grade3Label.setSclass("link");
        grade3Label.addForward(null, grade3Label.getParent(), 
				"onClickGrade", tG.getGrade3());
        row.appendChild(grade3Label);
        Label grade4Label = new Label(tG.getGrade4().getDescr());
        grade4Label.setSclass("link");
        grade4Label.addForward(null, grade4Label.getParent(), 
				"onClickGrade", tG.getGrade4());
        row.appendChild(grade4Label);
        Label grade5Label = new Label(tG.getGrade5().getDescr());
        grade5Label.setSclass("link");
        grade5Label.addForward(null, grade5Label.getParent(), 
				"onClickGrade", tG.getGrade5());
        row.appendChild(grade5Label);
        id += 1;
        
	}
	
	private Hlayout drawTermDef(Term term, Row row) {

		Hlayout labelAndLink = new Hlayout();
		
		if (term != null) {

			Label termLabel = new Label(term.getTerm());
			termLabel.setSclass("heavy");
			
			labelAndLink.setSpacing("5px");
			labelAndLink.appendChild(termLabel);
	        
			if (term.getDefinition() != null) {
				Div glass = new Div();
				glass.setWidth("22px");
				glass.setHeight("22px");
				glass.setSclass("glass");	
	
				Popup defPopUp = new Popup();
				defPopUp.setParent(row.getParent().getParent().getParent());
				
				Label defLabel = new Label(term.getDefinition());
				defLabel.setSclass("popup");
				defPopUp.appendChild(defLabel);
				
				glass.setTooltip(defPopUp);
	
				labelAndLink.appendChild(glass);
			}
		}
		return labelAndLink;
	}

	//	@Override
	//    public void render(Row row, LanguageContribution data, int index) {
	//        
	//        // we create a thumb up/down comment to each row
	//        final Div d = new Div();
	//        final Button thumbBtn = new Button(null, "/images/thumb-up.png");
	//        thumbBtn.setParent(d);
	//        thumbBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
	//            public void onEvent(Event event) throws Exception {
	//                d.appendChild(new Label("Thumbs up"));
	//                thumbBtn.setDisabled(true);
	//            }
	//        });
	//        row.appendChild(d); // Any component could created as a child of grid
	//    }


}
