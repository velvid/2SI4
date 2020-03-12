import java.util.Random;

public class TestClass {
    public static void main(String[] args) {
        
        Random rand = new Random();
        
        double averageProbesExperimental = 0;
        double averageProbesTheoretical = 0;
        int runs = 100;
        int insertions = 100000;
        double lambdaList[] = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.99};
        
        System.out.println("\n<LINEAR PROBING/>");
        for(double lambda : lambdaList) { 
            for(int i=0; i<runs; i++) {       
                HashTableLin test = new HashTableLin(insertions, lambda);
                int upper = 999999999, lower = 1;
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
        System.out.println("<LINEAR PROBING/>\n");
        
        System.out.println("\n<QUADRATIC PROBING/>");
        for(double lambda : lambdaList) { 
            for(int i=0; i<runs; i++) {       
                HashTableQuad test = new HashTableQuad(insertions, lambda);
                int upper = 999999999, lower = 1;
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
        System.out.println("</QUADRATIC PROBING>\n");
        
    }
}
