
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
        
        System.out.format("<ADD&REMOVE&SIZE&HEIGHT TEST/>\n");
        
        BSTSet test = new BSTSet(new int[] {1, 2, 3, 4});
        boolean isIn;
        
        System.out.format("(origin)\t");
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        
        System.out.format("add(5):\t\t");
        test.add(5);
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        
        System.out.format("add(5):\t\t");
        test.add(5);
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        
        System.out.format("add(-5):\t");
        test.add(-5);
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        
        System.out.format("remove(-5):\t");
        isIn = test.remove(-5);
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        System.out.printf("\t\tRemove: %s\n", (isIn) ? "true" : "false");
        
        System.out.format("remove(2):\t");
        isIn = test.remove(2);
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        System.out.printf("\t\tRemove: %s\n", (isIn) ? "true" : "false");
        
        System.out.format("remove(0):\t");
        isIn = test.remove(0);
        test.printBSTSet();
        System.out.format("\t\tSize: %d\tHeight: %d\n",
                test.size(), test.height());
        System.out.printf("\t\tRemove: %s\n", (isIn) ? "true" : "false");
        
        System.out.format("</ADD&REMOVE&SIZE&HEIGHT TEST>\n");

        System.out.format("\n<UNION&INTERSECT&DIFFERENCE TEST/>\n");
        int input2[][] = {
            {1, 2, 3, 4, 5},
            {-1, -2, -3, -4, -5},
            {3, 3, 3, 3, 3},
            {0, 0, 5, 5, 5},
            {-1, 0, 1, -2, 2, -3, 3, -4, -5, 6, 6, 6, 5, -1},
            {1},
            {},
        };
        for(int[] array1:input1) {
            for(int[] array2:input2) {
                try {
                    BSTSet set1 = new BSTSet(array1);
                    BSTSet set2 = new BSTSet(array2);
                    
                    System.out.format("(set 1)\t");
                    set1.printBSTSet();
                    System.out.format("(set 2)\t");
                    set2.printBSTSet();
                    System.out.format("(union)\t");
                    (set1.union(set2)).printBSTSet();
                    System.out.format("(intersect)\t");
                    (set1.intersection(set2)).printBSTSet();
//                    System.out.format("(difference)\t");
//                    (set1.difference(set2)).printBSTSet();
                    System.out.println();
                } catch(IllegalArgumentException 
                    | NullPointerException 
                    | NegativeArraySizeException e) {
                    System.out.println(e);
                }
            }
        }
        System.out.format("</UNION&INTERSECT&DIFFERENCE TEST>\n");
        
    }
    
    
}