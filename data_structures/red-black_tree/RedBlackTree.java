public class RedBlackTree<E extends Comparable<E>> {
    private TreeNode<E> root;

    public void insert(E data) {
       root = insert(data, root);
       // root always black
       root.color = TreeNode.TreeColor.BLACK;
    }

    private TreeNode<E> insert(E data, TreeNode<E> node) {
        if (node == null) {
            // leaf nodes always RED as they have two black (null) children 
            return new TreeNode<E>(data, TreeNode.TreeColor.RED);
        } else {
            int comparison = data.compareTo(node.data);
            if (comparison < 0) {
                node.left = insert(data, node.left);
                node.left.parent = node;
            } else if (comparison > 0) {
                node.right = insert(data, node.right);
                node.right.parent = node;
            }
            if (!node.isBlack()) { // its red
                if (!node.left.isBlack()) {
                    // red node with red child illegal
                }
                if (!node.right.isBlack()) {
                
                }
            }
            setColor(node);
            return node;
        }
    }

    public String toString() {
        StringBuilder s = toString(root, new StringBuilder());
        s.deleteCharAt(0);
        s.setCharAt(0, '[');
        return s.append("]").toString();
    }

    private StringBuilder toString(TreeNode<E> node, StringBuilder s) {
        if (node.left != null) {
            s = toString(node.left, s);
        }
        s.append(", ").append(node.data);
        if (node.right != null) {
            s = toString(node.right, s);
        }
        return s;
    }
    
    private void setColor(TreeNode<E> node) {
        if (node != null) {
            boolean leftBlack = node.left == null || node.left.isBlack();
            boolean rightBlack = node.right == null || node.right.isBlack();
            node.color = (leftBlack && rightBlack)? TreeNode.TreeColor.RED : TreeNode.TreeColor.BLACK;
        }
    }

    private TreeNode<E> leftRotate(TreeNode<E> node) {
        if (node.right == null) {
            throw new IllegalStateException("right child node is null");
        }
        TreeNode<E> other = node.right;
        node.right = other.left;
        if (other.left != null) {
            other.left.parent = node;
        }
        other.parent = node.parent;
        other.left = node;
        node.parent = other;
        return other;
    }

    private TreeNode<E> rightRotate(TreeNode<E> node) {
        if (node.left == null) {
            throw new IllegalStateException("right child node is null");
        }
        TreeNode<E> other = node.left;
        node.left = other.right;
        if (other.right != null) {
            other.right.parent = node;
        }
        other.parent = node.parent;
        other.right = node;
        node.parent = other;
        return other;
    }

    private static final class TreeNode<E> {
        enum TreeColor {BLACK, RED}
        E data;
        TreeColor color;
        TreeNode<E> parent;
        TreeNode<E> left;
        TreeNode<E> right;

        TreeNode(E data) {
            this(data, TreeColor.BLACK);
        }

        TreeNode(E data, TreeColor color) {
            this(data, color, null);
        }

        TreeNode(E data, TreeColor color, TreeNode<E> parent) {
            this(data, color, parent, null, null);
        }

        TreeNode(E data, TreeColor color, TreeNode<E> parent, TreeNode<E> left, TreeNode<E> right) {
            this.data = data;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public boolean isBlack() {
            return color == TreeColor.BLACK;
        }
    }
}
