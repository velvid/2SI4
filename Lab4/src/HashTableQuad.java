
public class HashTableQuad {
    
    private int[] table;
    private boolean[] filled;
    private int length;
    private int maxNum;
    private double load;
    private int count = 0;
    private int totalProbes = 0;
    
    public HashTableQuad(int maxNum, double load) {
        this.maxNum = maxNum;
        this.load = load;
        this.length = findMinPrimeAbove((int)((double)maxNum/load));
        this.table = new int[length];
        this.filled = new boolean[length];
    }
    
    public int getLength()  { return length; }
    public int getMaxNum()  { return maxNum; }
    public double getLoad() { return load; }
    public int getCount()   { return count; }
    public int getTotalProbes() { return totalProbes; }
    
    public boolean isPrime(int n) {
        if(n == 1) return false;
        if(n%2 == 0) return false;
        for(int i=3; i*i<n; i+=2)
            if(n%i == 0) return false;
        return true;
    }
    
    public int findMinPrimeAbove(int n) {
        for(int num=n; true; num++)
            if(isPrime(num)) return num;
    }
    
    public void insert(int n) {
        int key = n % length;
        int oldKey = key;
        
        int i=1, probes = 1;
        while(filled[key]) {
            if(table[key] == n) { return; }
            key = (oldKey + i*i) % length;
            i++;
            probes++;
        }
        
        table[key] = n;
        filled[key] = true;
        count++;
        totalProbes += probes;
        
        if(count >= load*length) this.rehash();
    }
    
    public void remove(int n) {
        int key = n % length;
        int oldKey = key;
        
        int i=1;
        while(filled[key]) {
            if(table[key] == n) {
                filled[key] = false;
                count--;
                return;
            }
            key = (oldKey + i*i) % length;
            i++;
        }
    }    
    
    public boolean isIn(int n) {
        int key = n % length;
        int oldKey = key;
        
        int i=1;
        while(filled[key]) {
            if(table[key] == n) return true;
            if(i == length) return false;
            key = (oldKey + i*i) % length;
            i++;
        }
        return false;
    }
    
    private void rehash() {
        int newLength = findMinPrimeAbove(2*length);
        int[] newTable = new int[newLength];
        boolean[] newFilled = new boolean[newLength];
        
        for(int i=0; i<length; i++) {
            if(filled[i]) {
                int key = table[i] % newLength;
                int oldKey = key;
                
                int j=1;
                while(newFilled[key]) {
                    key = (oldKey + j*j) % newLength;
                    j++;
                }
                
                newTable[key] = table[i];
                newFilled[key] = true;
            }
        }
        
        length = newLength;
        table = newTable;
        filled = newFilled;
        maxNum = (int)((double)(load)*(double)(newLength));
    }
    
    public void printKeys() {
        for(int i=0; i<length; i++)
            if(filled[i])
                System.out.println(table[i]);
    }
    
    public void printKeysAndIndexes() {
        for(int i=0; i<length; i++)
            if(filled[i]) System.out.println(i + "\t" + table[i]);
            else System.out.println(i + "\tnull");
    }
    
}
