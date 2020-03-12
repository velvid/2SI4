import java.util.Random;

public class TestHashTable {
    public static void main(String[] args) {
        
        Random rand = new Random();
        
        double averageProbesExperimental = 0;
        double averageProbesTheoretical = 0;
        int runs = 100;
        int insertions = 100000;
        double lambdaList[] = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95};
        
        System.out.println("\n<SUCCESSFUL LINEAR PROBING/>");
        for(double lambda : lambdaList) { 
            for(int i=0; i<runs; i++) {       
                HashTableLin test = new HashTableLin(insertions, lambda);
                int upper = 2147483646, lower = 1;
                for(int j=0; j<insertions; j++)
                    test.insert(rand.nextInt(upper-lower+1)+lower);
                averageProbesExperimental += 
                        (double)(test.getTotalProbes())/(double)(test.getCount());
            }
            averageProbesExperimental /= (double)(runs);
            averageProbesTheoretical = 0.5*( 1 + (1/(1-lambda)) );
            System.out.format("load factor: %1.2f\ttheoretical: %1.2f\texperimental: %1.2f\n",
                    lambda,
                    averageProbesTheoretical,
                    averageProbesExperimental);
        }
        System.out.println("<SUCCESSFUL LINEAR PROBING/>\n");
        
        System.out.println("\n<SUCCESSFUL QUADRATIC PROBING/>");
        for(double lambda : lambdaList) { 
            for(int i=0; i<runs; i++) {       
                HashTableQuad test = new HashTableQuad(insertions, lambda);
                int upper = 2147483646, lower = 1;
                for(int j=0; j<insertions; j++)
                    test.insert(rand.nextInt(upper-lower+1)+lower);
                averageProbesExperimental += 
                        (double)(test.getTotalProbes())/(double)(test.getCount());
            }
            averageProbesExperimental /= (double)(runs);
            averageProbesTheoretical = 0.5*( 1 + (1/(1-lambda)) );
            System.out.format("load factor: %1.2f\ttheoretical: %1.2f\texperimental: %1.2f\n",
                    lambda,
                    averageProbesTheoretical,
                    averageProbesExperimental);
        }
        System.out.println("</SUCCESSFUL QUADRATIC PROBING>\n");
        
        int numList[] = {1, 5, 7, 11, 11*2, 11*3, 11*4};
        
        System.out.println("\n<TEST LINEAR PROBE INSERTION/>");
        HashTableLin test1 = new HashTableLin(10, 0.9);
        for(int num : numList) {
            System.out.format("<.insert(%d)>\n", num);
            test1.insert(num);
            test1.printKeysAndIndexes();
            System.out.format("Current Load: %1.2f\t",
                    (double)test1.getCount()/(double)test1.getLength());
            System.out.println("Load:\t" + test1.getLoad());
            System.out.println("MaxNum:\t" + test1.getMaxNum());
            System.out.println("Count:\t" + test1.getCount());
            System.out.println("Length:\t" + test1.getLength());
            if(num != numList[numList.length-1]) System.out.println();
        }
        System.out.println("</TEST LINEAR PROBE INSERTION>\n");
        
        System.out.println("\n<TEST QUADRATIC PROBE INSERTION/>");
        HashTableQuad test2 = new HashTableQuad(10, 0.9);
        for(int num : numList) {
            System.out.format("<.insert(%d)>\n", num);
            test2.insert(num);
            test2.printKeysAndIndexes();
            System.out.format("Current Load:% 1.2f\t",
                    (double)test2.getCount()/(double)test2.getLength());
            System.out.println("Load:\t" + test2.getLoad());
            System.out.println("MaxNum:\t" + test2.getMaxNum());
            System.out.println("Count:\t" + test2.getCount());
            System.out.println("Length:\t" + test2.getLength());
            if(num != numList[numList.length-1]) System.out.println();
        }
        System.out.println("</TEST QUADRATIC PROBE INSERTION>\n");
        
        System.out.println("\n<TEST REHASHING/>");
        HashTableLin test3 = new HashTableLin(5, 0.5);
        for(int num=1; num<=10; num++) {
            System.out.format("<.insert(%d)>\n", num);
            test3.insert(num*num);
            test3.printKeysAndIndexes();
            System.out.format("Current Load:% 1.2f\t",
                    (double)test3.getCount()/(double)test3.getLength());
            System.out.println("Load:\t" + test3.getLoad());
            System.out.println("MaxNum:\t" + test3.getMaxNum());
            System.out.println("Count:\t" + test3.getCount());
            System.out.println("Length:\t" + test3.getLength());
            if(num != 10) System.out.println();
        }
        System.out.println("</TEST REHASHING>\n");
        
        System.out.println("\n<TEST IS IN/>");
        HashTableLin test4 = new HashTableLin(11, 0.9);
        for(int num=1; num<=10; num++) test4.insert(num);
        test4.printKeysAndIndexes();
        System.out.println();
        
        int isInList[] = {1, 3, 6, 10, 11, 13, 100};
        for(int num : isInList) {
            System.out.format("isIn(%d):\t%b\n", num, test4.isIn(num));
        }        
        System.out.println("</TEST IS IN>\n");
    }
}
