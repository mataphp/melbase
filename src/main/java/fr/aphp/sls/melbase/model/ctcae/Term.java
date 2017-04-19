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
package fr.aphp.sls.melbase.model.ctcae;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Objet persistant mappant la table TERM du CTCAE.
 * Classe créée le 09/08/12.
 * 
 * @author Mathieu BARTHELEMY
 * @version 1.0
 * 
 */
@Entity
@Table(name = "TERM")
@NamedQueries(value = {
		@NamedQuery(name = "Term.findBySoc", 
			query = "SELECT t FROM Term t WHERE t.soc = ?1 order by t.term"),
		@NamedQuery(name = "Term.findByTermLike", 
				query = "SELECT t FROM Term t WHERE t.term like ?1 order by t.term")
})
public class Term implements java.io.Serializable {
	
	private static final long serialVersionUID = 78644354386453143L;
	
	private Integer termId;
	private String term;
	private String definition;
	private Soc soc;
	private List<Grade> grades = new ArrayList<Grade>();
	
	public Term() {
	}

	@Id
	@Column(name = "TERM_ID", unique = true, nullable = false)
	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer s) {
		this.termId = s;
	}

	@Column(name = "TERM", nullable = false, length = 100)
	public String getTerm() {
		return term;
	}

	public void setTerm(String s) {
		this.term = s;
	}
	
	@Column(name = "DEFINITION", nullable = true)
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String d) {
		this.definition = d;
	}

	@ManyToOne
	@JoinColumn(name = "SOC_ID", nullable = false)
	public Soc getSoc() {
		return soc;
	}

	public void setSoc(Soc s) {
		this.soc = s;
	}

	@OneToMany(mappedBy = "term", fetch = FetchType.EAGER)
	@OrderBy("grade")
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> gs) {
		this.grades = gs;
	}

	/**
	 * 2 Term sont considérés comme égaux s'ils ont le même Term.
	 * @param obj est le Term à tester.
	 * @return true si les Term sont égaux.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if ((obj == null) || obj.getClass() != this.getClass()) {
			return false;
		}
		Term test = (Term) obj;
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