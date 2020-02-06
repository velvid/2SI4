
public class TestHugeInteger {
    public static void main(String[] args) {

        System.out.println("<BEGIN LINKED LIST ITERATION TEST/>");
        HugeInteger test = (new HugeInteger("5")).multiply(new HugeInteger("-900")).subtract(new HugeInteger("-1245"));
        System.out.format("toString(): %s\tsize: %d\n", test.toString(), test.size);
        Node current;
        System.out.print("head -> tail:\t");
        current = test.head;
        while(current != null) {
            System.out.print(current.value);
            current = current.next;
        }
        System.out.print("\ntail -> head:\t");
        current = test.tail;
        while(current != null) {
            System.out.print(current.value);
            current = current.prev;
        }
        System.out.println("\n</END LINKED LIST ITERATION TEST>\n\n");
        
        System.out.println("<BEGIN CONSTRUCTOR_1 TEST/>");
        String[] inputConst1 = {
            "54321",
            "-54321",
            "1056262284384840902705937967203072379562530165957848456121373462",
            "0003",
            "0000",
            "0",
            "123R123",
            "ZXDF3SF",
            ""};
        for(String operand:inputConst1) {
            try {
                System.out.println("\tHugeInteger('"+operand+"')\toutput: "+(new HugeInteger(operand)).toString());
            } catch(IllegalArgumentException e) {
                System.out.println(e+"\n  argument: '"+operand+"'");
            }
        }
        System.out.println("</END CONSTRUCTOR_1 TEST>\n\n");
        
        System.out.println("<BEGIN CONSTRUCTOR_2 TEST/>");
        int[] inputConst2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 64, -1, 0, -100};
        for(int operand:inputConst2) {
            try {
                System.out.println("\tHugeInteger("+operand+")\toutput: "+(new HugeInteger(operand)).toString());
            } catch(IllegalArgumentException e) {
                System.out.println(e+"\n  argument: "+operand);
            }
        }
        System.out.println("</END CONSTRUCTOR_2 TEST>\n\n");
        
        System.out.println("<BEGIN COMPARE TEST/>");
        String[][] inputCompare = {
            {"123", "123"},
            {"-123", "123"},
            {"123", "-123"},
            {"1234", "123"},
            {"123", "1234"},
            {"-1234", "-123"},
            {"-123", "-1234"},
            {"1234", "1235"},
            {"1235", "1234"},
            {"5432", "1234"},
            {"-1234", "-1235"}};
        for(String[] operand:inputCompare) {
            try {
                int output = (new HugeInteger(operand[0])).compareTo(new HugeInteger(operand[1]));
                System.out.println("\t("+operand[0]+").compareTo("+operand[1]+")\toutput: "+output);
            } catch(IllegalArgumentException e) {
                System.out.println(e+"\n  operand[0]: "+operand[0]+"\toperand[1]: "+operand[1]);
            }
        }
        System.out.println("</END COMPARE TEST>\n\n");
        
        System.out.println("<BEGIN ADDITION TEST/>");
        String[][] inputAdd = {
            {"1000", "1000"},
            {"-1234", "1000"},
            {"1234", "-1000"},
            {"999", "1"},
            {"1", "999"},
            {"0", "0"},
            {"123", "0"},
            {"-1000", "-100"},
            {"100", "1000"},
            {"54321", "12345"},
            {"6666", "3334"}};
        for(String[] operand:inputAdd) {
            try {
                HugeInteger output = ((new HugeInteger(operand[0])).add(new HugeInteger(operand[1])));
                System.out.format("\t(%s).add(%s)\toutput: %s\tsize: %d\n",
                        operand[0],operand[1],output.toString(),output.size);
            } catch(IllegalArgumentException e) {
                System.out.println(e + "\n  operand[0]: "+operand[0]+"\toperand[1]: "+operand[1]);
            }
        }
        System.out.println("</END ADDITION TEST>\n\n");
        
        System.out.println("<BEGIN SUBTRACTION TEST/>");
        String[][] inputSubtract = {
            {"1024", "1024"},
            {"-1024", "-1024"},
            {"-1234", "1000"},
            {"1000", "1"},
            {"1", "1000"},
            {"0", "0"},
            {"1a00", "991"},
            {"-1000", "-100"},
            {"100", "1000"},
            {"54321", "12345"},
            {"6666", "9999"}};
        for(String[] operand:inputSubtract) {
            try {
                HugeInteger output = ((new HugeInteger(operand[0])).subtract(new HugeInteger(operand[1])));
                System.out.format("\t(%s).subtract(%s)\toutput: %s\tsize: %d\n",
                        operand[0],operand[1],output.toString(),output.size);
            } catch(IllegalArgumentException e) {
                System.out.println(e + "\n  operand[0]: "+operand[0]+"\toperand[1]: "+operand[1]);
            }
        }
        System.out.println("</END SUBTRACTION TEST>\n\n");
        
        System.out.println("<BEGIN MULTIPLICATION TEST/>");
        String[][] inputMultiply = {
            {"0", "9999"},
            {"1234", "0"},
            {"1", "2"},
            {"5", "1"},
            {"1234", "8"},
            {"8", "1234"},
            {"999", "99"},
            {"999", "999"},
            {"999", "-999"},
            {"-999", "999"},
            {"-999", "-999"},
            {"13243546576879", "123456789123456789"}};
        for(String[] operand:inputMultiply) {
            try {
                HugeInteger output = ((new HugeInteger(operand[0])).multiply(new HugeInteger(operand[1])));
                System.out.format("\t(%s).multiply(%s)\toutput: %s\tsize: %d\n",
                        operand[0],operand[1],output.toString(),output.size);
            } catch(IllegalArgumentException e) {
                System.out.println(e + "\n  operand[0]: "+operand[0]+"\toperand[1]: "+operand[1]);
            }
        }
        System.out.println("</END MULTIPLICATION TEST>\n\n");

    }
}
