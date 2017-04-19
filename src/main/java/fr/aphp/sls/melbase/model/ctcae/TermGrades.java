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
 * risques asGradeiés au chargement,  à l'utilisation,  à la modification 
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
package fr.aphp.sls.melbase.model.ctcae;

/**
 * Objet de transfert contenant un objet Term et donc 
 * tous ses grades associés (car FetchType EAGER).
 * Objet utilisé par la vue pour dessiner le tableau.
 * 
 * Classe créée le 09/08/12.
 * 
 * @author Mathieu BARTHELEMY
 * @version 1.0
 * 
 */
public class TermGrades implements java.io.Serializable {
	
	private static final long serialVersionUID = 78644354386453143L;

	private Term term;

	public TermGrades() {
	}
	
	public TermGrades(Term t) {
		setTerm(t);
	}
	
	public Term getTerm() {
		return term;
	}


	public void setTerm(Term t) {
		this.term = t;
	}

	public Grade getGrade1() {
		return term.getGrades().get(0);
	}
	
	public Grade getGrade2() {
		return term.getGrades().get(1);
	}
	
	public Grade getGrade3() {
		return term.getGrades().get(2);
	}
	
	public Grade getGrade4() {
		return term.getGrades().get(3);
	}
	
	public Grade getGrade5() {
		return term.getGrades().get(4);
	}

	/**
	 * 2 Grade sont considérés comme égaux s'ils ont le même term.
	 * @param obj est le TermGrades à tester.
	 * @return true si les TermGrades sont égaux.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if ((obj == null) || obj.getClass() != this.getClass()) {
			return false;
		}
		TermGrades test = (TermGrades) obj;
		return ((this.term == test.term || (this.term != null 
					&& this.term.equals(test.term))));	
	}

	/**
	 * Le hashcode est calculé sur l'attribut Term.
	 * @return la valeur du hashcode.
	 */
	@Override
	public int hashCode() {
		
		int hash = 7;
		int hashTerm = 0;
		
		if (this.term != null) {
			hashTerm = this.term.hashCode();
		}
		
		hash = 31 * hash + hashTerm;
		
		return hash;
		
	}
}
