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
        neg = false;
    }
    
    /* method to remove leading zeros when constructing new linked lists */
    /* this method traverses FROM TAIL TO HEAD, since valid numbers are in form 0 (head) <-> 0 <-> 1 (tail) */
    private void removeLeadingZeros() {
        if(this.size > 1) {    // if size is 1, there is no trailing zeros
            /* iterate while there are leading 0's and until head is reached */
            Node current = this.tail;
            while(current.value == 0 && current.prev != null) {     // iterate until non-zero value reached
                current = current.prev;
                this.size--;    // decrement size
            }
            /* assign new tail */
            this.tail = current;
            this.tail.next = null;  // break the link
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
        if(!this.neg && h.neg) {            // this = 1000, h = -100, result = 900
            h.neg = false;
            result = this.subtract(h);      // special case for subtracion
            h.neg = true;
        } else if(this.neg && !h.neg) {     // this = -1000, h = 100, result = -900
            this.neg = false;
            result = h.subtract(this);      // special case for subtracion
            this.neg = true;
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

        int absComparison = this.compareAbs(h);     // value to determine which is the longer 
        if(absComparison == 0 && !this.neg && !h.neg) return new HugeInteger("0");
        
        HugeInteger result = new HugeInteger();
        if(!this.neg && h.neg) {            // this = 1000, h = -100, result = 1100
            h.neg = false;                  // to avoid flags in add()
            result = this.add(h);
            h.neg = true;
        } else if(this.neg && !h.neg) {     // this = -1000, h = 100, result = -1100
            h.neg = true;
            result = this.add(h);
            h.neg = false;
        } else if(this.neg && h.neg) {      // this = -1000, h = -100, result = abs(h) - this = -900
            this.neg = false;               // rearrange negative signs temporarily
            h.neg = false;
            result = h.subtract(this);      // h becomes "this", "this" becomes h
            this.neg = true;
            h.neg = true;
        } else if(absComparison < 0) {
            result = h.subtract(this);      // A-B = -(B-A), since |B|>|A|
            result.neg = !result.neg;
        } else {        // performs A-B, given |A|>|B|
            
            /* if the sizes are 1...just return a node with the subtraction */
            if(this.size == 1) return new HugeInteger(this.head.value - h.head.value);
            
            /* make a deep copy of A to the result (C) */
            result.size = this.size;
            Node currentA = this.head;                  // assign iterator node, A to "this"
            result.head = new Node(currentA.value);     // copy node from A to result
            Node currentC = result.head;                // assign C to result
            Node previous;                              // temporary node to create previous links
            while(currentA.next.next != null) {
                currentC.next = new Node(currentA.next.value);  // copy value
                previous = currentC;        // store previous node
                currentC = currentC.next;   // iterate to next node
                currentC.prev = previous;   // assign previous node
                currentA = currentA.next;   // iterate A
            }
            currentC.next = new Node(currentA.next.value);  // the final node, the tail
            result.tail = currentC.next;
            result.tail.prev = currentC;
            
            /* subtracts B from C (C-B), altering C (note: |C|>|B|) */
            Node currentB = h.head;     // assign iterator node, B to h
            currentC = result.head;     // reassign head for C
            while(currentB != null) {
                if(currentB.value > currentC.value && currentC.next.value == 0) {
                    Node tempC = currentC;
                    while(tempC.next != null && tempC.value == 0)
                        tempC = tempC.next;
                    tempC.value--;
                    while(tempC.prev != currentC) {
                        tempC = tempC.prev;
                        tempC.value = 9;
                    }
                    currentC.value = 10 + currentC.value - currentB.value;
                } else if(currentB.value > currentC.value) {
                    currentC.next.value--;
                    currentC.value = 10 + currentC.value - currentB.value;
                } else {
                    currentC.value = currentC.value - currentB.value;
                }
                currentB = currentB.next;
                currentC = currentC.next;
            }
        }
        
        result.removeLeadingZeros();
        return result;
    }
    
    public HugeInteger multiply(HugeInteger h) {
        if((this.size == 1 && this.head.value == 0) 
                || (h.size == 1 && h.head.value == 0)) return new HugeInteger("0");
        
        HugeInteger result = new HugeInteger();
        if(this.neg == h.neg) 
            result.neg = false;     // a negative times a negative is a positive
        else 
            result.neg = true;      // a negative times a positive is a negative
        
        Node currentA = this.head;
        Node currentB = h.head;
        Node currentC = result.head;
        
        while(currentA != null || currentB != null) {
            
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
    
    public int compareAbs(HugeInteger h) {
        /* simple cases if unequal sizes */
        if(this.size > h.size) return 1;
        if(this.size < h.size) return -1;
        
        Node currentA = this.tail;
        Node currentB = h.tail;
        while(currentA != null || currentB != null) {
            if(currentA.value > currentB.value) return 1;   // if 
            if(currentA.value < currentB.value) return -1;  // 
            currentA = currentA.prev;
            currentB = currentB.prev;
        }
        return 0;   // if all cases fail, then both are equal
    }
    
    public String toString() {
        String output = "";
        Node current = this.head;
        while(current != null) {
            output = String.valueOf(current.value) + output;
            current = current.next;
        }
        if(this.neg) output = "-" + output;
        return output;
    }
    
}
