
public class MaxHeap {
    
    private Integer[] array;
    private int capacity;
    private int size;
    
    /* empty constructor of size of input  */
    public MaxHeap(int n) throws IllegalArgumentException {
        if(n<=0) throw new IllegalArgumentException(
                "Cannot construct heap of capacity 0 or less.");
        this.size = 0;
        this.capacity = n;
        this.array = new Integer[n];
    }
    
//    /* O(n*log(n)) constructor from input array */
//    public MaxHeap(Integer[] input) {
//        this.size = 0;
//        this.capacity = input.length;
//        this.array = new Integer[capacity];
//        for(int num : input)
//            this.insert(num);
//    }
    
    /* O(n) constructor from input array */
    public MaxHeap(Integer[] input) {
        this.size = input.length;
        this.capacity = input.length;
        this.array = new Integer[capacity];
        for(int i=0; i<capacity; i++)
            array[i] = input[i];
        for(int i=size/2-1; i>=0; i--) 
            heapifyDown(array, size, i);
    }
    
    /* swaps array element values at given indices */
    private static void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /* max-heapifies a parent node and its children */
    /* then recursively heapifies the newly swapped child of new parent */
    private static void heapifyDown(Integer[] array, int heapSize, int root) {
        int node = root;
        int left = 2*root + 1;
        int right = 2*root + 2;
        if(left < heapSize && array[left] > array[node])        // if left is greater than parent
            node = left;
        if(right < heapSize && array[right] > array[node])      // if right is greater than parent or left
            node = right;
        if(node != root) {
            swap(array, node, root);
            heapifyDown(array, heapSize, node);
        }
    }
    
    /* max-heapifies a parent node and its children */
    /* then recursively heapifies the newly swapped parent of new child */
    private static void heapifyUp(Integer[] array, int heapSize, int child) {
        int parent = (child-1)/2;
        if(array[child] > array[parent]) {
            swap(array, child, parent);
            heapifyUp(array, heapSize, parent);
        }
    }
    
    /* doubles the length of the array */
    private void updateLength() {
        Integer[] newArray = new Integer[capacity*2];
        for(int i=0; i<capacity; i++)
            newArray[i] = array[i];
        this.array = newArray;
        this.capacity = newArray.length;
    }
    
    /* inserts at the end of heap, then heapifies up */
    public void insert(int n) {
        size++;
        if(size > capacity) this.updateLength();
        array[size-1] = n;
        heapifyUp(array, size, size-1);
    }
    
    /* swaps first and end of heap, then heapifies down from first */
    public Integer deleteMax() {
        if(size == 0) return null;
        int maxValue = array[0];
        swap(array, 0, size-1);
        array[size-1] = null;
        size--;
        heapifyDown(array, size, 0);
        return maxValue;
    }
    
    /* appends all elements of heap to output string, and returns it */
    @Override
    public String toString() {
        if(size == 0) return "EMPTY";
        String output = "";
        for(int i=0; i<size-1; i++)
            output = output + array[i] + " ";
        output = output + array[size-1];
        return output;
    }
    
    /* uses O(n) space to sort */
//    public static void heapSort(Integer[] arrayToSort) {
//        MaxHeap heap = new MaxHeap(arrayToSort);
//        for(int i=arrayToSort.length-1; i>=0; i--)
//            arrayToSort[i] = heap.deleteMax();
//    }
    
    /* infix sorting in ascending order */
    public static void heapSort(Integer[] array) {
        int n = array.length; 
        for(int i=n/2-1; i>=0; i--) 
            heapifyDown(array, n, i);
        for(int i=n-1; i>=0; i--) { 
            swap(array, i, 0);
            heapifyDown(array, i, 0); 
        }
        for(int i=0, j=n-1; i<j; i++, j--)
            swap(array, i, j);
    }
    
    /* accessors for instance fields */
    public int getCapacity() { return capacity; }
    public int getSize() { return size; }
    
}
