import java.util.Random;

public class HugeInteger {

    Node head;              // head for linked list (least significant digit)
    Node tail;              // tail for linked list (most significant digit)
    public int size;        // int size variable
    public Boolean neg;     // boolean if number is negative/positive
    private Random random;  // random variable for THIS CLASS ONLY
    
    /* method for this class only, to initialize empty linked lists */
    private HugeInteger() {
        size = 0;
    }
    
    /* method to remove leading zeros when constructing new linked lists */
    private void removeLeadingZeros() {
        if(this.size > 1) {    // if size is 1, there is no trailing zeros
            Node current = this.tail;      // start from most significant digit
            
            /* iterate while there are leading 0's, and leave 1 number left*/
            while(current.value == 0 && current.prev != null) { 
                current = current.prev; // iterate
                current.next = null;    // destroy forward link
                this.size--;    // decrement size by 1
            }
        }
    }
    
    /* constructor from string input */
    public HugeInteger(String val) throws IllegalArgumentException {
        
        if(val.length() == 0 || (val.charAt(0) == 45 && val.length() == 1)) // ex. if "" or "-"
            throw new IllegalArgumentException("No digits in input.");
        
        if(val.charAt(0) == 45) {       // if minus sign at start of string
            neg = true;
            size = val.length() - 1;    // length minus one, to accomodate the "-" sign
        } else {
            neg = false;
            size = val.length();
        }
        
        /* initialize the head */
        int tempChar = val.charAt(val.length()-1)-'0';  // variable to store temporary char value of each string index
        if(tempChar < 0 || tempChar > 9)    // if char is NOT an integer
                throw new IllegalArgumentException("Invalid input.");
        head = new Node(tempChar); 
        Node current = head;
        Node previous;      // pointer to previosu node (for link creation)
        
        /* iterates from end to first digit */
        for(int i=val.length()-2; i>=(neg?1:0); i--) {
            tempChar = val.charAt(i) - '0';     // character value (converted from unicode)
         
            if(tempChar < 0 || tempChar > 9)    // if char is NOT an integer
                throw new IllegalArgumentException("Invalid input.");

            /* iterate through LL and create links */
            current.next = new Node(tempChar);
            previous = current;
            current = current.next;
            current.prev = previous;
        }
        tail = current;   
        
        /* call method to remove any leading zeros */
        this.removeLeadingZeros();
    }
    
    /* constructor from number of digits, generates a random number*/
    public HugeInteger(int n) throws IllegalArgumentException{
        
        random = new Random();          // random var
        neg = random.nextBoolean();     // randomly neg or pos
        size = n;                       // set size
        
        if(size <= 0) {         // throw exception for size 0 input
            throw new IllegalArgumentException("Cannot have 0 or less length list.");
        } else if(size == 1) {  // 1 digit is just the head
            head = new Node(random.nextInt(10));    // random value from 0-9
        } else {
            /* initialize the head */
            head = new Node(random.nextInt(10));
            Node current = head;
            Node previous;

            /* iterates from end to SECOND digit */
            for(int i=1; i<n-1; i++) {
                current.next = new Node(random.nextInt(10));
                previous = current;
                current = current.next;
                current.prev = previous;
            }
            current.next = new Node(random.nextInt(9)+1);   // FIRST DIGIT, from 1-9 (to avoid leading 0)
            tail = current.next;
            tail.prev = current;
        }
    }
    
    public HugeInteger add(HugeInteger h) {
        
        HugeInteger result = new HugeInteger();
        
        if(!this.neg && h.neg) {
            result = this.subtract(h);      // special case for subtracion
        } else if(this.neg && !h.neg) {
            result = h.subtract(this);      // special case for subtracion
        } else {
            
            if(this.neg && h.neg)
                result.neg = true;  // if both are positive, result is positive
            else
                result.neg = false; // if both are negative, result is negative
            
            /* A + B = C */
            Node currentA = this.head;  // iterator node for legiblity
            Node currentB = h.head;
            int sum = 0;    // variable to store digit sum
            int carry = 0;  // variable to store digit carry
            
            /* initializes the head */
            sum = currentA.value + currentB.value + carry;  // add the sum
            carry = sum / 10;   // int division (ex. 12/10 = 1)
            result.head = new Node(sum % 10);   // mod operation (ex. 12%10 = 2)
            result.size++;      // increase size of result by 1
            
            Node currentC = result.head;    // iterator node for legibility
            currentA = currentA.next;       // iterate
            currentB = currentB.next;       // iterate
            Node previous;  // pointer to previous node to create prev links
            
            /* adds two numbers to result, A + B = C  */
            while(currentA != null && currentB != null) {       // adds up to length of either A or B
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
            while(currentA != null) {               // adds remainder of A
                sum = currentA.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentA = currentA.next;
                previous = currentC;
                currentC = currentC.next;
                currentC.prev = previous;
                result.size++;
            }
            while(currentB != null) {               // adds remainder of B
                sum = currentB.value + carry;
                carry = sum / 10;
                currentC.next = new Node(sum % 10);
                currentB = currentB.next;
                previous = currentC;
                currentC = currentC.next;
                currentC.prev = previous;
                result.size++;
            }
            if(carry > 0) {     // if there's still a carry, add one more node
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
