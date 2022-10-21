package AVLTree;
import java.util.Stack;



/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and value.
 *
 */

public class AVLTree {
	AVLNode root; // pointer to the root of the tree (if the tree empty, the root is non-real node)

	/**
	 * constructor of empty AVL tree, so the size of the tree initialized to 0
	 * creating new non-real node and making it the root
	 */
	public AVLTree(){
		root=new AVLNode();
	}

	/**
	* public boolean empty()
	*
	* Returns true if and only if the tree is empty.
	*
	*/
	public boolean empty() {
		return this.size()==0;
	}

	/**
	* public String search(int k)
	*
	* Returns the value of an item with key k if it exists in the tree.
	* otherwise, returns null.
	*/
	public String search(int k)
	{
		AVLNode node= find(k); // returning pointer to the node in tree with the right key,

		if(node.isRealNode()){// if the key doesn't exist in the tree returning non-real node from the tree
			return node.getValue();// if the node is real returning the value of the node
		}
		return null; // else returning null - the key isn't in the tree
	}

	/**
	* public int insert(int k, String i)
	*
	* Inserts an item with key k and value i to the AVL tree.
	* The tree must remain valid, i.e. keep its invariants.
	* Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	* A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	* Returns -1 if an item with key k already exists in the tree.
	*/
	public int insert(int k, String i) {
		AVLNode node =  find(k); // returning pointer to the node in tree with the correct key
		if(node.isRealNode()){ // if the node is real so the key is already exist in the tree
			return -1;
		}
		node.setHeight(0); // we change the height of the node to zero because we can only insert to non-real nodes
		AVLNode leftSon = new AVLNode(); //creating new non-real node as a left son
		AVLNode rightSon = new AVLNode(); //creating new non-real node as a right son
		node.setLeft(leftSon); //setting the left son of the node
		node.setRight(rightSon); //setting the right son of the node
		leftSon.setParent(node); // setting the left non-real node's parent to point the inserting node
		rightSon.setParent(node); // setting the right non-real node's parent to point the inserting node
		node.setKey(k); // setting the key of the node to the key we got in the func
		node.setValue(i); // setting the value of the node to the value we got in the func
		updateBottomUp(node);
		if(this.size()==1){ // if the tree was empty (now has 1 node), there is no balancing needed
			return 0;
		}
		AVLNode parent = node.getParent();
		//if inserting to unary node so there is no balancing or promoting needed
		if(parent.getLeft().isRealNode() && parent.getRight().isRealNode()){ //no balancing required
			return 0;
		}
		//if inserting to a leaf node, so we increase the height of the parent in 1 and
		// checking the balance by the func balancing
		increaseHeight(parent,1);
		return 1+(balance(parent.getParent()));
   }

	/**
	* public int delete(int k)
	*
	* Deletes an item with key k from the binary tree, if it is there.
	* The tree must remain valid, i.e. keep its invariants.
	* Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	* A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	* Returns -1 if an item with key k was not found in the tree.
	*/
	public int delete(int k) {
		AVLNode node=  find(k); // returning pointer to the node in tree with the correct key,
		AVLNode parent =  node.getParent(); //pointer to the parent of the node
		AVLNode spare = new AVLNode(); // pointer to new not real node
		AVLNode left =  node.getLeft(); // pointer to the left son of the node
		AVLNode right =  node.getRight(); // pointer to the right son of the node
		AVLNode z; //parent of the removed node

		if(!node.isRealNode()){//k isn't in the tree
			return -1; // updating the value of num of nodes in the tree
		}
		else {//k is in the tree
			if (!left.isRealNode() && !right.isRealNode()) {//node has no kids
				if(parent==null){ //root is the only node in the tree
					this.root = new AVLNode();
					return 0;
				}
				if (parent.getLeft() == node) {//node is a left son
					parent.setLeft(spare);//spare is a non-real node
				} else {//node is a right son
					parent.setRight(spare);//spare is a non-real node
				}
				spare.setParent(parent); // setting the not real node's parent
				z = parent;
			}
			else if (left.isRealNode() && !right.isRealNode()){//node only has a left son
				if(parent==null){ // if node is the root in the tree
					left.setParent(root.getParent());
					root=left; // the left node is the new root
					return 0; // no balancing needed
				}
				if (parent.getLeft() == node){//node is a left son
					parent.setLeft(left); // setting the left son of the parent
				} else {//node is a right son
					parent.setRight(left); // setting the right son of the parent
				}
				left.setParent(parent); // setting the parent of the right son
				node.SetSubtreeSize(node.getLeft().getSubtreeSize()+ node.getRight().getSubtreeSize()+1);
				z = parent;
			}
			else if (!left.isRealNode() && right.isRealNode()){//node only has a right son
				if(parent==null){ // if node is the root in the tree
					right.setParent(root.getParent());
					root=right; // the right node is the new root
					return 0; // no balancing needed
				}
				if (parent.getLeft() == node) {//node is a left son
					parent.setLeft(right); // setting the left son of the parent to the right son of the node
				} else {//node is a right son
					parent.setRight(right); // setting the right son of the parent
				}
				right.setParent(parent);// setting the parent of the right son
				node.SetSubtreeSize(node.getLeft().getSubtreeSize()+ node.getRight().getSubtreeSize()+1);
				z = parent;
			}
			else {//node has two sons
				AVLNode successor =  successor(node); // finding the successor of the node
				AVLNode successorParent =  successor.getParent(); // pointer to the parent of the successor

				this.updateBottomUp(successor); //because moving the successor decreases its parents size

				//moving successor to node's position
				if(successorParent != node) {
					successor.getRight().setParent(successorParent);
				}
				else{ // if the successor's parent is the node we want to delete
					right =  right.getRight();
				}
				successor.getLeft().setParent(successorParent);
				successorParent.setLeft(successor.getRight());

				left.setParent(successor);
				right.setParent(successor);

				if(parent==null){
					root=successor;
				}
				else {
					if (parent.getLeft() == node) {//node is a left son
						parent.setLeft(successor);
					} else {//node is a right son
						parent.setRight(successor);
					}
				}
				// updating pointers of the successor
				successor.setLeft(left);
				successor.setRight(right);
				successor.setParent(parent);

				successor.SetSubtreeSize(successor.getRight().getSubtreeSize()+successor.getLeft().getSubtreeSize()+1);
				successor.setHeight(node.getHeight());

				z = successorParent;
				if(z.getKey()== k){//balancing from successor
					z=successor;
				}
			}
			return balance_del(z); // calling the balancing for deletion func
		}
	}


   /**
    * the func get AVLNode from the tree
    * Returns the successor of the node
    * or null if there is none
    */
	public AVLNode successor(AVLNode node){

		AVLNode parent=node.getParent();
		if(node.getRight().isRealNode()){ // if the right node is real
			AVLNode loop =node.getRight(); // saving pointer to the right son of the root
			// getting the most left real node under the right son of the root
			while(loop.getLeft().isRealNode()){
				loop=loop.getLeft();
			}
			return loop;
		}
		// if the root doesn't have real right son
		while (parent.isRealNode() && node==parent.getRight()){  // while parent is real, node is right son of parent
			node = parent;
			parent = node.getParent();
		}
		return parent;
	}

	/**
	* public String min()
	*
	* Returns the value of the item with the smallest key in the tree,
	* or null if the tree is empty.
	*/
	public String min() {
		AVLNode pointer=root; //pointer the root
		String min = null;
		// while the node is real node, we continue to the left son, which is the smaller
		while(pointer.isRealNode()){
			min = pointer.getValue();
			pointer=pointer.getLeft();
		}
		return min;
	}

   /**
    * public String max()
    *
    * Returns the value of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
	public String max(){
		AVLNode pointer=root; // pointer to the root
		String max = null;
		// while the node is real node, we continue to the right son, which is the bigger
		while(pointer.isRealNode()){
			max= pointer.getValue();
			pointer=pointer.getRight();
		}
		return max;
	}

	/**
	* public int[] keysToArray()
	*
	* Returns a sorted array which contains all keys in the tree,
	* or an empty array if the tree is empty.
	*/
	public int[] keysToArray()
	{
		int[] arr = new int[this.root.getSubtreeSize()]; // creating empty array in the size of the tree
		int i = 0;
		Stack<AVLNode> st = new Stack<>();
		AVLNode pointer = root;
		// while the node is real or there are nodes in the stuck
		while(pointer.isRealNode() || !st.isEmpty()){
			while (pointer.isRealNode()){
				st.push(pointer);
				pointer = pointer.getLeft(); //continue to the left son of the node
			}
			pointer = st.pop();
			arr[i] = pointer.getKey(); // adding the key to the tha array
			i++; // increasing the place which we need to insert the next key
			pointer=pointer.getRight(); //continue to the right side of the node
		}
		return arr;
	}

	/**
	* public String[] infoToArray()
	*
	* Returns an array which contains all value in the tree,
	* sorted by their respective keys,
	* or an empty array if the tree is empty.
	*/
	public String[] infoToArray()
	{
		String[] arr = new String[this.size()];  // creating empty array in the size of the tree
		int i=0;
		Stack<AVLNode> st = new Stack<>();
		AVLNode pointer = root;
		// while the node is real or there are nodes in the stuck
		while(pointer.isRealNode() || !st.isEmpty()){
			while (pointer.isRealNode()){
				st.push(pointer);
				pointer=pointer.getLeft(); //continue to the left son of the node
			}
			pointer = st.pop();
			arr[i] = pointer.getValue(); // adding the value to the tha array
			i++; // increasing the place which we need to insert the next value
			pointer=pointer.getRight(); //continue to the right side of the node
		}
		return arr;
	}

	/**
	* public int size()
	*
	* Returns the number of nodes in the tree.
	*/
	public int size() {
		return this.getRoot().getSubtreeSize();
	}

	/**
	* public int getRoot()
	*
	* Returns the root AVL node, or null if the tree is empty
	*/
	public AVLNode getRoot() {
	   return root;
	}

	/**
	* public AVLTree[] split(int x)
	*
	* splits the tree into 2 trees according to the key x.
	* Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	*
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	* postcondition: none
	*/
	public AVLTree[] split(int x) {
		AVLTree smallerTree = new AVLTree(); // an empty new tree for bigger values than x
		AVLTree biggerTree = new AVLTree(); // an empty new tree for smaller values than x
		AVLNode node = this.find(x); // finding the node with the key
		// setting pointers to the smaller sub-tree and bigger sub-tree
		smallerTree.root =node.getLeft();
		smallerTree.root.setParent(null);

		biggerTree.root =node.getRight();
		biggerTree.root.setParent(null);
		node.setLeft(null);
		node.setRight(null);
		node = node.getParent();

		if (node!= null){ // if the parent of node with the key is not the root
			AVLNode fakeSon = new AVLNode(); //creating new non-real node as replacing son for x
			if(node.getLeft().getKey()==x){
				node.setLeft(fakeSon);
			}
			if(node.getRight().getKey()==x){
				node.setRight(fakeSon);
			}

			// checking if node is the root
			AVLNode parent = node.getParent();
			AVLTree tempTree = new AVLTree();

			// running on all parents of the node until the root
			while(node!=null){
				if(node.getKey()>x){//node is a right parent
					tempTree.root=node.getRight();
					tempTree.root.setParent(null);
					node.SetSubtreeSize(1); //we treat him as a single node
					node.setLeft(null);
					node.setRight(null);
					biggerTree.join(node,tempTree); // joining the values to the big tree values
				} else {//node is a left parent
					tempTree.root=node.getLeft();
					tempTree.root.setParent(null);
					node.SetSubtreeSize(1); //we treat him as a single node
					node.setLeft(null);
					node.setRight(null);
					smallerTree.join(node,tempTree); // joining the values to the small tree values
				}
				node.SetSubtreeSize(node.getLeft().getSubtreeSize()+ node.getRight().getSubtreeSize()+1); // updating the size of the node
				node = parent;
				if(node!=null){
					parent=node.getParent();
				}
			}
		}
		AVLTree[] arr = new AVLTree[2];
		arr[0]=smallerTree;
		arr[1]=biggerTree;
		this.root = smallerTree.root;
		return arr; // returning the smaller tree and the bigger tree
	}


	/**
	* public int join(AVLNode x, AVLTree t)
	*
	* joins t and x with the tree.
	* Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
	* postcondition: none
	*/
	public int join(AVLNode x, AVLTree t) {
		AVLTree bigTree;
		AVLTree smallTree;

		//checking which tree is higher
		if(this.getRoot().getHeight()>t.root.getHeight()){
			bigTree=this;
			smallTree=t;
		}
		else if(this.getRoot().getHeight()<t.root.getHeight()){
			bigTree=t;
			smallTree=this;
		}
		else{//same height
			x.setHeight((this.getRoot().getHeight()+1)); // updating the height
			if(this.getRoot().getKey()>x.getKey()){ // checking which tree as bigger values and change pointers accordingly
			x.setRight(this.root);
			x.setLeft(t.root);
			this.root.setParent(x);
			t.root.setParent(x);
			}
			else{//this.getRoot().getKey()>x.getKey()
			x.setRight(t.root);
			x.setLeft(this.root);
			t.root.setParent(x);
			this.root.setParent(x);
			}
			this.root=x;
			this.root.setParent(null);
			root.SetSubtreeSize(root.getLeft().getSubtreeSize()+root.getRight().getSubtreeSize() +1);
			return 1;
		}
		AVLNode bigRoot = bigTree.getRoot();
		AVLNode smallRoot = smallTree.getRoot();
		int smallHeight;
		int bigHeight=bigRoot.getHeight();
		if(bigTree.getRoot().getKey()>x.getKey()){//bigger tree has higher values
			smallHeight = smallRoot.getHeight();
			while(bigRoot.getHeight()>smallHeight){//else the gap between the heights will be 0 or -1
				bigRoot=bigRoot.getLeft();
			}
			AVLNode parent = bigRoot.getParent();
			// changing pointers
			parent.setLeft(x);
			x.setParent(parent);
			bigRoot.setParent(x);
			x.setRight(bigRoot);
			x.setLeft(smallRoot);
			smallRoot.setParent(x);
			//fixing size to all parents
			bigTree.updateBottomUp(x);
			this.root=bigTree.getRoot();

			x.setHeight(smallHeight+1);
			parent.setHeight(Math.max(parent.getLeft().getHeight(),parent.getRight().getHeight())+1);
			balance(parent);
			balance(parent.getParent());

		}
		else{//bigTree.getRoot().getKey()<x.getKey() //smaller tree has higher values
			smallHeight = smallTree.getRoot().getHeight();
			while(bigRoot.getHeight()>smallHeight){//else the gap between the heights will be 0 or -1
				bigRoot=bigRoot.getRight();
			}
			AVLNode parent = bigRoot.getParent();
			//changing pointers
			parent.setRight(x);
			x.setParent(parent);
			bigRoot.setParent(x);
			x.setLeft(bigRoot);
			x.setRight(smallRoot);
			smallRoot.setParent(x);
			//fixing size to all parents
			bigTree.updateBottomUp(x);
			this.root=bigTree.getRoot();

			x.setHeight(smallHeight+1);
			parent.setHeight(Math.max(parent.getLeft().getHeight(),parent.getRight().getHeight())+1);
			balance(parent); // balancing the sub-tree
			balance(parent.getParent());

		}
		return bigHeight-smallHeight+1;
	}

	/**
	 * getting an  integer - key
	 * searching the node with this key, returning the node if the node exist
	 * else returning a non-real node where we will need to insert this node in order to
	 * preserve the order of BST
	 */
	private AVLNode find(int key){
		AVLNode pointer = root;
		while (pointer.isRealNode()){ // while the node is real
			// if the current node key is what we are looking for we're returning this node
			if(pointer.getKey()==key){
				return pointer;
			}
			// if the current node's key is bigger than k, we continue to the left son
			else if(key<pointer.getKey()){
				pointer=pointer.getLeft();
			}
			// if the current node's key is smaller than k, we continue to the right son
			else{
				pointer=pointer.getRight();
			}
		}
		return pointer;
	}

	/**
	*
	* the function get a node, and fixing the size
	* of all the parents until the root of the tree
	*/
	private int updateBottomUp(AVLNode node){
		while(node!=null){
			node.SetSubtreeSize(node.getLeft().getSubtreeSize()+node.getRight().getSubtreeSize() +1);
			node= node.getParent();
		}
		return 0;
	}

	/**
	*
	* getting AVL node and doing right rotation,
	* returning 1, the number of rotations
	*/
	private int rotateRight(AVLNode node){
		AVLNode x =  node.getLeft();
		AVLNode parent = node.getParent();
		AVLNode b = x.getRight();
		if(node!=root){ // if we don't do rotation on the root
			if(parent.getLeft()==node) {//node is a left son
				parent.setLeft(x);
			}
			else //node is a right son
			{
				parent.setRight(x);
			}
		} else{
			root = x;
		}
		// changing the pointers for right rotation
		x.setParent(parent);
		x.setRight(node);
		node.setParent(x);

		if(b!=null){ // if x had left son, we need to change pointers accordingly
			node.setLeft(b);
			b.setParent(node);
		}

		node.SetSubtreeSize(node.getLeft().getSubtreeSize()+node.getRight().getSubtreeSize()+1);
		x.SetSubtreeSize(x.getLeft().getSubtreeSize()+x.getRight().getSubtreeSize()+1);
		return 1; // we did only one rotation here
	}

	/**
	*
	* getting AVL node and doing left rotation,
	* returning 1, the number of rotations
	*/
	private int rotateLeft(AVLNode node){
	AVLNode x = node.getRight();
	AVLNode parent = node.getParent();
	AVLNode b = x.getLeft();
	if(node!=root){// if we don't do rotation on the root
		if(parent.getRight()==node) {//node is a right son
			parent.setRight(x);
		}
		else { //node is a left son
		parent.setLeft(x);
		}
	} else {
		root = x;
	}
	// changing the pointers for left rotation
	x.setParent(parent);
	x.setLeft(node);
	node.setParent(x);

	if(b!=null){// if x had left son, we need to change pointers accordingly
		node.setRight(b);
		b.setParent(node);
	}
		node.SetSubtreeSize(node.getLeft().getSubtreeSize()+node.getRight().getSubtreeSize()+1);
		x.SetSubtreeSize(x.getLeft().getSubtreeSize()+x.getRight().getSubtreeSize()+1);
		return 1; // we did only one rotation here
	}

	/**
	*
	* getting AVL node and doing left rotation and then right rotation,
	* returning 2, the number of rotations
	*/
	private int rotateLeftRight(AVLNode z){
		rotateLeft(z.getLeft());
		rotateRight(z);
		return 2;
	}

	/**
	*
	* getting AVL node and doing right rotation and then left rotation,
	* returning 2, the number of rotations
	*/
	private int rotateRightLeft(AVLNode z){
		rotateRight(z.getRight());
		rotateLeft(z);
		return 2;
	}

	/**
	*
	* getting AVL node, checking the balance (according to the node's sons)
	* and choosing according which balance operation to do
	* returning the number of balance operations which were preformed
	*/
	private int balance(AVLNode node){
		if(node==null){ // if we got null there is no balancing needed
			return 0;
		}
		int diff = getDiff(node); // getting the diff height of the node's sons
		node.SetSubtreeSize(node.getLeft().getSubtreeSize()+ node.getRight().getSubtreeSize()+1);
		if(diff==1 || diff==-1){ // if the diff is -1 or 1, so we increase the height in one
		//and checking if balancing needed for the parent
			node.setHeight(Math.max(node.getLeft().getHeight(),node.getRight().getHeight()));
			increaseHeight(node, 1);
			return 1+balance(node.getParent());
		}
		else if (diff==2){ // if the diff is 2 we're splitting into two situations
			if(getDiff(node.getLeft())==1){ // if the left son diff is 1, so we change
			// the height accordingly and doing right rotation
				increaseHeight(node,-1);
				int temp = rotateRight(node)+1;
				updateBottomUp(node);
				return temp;
			}
			else if(getDiff(node.getLeft())==-1){ // if the left son diff is -1, so we change
			// the height accordingly and doing left-right rotation
				increaseHeight(node,-1);
				increaseHeight(node.getLeft(),-1);
				increaseHeight(node.getLeft().getRight(),1);
				int temp =  rotateLeftRight(node)+3;
				updateBottomUp(node);
				return temp;
			}
			else{//diff is 0
				increaseHeight(node,-1);
				increaseHeight(node.getLeft(),1);
				int temp = rotateRight(node)+1;
				updateBottomUp(node);
				return temp;
			}
		}
		else if (diff==-2){ // if the diff is -2 we're splitting into two situations
			if(getDiff(node.getRight())==-1){// if the right son diff is -1, so we change
			// the height accordingly and doing left rotation
				increaseHeight(node,-1);
				int temp = rotateLeft(node)+1;
				updateBottomUp(node);
				return temp;
			}
			else if (getDiff(node.getRight())==1){// if the left son diff is 1, so we change
			// the height accordingly and doing right-left rotation
				increaseHeight(node,-1);
				increaseHeight(node.getRight(),-1);
				increaseHeight(node.getRight().getLeft(),1);
				int temp = rotateRightLeft(node)+3;
				updateBottomUp(node);
				return temp;
			}
			else {//diff is 0
				increaseHeight(node,-1);
				increaseHeight(node.getRight(),1);
				int temp = rotateLeft(node)+1;
				updateBottomUp(node);
				return temp;
			}
		}
		return 0;
	}

	/**
	*
	* getting AVL node, checking the balance (according to the node's sons)
	* and choosing according which balance operation to do for the deletion
	* returning the number of balance operations which were preformed
	*/
	private int balance_del(AVLNode node){
		int SonsDiff;
		if(node==null){ // if we got null there is no balancing needed
			return 0;
		}
		node.SetSubtreeSize(node.getLeft().getSubtreeSize()+ node.getRight().getSubtreeSize()+1);

		int Diff = getDiff(node); // getting the diff height of the node's sons
		if(Diff==0){// if the diff is 0, so we decrease the height in one
		//and checking if balancing needed for the parent
			increaseHeight(node,-1);
			return 1+balance_del(node.getParent());
		}
		else if(Diff==-1||Diff==1){ // so there is no balancing needed
			updateBottomUp(node);
			return 0;
		}
		else if(Diff==-2){ // if the diff is -2 we're splitting into two situations
			SonsDiff = getDiff(node.getRight());
			if(SonsDiff == 0){ // if the right son diff is 0, so we change
			// the height accordingly and doing left rotation
				increaseHeight(node,-1);
				increaseHeight(node.getRight(),1);
				int temp =(rotateLeft(node)+2);
				updateBottomUp(node);
				return temp;
			}
			else if(SonsDiff == -1){// if the right son diff is -1, so we change
			// the height accordingly and doing left rotation and running balancing on his parent
				increaseHeight(node,-2);
				return(rotateLeft(node)+1+ balance_del(node.getParent().getParent()));// because the node changed its location during rotate
			}
			else if(SonsDiff == 1){ // if the left son diff is 1, so we change
			// the height accordingly and doing right-left rotation and running balancing on his parent
				increaseHeight(node,-2);
				increaseHeight(node.getRight(),-1);
				increaseHeight(node.getRight().getLeft(),1);
				return(rotateRightLeft(node)+1+ balance_del(node.getParent().getParent()));// because the node changed its location during rotate
			}
		}
		else if(Diff==2){ // if the diff is 2 we're splitting into two situations
			SonsDiff = getDiff(node.getLeft());
			if(SonsDiff == 0){ // if the right son diff is 0, so we change
			// the height accordingly and doing left rotation
				increaseHeight(node,-1);
				increaseHeight(node.getLeft(),1);
				int temp =(rotateRight(node)+2);
				updateBottomUp(node);
				return temp;
			}
			else if(SonsDiff == 1){ // if the right son diff is 1, so we change
			// the height accordingly and doing right rotation and running balancing on his parent
				increaseHeight(node,-2);
				return(rotateRight(node)+1+ balance_del(node.getParent().getParent()));// because the node changed its location during rotate
			}
			else if(SonsDiff == -1){// if the left son diff is -1, so we change
			// the height accordingly and doing left-right rotation and running balancing on his parent
				increaseHeight(node,-2);
				increaseHeight(node.getLeft(),-1);
				increaseHeight(node.getLeft().getRight(),1);
				return(rotateLeftRight(node)+1+ balance_del(node.getParent().getParent()));// because the node changed its location during rotate
			}
		}
		return 0;
	}


	/**
	* getting an IAV node and returning the left son's height minus the right son's height
	*/
	private int getDiff(AVLNode node){
		return node.getLeft().getHeight()-node.getRight().getHeight(); //gap between left and right heights
	}

	/**
	* getting a node and an integer (d) and increase his height in d
	*/
	private void increaseHeight(AVLNode node, int d){
		node.setHeight(node.getHeight()+d);
	}
	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree
	 * (for example AVLNode), do it in this file, not in another file.
	 *
	 */
	public class AVLNode{
		private int key;  //The key of the node, affecting the order of the tree
		private String value; // The info of the node, not affecting the order of the tree
		private AVLNode left; // pointer to the left son of the node, can be real or not
		private AVLNode right; // pointer to the right son of the node, can be real or not
		private int height; // value of the height of the node in the tree
		private AVLNode parent; // pointer to the parent of this node, if null the node is the root
		private int subtreeSize; //size of the subtree whose root is the current node

		/**
		 * constructor of node, creating non-real node (height -1), when creating this node
		 * we may change his pointer to the parent according to func
		 * the other values are the default, and we will change them if the node becoming real
		 */
		public AVLNode()
		{
			this.key = -1;
			this.height = -1;
		}

		/**
		 * returning the key of this specific node
		 */
		public int getKey() {
			return this.key;
		}

		/**
		 * get integer and change the key of the node to this integer
		 */
		public void setKey(int k){
			this.key = k;
		}

		/**
		 * returning the value of this specific node
		 */
		public String getValue() {
			return this.value;
		}

		/**
		 * get String and change the value of the node to this String
		 */
		void setValue(String i){
			this.value = i;
		}

		/**
		 * the func get a pointer to a AVLNode and change the left son of the current node to point it
		 */
		void setLeft(AVLNode node) {
			this.left=node;
		}

		/**
		 * returning a pointer to the left son of the node
		 */
		public AVLNode getLeft() {
			return this.left;
		}

		/**
		 * the func get a pointer to a AVLNode and change the right son of the current node to point it
		 */
		void setRight(AVLNode node) {
			this.right=node;
		}

		/**
		 * returning a pointer to the right son of the node
		 */
		public AVLNode getRight() {
			return this.right;
		}

		/**
		 * the func get a pointer to a AVLNode and change the parent of the current node to point it
		 */
		void setParent(AVLNode node) {
			this.parent=node;
		}

		/**
		 * returning a pointer to the parent of the node or null if it has no parent (will be the root)
		 */
		public AVLNode getParent() {
			return this.parent;
		}

		/**
		 * returning if the node is real - only non-real nodes has height -1
		 */
		public boolean isRealNode() {
			return (height!=-1);
		}

		/**
		 * the func get integer and set the height of the node accordingly
		 */
		void setHeight(int height) {
			this.height=height;
		}

		/**
		 * returning integer of the height of the node
		 */
		public int getHeight() {
			return this.height;
		}

		/**
		 * the func get integer and set the size of the subtree whose root is the current node
		 */
		void SetSubtreeSize(int size) {
			this.subtreeSize=size;
		}

		/**
		 * returning integer of the size of the subtree whose root is the current node
		 */
		public int getSubtreeSize() {
			return this.subtreeSize;
		}
	}
}

