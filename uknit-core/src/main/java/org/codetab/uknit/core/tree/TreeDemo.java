package org.codetab.uknit.core.tree;

import java.util.Iterator;

public class TreeDemo {

    public static void main(final String[] args) {
        TreeDemo demo = new TreeDemo();
        demo.displayDepthFirstEx1();
        demo.displayDepthFirstEx2();
    }

    public void displayDepthFirstEx1() {
        TreeNode<String> root = new TreeNode<String>(0, "root", "root");
        TreeNode<String> a = new TreeNode<String>(0, "a", "a");
        TreeNode<String> aa = new TreeNode<String>(0, "aa", "aa");
        TreeNode<String> aa1 = new TreeNode<String>(0, "aa1", "aa1");
        root.add(a);
        a.add(aa);
        aa.add(aa1);

        TreeNode<String> b = new TreeNode<String>(0, "b", "b");
        TreeNode<String> ba = new TreeNode<String>(0, "ba", "ba");
        TreeNode<String> ba1 = new TreeNode<String>(0, "ba1", "ba1");
        TreeNode<String> bb = new TreeNode<String>(0, "bb", "bb");
        TreeNode<String> bb1 = new TreeNode<String>(0, "bb1", "bb1");
        TreeNode<String> bb2 = new TreeNode<String>(0, "bb2", "bb2");
        root.add(b);
        b.add(ba);
        ba.add(ba1);
        b.add(bb);
        bb.add(bb1);
        bb1.add(bb2);

        display(root);
    }

    // Ref - https://www.techiedelight.com/depth-first-search/
    public void displayDepthFirstEx2() {
        TreeNode<String> n1 = new TreeNode<String>(0, "1", "1");
        TreeNode<String> n2 = new TreeNode<String>(0, "2", "2");
        TreeNode<String> n3 = new TreeNode<String>(0, "3", "3");
        TreeNode<String> n4 = new TreeNode<String>(0, "4", "4");
        TreeNode<String> n5 = new TreeNode<String>(0, "5", "5");
        TreeNode<String> n6 = new TreeNode<String>(0, "6", "6");
        TreeNode<String> n7 = new TreeNode<String>(0, "7", "7");
        TreeNode<String> n8 = new TreeNode<String>(0, "8", "8");
        TreeNode<String> n9 = new TreeNode<String>(0, "9", "9");
        TreeNode<String> n10 = new TreeNode<String>(0, "10", "10");
        TreeNode<String> n11 = new TreeNode<String>(1, "11", "11");
        TreeNode<String> n12 = new TreeNode<String>(2, "12", "12");

        n1.add(n2);
        n1.add(n7);
        n1.add(n8);

        n2.add(n3);
        n2.add(n6);

        n3.add(n4);
        n3.add(n5);

        n8.add(n9);
        n8.add(n12);

        n9.add(n10);
        n9.add(n11);

        display(n1);
    }

    public void display(final TreeNode<String> tree) {
        Trees trees = new Trees();
        System.out.println("Tree");
        System.out.println(trees.prettyPrintBasicTree(tree, "", "", null));

        System.out.println("Depth First iterator order");
        Iterator<TreeNode<String>> it = tree.iterator();
        while (it.hasNext()) {
            TreeNode<String> n = it.next();
            System.out.print(n.getName() + "  ");
        }
    }
}
