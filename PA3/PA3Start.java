import java.util.Random;

public class PA3Start {

    static Random prng = new Random(123);

    //pesudorandom large values for first and second test. Greater than 500000 less than 750000
    static int bound = prng.nextInt(500000, 750000);
    static int bound2 = prng.nextInt(500000, 750000);

    public static void main(String[] args) {

        //Test BSTs
        BinarySearchTree<Person> BST1 = new BinarySearchTree<Person>();
        BinarySearchTree<Person> BST2 = new BinarySearchTree<Person>();
        //Test Treaps
        CS211Treap<Person> Treap1 = new CS211Treap<Person>();
        CS211Treap<Person> Treap2 = new CS211Treap<Person>();
        //Person
        CS211RandomPerson p = new CS211RandomPerson();
        //First Test
        System.out.println("          TEST 1");
        System.out.println("Treap Height   BST1 Height");
        //outer loop for printing results neatly
        for (int i = 0; i < 12; i++) {
            //inner loop for adding things into the BST and Treap
            for (int j = 0; j < bound; j++) {
                Person k = p.getRand1();
                BST1.add(k);
                Treap1.add(k);
            }
            System.out.println("     " + Treap1.height() + "             " + BST1.height());
        }
        //Second Test
        System.out.println("          TEST 2");
        System.out.println("Treap Height   BST1 Height");
        //same outer and inner loop structure
        for (int l = 0; l < 12; l++) {
            for (int m = 0; m < bound2; m++) {
                Person n = p.getRand2();
                BST2.add(n);
                Treap2.add(n);
            }
            System.out.println("     " + Treap2.height() + "             " + BST2.height());
        }
    }

}
