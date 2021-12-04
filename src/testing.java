import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class testing {
    public static <random> void main(String[] args){
          AVLTree tree = new AVLTree();
        AVLTree tree2 = new AVLTree();
        AVLTree tree3 = new AVLTree();
//        System.out.println(Arrays.toString(tree.keysToArray()) + " size "+tree.size+" height "+tree.root.getHeight());

        tree.insert(2,"jgj");
        tree.insert(4,"jgj");
        tree.insert(8,"jgj");
        tree.insert(14,"jgj");
        tree.insert(12,"jgj");

        tree.insert(74,"jgj");
        tree.insert(78,"jgj");
        tree.delete(4);
        tree.insert(-74,"jgj");
        tree.insert(-78,"jgj");
        tree.delete(14);
        tree.insert(18,"jgj");
        tree.insert(124,"jgj");
        tree.insert(15,"jgj");
        tree.insert(18,"jgj");
        tree.insert(104,"jgj");
        tree.insert(-15,"jgj");
        tree.delete(104);
        tree.delete(-78);
        tree.delete(8);
        tree.insert(10,"jgj");
        tree.insert(-14,"jgj");
        tree.insert(15,"jgj");
        tree.delete(14);
        tree.delete(78);
        tree.delete(80);



        // System.out.println(tree3.insert(15,"jgj"));
       // tree.join(tree3.getRoot(),tree2);
        int rnd = new Random().nextInt(tree.size());
        System.out.println(Arrays.toString(tree.keysToArray()) + " size "+tree.size()+" height "+tree.root.getHeight());
        System.out.println(rnd);
        AVLTree[] array =  tree.split(tree.keysToArray()[rnd]);
        tree2 = array[0];
        tree3=array[1];
        //tree2.size=10;
        //tree3.size=10;

        System.out.println("big tree"+ Arrays.toString( tree2.keysToArray()) + " size "+tree2.size() +" height "+tree2.root.getHeight());
        System.out.println("small tree"+ Arrays.toString( tree3.keysToArray()) + " size "+tree3.size()+" height "+tree3.root.getHeight());
        System.out.println(isTreeConsistent(tree2.root));
        System.out.println(isTreeConsistent(tree.root));
        //        System.out.println(tree.insert(-13,"jgj"));
//        System.out.println(tree.insert(-20,"jgj"));
//          System.out.println(tree.insert(-10,"jgj"));




//        int b = tree.delete(-10);
//        System.out.println(b);
//        System.out.println(Arrays.toString(tree.keysToArray()) + " size "+tree.size+" height "+tree.root.getHeight());
//        int a=tree.delete(-12);
//        System.out.println(Arrays.toString(tree.keysToArray()) + " size "+tree.size+" height "+tree.root.getHeight());
//        int c=tree.delete(-11);
//        System.out.println(Arrays.toString(tree.keysToArray()) + " size "+tree.size+" height "+tree.root.getHeight());
//        System.out.println(a);
          //System.out.println(tree.insert(11,"jgj"));
//        tree.insert(4,"jgj");
//        tree.insert(5,"jgj");

//        tree.insert(98,"jgj");
//        tree.insert(0,"jgj");
//        tree.insert(20,"jgj");
//        tree.insert(2,"jgj");
//        tree.insert(88,"jgj");
//        tree.insert(1,"jgj");
//        tree.insert(9,"jgj");

//        Random rnd = new Random();
//        for(int i=0; i<1000; i++){
//            tree.insert(rnd.nextInt(10000),"");
//        }




    }


    public static boolean isTreeConsistent(AVLTree.IAVLNode root) {
        if (containsLoops(root, new HashSet<Integer>())) {
            System.out.println("Loop");
            return false;
        }
        boolean ret = true;
        if(root.isRealNode()){
            if (root.getLeft().isRealNode()) {
                if (root.getLeft().getParent() != root) {
                    System.out.println(root.getKey() + " Left son.");
                    return false;
                }
                ret = ret && isTreeConsistent(root.getLeft());
            }
            if (root.getRight().isRealNode()) {
                if (root.getRight().getParent() != root) {
                    System.out.println(root.getKey() + " Right son.");
                    return false;
                }
                ret = ret && isTreeConsistent(root.getRight());
            }
        }
        return ret;
    }

    public static boolean containsLoops(AVLTree.IAVLNode cur, Set<Integer> set) {
        if (cur == null)
            return false;
        if (!cur.isRealNode())
            return false;
        if (set.contains(cur.getKey()))
            return true;
        set.add(cur.getKey());
        boolean leftLoops = containsLoops(cur.getLeft(), set);
        boolean rightLoops = containsLoops(cur.getRight(), set);
        return leftLoops || rightLoops;
    }

}
