public class TreeNode<E> {
    E data;
    TreeNode<E> left;
    TreeNode<E> right;
    
    public TreeNode(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
