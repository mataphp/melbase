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
package fr.aphp.sls.melbase.model.cim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

import fr.aphp.sls.melbase.dao.cim.CimDao;

public class CimNode<T> {
   	
	private CimDao cimDao = ((CimDao) (ContextLoader
			.getCurrentWebApplicationContext()).getBean("cimDao"));
    
    // the data bean
    private T _data;
    // parent tree node
    private CimNode<T> _parent;
    // child list
    private List<CimNode<T>> _children;
    // whether this node is open
    private boolean _open;

    public CimNode(T data) {
    	_data = data;
    	_children = getChildren();
    	init();
    }

    // constructor for receiving List children
    public CimNode(T data, List<CimNode<T>> children) {
        _data = data;
        _children = children;
        init();
    }
    
    // constructor for receiving Array children
    public CimNode(T data, CimNode<T>[] children) {
        _data = data;
        if (children != null
            && children.length > 0)
            _children = Arrays.asList(children);
        init();
    }

    /**
     * initial the parent-child relation
     */
    public void init () {
        if (_children != null) {
            for (CimNode<T> child : _children) {
                child.setParent(this);
            }
        }
    }
    //setter/getter
    /**
     * @return T the data bean
     */
    public T getData () {
        return _data;
    }

    /**
     * @param open whether the node is open
     */
    public void setOpen (boolean open) {
        _open = open;
    }
    public boolean isOpen () {
        return _open;
    }
    public boolean getOpen () {
        return _open;
    }
    /**
     * @param parent parent tree node
     */
    public void setParent (CimNode<T> parent) {
        _parent = parent;
    }
    public CimNode<T> getParent () {
        return _parent;
    }
    
    /**
     * create TreeNode children based on the children of data bean
     * 
     * @see {@link RODTreeNodeData#getChildren()}
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CimNode<T>> getChildren () {
        if (_children == null) {
            _children = new ArrayList<CimNode<T>>();
                   	
        	Integer lev = ((Cim) getData()).getLevel();
        	
        	List<T> cims;
        	
        	switch (lev) {
    		case 1 : cims = (List<T>) cimDao.findById1(((Cim) getData()).getSid(), lev + 1);
    			break;
    		case 2 : cims = (List<T>) cimDao.findById2(((Cim) getData()).getSid(), lev + 1);
    			break;
    		case 3 : cims = (List<T>) cimDao.findById3(((Cim) getData()).getSid(), lev + 1);
    			break;
    		case 4 : cims = (List<T>) cimDao.findById4(((Cim) getData()).getSid(), lev + 1);
    			break;
    		case 5 : cims = (List<T>) cimDao.findById5(((Cim) getData()).getSid(), lev + 1);
    			break;
    		default: cims = (List<T>) new ArrayList<Cim>();
    			break;
    		}
            
            for (T data : cims) {
                CimNode<T> child = new CimNode<T>(data);
                child.setParent(this);
                _children.add(child);
            }
        }

        return _children;
    }
    /**
     * get child count from children size if children created,
     * or get child count from data
     * 
     * @see RODTreeNodeData#getChildCount()
     * @return
     */
    public int getChildCount () {
        int count = _children == null ? 0 : _children.size();
        return count;
    }
  
}
