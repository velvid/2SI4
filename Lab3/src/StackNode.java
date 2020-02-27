
public class StackNode {
    
    BSTNode item;
    StackNode down;
    
    public StackNode(BSTNode value, StackNode down) {
        this.item = value;
        this.down = down;
    }
}
