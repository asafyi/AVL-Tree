# AVL Tree   
AVL Tree class implementation in Java. An AVL tree self-balancing binary search tree.   
In this data structure, the heights of the two child subtrees of any node differ by at most one and if at any time 
they differ by more than one, *rebalancing* is done to restore this property.   
This special property assured us that operations such as searching, insertion, deletion, joining and splitting 
are operating in  O(log n) time in both the average and worst case.    
In this implementation "imaginary nodes" were added, so that for every node there will be two 
sons (helping in rebalancing the tree).    
For more information about AVL trees and their rebalancing methods can be found in ["Notes on AVL Trees"](Notes_on_AVL_Trees.pdf)
and many Data Structures and Algorithms books.

## Usage
In order to use the AVLTree and AVLNode instances you should download the files, place them in the correct directory 
and import the package as in the example:
```java
import AVLTree.*;
```
### AVLTree Class
| Function                    | Description                                                                                                                                                                                                                                                                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| empty()                     | Return True if the tree is empty.                                                                                                                                                                                                                                                                                                                |
| search(int k)               | Searching a node in the tree with a key equal to k and returning the node's value. If the node doesn't exist in the tree returns null.                                                                                                                                                                                                           |
| insert(int k, String s)     | Creating and inserting a new node to the tree with value equals to s and a key equal to k (if the key doesn't already exist in the tree). The function returns the number of rebalncing actions which were occurred or -1 if insertion didn't occurred.                                                                                          |
| delete(int k)               | Deleting the node in the tree with a key equal to k. The function returns the number of rebalncing actions which were occurred or -1 if a node with this key don't exist in the tree.                                                                                                                                                            |
| min()                       | Returning the value of the node with the smallest key in the tree or Null if the tree is empty.                                                                                                                                                                                                                                                  |
| max()                       | Returning the value of the node with the biggest key in the tree or Null if the tree is empty.                                                                                                                                                                                                                                                   |
| keysToArray()               | Returning a sorted array with all the keys in the tree. If the tree is empty returns empty array.                                                                                                                                                                                                                                                |
| infoToArray()               | Returning a sorted array (according to the keys) with all the values in the tree. If the tree is empty returns empty array.                                                                                                                                                                                                                      |
| size()                      | Returning the number of nodes in the tree.                                                                                                                                                                                                                                                                                                       |
| split(int x)                | The function gets a key x which exists in the tree. The function splits the tree so that there are two AVL trees. The first includes all the nodes with keys smaller than x, and the second includes all those with keys bigger than x. The function returns an AVLTree array with the first cell containing the tree with the smaller keys. |
| join(AVLNode x, AVLtree t)  | The function gets a node x and a tree t which all their keys are smaller or bigger than all the keys in the current tree. The function connects the trees with the new node to the current AVL tree. The function returns the new number of rebalncing actions which were occurred.                                                              |
| getRoot()                   | Returning the node which is the root of the tree.                                                                                                                                                                                                                                                                                                |

### AVLNode SubClass
| Function         | Description                                                        |
|------------------|--------------------------------------------------------------------|
| getKey()         | Returning the key of the node.                                     |
| getValue()       | Returning the value of the node.                                   |
| getLeft()        | Returning the left son of the node (can be a real node or not).    |
| getRight()       | Returning the right son of the node (can be a real node or not).   |
| getParent()      | Returning the parent of the node or null if the node is a root.    |
| isRealNode()     | Returning True if the node is real.                                |
| getHeight()      | Returning the height of the node in the tree it belongs.           |
| getSubtreeSize() | Returning the size of the subtree whose root is the current node.  |


***Made by [@asafyi](https://github.com/asafyi) && [@galgodsi](https://github.com/galgodsi)***
