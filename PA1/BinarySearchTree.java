import java.util.ArrayList;


public class BinarySearchTree<P extends Comparable<P> & getP> {

    private BSTreeNode<P> root;


    public void add(P p) {

        if (root == null) {
            root = new BSTreeNode<P>(p);
            root.parent = null;
        } else root.add(p);
    }

    public int count() {
        if (root == null) {
            return 0;
        } else return root.count();
    }

    public int countLeaves() {
        //if the tree has no root just return 0
        if (root == null) {
            return 0;
        }
        //otherwise recurse
        return root.countLeaves();
    }

    public P smallest() {
        //if the root is null
        if (root == null) {
            //return null
            return null;
        }
        //if root is not null or the smallest call smallest to recurse through tree
        return root.smallest();
    }

    public int height() {
        //if the root is null or has no children just return 0
        if (root == null) {
            return 0;
        }
        //otherwise recurse
        return root.height();
    }

    public void getNodesInOrder(ArrayList<BSTreeNode<P>> a) {
        //if the root is null
        if (root == null) {
            return;
        }
        //if root is not null recurse
        root.getNodesInOrder(a);
        //print out the nodes in the arraylist order
        for (BSTreeNode<P> pbsTreeNode : a) {
            System.out.println(pbsTreeNode.p);
        }
    }


    public int countG(P p) {
        if (root == null) {
            return 0;
        }

        return root.countG(p);
    }

    public void delete (P p) {
        if (root == null) {
            return;
        }
        root.delete(p);
    }

    //limit methods

    public void truncate(int limit) {
        root.truncate(limit);
    }


    public boolean inBalance(int limit) {
        //base case
        if (root == null) {
            return false;
        }
       return root.inBalance(limit);
    }

    //utility/root methods
    public void setRoot(BSTreeNode<P> r) {
        root = r;
    }

    public BSTreeNode<P> getRoot() {
        return root;
    }

    public void printMe() {
        if (root == null) {
            System.out.println("Empty tree");
        } else root.printMe();
    }
}