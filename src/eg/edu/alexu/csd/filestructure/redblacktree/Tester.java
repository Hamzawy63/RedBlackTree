package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.*;

public class Tester {
    static final int bound  = 100000;
    public static void main(String[] args) {
        Random r = new Random();
        RedBlackTree<Integer , Integer > redBlackTree = new RedBlackTree<>() ;
        Set<Integer> keys = new TreeSet<>() ;
        ArrayList<Integer> inorders = new ArrayList<>() ;
        for(int i=0 ; i < bound ; i++ ){
            int key = r.nextInt(1000 * 1000) ;
            keys.add(key) ;
            redBlackTree.insert(key , r.nextInt(10));
        }
        inorder(redBlackTree.getRoot()  , inorders) ;
        int i = 0 ;
        for(Integer key : keys) {
            if(!key.equals(inorders.get(i))) System.out.println("expected " + key + " " +  inorders.get(i));
            i++ ;
        }
//        System.out.println();
//        for(Integer key : keys)
//            System.out.print(key + " ");
    }
    public static void inorder(INode node , List arr ) {
        if (node == null || node.isNull()) return;
        inorder(node.getLeftChild() , arr);
        arr.add(node.getKey());
        inorder(node.getRightChild() , arr);

    }


}
