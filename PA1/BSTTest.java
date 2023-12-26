
import java.util.ArrayList;

public class BSTTest {

    public static void main(String[] args) {

        new BSTTest().process();
    }

    public void process() {
        BinarySearchTree<Person> sampleTree = new BinarySearchTree<Person>();

        sampleTree.add(new Person("H", "Test"));

        sampleTree.add(new Person("D", "Test"));
        sampleTree.add(new Person("I", "Test"));
        sampleTree.add(new Person("C", "Test"));

        sampleTree.add(new Person("G", "Test"));
        sampleTree.add(new Person("A", "Test"));
        sampleTree.add(new Person("G", "Test"));

        sampleTree.add(new Person("E", "Test"));
        sampleTree.add(new Person("F", "Test"));

        sampleTree.add(new Person("J", "Test"));
        sampleTree.add(new Person("K", "Test"));
        sampleTree.add(new Person("L", "Test"));

    }

}

