package eg.edu.alexu.csd.filestructure.redblacktree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {
    private int size = 0;
    private INode<T,V> root ; // We only use it in the method getRoot() . In case you want to get the root of the redBlackTree use the method getRoot() instead of cally this field directly

    RedBlackTree() {
        this.size = 0;
    }

    @Override
    public INode<T, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = null ;
    }

    @Override
    public V search(T key) {
        INode<T, V> current = getRoot();
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            } else if (current.getKey().compareTo(key) < 0) {
                current = current.getRightChild();
            } else if (current.getKey().compareTo(key) > 0) {
                current = current.getLeftChild();
            } else {
                System.out.println("Something went wrong "); // for debugging purposes
            }
        }
        return null;
    }

    @Override
    public boolean contains(T key) {
        return (search(key) != null);
    }

    @Override
    public void insert(T key, V value) {
        if(isEmpty()) {
            root = new Node<T,V>(key , value) ;
        }
        size ++ ;
    }

    @Override
    public boolean delete(T key) {
        return false;
    }

    // Helping methods

}
