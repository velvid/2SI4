
public class Stack {
    
    private StackNode top;
    private int size = 0;
    
    /* empty constructor */
    public Stack() { }
    
    /* remove the top Type and return the Type object */
    public BSTNode pop() {
        if(top == null) return null;
        BSTNode output = top.item;
        top = top.down;
        size--;
        return output;
    }
    
    /* push a new Type object onto top of stack */
    public void push(BSTNode item) {
        top = new StackNode(item, top);
        size++;
    }
    
    /* getter for size */
    public int size() { return size; }
    
}
