
public class TestHugeInteger {
        public static void main(String[] args) {
        
        HugeInteger test = new HugeInteger(5);
        Node current;
        current = test.head;
        while(current != null) {
            System.out.print(current.value);
            current = current.next;
        }
        System.out.println();
        current = test.tail;
        while(current != null) {
            System.out.print(current.value);
            current = current.prev;
        }
        System.out.println("\n");
        
        System.out.println("<BEGIN CONSTRUCTOR TEST/>");
        HugeInteger const1 = new HugeInteger("54321");
        System.out.println(const1.toString());
        HugeInteger const2 = new HugeInteger("-54321");
        System.out.println(const2.toString());
        HugeInteger const3 = new HugeInteger(5);
        System.out.println(const3.toString() + " (random)");
        HugeInteger const4 = new HugeInteger(100);
        System.out.println(const4.toString() + " (random)");
        System.out.println("</END CONSTRUCTOR TEST>\n");

        System.out.println("<BEGIN COMPARE TEST/>");
        HugeInteger comp1 = new HugeInteger("123");
        HugeInteger comp2 = new HugeInteger("123");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("-123");
        comp2 = new HugeInteger("123");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("123");
        comp2 = new HugeInteger("-123");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("1234");
        comp2 = new HugeInteger("123");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("123");
        comp2 = new HugeInteger("1234");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("-1234");
        comp2 = new HugeInteger("-123");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("-123");
        comp2 = new HugeInteger("-1234");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("1234");
        comp2 = new HugeInteger("1243");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        comp1 = new HugeInteger("-1234");
        comp2 = new HugeInteger("-1243");
        System.out.println("input: ("+comp1+").compareTo("+comp2+")\toutput: "+comp1.compareTo(comp2));
        System.out.println("</END COMPARE TEST>\n");

        System.out.println("<BEGIN ADD TEST/>");
        HugeInteger add1 = new HugeInteger("9999");
        HugeInteger add2 = new HugeInteger("1");
        HugeInteger sum = add1.add(add2);
        System.out.println(add1.toString() + "+" + add2.toString() + "=" + sum.toString());
        add1 = new HugeInteger("-1234");
        add2 = new HugeInteger("-4321");
        sum = add1.add(add2);
        System.out.println(add1.toString() + "+" + add2.toString() + "=" + sum.toString());
        add1 = new HugeInteger("4294967296");   // 2^32
        add2 = new HugeInteger("65536");        // 2^16
        sum = add1.add(add2);
        System.out.println(add1.toString() + "+" + add2.toString() + "=" + sum.toString());
        System.out.println("</END ADD TEST>\n");
        
        System.out.println("<BEGIN SUBTRACTION TEST/>");
        HugeInteger subtract1 = new HugeInteger("1000");
        HugeInteger subtract2 = new HugeInteger("1000");
        HugeInteger difference = subtract1.subtract(subtract2);
        System.out.println(subtract1.toString() + "-" + subtract2.toString() + "=" + difference.toString());
        subtract1 = new HugeInteger("1000");
        subtract2 = new HugeInteger("-1000");
        difference = subtract1.subtract(subtract2);
        System.out.println(subtract1.toString() + "-" + subtract2.toString() + "=" + difference.toString());
        subtract1 = new HugeInteger("-1234");
        subtract2 = new HugeInteger("1000");
        difference = subtract1.subtract(subtract2);
        System.out.println(subtract1.toString() + "-" + subtract2.toString() + "=" + difference.toString());
        subtract1 = new HugeInteger("1000");
        subtract2 = new HugeInteger("100");
        difference = subtract1.subtract(subtract2);
        System.out.println(subtract1.toString() + "-" + subtract2.toString() + "=" + difference.toString());
        System.out.println("</END SUBTRACTION TEST>\n");
    }
}
