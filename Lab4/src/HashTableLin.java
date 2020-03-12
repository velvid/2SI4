
public class HashTableLin {
    
    private int[] table;            // to store hash values
    private boolean[] filled;       // array to determine if table is filled
    private int length;             // size of the hash table
    private int maxNum;             // maximum occupied cells allowed
    private double load;            // max occupied per size allowed
    private int count = 0;          // count of occupied/filled cells
    private int totalProbes = 0;    // total probes when inserting
    
    /* constructor */
    public HashTableLin(int maxNum, double load) {
        this.maxNum = maxNum;
        this.load = load;
        this.length = findMinPrimeAbove((int)((double)maxNum/load));    // length has to be prime
        this.table = new int[length];       // all initialized to zero
        this.filled = new boolean[length];  // all initialized to false
    }
    
    /* getters for field variables */
    public int getLength()  { return length; }
    public int getMaxNum()  { return maxNum; }
    public double getLoad() { return load; }
    public int getCount()   { return count; }
    public int getTotalProbes() { return totalProbes; }
    
    /* function to check if ia number is prime */
    public boolean isPrime(int n) {
        if(n == 1) return false;        // 1 is not prime
        if(n%2 == 0) return false;      // if divisible by 2, not prime
        for(int i=3; i*i<n; i+=2)       // increment up by 2 and until sqrt(n)
            if(n%i == 0) return false;  // if divisibly by any number from 3 to n-1, not prime
        return true;    // if all cases pass, it is prime
    }
    
    /* finds minimum prime greater than given integer */
    public int findMinPrimeAbove(int n) {
        for(int num=n; true; num++)
            if(isPrime(num)) return num;
    }
    
    /* inserts a key into the hash table */
    public void insert(int n) {
        int key = n % length;   // takes mod of key
        int oldKey = key;       // stores the initial key
        
        int i=1, probes = 1;    // counters for probe number and total probes
        while(filled[key]) {    // if collision...
            if(table[key] == n) { return; } // if value is in table, do nothing
            key = (oldKey + i) % length;    // linearly probe to new index
            i++;
            probes++;
        }
        
        // this block only executes once an empty cell is reached 
        table[key] = n;         // assign new value to key
        filled[key] = true;     // cell is now filled
        count++;                // increment filled counter
        totalProbes += probes;  // add to total probes
        
        // check if the table needs to be rehashed
        if(count > load*length) this.rehash();
    }
    
    /* removes value from table */
    public void remove(int n) {
        int key = n % length;
        int oldKey = key;
        
        // probes until value is found
        int i=1;
        while(filled[key]) {
            if(table[key] == n) {
                filled[key] = false;
                count--;
                return;
            }
            key = (oldKey + i) % length;
            i++;
            if(i == length) return;     // if for some reason table is full, return
        }
    }
    
    /* finds if value exists in table */
    public boolean isIn(int n) {
        int key = n % length;
        int oldKey = key;
        
        // probes until value is found
        int i=1;
        while(filled[key]) {
            if(table[key] == n) return true;    // if found, return true
            if(i == length) return false;       // if for some reason table is full
            key = (oldKey + i) % length;
            i++;
        }
        return false;   // if empty cell found, return false
    }
    
    /* rehashes 'this' hashtable when load tolerance is exceeded */
    private void rehash() {
        int newLength = findMinPrimeAbove(2*length);    // new prime length at least 2 times larger than old
        int[] newTable = new int[newLength];            // array for new table
        boolean[] newFilled = new boolean[newLength];
        
        // iterates through old table and hashes each value into new table
        for(int i=0; i<length; i++) {
            if(filled[i]) {
                int key = table[i] % newLength;     // hashes to new table
                int oldKey = key;                   // stores initial key
                int j=1;                            // offset for linear probe
                while(newFilled[key]) {         // while there are collisions...
                    key = (oldKey + j) % newLength; // ...linearly probe
                    j++;
                }
                newTable[key] = table[i];   // insert to new table
                newFilled[key] = true;      // set cell to full
            }
        }
        
        // update all field variables for 'this'
        length = newLength;
        table = newTable;
        filled = newFilled; 
        double temp = ((double)(load)*(double)(newLength));
        maxNum = (double)(int)temp == temp ? (int)temp : (int)(temp);
    }
    
    /* iterates through all elements in table and prints values */
    public void printKeys() {
        for(int i=0; i<length; i++)
            if(filled[i])
                System.out.println(table[i]);
    }
    
    /* iterates through all elements in table and prints indexes and values */
    public void printKeysAndIndexes() {
        System.out.println("Index\tKey");
        for(int i=0; i<length; i++)
            if(filled[i]) System.out.println(i + "\t" + table[i]);
            else System.out.println(i + "\t");  // if empty
    }
    
}
