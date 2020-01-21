import java.util.Random;

public class HugeInteger {

    Node head;              // head for linked list the number (the last digit)
    public int size;        // int size variable
    public Boolean neg;     // boolean if number is negative/positive
    private Random random;
    
    public HugeInteger() {
        size = 0;
    }
    
    /* constructor from string input */
    public HugeInteger(String val) {
        
        if(val.charAt(0) == 45) {       // if minus sign at start of string
            neg = true;
            size = val.length() - 1;    // length minus one, to accomodate the "-" sign
        } else {
            neg = false;
            size = val.length();
        }
        
        head = new Node(val.charAt(val.length()-1)-'0');
        Node current = head;
        for(int i=val.length()-2; i>=(neg?1:0); i--) {
            current.next = new Node(val.charAt(i)-'0');
            current = current.next;
        }
        
    }
    
    /* constructor from number of digits, generates a random number*/
    public HugeInteger(int n) {
        
        random = new Random();          // random var
        neg = random.nextBoolean();     // randomly neg or pos
        size = n;
        
        head = new Node(random.nextInt(10));
        Node current = head;
        for(int i=1; i<n-1; i++) {
            current.next = new Node(random.nextInt(10));
            current = current.next;
        }
        current.next = new Node(random.nextInt(9)+1);
        
    }
    
    public HugeInteger add(HugeInteger h) {
        
        HugeInteger result = new HugeInteger();
        
        if(!this.neg && h.neg) {
            result = this.subtract(h);
        } else if(this.neg && !h.neg) {
            result = h.subtract(this);
        } else {
            
            if(this.neg && h.neg)
                result.neg = true;
            else
                result.neg = false;
            
            Node currentA = this.head;
            Node currentB = h.head;
            result.size = 0;
            int sum = 0;
            int carry = 0;
            
            /* initializes the head */
            sum = currentA.value + currentB.value + carry;
            carry = sum / 10;
            result.head = new Node(sum % 10);
            Node currentC = result.head;
            currentA = currentA.next;
            currentB = currentB.next;
            
            /* adds two numbers to result, A + B = C  */
            while(currentA != null && currentB != null) {
                sum = currentA.value + currentB.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentA = currentA.next;
                currentB = currentB.next;
                currentC = currentC.next;
                result.size++;
            }
            while(currentA != null) {
                sum = currentA.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentA = currentA.next;
                currentC = currentC.next;
                result.size++;
            }
            while(currentB != null) {
                sum = currentB.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentB = currentB.next;
                currentC = currentC.next;
                result.size++;
            }
            if(carry > 0) {
                currentC.next = new Node(carry);
                result.size++;
            }
        }
        
        return result;
    }
    
    public HugeInteger subtract(HugeInteger h) {
        
        HugeInteger result = new HugeInteger();
        
        
        
        return result;
    }
    
    public String toString() {
        String output = "";
        Node current = head;
        while(current != null) {
            output = String.valueOf(current.value) + output;
            current = current.next;
        }
        if(neg) output = "-" + output;
        return output;
    }
    
    public static void main(String[] args) {
        
        HugeInteger test1 = new HugeInteger("54321");
        HugeInteger test2 = new HugeInteger(5);
        System.out.println(test1.toString());
        System.out.println(test2.toString());
        
        HugeInteger add1 = new HugeInteger("9999");
        HugeInteger add2 = new HugeInteger("1");
        HugeInteger sum1 = add1.add(add2);
        System.out.println(sum1.toString());
        
        add1 = new HugeInteger("-1234");
        add2 = new HugeInteger("-4321");
        HugeInteger sum2 = add1.add(add2);
        System.out.println(sum2.toString());
        
        
    }
}
