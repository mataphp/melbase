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
 * risques associés au chargement,  à l'utilisation,  à la modification 
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Objet persistant mappant la table SOC du CTCAE.
 * Classe créée le 09/08/12.
 * 
 * @author Mathieu BARTHELEMY
 * @version 1.0
 * 
 */
@Entity
@Table(name = "SOC")
@NamedQueries(value = {
		@NamedQuery(name = "Soc.findBySocLike", 
			query = "SELECT s FROM Soc s WHERE s.soc like ?1")
})
public class Soc implements java.io.Serializable {
	
	private static final long serialVersionUID = 78644354386453143L;
	
	private Integer socId;
	private String soc;
	
	public Soc() {
	}

	@Id
	@Column(name = "SOC_ID", unique = true, nullable = false)
	public Integer getSocId() {
		return socId;
	}

	public void setSocId(Integer s) {
		this.socId = s;
	}

	@Column(name = "SOC", nullable = false, length = 100)
	public String getSoc() {
		return soc;
	}

	public void setSoc(String s) {
		this.soc = s;
	}
	
	/**
	 * 2 soc sont considérés comme égaux s'ils ont le même soc.
	 * @param obj est le soc à tester.
	 * @return true si les soc sont égaux.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if ((obj == null) || obj.getClass() != this.getClass()) {
			return false;
		}
		Soc test = (Soc) obj;
		return ((this.soc == test.soc || (this.soc != null 
				&& this.soc.equals(test.soc))));	
	}

	/**
	 * Le hashcode est calculé sur l'attribut soc.
	 * @return la valeur du hashcode.
	 */
	@Override
	public int hashCode() {
		
		int hash = 7;
		int hashSoc = 0;
		
		if (this.soc != null) {
			hashSoc = this.soc.hashCode();
		}
		
		hash = 31 * hash + hashSoc;
		
		return hash;
		
	}

}
