
public class BSTSet {
    
    public BSTNode root;
    private int size;
    
    public BSTSet() {
        root = null;
        size = 0;
    }
    
    public BSTSet(int[] array) {
        // if array is empty, use null constructor
        if(array.length == 0) { new BSTSet(); return; }
        
        // if array is size 1, set root as node
        if(array.length == 1) { 
            root = new BSTNode(array[0]);
            size = 1;
            return;
        }
        
        // create deep copy of input array into temp1
        int temp1[] = new int[array.length];
        for(int i=0; i<array.length; i++)
            temp1[i] = array[i];
        
        // quicksort the copy
        (new QuickSort()).sort(temp1, 0, temp1.length-1);
        
        // get the new size of sorted array without duplicates
        int newSize = temp1.length;
        for(int i=0; i<array.length-1; i++)
            if(temp1[i] == temp1[i+1])
                newSize--;
        size = newSize;
        
        // create new sorted array without duplicates
        int temp2[] = new int[newSize];
        int j=0;
        for(int i=0; i<array.length; i++) {
            if(i+1 < array.length && temp1[i] == temp1[i+1])
                continue;
            temp2[j++] = temp1[i];
        }
        
        // convert the set into a binary tree
        root = sortedArrayToBST(temp2, 0, temp2.length-1);
    }
    
    public BSTNode sortedArrayToBST(int array[], int beg, int end) {
        if(beg > end) return null;                          // length somehow is equal or smaller to 0
        
        int mid = beg+(end-beg)/2;                          // root of each sub-branch is the middle of partition
        BSTNode node = new BSTNode(array[mid]);             
        node.left = sortedArrayToBST(array, beg, mid-1);    // create left sub-branch with same process
        node.right = sortedArrayToBST(array, mid+1, end);   // create right sub-branch with same process
        
        return node;
    }
    
    public boolean isIn(int v) {
        if(root == null) return false;                          // if tree is empty, return null
        return recursiveSearch(root, v) != null;      // checks if value matches recursive search (if not found, search is null)
    }
    
    public BSTNode recursiveSearch(BSTNode node, int value) {
        if(node == null) return null;
        if(node.value == value) return node;
        if(node.value > value) return recursiveSearch(node.left, value);
        else return recursiveSearch(node.right, value);
    }
    
    public void add(int v) {
        root = recursiveAdd(root, v);
    }
    
    private BSTNode recursiveAdd(BSTNode node, int value) {
        // if the tree is empty, append a new node
        if(node == null) {
            node = new BSTNode(value);
            size++;
            return node;
        }
        
        // keep recurring along the tree otherwise
        if(value < node.value) node.left = recursiveAdd(node.left, value);      // if value is smaller, add to left sub
        if(value > node.value) node.right = recursiveAdd(node.right, value);    // if value is larger, add to right sub
        return node;                                                            // if value is equal or end of recursion, return
    }
    
    public BSTNode remove(int v) {
        return recursiveRemove(root, v);
    }
    
    private BSTNode recursiveRemove(BSTNode node, int value) {
        if(node == null) return node;                               // if tree is empty, just return node
        
        // keep recurring along the tree otherwise
        if(value < node.value)                                      // if less, keep iterating
            node.left = recursiveRemove(node.left, value);
        else if(value > node.value)                                 // if larger, keep iterating
            node.right = recursiveRemove(node.right, value);
        else {                                                      // if equal, remove
            size--;                 // decrease size of BST
            if(node.left == null) return node.right;                // return right if left is null
            if(node.right == null) return node.left;                // return left is right is null
            node.value = minValue(node.right);                      // if node has two children, find the minimum of the RIGHT subtree 
            node.right = recursiveRemove(node.right, node.value);   // reorganise nodes from subtree
        }
        
        return node;    // return the node
    }
    
    public int minValue(BSTNode node) {
        int min = node.value;
        while(node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }
    
    public int maxValue(BSTNode node) {
        int max = node.value;
        while(node.right != null) {
            max = node.right.value;
            node = node.right;
        }
        return max;
    }
    
    public BSTSet union(BSTSet that) {
        DLL list = new DLL();                   // linked list to store all values
        recursiveAppend(this.root, list);       // appends all values of nodes in this to list
        recursiveAppend(that.root, list);       // appends all values of nodes in that to list
        return new BSTSet(list.toArray());      // convert list to an array, and use the constructor
    }
    
    public BSTSet intersection(BSTSet that) {
        DLL output = new DLL();
        DLL thisList = new DLL();
        DLL thatList = new DLL();
        recursiveAppend(this.root, thisList);
        recursiveAppend(that.root, thatList);
        
        // intersection of linked lists
        DLLNode currentA = thisList.head;
        DLLNode currentB = thatList.head;
        while(currentA != null && currentB != null) {
            if(currentA.value < currentB.value)
                currentA = currentA.next;
            else if(currentA.value > currentB.value)
                currentB = currentB.next;
            else {
                output.append(currentA.value);
                currentA = currentA.next;
                currentB = currentB.next;
            }
        }
        
        return new BSTSet(output.toArray());
    }
    
    public BSTSet difference(BSTSet that) {
        DLL output = new DLL();
        DLL toRemove = new DLL();
        recursiveAppend(this.root, output);
        recursiveAppend(that.root, toRemove);
        
        DLLNode current = toRemove.head;
        while(current != null) {
            output.removeValue(current.value);
            current = current.next;
        }
        
        return new BSTSet(output.toArray());
    }
    
    private void recursiveAppend(BSTNode node,  DLL list) {
        if(node == null) return;
        recursiveAppend(node.left, list);
        list.append(node.value);
        recursiveAppend(node.right, list);
    }
    
    public int size() { return size; }
    
    public int height() { 
        return 0;
    }
    
    public void printBSTSet() {
        if(root == null)
            System.out.println("The set is empty");
        else {
            System.out.print("The set elements are: ");
            printInOrder(root);
            System.out.print("\n");
        }
    }
    
    public void printBSTSet(BSTNode node) {
        if(node != null) {
            System.out.format("The child elements of %d are: ", node.value);
            printInOrder(node);
            System.out.print("\n");
        }
    }
    
    public void printPostOrder(BSTNode node) {
        if(node == null) return;
        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(node.value + " ");
    }
    
    public void printInOrder(BSTNode node) {
        if(node == null) return;
        printInOrder(node.left);
        System.out.print(node.value + " ");
        printInOrder(node.right);
    }
    
    public void printPreOrder(BSTNode node) {
        if(node == null) return;
        System.out.print(node.value + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);
    }
    
    public void printNonRec(BSTNode node) {
        if(node == null) return;
        DLL list = new DLL();
        BSTNode current = this.root;
        while(current != null || list.length > 0) {
            while(current.left != null) {
                list.append(current.value);
                current = current.left;
            }
            System.out.print(current.value + " ");
            current = current.right;
        }
    }
    
}
