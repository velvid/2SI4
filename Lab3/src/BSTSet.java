
public class BSTSet {
    
    private BSTNode root;
    
    public BSTSet() {
        root = null;
    }
    
    /* constructor from (assumed) unsorted array with duplicates */
    public BSTSet(int[] array) {
        // if array is empty, use null constructor
        if(array.length == 0) { new BSTSet(); return; }
        
        // if array is size 1, set root as node
        if(array.length == 1) { 
            root = new BSTNode(array[0]);
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
    
    /* recursively partitions the sorted  array and converts to BST */
    public BSTNode sortedArrayToBST(int array[], int beg, int end) {
        if(beg > end) return null;                          // length somehow is equal or smaller to 0
        
        int mid = beg+(end-beg)/2;                          // root of each sub-branch is the middle of partition
        BSTNode node = new BSTNode(array[mid]);             
        node.left = sortedArrayToBST(array, beg, mid-1);    // create left sub-branch with same process
        node.right = sortedArrayToBST(array, mid+1, end);   // create right sub-branch with same process
        
        return node;
    }
    
    /* getter for root */
    public BSTNode getRoot() { return root; }
    
    /* searches if value is in the BST (true if is in, else false) */
    public boolean isIn(int v) {
        if(root == null) return false;                          // if tree is empty, return null
        return recursiveSearch(root, v) != null;                // checks if value matches recursive search (if not found, search is null)
    }
    
    /* returns the when value is found, else returns null */
    public BSTNode recursiveSearch(BSTNode node, int value) {
        if(node == null) return null;           // if null is reached (when value is not in BST)
        if(node.value == value) return node;    // return node if value found
        if(node.value > value) return recursiveSearch(node.left, value);    // traverse left if value is smaller
        else return recursiveSearch(node.right, value);                     // traverse right if value is larger
    }
    
    /* appends node to BST while keeping it sorted */
    public void add(int v) {
        root = recursiveAdd(root, v);   // calls helper function
    }
    
    /* recursively searches the tree until suitable node to append to is found */
    private BSTNode recursiveAdd(BSTNode node, int value) {
        // if the tree is empty, append a new node OR if end of BST is reached
        if(node == null) {
            node = new BSTNode(value);
            return node;
        }
        // keep recurring along the tree otherwise
        if(value < node.value) node.left = recursiveAdd(node.left, value);      // if value is smaller, recur comparison for left
        if(value > node.value) node.right = recursiveAdd(node.right, value);    // if value is larger, recur comparison for right
        return node;                                                            // if value is equal or end of recursion, return
    }
    
    /* removes the value in BST if it is in it*/
    public boolean remove(int v) {
        boolean temp = this.isIn(v);            // first searches if it is in
        if(temp) recursiveRemove(root, v);      // if in, remove it
        return temp;    // return bool value
    }
    
    private BSTNode recursiveRemove(BSTNode node, int value) {
        if(node == null) return null;                               // if tree is empty, just return node
        
        // keep recurring along the tree otherwise
        if(value < node.value)                                      // if less, keep iterating
            node.left = recursiveRemove(node.left, value);
        else if(value > node.value)                                 // if larger, keep iterating
            node.right = recursiveRemove(node.right, value);
        else {                                                      // if equal, remove
            if(node.left == null) return node.right;                // return right if left is null
            if(node.right == null) return node.left;                // return left is right is null
            node.value = minValue(node.right);                      // if node has two children, find the minimum of the RIGHT subtree 
            node.right = recursiveRemove(node.right, node.value);   // reorganise nodes from subtree
        }
        
        return node;    // return the node
    }
    
    /* traverses along the left of the tree to find min value */
    public int minValue(BSTNode node) {
        int min = node.value;
        while(node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }
    
    /* traverses along the right of the tree to find max value */
    public int maxValue(BSTNode node) {
        int max = node.value;
        while(node.right != null) {
            max = node.right.value;
            node = node.right;
        }
        return max;
    }
    
    /* union of two BST (returns merge of them without duplicates */
    public BSTSet union(BSTSet that) {
        DLL list = new DLL();                   // linked list to store all values
        recursiveAppend(this.root, list);       // appends all values of nodes in this to list
        recursiveAppend(that.root, list);       // appends all values of nodes in that to list
        return new BSTSet(list.toArray());      // convert list to an array, and use the constructor
    }
    
    /* intersection of two BST (returns common values) */
    public BSTSet intersection(BSTSet that) {
        DLL output = new DLL();     // to store intersection
        DLL thisList = new DLL();   // to store this list
        DLL thatList = new DLL();   // to store that list
        recursiveAppend(this.root, thisList);   // appends all elements of this BST to list
        recursiveAppend(that.root, thatList);   // appends all elements of that BST to list
        
        // intersection of linked lists (lists are sorted)
        DLLNode currentA = thisList.head;
        DLLNode currentB = thatList.head;
        while(currentA != null && currentB != null) {
            if(currentA.value < currentB.value)         // if A is less than B, iterate A
                currentA = currentA.next;
            else if(currentA.value > currentB.value)    // if B is less than A, iterate B
                currentB = currentB.next;
            else {                                      // if A == B, append common value to output list
                output.append(currentA.value);
                currentA = currentA.next;   // iterate A
                currentB = currentB.next;   // iterate B
            }
        }
        
        return new BSTSet(output.toArray());    // convert list to an array, and use the constructor
    }
    
    /* difference of two BST (returns values in this that are NOT in that) */
    public BSTSet difference(BSTSet that) {
        DLL output = new DLL();     // list to store output
        DLL toRemove = new DLL();   // list to store values to remove from output
        recursiveAppend(this.root, output);     // converts this to linked list
        recursiveAppend(that.root, toRemove);   // converts that to linked list
        
        if(output.equals(toRemove)) return(new BSTSet()); // shorcut
        
        DLLNode current = toRemove.head;        // temp node to iterate
        while(current != null) {
            output.removeValue(current.value);  // removes values in toRemove from output
            current = current.next;
        }
        
        return new BSTSet(output.toArray());    // convert list to an array, and use the constructor
    }
    
    /* helper function to append all elements in BST to a linked list in post order */
    private void recursiveAppend(BSTNode node,  DLL list) {
        if(node == null) return;
        recursiveAppend(node.left, list);
        list.append(node.value);
        recursiveAppend(node.right, list);
    }
    
    /* returns the size of the BST */
    public int size() {
        return recursiveSize(root);
    }
    
    /* helper function for size() */
    /* recursively traverses through list and adds to counter */
    private int recursiveSize(BSTNode node) {
        if(node == null) return 0;          // return 0 when null node is reached
        return 1 + recursiveSize(node.left) + recursiveSize(node.right);
    }
    
    /* returns the height of the BST */
    public int height() { 
        return recursiveHeight(root) - 1;   // -1 to ccount for height of edges, not nodes
    }
    
    /* helper function to return height */
    private int recursiveHeight(BSTNode node) {
        if(node == null) return 0;                  // when null node is reached, return 0
        int left = recursiveHeight(node.left);
        int right = recursiveHeight(node.right);
        return 1 + (left > right ? left : right);   // return the max of left or right
    }
    
    /* prints the BST */
    public void printBSTSet() {
        if(root == null)
            System.out.println("The set is empty");
        else {
            System.out.print("The set elements are: ");
            printInOrder(root);     // calls helper function
            System.out.print("\n");
        }
    }
    
    /* prints the sub-tree at specified node */
    public void printBSTSet(BSTNode node) {
        if(node != null) {
            System.out.format("The child elements of %d are: ", node.value);
            printInOrder(node);
            System.out.print("\n");
        }
    }
    
    /* recursively prints post order */
    public void printPostOrder(BSTNode node) {
        if(node == null) return;
        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(node.value + " ");
    }
    
    /* recursively prints in order */
    public void printInOrder(BSTNode node) {
        if(node == null) return;
        printInOrder(node.left);
        System.out.print(node.value + " ");
        printInOrder(node.right);
    }
    
    /* recursively prints pre order */
    public void printPreOrder(BSTNode node) {
        if(node == null) return;
        System.out.print(node.value + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);
    }
    
    /* prints in order WITHOUT recursion, but with stack */
    public void printNonRec() {
        
        // if tree is empty, the set is empty
        if(root == null) { 
            System.out.println("(non-rec) The set is empty.");
            return;     // exit the method
        }
        
        System.out.format("(non-rec) The set elements are: ");
        Stack list = new Stack();   // impelements a stack
        BSTNode current = root;     // iterator node for BST
        while(current != null || list.size() > 0) {     // while the stack has elements or if current node exists
            while(current != null) {
                list.push(current);         // pushes all the left-most nodes onto stack
                current = current.left;     // iterate along left branch
            }
            current = (BSTNode) list.pop();         // go "up" to parrent node
            System.out.print(current.value + " ");  // print the value
            current = current.right;                // go to right node of parent
        }
        
        System.out.println();
    }
  
    public void printNonRecNoStack() {
        if(root == null) {
            System.out.println("(non-rec, no-stack) The set is empty.");
            return;
        }
        
        System.out.format("(non-rec, no-stack) The set elements (non-rec) are: ");
        
        BSTNode prev, curr = root;
        while(curr != null) {
            if(curr.left == null) {
                System.out.print(curr.value + " ");
                curr = curr.right;
            } else {
                prev = curr.left;
                while(prev.right != null && prev.right != curr)
                    prev = prev.right;
                if(prev.right == null) {
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    prev.right = null;
                    System.out.print(curr.value + " ");
                    curr = curr.right;
                }
            }
        }
        
        System.out.println();
    }
    
}
