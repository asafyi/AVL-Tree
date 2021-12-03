import java.util.Arrays;
import java.util.Random;

public class testing {
    public static void main(String[] args){
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
        tree.insert(18,"jgj");
        tree.insert(124,"jgj");
        tree.insert(15,"jgj");


        // System.out.println(tree3.insert(15,"jgj"));
       // tree.join(tree3.getRoot(),tree2);
        System.out.println(Arrays.toString(tree.keysToArray()) + " size "+tree.size()+" height "+tree.root.getHeight());
        AVLTree[] array =  tree.split(78);
        tree2 = array[0];
        tree3=array[1];
        //tree2.size=10;
        //tree3.size=10;

        System.out.println("big tree"+ Arrays.toString( tree2.keysToArray()) + " size "+tree2.size() +" height "+tree2.root.getHeight());
        System.out.println("small tree"+ Arrays.toString( tree3.keysToArray()) + " size "+tree3.size()+" height "+tree3.root.getHeight());
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

}
