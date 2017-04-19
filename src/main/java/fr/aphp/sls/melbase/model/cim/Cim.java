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
package fr.aphp.sls.melbase.model.cim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * Objet persistant mappant la table CIM.
 * Classe créée le 21/10/2013.
 * 
 * @author Mathieu BARTHELEMY
 * @version 1.0
 * 
 */
@Entity
@Table(name = "CIM")
@NamedQueries(value = {@NamedQuery(name = "Cim.findByCodeLike", 
			query = "SELECT c FROM Cim c WHERE c.code like ?1"),
		@NamedQuery(name = "Cim.findByLibelleLike", 
			query = "SELECT c FROM Cim c WHERE c.libelle like ?1"),
		@NamedQuery(name = "Cim.findById1", 
			query = "SELECT c FROM Cim c WHERE c.id1 = ?1 AND c.level = ?2"),
		@NamedQuery(name = "Cim.findById2", 
			query = "SELECT c FROM Cim c WHERE c.id2 = ?1 AND c.level = ?2"),
		@NamedQuery(name = "Cim.findById3", 
			query = "SELECT c FROM Cim c WHERE c.id3 = ?1 AND c.level = ?2"),
		@NamedQuery(name = "Cim.findById4", 
			query = "SELECT c FROM Cim c WHERE c.id4 = ?1 AND c.level = ?2"),
		@NamedQuery(name = "Cim.findById5", 
			query = "SELECT c FROM Cim c WHERE c.id5 = ?1 AND c.level = ?2"),
		@NamedQuery(name = "Cim.findById6", 
			query = "SELECT c FROM Cim c WHERE c.id4 = ?1 AND c.level = ?2"),
		@NamedQuery(name = "Cim.findById7", 
			query = "SELECT c FROM Cim c WHERE c.id7 = ?1 AND c.level = ?2"),
	})
public class Cim implements Serializable {

	private static final long serialVersionUID = 2109336606347184982L;
	
	private Integer sid;
	private String code;
	private Integer level;
	private Integer id1;
	private Integer id2;
	private Integer id3;
	private Integer id4;
	private Integer id5;
	private Integer id6;
	private Integer id7;
	private Boolean cimo3;
	
	private String libelle;

	public Cim() {
	}
	
	public String toString() {
		return "{Cim: " + this.code + "}";
	}

	@Id
	@Column(name = "SID", unique = true, nullable = false)
	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer id) {
		this.sid = id;
	}
	
	@Column(name = "CODE", nullable = true, length = 20)
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String c) {
		this.code = c;
	}

	@Column(name = "LEVEL_", nullable = true, length = 2)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer lev) {
		this.level = lev;
	}

	@Column(name = "ID1", nullable = true)
	public Integer getId1() {
		return this.id1;
	}

	public void setId1(Integer id) {
		this.id1 = id;
	}

	@Column(name = "ID2", nullable = true)
	public Integer getId2() {
		return this.id2;
	}

	public void setId2(Integer id) {
		this.id2 = id;
	}

	@Column(name = "ID3", nullable = true)
	public Integer getId3() {
		return this.id3;
	}

	public void setId3(Integer id) {
		this.id3 = id;
	}

	@Column(name = "ID4", nullable = true)
	public Integer getId4() {
		return this.id4;
	}

	public void setId4(Integer id) {
		this.id4 = id;
	}

	@Column(name = "ID5", nullable = true)
	public Integer getId5() {
		return this.id5;
	}

	public void setId5(Integer id) {
		this.id5 = id;
	}

	@Column(name = "ID6", nullable = true)
	public Integer getId6() {
		return this.id6;
	}

	public void setId6(Integer id) {
		this.id6 = id;
	}

	@Column(name = "ID7", nullable = true)
	public Integer getId7() {
		return this.id7;
	}

	public void setId7(Integer id) {
		this.id7 = id;
	}
	
	@Column(name = "CIMO3", nullable = true)
	public Boolean getCimo3() {
		return this.cimo3;
	}

	public void setCimo3(Boolean cim) {
		this.cimo3 = cim;
	}
	
	/**
	 * 2 CIMs sont considérés comme égaux s'ils ont le même SID.
	 * @param obj est le CIM à tester.
	 * @return true si les CIMs sont égaux.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		// instanceOf est utilise plutot que != a cause des instances
		// CimMaster$$EnhancerByCGLIB qui sont crées par lors du fetch par 
		// la relation manyToAny
		if ((obj == null) || !(obj instanceof Cim)) {
			return false;
		}
		Cim test = (Cim) obj;
		if (this.getSid() != null) {
			// utilisation get car CimMaster$$EnhancerByCGLIB proxy
			return this.getSid().equals(test.getSid()); 
		} else { //impossible
			return false;
		}
	}

	/**
	 * Le hashcode est calculé sur le SID.
	 * @return la valeur du hashcode.
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		int hashSID = 0;
		
		if (this.sid != null) {
			hashSID = this.sid.hashCode();
		}
		
		hash = 31 * hash + hashSID;
		
		return hash;
	}

	@Column(name = "LIBELLE", nullable = true, length = 300)
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String lib) {
		this.libelle = lib;
	}
	
	@Override
	public Cim clone() {
		return this;
	}
}