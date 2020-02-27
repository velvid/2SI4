
public class DLL {
    
    DLLNode head;
    DLLNode tail;
    int length;
    
    /* null constructor */
    public DLL() {
        this.head = null;
        this.tail = this.head;
        this.length = 0;
    }

    /* construct from array */
    public DLL(int[] array) {
        this.head = new DLLNode(array[0]);
        DLLNode current = this.head;
        for(int i=1; i<array.length; i++) {         // iterate across array
            current.next = new DLLNode(array[i]);      // create next link of new node
            current.next.prev = current;            // create previous link of new node
            current = current.next;
        }
        this.tail = current;
        this.length = array.length;
    }
    
    /* adds new node to end of list, becoming new tail */
    public void append(int x) {
        if(this.head == null) {
            this.head = new DLLNode(x);
            this.tail = this.head;
        } else {
            tail.next = new DLLNode(x);
            tail.next.prev = tail;
            tail = tail.next;
        }
        this.length++;
    }
    
    /* adds new node to start of list, becoming new head*/
    public void prepend(int x) {
        if(this.head == null) {
            this.head = new DLLNode(x);
            this.tail = this.head;
        } else {
            head.prev = new DLLNode(x);
            head.prev.next = head;
            head = head.prev;
        }
        this.length++;
    }
    
    /* insert new node at specific position (index starts at 0) */
    public void insert(int x, int pos) {
        if(pos == 0) { prepend(x); return; }
        if(pos == this.length) { append(x); return; }
        
        DLLNode current = this.head.next;
        for(int i=1; i<pos; i++)
            current = current.next;
    
        DLLNode dummy = new DLLNode(x);
        dummy.next = current;
        dummy.prev = current.prev;
        
        current.prev.next = dummy;
        current.prev = dummy;
        
        this.length++;
    }
    
    /* removes node from end of list */
    public DLLNode popTail() {
        DLLNode temp = tail;
        tail = tail.prev;
        tail.next = null;
        this.length--;
        return temp;
    }
    
    /* removes node from start of list */
    public DLLNode popHead() {
        DLLNode temp = head;
        head = head.next;
        head.prev = null;
        this.length--;
        return tail;
    }
    
    /* deletes node at specified position (index starting at 0) */
    public void removeAt(int pos) {
        if(pos == 0) { popHead(); return; }
        if(pos == this.length - 1) { popTail(); return; }
        
        DLLNode current = this.head.next;
        for(int i=1; i<pos; i++)
            current = current.next;
        
        current.next.prev = current.prev;
        current.prev.next = current.next;
        
        this.length--;
    }
    
    /* removes the FIRST VALUE FOUND of input parameter value */
    /* if value isn't found, it does nothing */
    public void removeValue(int value) {
        if(head.value == value) { popHead(); return; }
        if(tail.value == value) { popTail(); return; }
        
        DLLNode current = this.head.next;
        while(current != null) {
            if(current.value == value) {
                
                current.next.prev = current.prev;
                current.prev.next = current.next;
                
                this.length--;
                
                return;
            }
        }
    }
    
    /* getter for length of linked list */
    public int length() { return this.length; }
    
    /* gets value at specified position (index starting at 0) */
    public int getValue(int pos) {
        if(pos == 0) { return this.head.value; }
        if(pos == this.length - 1) { return this.tail.value; }
        
        DLLNode current = this.head.next;
        for(int i=1; i<pos; i++)
            current = current.next;
        return current.value;
    }
    
    /* checks if linked list contains value x */
    public boolean contains(int x) {
        DLLNode current = this.head;
        while(current != null) {
            if(current.value == x)
                return true;
            current = current.next;
        }
        return false;
    }
    
    /* returns suitable string output format for linked list */
    @Override
    public String toString() {
        if(this.head == null) { return ""; }
        
        String output = "(H) ";
        DLLNode current = this.head;
        while(current.next != null) {
            output += current.value + "⇌";
            current = current.next;
        }
        output += current.value + " (T)";
        return output;
    }
    
    /* returns deep copy of *this* LinkedList */
    public DLL copy() {
        DLL copy = new DLL();
        
        copy.head = new DLLNode(this.head.value);
        copy.tail = copy.head;
        copy.length++;
        
        DLLNode current = this.head.next;
        while(current != null) {
            copy.append(current.value);
            current = current.next;
        }
        
        return copy;
    }
    
    /* checks if linked lists are equal by size and value */
    public boolean equals(DLL that) {
        if(this.length != that.length) { return false; }
        
        DLLNode currentA = this.head;
        DLLNode currentB = that.head;
        while(currentA != null) {
            if(currentA.value != currentB.value)
                return false;
            currentA = currentA.next;
            currentB = currentB.next;
        }
        
        return true;
    }
    
    /* returns array containing values of this linked list */
    public int[] toArray() {
        if(this.head == null) return new int[0];
        
        int[] output = new int[this.length];
        
        DLLNode current = this.head;
        for(int i=0; i<output.length; i++) {
            output[i] = current.value;
            current = current.next;
        }
        
        return output;
    }
    
}