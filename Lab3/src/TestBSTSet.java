
public class TestBSTSet {

    public static void main(String[] args) {
        
        System.out.format("<CONSTRUCTOR TEST/>\n");
        int input1[][] = {
            {1, 2, 3, 4, 5},
            {6, 5, 4, 3, 2},
            {-1, -2, -3, -4, -5},
            {-6, -5, -4, -3, -2},
            {3, 3, 3, 3, 3},
            {9, 9, 9, 0, 0},
            {0, 0, 5, 5, 5},
            {-1, 0, 1, -2, 2, -3, 3, -4, -5, 6, 6, 6, 5, -1},
            {1},
            {},
        };
        for(int[] array:input1) {
            try {
                BSTSet set = new BSTSet(array);
                set.printBSTSet();
            } catch(IllegalArgumentException 
                    | NullPointerException 
                    | NegativeArraySizeException e) {
                System.out.println(e);
            }
        }
        System.out.format("</CONSTRUCTOR TEST>\n\n");
        
        System.out.format("<ADD TEST/>\n");
        BSTSet test = new BSTSet(new int[] {1, 2, 3, 4});
        test.printBSTSet();
        test.add(5);
        test.printBSTSet();
        test.add(5);
        test.printBSTSet();
        test.printBSTSet(test.recursiveSearch(test.root, 2));
        System.out.format("<ADD TEST/>\n");
        
        
    }
    
    
}
