import java.util.Random;

public class TreapTest {

    public static void main(String[] args) {
        new TreapTest().process();
    }
    public void process() {
        //boot strapping
        Treap<Person> sampleTreap = new Treap<Person>();
        sampleTreap.add(new Person("", ""), true);
        //actual test
        sampleTreap.add(new Person("D", "Test"));
        sampleTreap.add(new Person("I", "Test"));
        sampleTreap.add(new Person("C", "Test"));
        sampleTreap.add(new Person("Z", "Test"));
        sampleTreap.add(new Person("L", "Test"));
//        sampleTreap.delete(new Person("Z", "Test"));
        //print Treap
        sampleTreap.printMe();
    }

}

