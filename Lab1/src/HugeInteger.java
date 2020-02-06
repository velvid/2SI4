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
            /* walk along list while there are leading 0's and until head is reached */
            Node current = this.tail;
            while(current.value == 0 && current.prev != null) {     // walk until non-zero value reached
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
        
        /* iterates from end to first digit */
        for(int i=val.length()-2; i>=(neg?1:0); i--) {
            tempChar = val.charAt(i) - '0';     // character value (converted from unicode)
         
            if(tempChar < 0 || tempChar > 9)    // if char is NOT an integer
                throw new IllegalArgumentException("Invalid input.");

            /* walk through LL and create links */
            current.next = new Node(tempChar);
            current.next.prev = current;
            current = current.next;
        }
        tail = current;     // assign tail
        
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
            /* iterates from end to SECOND digit */
            for(int i=1; i<n-1; i++) {
                current.next = new Node(random.nextInt(10));    // next link of new node; generate from 0-9
                current.next.prev = current;                    // assign previous link of new node
                current = current.next;
            }
            current.next = new Node(random.nextInt(9)+1);       // first digit, from 1-9 (to avoid leading 0)
            tail = current.next;    // assign tail
            tail.prev = current;
        }
    }
    
    /* returns new list, which is the sum of this and that */
    public HugeInteger add(HugeInteger that) {  
        HugeInteger result = new HugeInteger();
        if(!this.neg && that.neg) {             // this = 1000, h = -100, result = 900
            that.neg = false;                   // change neg to avoid flags in subtract()
            result = this.subtract(that);       // special case for subtracion
            that.neg = true;
        } else if(this.neg && !that.neg) {      // this = -1000, h = 100, result = -900
            this.neg = false;                   // change neg to avoid flags in subtract()
            result = that.subtract(this);       // special case for subtracion
            this.neg = true;
        } else {
            
            if(this.neg && that.neg)
                result.neg = true;  // if both are positive, result is positive
            else
                result.neg = false; // if both are negative, result is negative
            
            /* A + B = C */
            Node currentA = this.head;  // iterator node for legiblity
            Node currentB = that.head;
            int sum = 0;    // variable to store digit sum
            int carry = 0;  // variable to store digit carry
            
            /* initializes the head */
            sum = currentA.value + currentB.value + carry;  // add the sum
            carry = sum / 10;                               // int division (ex. 12/10 = 1)
            result.head = new Node(sum % 10);               // mod operation (ex. 12%10 = 2)
            result.size++;                                  // increase size of result by 1
            
            Node currentC = result.head;    // nodes for legibility
            currentA = currentA.next;
            currentB = currentB.next;
            
            /* adds two numbers to result, A + B = C  */
            while(currentA != null && currentB != null) {       // adds up to length of either A or B
                sum = currentA.value + currentB.value + carry;
                carry = sum / 10;
                
                currentC.next = new Node(sum % 10);     // create next link of new node
                currentC.next.prev = currentC;          // create previous link of new node
                result.size++;                          // increment size
                
                currentA = currentA.next;   // iterate all linked lists
                currentB = currentB.next;
                currentC = currentC.next;
            }
            
            /* add remaining of A to C  */
            while(currentA != null) {
                sum = currentA.value + carry;
                carry = sum / 10;
                
                currentC.next = new Node(sum % 10);
                currentC.next.prev = currentC;
                result.size++;
                
                currentA = currentA.next;
                currentC = currentC.next;
            }
            
            /* add remaining of B to C */
            while(currentB != null) {
                sum = currentB.value + carry;
                carry = sum / 10;
                
                currentC.next = new Node(sum % 10);
                currentC.next.prev = currentC;
                result.size++;
                
                currentB = currentB.next;
                currentC = currentC.next;
            }
            
            /* add a carry to the result, it it exists */
            if(carry > 0) {  
                currentC.next = new Node(carry);
                currentC.next.prev = currentC;
                result.size++;
                
                currentC = currentC.next;
            }
            
            result.tail = currentC;     // create the tail
        }
        
        return result;
    }
    
    /* returns new list, which is difference of this and that (this - that) */
    public HugeInteger subtract(HugeInteger that) {

        int absComparison = this.compareAbs(that);          // value to determine which is larger in magnitude 
        if(absComparison == 0 && (this.neg == that.neg))    // special case when both are equal in sign and magnitude
            return new HugeInteger("0");
        
        HugeInteger result = new HugeInteger();
        if(!this.neg && that.neg) {     // this = 1000, h = -100, result = 1100
            that.neg = false;           // to avoid flags in add()
            result = this.add(that);
            that.neg = true;
        } else if(this.neg && !that.neg) {      // this = -1000, h = 100, result = -1100
            that.neg = true;
            result = this.add(that);
            that.neg = false;
        } else if(this.neg && that.neg) {       // this = -1000, h = -100, result = abs(h) - this = -900
            this.neg = false;                   // rearrange negative signs temporarily
            that.neg = false;
            result = that.subtract(this);       // h becomes "this", "this" becomes h
            this.neg = true;
            that.neg = true;
        } else if(absComparison < 0) {
            result = that.subtract(this);       // A-B = -(B-A), since |B|>|A|
            result.neg = !result.neg;
        } else {    // performs A-B, given |A|>|B|
            
            /* if the sizes are 1...just return a node with the subtraction */
            if(this.size == 1) return new HugeInteger(this.head.value - that.head.value);
            
            /* make a deep copy of A to the result, C */
            /* reason for this is to modify the result without modifying A */
            result.size = this.size;
            Node currentA = this.head;                  // assign "this" to node A
            result.head = new Node(currentA.value);     // copy value of A to result head
            Node currentC = result.head;                // assign C to result head
            while(currentA.next.next != null) {
                currentC.next = new Node(currentA.next.value);  // copy value
                currentC.next.prev = currentC;                  // assign previous link
                
                currentC = currentC.next;
                currentA = currentA.next;
            }
            currentC.next = new Node(currentA.next.value);      // the final node, the tail
            result.tail = currentC.next;
            result.tail.prev = currentC;
            
            /* subtracts B from C (C-B), altering C (note: |C|>|B|) */
            Node currentB = that.head;  // assign iterator node, B to h
            currentC = result.head;     // reassign head for C
            while(currentB != null) {   // iterate to the end of B, the smaller list
                if(currentB.value > currentC.value && currentC.next.value == 0) {       // if borrow from a 0
                    Node tempC = currentC;              // temp node to walk only along 0's
                    while(tempC.next != null && tempC.value == 0) 
                        tempC = tempC.next;             // walks until non-zero value reached
                    tempC.value--;                      // borrow one from the non-zero value
                    while(tempC.prev != currentC) {     // until you reach the original node...
                        tempC = tempC.prev;                 // ...walk backwards along list
                        tempC.value = 9;                    // ...make the 0's into 9's
                    }
                    currentC.value = 10 + currentC.value - currentB.value;  // subtract original node with borrow
                } else if(currentB.value > currentC.value) {                            // borrow from non-0 value
                    currentC.next.value--;                                  // borrow one from the next digit
                    currentC.value = 10 + currentC.value - currentB.value;  // subtract original node with borrow
                } 
                else {                                                                  // no borrow needed
                    currentC.value = currentC.value - currentB.value;       // subtract normally
                }
                currentB = currentB.next;   // walk along list
                currentC = currentC.next;
            }
        }
        
        result.removeLeadingZeros();    // remove 0's because C-B yields them
        
        return result;
    }
    
    /* returns new list, which is product of this and that */
    public HugeInteger multiply(HugeInteger that) {
        
        if((this.size == 1 && this.head.value == 0)     // if you multiply by 0, return 0
                || (that.size == 1 && that.head.value == 0)) return new HugeInteger("0");
        
        HugeInteger result = new HugeInteger("0");      // result to add to
        HugeInteger toAdd = new HugeInteger();          // new linked list to store (B)*(digit of A)
        
        /* length of B will be larger or equal to A */
        Node currentA;
        Node currentB;
        if(this.size > that.size) {
            currentB = this.head;
            currentA = that.head;
        } else {
            currentB = that.head;
            currentA = this.head;
        }
        
        /* initialize the head of toAdd list */
        int value = currentA.value * currentB.value;    // variable for product
        int carry = value / 10;                         // variable for multiplication carry
        toAdd.head = new Node(value%10);                // new node with last digit of value, ignoring carry
        currentB = currentB.next;       // walk along larger list
        
        Node currentC = toAdd.head;     // iterator node for result list
        int numZeros = 0;               // counter for shift 0's for each iteration of toAdd sum
        
        while(currentA != null) {
            
            if(numZeros > 0) {                      // if there are zeros to add
                toAdd.head = new Node(0);           // initialize the head
                currentC = toAdd.head;
                for(int i=1; i<numZeros; i++) {     // shift remaining zeros
                    currentC.next = new Node(0);
                    currentC.next.prev = currentC;
                    currentC = currentC.next;
                }
            }
            
            /* walk along larger list, B */
            while(currentB != null) {
                value = currentA.value * currentB.value + carry;    // value to store product of digits + prior carry
                carry = value / 10;                                 // carry of new product
                
                currentC.next = new Node(value % 10);   // create next link with new value
                currentC.next.prev = currentC;          // create prev link of new node
                    
                currentC = currentC.next;   // walk along lists
                currentB = currentB.next;
            }
            
            /* add remaining carry to linked list */
            if(carry > 0) {
                currentC.next = new Node(carry);
                currentC.next.prev = currentC;
                carry = 0;
            }
            
            currentB = this.size > that.size ? this.head : that.head;   // reset B to original head
            currentA = currentA.next;                                   // walk along A
            numZeros++;                                 // increment amount of 0's to shift
            
            result = result.add(toAdd);                 // add toAdd to result
        }
        
        result.neg = this.neg != that.neg;              // result is negative if operands are EXCLUSIVELY OR negative

        return result;
    }
    
    /* returns 1 if this > that, returns -1 if this < that, returns 0 if this == that */
    public int compareTo(HugeInteger that) {
        /* simple cases if unequal sizes or opposite signs */
        if(this.neg && !that.neg) return -1;                                    // this = -10, that = 10 => this < that => -1
        if(!this.neg && that.neg) return 1;                                     // this = 10, that = -10 => this > that => 1
        if(this.neg && that.neg && this.size > that.size) return -1;            // this = -100, that = -10 => this < that => -1
        if(this.neg && that.neg && this.size < that.size) return 1;             // this = -10, that = -100 => this > that => 1
        if(!this.neg && !that.neg && this.size > that.size) return 1;           // this = 100, that = 10 => this > that => 1
        if(!this.neg && !that.neg && this.size < that.size) return -1;          // this = 10, that = 100 => this < that => -1
        
        /* block executes only if same sign and both are equal size */
        Node currentA = this.tail;
        Node currentB = that.tail;
        while(currentA != null || currentB != null) {
            if(currentA.value > currentB.value && !this.neg && !that.neg) return 1;    // this = 110, that = 100 => this > that => 1
            if(currentA.value < currentB.value && !this.neg && !that.neg) return -1;   // this = 100, that = 110 => this < that => -1
            if(currentA.value > currentB.value && this.neg && that.neg) return -1;     // this = -110, that = -100 => this < that => -1
            if(currentA.value < currentB.value && this.neg && that.neg) return 1;      // this = -100, that = -110 => this > that => 1
            currentA = currentA.prev;
            currentB = currentB.prev;
        }
        
        return 0;   // if all cases fail, then that means both are equal
    }
    
    /* returns 1 if |this| > |that|, returns -1 if |this| < |that|, returns 0 if |this| == |that| */
    public int compareAbs(HugeInteger that) {
        /* simple cases if unequal sizes */
        if(this.size > that.size) return 1;
        if(this.size < that.size) return -1;
        
        /* block only executes if both are equal size */
        Node currentA = this.tail;
        Node currentB = that.tail;
        while(currentA != null || currentB != null) {
            if(currentA.value > currentB.value) return 1;
            if(currentA.value < currentB.value) return -1;
            currentA = currentA.prev;
            currentB = currentB.prev;
        }
        return 0;   // if all cases fail, then both are equal
    }
    
    /* converts HugeInteger to a string */
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
