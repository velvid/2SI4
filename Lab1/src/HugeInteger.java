import java.util.Random;

public class HugeInteger {

    Node head;              // head for linked list (the last digit)
    Node tail;              // tail for linked list (the first digit)
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
        Node previous;
        for(int i=val.length()-2; i>=(neg?1:0); i--) {
            current.next = new Node(val.charAt(i)-'0');
            previous = current;
            current = current.next;
            current.prev = previous;
        }
        tail = current;
    }
    
    /* constructor from number of digits, generates a random number*/
    public HugeInteger(int n) {
        
        random = new Random();          // random var
        neg = random.nextBoolean();     // randomly neg or pos
        size = n;
        
        head = new Node(random.nextInt(10));
        Node current = head;
        Node previous;
        for(int i=1; i<n-1; i++) {
            current.next = new Node(random.nextInt(10));
            previous = current;
            current = current.next;
            current.prev = previous;
        }
        current.next = new Node(random.nextInt(9)+1);
        tail = current.next;
        tail.prev = current;
    }
    
    public HugeInteger add(HugeInteger h) {
        
        HugeInteger result = new HugeInteger();
        
        if(!this.neg && h.neg) {
            result = this.subtract(h);
        } else if(this.neg && !h.neg) {
            result = h.subtract(this);
        } else {
            
            if(this.neg && h.neg)   result.neg = true;
            else                    result.neg = false;
            
            /* A + B = C */
            Node currentA = this.head;
            Node currentB = h.head;
            int sum = 0;
            int carry = 0;
            
            /* initializes the head */
            sum = currentA.value + currentB.value + carry;
            carry = sum / 10;
            result.head = new Node(sum % 10);
            Node currentC = result.head;
            Node previous;
            currentA = currentA.next;
            currentB = currentB.next;
            
            /* adds two numbers to result, A + B = C  */
            while(currentA != null && currentB != null) {
                sum = currentA.value + currentB.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentA = currentA.next;
                currentB = currentB.next;
                previous = currentC;
                currentC = currentC.next;
                currentC.prev = previous;
                result.size++;
            }
            while(currentA != null) {
                sum = currentA.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentA = currentA.next;
                previous = currentC;
                currentC = currentC.next;
                currentC.prev = previous;
                result.size++;
            }
            while(currentB != null) {
                sum = currentB.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentB = currentB.next;
                previous = currentC;
                currentC = currentC.next;
                currentC.prev = previous;
                result.size++;
            }
            if(carry > 0) {
                previous = currentC;
                currentC.next = new Node(carry);
                currentC = currentC.next;
                currentC.prev = previous;
                result.size++;
            }
            result.tail = currentC;
        }
        
        return result;
    }
    
    public HugeInteger subtract(HugeInteger h) {
        
        int comparison = this.compareTo(h);
        if(comparison == 0) return new HugeInteger("0");
        
        HugeInteger result = new HugeInteger();
        
        if(!this.neg && h.neg) {    // this = 1000, h = -100, result = 1100
            h.neg = false;
            result = this.add(h);
            h.neg = true;
        } else if(this.neg && !h.neg) {     // this = -1000, h = 100, result = -1100
            h.neg = true;
            result = this.add(h);
            h.neg = false;
        } else if(this.neg && h.neg) {      // this = -1000, h = -100, result = abs(h) - this = 
            h.neg = false;
            result = h.subtract(this);
            h.neg = true;
        } else if(comparison == -1) {       // this = 10, h = 100, result = -(h - this) = -90
            h.neg = false;
            result = h.subtract(this);
            h.neg = true;
            result.neg = true;
        } else if(!this.neg && !h.neg) {
            
            /* A - B = result, A + C = 1result (there will always be a leading 1) */
            Node currentA = this.head;
            Node currentB = h.head;
            
            /* initialize the complement and head of it */
            HugeInteger complement = new HugeInteger();     // to store 10's complement
            complement.head = new Node(9 - currentB.value);
            Node currentC = complement.head;
            Node previous;
            currentB = currentB.next;
            
            while(currentB != null) {
                previous = currentC;
                currentC.next = new Node(9 - currentB.value);
                System.out.println("test: " + currentC.value);
                currentC = currentC.next;
                currentC.prev = previous;
                currentB = currentB.next;
            }
            
            Node current;
            current = complement.head;
            while(current != null) {
                System.out.print(current.value);
                current = current.next;
            }
            System.out.println();
            current = complement.tail;
            while(current != null) {
                System.out.print(current.value);
                current = current.prev;
            }
            System.out.println("\n");
            
            result = new HugeInteger(10);
            //result = this.add(complement);
            result.neg = false;
        }
        
        return result;
    }
    
    public int compareTo(HugeInteger h) {
        
        /* simple cases if unequal sizes or opposite signs */
        if(this.neg && !h.neg) return -1;   // this = -10, h = 10 => this < h => -1
        if(!this.neg && h.neg) return 1;    // this = 10, h = -10 => this > h => 1
        if(this.neg && h.neg && this.size > h.size) return -1;  // this = -100, h = -10 => this < h => -1
        if(this.neg && h.neg && this.size < h.size) return 1;   // this = -10, h = -100 => this > h => 1
        if(!this.neg && !h.neg && this.size > h.size) return 1;     // this = 100, h = 10 => this > h => 1
        if(!this.neg && !h.neg && this.size < h.size) return -1;    // this = 10, h = 100 => this < h => -1
        
        /* block executes only if same sign and both are equal size */
        Node currentA = this.tail;
        Node currentB = h.tail;
        while(currentA != null || currentB != null) {
            if(currentA.value > currentB.value && !this.neg && !h.neg) return 1;    // this = 110, h = 100 => this > h => 1
            if(currentA.value < currentB.value && !this.neg && !h.neg) return -1;   // this = 100, h = 110 => this < h => -1
            if(currentA.value > currentB.value && this.neg && h.neg) return -1;     // this = -110, h = -100 => this < h => -1
            if(currentA.value < currentB.value && this.neg && h.neg) return 1;      // this = -100, h = -110 => this > h => 1
            currentA = currentA.prev;
            currentB = currentB.prev;
        }
        
        return 0;   // if all cases fail, then that means both are equal
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
}
