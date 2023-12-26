import java.util.ArrayList;
import java.util.Random;
//George Henry with help from the TAs and professor John Rager's office hours
//COSC 211
//PA2

public class TreapNode<P extends Comparable<P> & getP> {

    //Pseudo random number generator
    static Random prng = new Random(123);

    P p;
    //new priority field
    int priority;

    TreapNode<P> right, left, parent;

    //normal node
    public TreapNode(P p) {
        this.p = p;
        this.priority = prng.nextInt(10000) + 1;
    }

    public void add(P newP) {
        // if keys are equal
        if (p.compareTo(newP) == 0) {
            // return
            return;
        }
        // if new key is greater than added key
        if (p.compareTo(newP) < 0) {
            // if right is null
            if (right == null) {
                // make new right node with the key of newP
                right = new TreapNode<P>(newP);
                // make the right parent key equal to the current node
                right.parent = this;
                // if right priority is less, rotate left
                if (right.priority < priority) {
                    rotateLeft();
                }
            } else {
                // recursively add right node
                right.add(newP);
                // rotate left if right priority is less
                if (right.priority < priority) {
                    rotateLeft();
                }
            }
        }
        // if new key is less than current node
        if (p.compareTo(newP) > 0) {
            // if left is null
            if (left == null) {
                // make new node with the value of the newP
                left = new TreapNode<P>(newP);
                // set new left parent to be equal to current node
                left.parent = this;
                // rotate right if priority of the left child is less than current
                if (left.priority < priority) {
                    rotateRight();
                }
            } else {
                // make a new left node recursively
                left.add(newP);
                // rotate right if left priority is less
                if (left.priority < priority) {
                    rotateRight();
                }
            }
        }
    }

    public int height() {
        //counters for left and right heights
        int lheight = 0;
        int rheight = 0;
        //recurse while bottom has not been reached
        if (this.left != null) {
            //increment and recurse
            lheight = 1 + left.height();
        }
        //recurse while bottom has not been reached
        if (this.right != null) {
            //increment and recurse on the right
            rheight = 1 + right.height();
        }
        //compare and return the larger of the r/l heights using Math.max
        return Math.max(lheight, rheight);
    }

    public void delete(P p) {
        //if new key is less than root
        if (p.compareTo(this.p) < 0) {
            //and there's a left child
            if (left != null) {
                //recurse on left
                left.delete(p);
            }
        //if new key is greater than root
        } else if (p.compareTo(this.p) > 0) {
            //and right isn't null
            if (right != null) {
                //recurse on right
                right.delete(p);
            }
        } else {
            //if left is null and right is null
            if (left == null && right == null) {
                //and it isn't the root
                if (parent != null) {
                    //if the current node is left child, set left of parent to null
                    if (parent.left == this) {
                        parent.left = null;
                        //otherwise if it's the right child set right to null
                    } else {
                        parent.right = null;
                    }
                }
            } else {
                //making the priority "really big"
                priority = Integer.MAX_VALUE; // Re
                //if at least one of the children isn't null rotate
                while (left != null || right != null) {
                    //if right is null or priority of left is less than right, rotate right
                    if (right == null || (left != null && left.priority < right.priority)) {
                        rotateRight();
                        //otherwise rotate left
                    } else {
                        rotateLeft();
                    }
                }
                //if current node isn't root
                if (parent != null) {
                    //make the left child null
                    if (parent.left == this) {
                        parent.left = null;
                        //if the node is the right child make the right child of the parent null
                    } else {
                        parent.right = null;
                    }
                }
            }
        }
    }


    //rotate methods

    public void rotateLeft() {
        //if there is nothing on the right side
        if (right == null) {
            //return since a left rotation is impossible
            return;
        }
        //create replacement right node
        TreapNode<P> newRoot = right;
        //swap right and left
        right = newRoot.left;
        //if there is a left child
        if (newRoot.left != null) {
            //redirect pointer
            newRoot.left.parent = this;
        }
        //make parent pointer of new node equal to parent
        newRoot.parent = parent;
        //if parent is not null
        if (parent != null) {
            //make parent left equal to newRoot key
            if (parent.left == this) {
                parent.left = newRoot;
                //otherwise do the opposite
            } else {
                parent.right = newRoot;
            }
        }
        //if parent is null make the left of the new root equal to p
        newRoot.left = this;
        //set newRoot key to be equal to parent
        parent = newRoot;
    }

    public void rotateRight() {
        //if left is null
        if (left == null) {
            //return since rotating right isn't possible
            return;
        }
        //create newRoot and set equal to left
        TreapNode<P> newRoot = left;
        //make right of new node equal to current left
        left = newRoot.right;
        //if right is not null
        if (newRoot.right != null) {
            //make the right parent's key equal to p
            newRoot.right.parent = this;
        }
        //newRoot parent becomes old parent
        newRoot.parent = parent;
        //if parent is not null
        if (parent != null) {
            //if original node is now left
            if (parent.left == this) {
                //left is new root
                parent.left = newRoot;

            } else {
                //right is new root
                parent.right = newRoot;
            }
        }
        //if parent is null
        newRoot.right = this;
        //make right equal to p and parent is the new root
        parent = newRoot;
    }


    public void printMe() {
        //slightly modified to also print priorities
        System.out.println(p + " " + priority);
        if (left != null)
            left.printMe();
        if (right != null)
            right.printMe();

    }
}