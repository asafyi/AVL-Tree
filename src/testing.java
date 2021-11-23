import org.omg.CORBA.SetOverrideTypeHelper;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Random;

public class testing {
    public static void main(String[] args){
          AVLTree tree = new AVLTree();
          System.out.println(tree.insert(12,"jgj"));
          System.out.println(tree.insert(10,"jgj"));
          System.out.println(tree.insert(11,"jgj"));
//        tree.insert(4,"jgj");
//        tree.insert(5,"jgj");
//        tree.insert(72,"jgj");
//        tree.insert(74,"jgj");
//        tree.insert(78,"jgj");
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
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println(tree.size);
//        System.out.println(tree.root.getHeight());
    }

}
