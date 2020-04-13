package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.*;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {
    IRedBlackTree<T, V> redBlackTree;

    TreeMap() {
        redBlackTree = new RedBlackTree<>();
    }

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if (key == null) Exception.throwException();
        INode<T, V> cur = null;
        INode<T, V> nxt = redBlackTree.getRoot();
        while (nxt != null && !nxt.isNull()) {
            if (nxt.getKey().compareTo(key) < 0) {
                nxt = nxt.getRightChild();
            } else if (nxt.getKey().compareTo(key) >= 0) {
                cur = nxt;
                nxt = nxt.getLeftChild();
            }

        }
        return cur == null ? null : new AbstractMap.SimpleEntry<>(cur.getKey(), cur.getValue());


    }

    @Override
    public T ceilingKey(T key) {
        return ceilingEntry(key).getKey();
    }

    @Override
    public void clear() {
        redBlackTree.clear();
    }

    @Override
    public boolean containsKey(T key) {
        return redBlackTree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        if (value == null) Exception.throwException();
        return containsValue(redBlackTree.getRoot(), value);
    }

    private boolean containsValue(INode node, V value) {
        if (node == null || node.isNull()) return false;
        else if (node.getValue().equals(value)) return true;
        else return containsValue(node.getRightChild(), value) || containsValue(node.getLeftChild(), value);
    }

    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        Set<Map.Entry<T, V>> set = new LinkedHashSet<>();
        inorderStore(redBlackTree.getRoot(), set);
        return set;
    }

    private void inorderStore(INode<T, V> node, Set<Map.Entry<T, V>> set) {
        if (node == null || node.isNull()) return;
        inorderStore(node.getLeftChild(), set);
        set.add(new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue()));
        inorderStore(node.getRightChild(), set);
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        INode<T, V> node = redBlackTree.getRoot();
        while (node != null && !node.isNull() && node.getLeftChild() != null && !node.getLeftChild().isNull()) {
            node = node.getLeftChild();
        }
        return node == null ? null : new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue());
    }

    @Override
    public T firstKey() {
        Map.Entry<T, V> entry = firstEntry();
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        if (key == null) Exception.throwException();
        INode<T, V> cur = null;
        INode<T, V> nxt = redBlackTree.getRoot();
        while (nxt != null && !nxt.isNull()) {
            if (nxt.getKey().compareTo(key) <= 0) {
                cur = nxt;
                nxt = nxt.getRightChild();
            } else if (nxt.getKey().compareTo(key) > 0) {
                nxt = nxt.getLeftChild();
            }
        }
        return cur == null ? null : new AbstractMap.SimpleEntry<>(cur.getKey(), cur.getValue());
    }

    @Override
    public T floorKey(T key) {
        Map.Entry<T, V> entry = floorEntry(key);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public V get(T key) {
        return redBlackTree.search(key);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        if (toKey == null) Exception.throwException();
        return headMap(toKey, false);
    }


    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        if (toKey == null) Exception.throwException();
        ArrayList<Map.Entry<T, V>> arr = new ArrayList<>();
        headMap(toKey, inclusive, arr, redBlackTree.getRoot());
        return arr;
    }

    private void headMap(T toKey, boolean inclusive, ArrayList<Map.Entry<T, V>> arr, INode<T, V> node) {
        if (node == null || node.isNull()) return;
        headMap(toKey, inclusive, arr, node.getLeftChild());
        if (node.getKey().compareTo(toKey) < 0)
            arr.add(new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue()));
        else if (inclusive && node.getKey().compareTo(toKey) == 0)
            arr.add(new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue()));
        headMap(toKey, inclusive, arr, node.getRightChild());
    }


    @Override
    public Set<T> keySet() {
        Set<T> keys = new LinkedHashSet<>();
        keySet(redBlackTree.getRoot(), keys);
        return keys;
    }
    private void keySet(INode<T, V> node, Set<T> set) {
        if (node == null || node.isNull()) return;
        keySet(node.getLeftChild(), set);
        set.add(node.getKey());
        keySet(node.getRightChild(), set);
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        INode<T, V> node = redBlackTree.getRoot();
        while (node != null && !node.isNull() && node.getRightChild() != null && !node.getRightChild().isNull()) {
            node = node.getRightChild();
        }
        return node == null ? null : new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue());
    }

    @Override
    public T lastKey() {
        Map.Entry<T, V> entry = lastEntry();
        return entry == null ? null : entry.getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        Map.Entry<T, V> entry = firstEntry();
        if (entry != null)
            redBlackTree.delete(entry.getKey());
        return entry;
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        Map.Entry<T, V> entry = lastEntry();
        if (entry != null)
            redBlackTree.delete(entry.getKey());
        return entry;
    }

    @Override
    public void put(T key, V value) {
        redBlackTree.insert(key, value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        if (map == null) Exception.throwException();
        for (Map.Entry<T, V> entry : map.entrySet()) {
            redBlackTree.insert(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean remove(T key) {
        return redBlackTree.delete(key);
    }

    @Override
    public int size() {
        return ((RedBlackTree) redBlackTree).size;
    }

    @Override
    public Collection<V> values() {
        List<V> listOfVal = new ArrayList<>();
        values(redBlackTree.getRoot(), listOfVal);
        return listOfVal;
    }

    private void values(INode<T, V> node, Collection<V> collection) {
        if (node == null || node.isNull()) return;
        values(node.getRightChild(), collection);
        collection.add(node.getValue());
        values(node.getLeftChild(), collection);
    }


}
