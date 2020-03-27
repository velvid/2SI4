
public class MaxHeap {
    
    private Integer[] array;    // stores values of heap with overhead
    private int capacity;       // total size of array
    private int size;           // number of elements in heap
    
    /* empty constructor of size of input  */
    public MaxHeap(int n) throws IllegalArgumentException {
        if(n<=0) throw new IllegalArgumentException(
                "Cannot construct heap of capacity 0 or less.");
        this.size = 0;                  // there are no elements in heap
        this.capacity = n;              // null array of length n
        this.array = new Integer[n];    // null array allocated
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
        this.size = input.length;               // assuming input is full
        this.capacity = input.length;           // heapsize is equal to input length
        this.array = new Integer[capacity];     // initialize array
        for(int i=0; i<size; i++)               // copy values
            array[i] = input[i];
        for(int i=size/2-1; i>=0; i--)          // heapify the copied values
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
        int node = root;            // temp index of the largest of the trio
        int left = 2*root + 1;      // index of left of parent node
        int right = 2*root + 2;     // index of right of parent node
        if(left < heapSize && array[left] > array[node])
            node = left;            // if left is greater than parent
        if(right < heapSize && array[right] > array[node])
            node = right;           // if right is greater than parent
        if(node != root) {
            swap(array, node, root);                // if largest value is not parent, swap
            heapifyDown(array, heapSize, node);     // recursively heapify switched child
        }
    }
    
    /* max-heapifies a parent node and its children */
    /* then recursively heapifies the newly swapped parent of new child */
    private static void heapifyUp(Integer[] array, int heapSize, int child) {
        int parent = (child-1)/2;                   // parent of child
        if(array[child] > array[parent]) {          // if child is larger than parent...
            swap(array, child, parent);                 // ...swap
            heapifyUp(array, heapSize, parent);         // ...recursively heapify switched parent
        }
    }
    
    /* doubles the length of the array */
    private void updateLength() {
        Integer[] newArray = new Integer[capacity*2];   // allocate new array double the length
        for(int i=0; i<capacity; i++)       // copy values to new array
            newArray[i] = array[i];
        this.array = newArray;              // assign new array
        this.capacity = newArray.length;    // assign new array length
    }
    
    /* inserts at the end of heap, then heapifies up */
    public void insert(int n) {
        size++;     // increment size of heap
        if(size > capacity) this.updateLength();    // if heap size is larger than array capacity
        array[size-1] = n;  // assign new value at end of heap
        heapifyUp(array, size, size-1);     // heapify up at inserted position
    }
    
    /* swaps first and end of heap, then heapifies down from first */
    private Integer deleteMax() {
        if(size == 0) return null;   
        int maxValue = array[0];    // get max value from start of heap
        swap(array, 0, size-1);     // swap first and last index of heap
        array[size-1] = null;       // value is null
        size--;     // decrease size of heap
        heapifyDown(array, size, 0);    // heapify the top value
        return maxValue;    // return max value
    }
    
    /* appends all elements of heap to output string, and returns it */
    @Override
    public String toString() {
        if(size == 0) return "EMPTY";   // return EMPTY if heap is empty
        String output = "";
        for(int i=0; i<size-1; i++)
            output = output + array[i] + ",";   // add elements to output string
        output = output + array[size-1];        // don't add comma to last element
        return output;
    }
    
    /* uses O(n) space to sort */
//    public static void heapSort(Integer[] arrayToSort) {
//        MaxHeap heap = new MaxHeap(arrayToSort);
//        for(int i=arrayToSort.length-1; i>=0; i--)
//            arrayToSort[i] = heap.deleteMax();
//    }
    
    /* infix sorting in ascending order */
    public static void heapsort(Integer[] array) {
        int n = array.length;                   // temp variable for size of array
        for(int i=n/2-1; i>=0; i--)             // build max heap
            heapifyDown(array, n, i);
        for(int i=n-1; i>=0; i--) {             // extract each element from heap
            swap(array, i, 0);                  // move current to end
            heapifyDown(array, i, 0);           // heapify the sub-heap
        }
        for(int i=0, j=n-1; i<j; i++, j--)      // descending order
            swap(array, i, j);
    }
    
    /* accessors for instance fields */
    public int getCapacity() { return capacity; }
    public int getSize() { return size; }
    
}
