import java.util.ArrayList;
//George Henry with help from the TAs and professor John Rager's office hours
//COSC 211
//PA1

public class BSTreeNode<P extends Comparable<P> & getP> {

    P p;

    BSTreeNode<P> right, left, parent;

    public BSTreeNode(P p) {
        this.p = p;
    }


    public BSTreeNode<P> add(P newP) {

        // we found it - don't add it

        int compare = p.compareTo(newP);
        if (compare == 0)
            return this;
        if (compare < 0) {
            if (right == null) {
                right = new BSTreeNode<P>(newP);
                right.parent = this;
                return right;
            } else {
                return right.add(newP);
            }
        } else {
            if (left == null) {
                left = new BSTreeNode<P>(newP);
                left.parent = this;
                return left;
            } else {
                return left.add(newP);
            }
        }
    }

    public P smallest() {
        //if the left most element has nothing below it
        if (this.left == null) {
            //return that element because it's the smallest
            return this.p;
        }
        //recurse through finding and then returning the smallest element
        return left.smallest();
    }

    public int countLeaves() {
        //leaf counter
        int counter = 0;
        //if there are no leaves
        if (this.left == null && this.right == null) {
            //return 1/increment
            return 1;
        }
        //if right is not null
        if (this.right != null) {
            //recurse
            counter += right.countLeaves();
        }
        //if left is not null
        if (this.left != null) {
            //recurse
            counter += left.countLeaves();
        }
        //return the counter after all nodes have been counted/looked through
        return counter;
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

    public void truncate(int limit) {
        //if there's a left node
        if (left != null) {
            //if left is less than the limit minus the p val
            if (left.p.getP() <= limit - p.getP()) {
                //truncate left
                left.truncate(limit - p.getP());
            }
            //if it is greater than limit
            if (left.p.getP() > limit - p.getP()) {
                //set it to null
                left = null;
            }
        }
        //if there's a right node
        if (right != null) {
            //if right is less than the limit minus the p val
            if (right.p.getP() <= limit - p.getP()) {
                //truncate right
                right.truncate(limit - p.getP());
            }
            //if it is greater than limit
            if (right.p.getP() > limit - p.getP()) {
                //set it to null
                right = null;
            }
        }
    }

    public boolean inBalance(int limit) {
        //if there is only one node return true since technically a one node BST is the most simple balanced tree
        if (left == null && right == null) {
            return true;
        }
        //if either left or right of root is null return false since the tree cannot be balanced
        if (left == null || right == null) {
            return false;
        }
        //compare heights and if the diff is less than the limit return true otherwise return false
        return (right.height() - left.height()) < limit || (left.height() - right.height()) < limit;
    }

    public int count() {
        int ret = 1;

        if (right != null)
            ret = ret + right.count();
        if (left != null)
            ret = ret + left.count();

        return ret;
    }

    public int countG(P p) {
        //counter for greater nodes
        int countG = 0;

        if (this.p.getP() > p.getP()) {
            //increment counter
            countG++;
            //recurse on left and right
            if (left != null) {
                countG += left.countG(p);
            }
            if (right != null) {
                countG += right.countG(p);
            }
        } else {
            //check right since left will be less than the root which is already useless given it's smaller than p
            if (right != null) {
                countG += right.countG(p);
            }
        }
        return countG;
    }

    public void delete(P p) {
        //root case
        if (p.compareTo(this.p) == 0 && parent == null) {
            //if left is not null
            if (left != null) {
                //create a node to store the largest element in the left sub tree
                BSTreeNode<P> leftSwap = left;
                //while there are right nodes in the left subtree
                while (leftSwap.right != null) {
                    //swap root with biggest node on the left
                    leftSwap = leftSwap.right;
                }
                //set root value to be equal to that value
                this.p = leftSwap.p;
                //delete the node
                leftSwap.delete(leftSwap.p);
            }
            //if right is not null
            else if (right != null) {
                //make node to store smallest node on the right (for right child/ability to delete on right)
                BSTreeNode<P> rightSwap = right;
                // if left is not null use while loop to recurse down the tree looking for the smallest node
                while (rightSwap.left != null) {
                    //setting the smallest on the right to be to the left of the smallest right node
                    rightSwap = rightSwap.left;
                }
                //swap the node value with the smallest on the right
                this.p = rightSwap.p;
                //delete the old node
                rightSwap.delete(rightSwap.p);
            }
            //if root has no children j return null
            else {
                //set root value to null
                this.p = null;
            }
            //break out of if
            return;
        }
        //if the arg p val is smaller than the root
        if (p.compareTo(this.p) < 0) {
            //and left is not null
            if (left != null) {
                //recurse on left
                left.delete(p);
            }
            //break out of this if
            return;
        }
        //if the arg p val is bigger than the root
        if (p.compareTo(this.p) > 0) {
            //and right is not null
            if (right != null) {
                //recurse on right
                right.delete(p);
            }
            //break out of this if
            return;
        }

        //if left and right are null (no child)
        if (left == null && right == null) {
            //and the node is not the root
            if (parent != null) {
                //if this node is the left child
                if (parent.left == this) {
                    //make the left of the parent null/ delete it
                    parent.left = null;
                } else {
                    //make the right of the parent null
                    parent.right = null;
                }
            }
            //break out of this case
            return;
        }
        //if there is only a left child
        if (left != null && right == null) {
            //and it's not the root
            if (parent != null) {
                //if this is the left child
                if (parent.left == this) {
                    //make the parent's left child this node
                    parent.left = left;
                    //if this is the right child of the parent
                } else {
                    //make the parent's right child this node
                    parent.right = left;
                }
            }
            //the left parent replaces the node
            left.parent = parent;
            //break out of this case
            return;
        }
        //make node to store smallest node on the right (for right child/ability to delete on right)
        BSTreeNode<P> rightSwap = right;
        // if left is not null use while loop to recurse down the tree looking for the smallest node
        while (rightSwap.left != null) {
            //setting the smallest on the right to be to the left of the smallest right node
            rightSwap = rightSwap.left;
        }
        //swap the node value with the smallest on the right
        this.p = rightSwap.p;
        //delete the old node
        rightSwap.delete(rightSwap.p);
    }

    public void getNodesInOrder(ArrayList<BSTreeNode<P>> a) {
        //check left
        if (left != null) {
            //recurse on left
            left.getNodesInOrder(a);
        }
        //add element that was looked at
        a.add(this);
        //check right
        if (right != null) {
            //recurse on right
            right.getNodesInOrder(a);
        }
    }

    public void printMe() {

        System.out.println(p);
        if (left != null)
            left.printMe();
        if (right != null)
            right.printMe();

    }
}