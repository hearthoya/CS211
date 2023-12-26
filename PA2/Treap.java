import java.util.ArrayList;


public class Treap<P extends Comparable<P> & getP> {

    private TreapNode<P> root;

    //core methods
    public void add(P p) {
        //if there is no root
        if (root == null) {
            //add a new node which becomes the new root
            root = new TreapNode<P>(p);
            //set parent to null since root is parentless 
            root.parent = null;
        } else {
            root.add(p);
        }
    }

    //overridden method for root case
    public void add(P p, boolean rootCase) {
        if (rootCase) {
            root = new TreapNode<P>(p);
            root.priority = 0;
        }
    }

    public void delete (P p) {
        //base case
        if (root == null) {
            return;
        }
        root.delete(p);
    }

    //utility/root methods
    public void setRoot(TreapNode<P> r) {
        root = r;
    }

    public TreapNode<P> getRoot() {
        return root;
    }

    public void printMe() {
        if (root == null) {
            System.out.println("Empty tree");
        } else root.printMe();
    }

    public int height() {
        //if the root is null or has no children just return 0
        if (root == null) {
            return 0;
        }
        //otherwise recurse
        return root.height();
    }
}