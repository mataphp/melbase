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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Objet persistant mappant la table GRADE du CTCAE.
 * Classe créée le 09/08/12.
 * 
 * @author Mathieu BARTHELEMY
 * @version 1.0
 * 
 */
@Entity
@Table(name = "GRADE")
public class Grade implements java.io.Serializable {
	
	private static final long serialVersionUID = 78644354386453143L;
	
	private Integer gradeId;
	private Integer grade;
	private String descr;
	private Term term;
	
	public Grade() {
	}

	@Id
	@Column(name = "GRADE_ID", unique = true, nullable = false)
	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer s) {
		this.gradeId = s;
	}

	@Column(name = "Grade", nullable = false, length = 1)
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer s) {
		this.grade = s;
	}
	
	@Column(name = "DESCR", nullable = true)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String d) {
		this.descr = d;
	}

	@ManyToOne
	@JoinColumn(name = "TERM_ID", nullable = false)
	public Term getTerm() {
		return term;
	}

	public void setTerm(Term s) {
		this.term = s;
	}

	/**
	 * 2 Grade sont considérés comme égaux s'ils ont le même grade 
	 * et le même term.
	 * @param obj est le Grade à tester.
	 * @return true si les Grade sont égaux.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if ((obj == null) || obj.getClass() != this.getClass()) {
			return false;
		}
		Grade test = (Grade) obj;
		return ((this.grade == test.grade || (this.grade != null 
				&& this.grade.equals(test.grade)))
			&& (this.term == test.term || (this.term != null 
					&& this.term.equals(test.term))));	
	}

	/**
	 * Le hashcode est calculé sur les attributs grade et Term.
	 * @return la valeur du hashcode.
	 */
	@Override
	public int hashCode() {
		
		int hash = 7;
		int hashGrade = 0;
		int hashTerm = 0;
		
		if (this.grade != null) {
			hashGrade = this.grade.hashCode();
		}
		
		if (this.term != null) {
			hashTerm = this.term.hashCode();
		}
		
		hash = 31 * hash + hashGrade;
		hash = 31 * hash + hashTerm;
		
		return hash;
		
	}
}
