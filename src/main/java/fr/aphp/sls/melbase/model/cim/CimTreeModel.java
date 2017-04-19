package fr.aphp.sls.melbase.model.cim;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.ext.TreeSelectableModel;

/**
 * tested with ZK 6.0.2
 * 
 * The TreeModel that supports ROD, with the RODTreeNode, and
 * any data bean that implements interface RODTreeNodeData<br><br>
 *
 * The basic rule is only call getChildren while you really want
 * to get children or the children has been loaded
 * 
 * @author benbai123
 *
 * @param <T> data bean implements RODTreeNodeData
 */
public class CimTreeModel<T>
    extends AbstractTreeModel<CimNode<T>> implements TreeSelectableModel {

    private static final long serialVersionUID = 7822729366554623684L;

    /**
     * Constructor, simply receive a root RODTreeNodeData
     */
    public CimTreeModel (CimNode<T> root) {
        super(root);
    }
 
    /**
     * get child from parent node by index
     */
    public CimNode<T> getChild(CimNode<T> parent, int index) {
        List<CimNode<T>> children = parent.getChildren();
        if (children == null) {
            return null;
        }
        if (index < 0 || index >= children.size()) {
            return null;
        }
        return children.get(index);
    }

    /**
     * call {@link RODTreeNode#getChildCount()} instead of
     * size of {@link RODTreeNode#getChildren()}
     */
    public int getChildCount(CimNode<T> parent) {
        // get child count directly instead of get size of children
        return parent.getChildCount();
    }

    /**
     * simply determine whether node is a leaf node
     */
    public boolean isLeaf(CimNode<T> node) {
        return getChildCount(node) == 0;
    }

    /**
     * here call getChildren since children
     * should already be loaded to get the child
     */
    public int getIndexOfChild(CimNode<T> parent, CimNode<T> child) {
        return parent.getChildren().indexOf(child);
    }

    /**
     * get path based on RODTreeNode, data independent
     */
    public int[] getPath (CimNode<T> child) {
        if (child == null || child.getParent() == null) {
            int[] path = new int[1];
            path[0] = 0;
            return path;
        }
        int[] path = null;
        List<Integer> dPath = new ArrayList<Integer>();
        CimNode<T> parent = child.getParent();
        while (parent != null) {
            dPath.add(0, parent.getChildren().indexOf(child));
            child = parent;
            parent = child.getParent();
        }
        path = new int[dPath.size()];
        for (int i = 0; i < dPath.size(); i++) {
            path[i] = dPath.get(i);
        }
        return path;
    }
}
