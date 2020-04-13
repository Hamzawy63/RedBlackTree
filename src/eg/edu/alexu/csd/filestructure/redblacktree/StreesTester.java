package eg.edu.alexu.csd.filestructure.redblacktree;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class StreesTester {
    static final int bound  = 100000;
    @Test
    public void test(){
        RedBlackTree<Integer , String> redBlackTree = new RedBlackTree<>();
        try {

            Random r = new Random();
            HashSet<Integer> list = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                int key = r.nextInt(10000);
                list.add(key);
                redBlackTree.insert(key, "soso" + key);
            }
            for (Integer elem : list)
                redBlackTree.delete(elem);
            INode<Integer, String> node = redBlackTree.getRoot();
            if (!(node == null || node.isNull()))
                Assert.fail();
            Assert.assertTrue(verifyProps(node));
        } catch (Throwable e) {
            System.out.println("Failedddddddd");
        }

    }


    private boolean validateBST(INode<Integer, String> node, INode<Integer, String> leftRange, INode<Integer, String> rightRange) {
        if (node == null || node.isNull()) return true;

        if ((leftRange == null || node.getKey().compareTo(leftRange.getKey()) > 0) &&
                (rightRange == null || node.getKey().compareTo(rightRange.getKey()) < 0))
            return validateBST(node.getLeftChild(), leftRange, node) &&
                    validateBST(node.getRightChild(), node, rightRange);
        return false;
    }

    private boolean verifyProperty2(INode<Integer, String> node) {
        return node.getColor() == INode.BLACK;
    }

    private boolean verifyProperty3(INode<Integer, String> node) {
        if (node == null || node.isNull()) return node.getColor() == INode.BLACK;

        return verifyProperty3(node.getLeftChild()) && verifyProperty3(node.getRightChild());
    }

    private boolean verifyProperty4(INode<Integer, String> node) {
        if (node == null || node.isNull()) return true;
        if (isRed(node)) {
            return !isRed(node.getParent()) && !isRed(node.getLeftChild()) && !isRed(node.getRightChild());
        }

        return verifyProperty4(node.getLeftChild()) && verifyProperty4(node.getRightChild());
    }

    private boolean verifyProperty5(INode<Integer, String> node) {
        boolean[] ans = new boolean[]{true};
        verifyProperty5Helper(node, ans);
        return ans[0];
    }

    private int verifyProperty5Helper(INode<Integer, String> node, boolean[] ans) {
        if (node == null) return 1;

        int leftCount = verifyProperty5Helper(node.getLeftChild(), ans);
        int rightCount = verifyProperty5Helper(node.getRightChild(), ans);

        ans[0] = ans[0] && (leftCount == rightCount);
        return leftCount + (!isRed(node)? 1 : 0);
    }

    private boolean verifyProps(INode<Integer, String> root) {
        return verifyProperty2(root) && verifyProperty3(root) && verifyProperty4(root) && verifyProperty5(root) && validateBST(root, null, null);
    }

    private boolean isRed(INode<Integer, String> node) {
        if (node == null || node.isNull()) return INode.BLACK;
        return node.getColor() == INode.RED;
    }

}
