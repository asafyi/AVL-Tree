import java.util.Stack;

/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	int size; // integer the save the number of real nodes in the tree
	IAVLNode root; // pointer to the root of the tree (if the tree empty, the root is non-real node)

	/**
	 * constructor of empty AVL tree, so the size of the tree initialized to 0
	 * creating new non-real node and making it the root
	 */
	public AVLTree(){
		this.size=0;
		root=new AVLNode();
	}

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
    return size==0;
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	  IAVLNode node= find(k); // returning pointer to the node in tree with the right key,
	  
	  if(node.isRealNode()){// if the key doesn't exist in the tree returning non-real node from the tree
		  return node.getValue();// if the node is real returning the info of the node
	  }
	  return null; // else returning null - the key isn't in the tree
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
  public int insert(int k, String i) {
	  AVLNode myNode = (AVLNode) find(k); // returning pointer to the node in tree with the correct key,
	  // if the key doesn't exist in the tree returning non-real node from the tree
	  if(myNode.isRealNode()){ // if the node is real so the key is already exist in the tree
		  return -1;
	  }
	  this.size+=1; // increasing the size of the tree in 1
	  myNode.setHeight(0); // we change the height of the node to zero because we can only insert to non-real nodes
	  IAVLNode leftSon = new AVLNode(); //creating new non-real node as a left son
	  IAVLNode rightSon = new AVLNode(); //creating new non-real node as a right son
	  myNode.setLeft(leftSon); //setting the left son of the node
	  myNode.setRight(rightSon); //setting the right son of the node
	  leftSon.setParent(myNode); // setting the left non-real node's parent to point the inserting node
	  rightSon.setParent(myNode); // setting the right non-real node's parent to point the inserting node
	  myNode.setKey(k); // setting the key of the node to the key we got in the func
	  myNode.setValue(i); // setting the value of the node to the value we got in the func
	  if(this.size==1){ // if the tree was empty (now has 1 node), there is no balancing needed
		  return 0;
	  }
	  IAVLNode parent = myNode.parent;
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
   public int delete(int k)
   {
		IAVLNode node= find(k); // returning pointer to the node in tree with the correct key,
		IAVLNode parent = node.getParent();
		IAVLNode spare = new AVLNode();
		IAVLNode left = node.getLeft();
		IAVLNode right = node.getRight();
		IAVLNode z; //parent of the removed node

	  if(!node.isRealNode()){//k isn't in the tree
		  return -1; //no rebalancing needed
	  }
	  else {//k is in the tree
		  this.size-=1;
		  if (!left.isRealNode() && !right.isRealNode())//node has no kids
		  {
			  if(parent==null){ //root is the only node in the tree
				  root=new AVLNode();
				  return 0;
			  }
			  if (parent.getLeft() == node) {//node is a left son
				  parent.setLeft(spare);//spare is a non-real node
				  spare.setParent(parent);
			  } else {//node is a right son
				  parent.setRight(spare);//spare is a non-real node
				  spare.setParent(parent);
			  }
			  z = parent;
		  }
		  else if (left.isRealNode() && !right.isRealNode()) {//node only has a left son
			  if(parent==null){
				  left.setParent(root.getParent());
				  root=left;
				  return 0;
			  }
			  if (parent.getLeft() == node) {//node is a left son
				  parent.setLeft(left);
				  left.setParent(parent);
			  } else {//node is a right son
				  parent.setRight(left);
				  left.setParent(parent);
			  }
			  z = parent;
		  }
		  else if (!left.isRealNode() && right.isRealNode()) {//node only has a right son
			  if(parent==null){
				  right.setParent(root.getParent());
				  root=right;
				  return 0;
			  }
			  if (parent.getLeft() == node) {//node is a left son
				  parent.setLeft(right);
				  right.setParent(parent);
			  } else {//node is a right son
				  parent.setRight(right);
				  right.setParent(parent);
			  }
			  z = parent;
		  } else {//node has two sons
			  IAVLNode successor = successor(node);
			  IAVLNode successorParent = successor.getParent();

			  //moving successor to node's position
			  successor.getRight().setParent(successor.getParent());
			  successor.getParent().setRight(successor.getRight());

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

			  successor.setLeft(node.getLeft());
			  successor.setRight(node.getRight());
			  successor.setParent(node.getParent());

			  successor.setHeight(node.getHeight());

			  z = successorParent;
		  }
		  //balancing the tree
//		  int max = Math.max(z.getLeft().getHeight(), z.getRight().getHeight());
//		  if (z.getHeight() - 1 == max) {
//			  return 0;
//		  }

//		  z.setHeight(max + 1);

		  return balance_del(z);


	  }
	  
		

   }


   /**
    * the func get IAVLNode from the tree
    * Returns the succsesor of the node 
    * or null if there is none
    */
  public IAVLNode successor(IAVLNode node){
  	  
	  IAVLNode parent=node.getParent();
	  if(node.getRight().isRealNode()){
		  IAVLNode loop =node.getRight();

		  while(loop.getLeft().isRealNode()){
			  loop=loop.getLeft();
		  }
	  	  return loop;
	  }
	  while (parent.isRealNode() && node==parent.getRight()){
		node = parent;
		parent = node.getParent();
	  }
	  return parent;
  }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
   	IAVLNode pointer=root; //pointer the root
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
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   IAVLNode pointer=root; // pointer to the root
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
  	int[] arr = new int[size]; // creating empty array in the size of the tree
  	int i = 0;
  	Stack<IAVLNode> st = new Stack<>();
  	IAVLNode pointer = root;
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
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  String[] arr = new String[size];  // creating empty array in the size of the tree
	  int i=0;
	  Stack<IAVLNode> st = new Stack<>();
	  IAVLNode pointer = root;
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
   public int size()
   {
	   return size;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
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
   public AVLTree[] split(int x)
   {
       AVLTree smallerTree = new AVLTree();
       AVLTree biggerTree = new AVLTree();
       IAVLNode node = this.find(x);

       smallerTree.root =node.getLeft();
       smallerTree.root.setParent(null);

       biggerTree.root =node.getRight();
       biggerTree.root.setParent(null);

       node.setLeft(null);
       node.setRight(null);
	   node = node.getParent();
	   IAVLNode parent;
	   if(node!=null) {
		   parent = node.getParent();
	   }
	   else{
		   parent=node;
	   }
       AVLTree tempTree = new AVLTree();

       while(node!=null){

           if(node.getKey()>x){//node is a right parent
               tempTree.root=node.getRight();
               tempTree.root.setParent(null);
               //node.setRight(null);
               biggerTree.join(node,tempTree);
           }
           else{//node is a left parent
               tempTree.root=node.getLeft();
               tempTree.root.setParent(null);
               //node.setLeft(null);
               smallerTree.join(node,tempTree);


           }

           node = parent;
		   if(node!=null){
			   parent=node.getParent();
		   }



       }

       AVLTree[] arr=new AVLTree[2];
       arr[0]=biggerTree;
       arr[1]=smallerTree;
	   return arr;
   }






   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   AVLTree bigTree;
	   AVLTree smallTree;

	   this.size =this.size()+ t.size()+1;

	   if(this.getRoot().getHeight()>t.root.getHeight()){
		   bigTree=this;
		   smallTree=t;
	   }
	   else if(this.getRoot().getHeight()<t.root.getHeight()){
		   bigTree=t;
		   smallTree=this;
	   }
	   else{//same height

			x.setHeight((this.getRoot().getHeight()+1));
		   if(this.getRoot().getKey()>x.getKey()){
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
		   return 1;
	   }


	   if(bigTree.getRoot().getKey()>x.getKey()){//bigger tree has higher values
		   IAVLNode node=bigTree.root;
		   int smallHeight=smallTree.getRoot().getHeight();
		   while(node.getHeight()>smallHeight){//else the gap between the heights will be 0 or -1
			   node=node.getLeft();
		   }
			IAVLNode parent = node.getParent();

		   parent.setLeft(x);
		   x.setParent(parent);
		   node.setParent(x);
		   x.setRight(node);
		   x.setLeft((smallTree.getRoot()));
		   smallTree.getRoot().setParent(x);

		   this.root=bigTree.getRoot();


		   //before: x.setHeight(smallTree.getRoot().getHeight()+1);
		   x.setHeight(smallHeight+1);
		   increaseHeight(parent,1);
		   //return balance(parent);
		   balance(parent);
		   return Math.abs(this.getRoot().getHeight()-t.root.getHeight())+1;

	   }
	   else{//bigTree.getRoot().getKey()<x.getKey() //smaller tree has higher values
		   IAVLNode node=bigTree.root;
		   int smallHeight=smallTree.getRoot().getHeight();
		   while(node.getHeight()>smallHeight){//else the gap between the heights will be 0 or -1
			   node=node.getRight();
		   }
		   IAVLNode parent = node.getParent();

		   parent.setRight(x);
		   x.setParent(parent);
		   node.setParent(x);
		   x.setLeft(node);
		   x.setRight((smallTree.getRoot()));
		   smallTree.getRoot().setParent(x);

		   this.root=bigTree.getRoot();
		   this.size =smallTree.size()+ bigTree.size()+1;

		   //before: x.setHeight(smallTree.getRoot().getHeight()+1);
		   x.setHeight(smallHeight+1);
		   increaseHeight(parent,1);




		   balance(parent);
		   return Math.abs(this.getRoot().getHeight()-t.root.getHeight())+1;
	   }


   }

	/**
	 * getting an  integer - key
	 * searching the node with this key, returning the node if the node exist
	 * else returning a non-real node whrere we will need to insert this node in order to
	 * preserve the order of BST
	 */
   private IAVLNode find(int key){
   	IAVLNode pointer = root;
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
   		else if(key>pointer.getKey()){
   			pointer=pointer.getRight();
		}
	}
	return pointer;
 }

	/**
	 *
	 * getting IAVL node and doing right rotation,
	 * returning 1, the number of rotations
	 */
   private int rotateRight(IAVLNode z){
   	IAVLNode x = z.getLeft();
   	IAVLNode parent = z.getParent();
   	IAVLNode b = x.getRight();
	if(z!=root){ // if we don't do rotation on the root
   		if(parent.getLeft()==z) {//z is a left son
			parent.setLeft(x);
   		}
   		else //z is a right son
		{
			parent.setRight(x);
		}
	}
   	else{
   		root = x;
	}
   // changing the pointers for right rotation
   	x.setParent(parent);
   	x.setRight(z);
   	z.setParent(x);

	if(b!=null){ // if x had left son, we need to change pointers accordingly
		z.setLeft(b);
		b.setParent(z);
	}
   	return 1; // we did only one rotation here
   }

	/**
	 *
	 * getting IAVL node and doing left rotation,
	 * returning 1, the number of rotations
	 */
	private int rotateLeft(IAVLNode z){
		IAVLNode x = z.getRight();
		IAVLNode parent = z.getParent();
		IAVLNode b = x.getLeft();
		if(z!=root){// if we don't do rotation on the root
			if(parent.getRight()==z) {//z is a left son
				parent.setRight(x);
			}
			else //z is a left son
			{
				parent.setLeft(x);
			}
		}
		else{
			root = x;
		}
		// changing the pointers for left rotation
		x.setParent(parent);
		x.setLeft(z);
		z.setParent(x);

		if(b!=null){// if x had left son, we need to change pointers accordingly
			z.setRight(b);
			b.setParent(z);
		}
		return 1; // we did only one rotation here
	}

	/**
	 *
	 * getting IAVL node and doing left rotation and then right rotation,
	 * returning 2, the number of rotations
	 */
	private int rotateLeftRight(IAVLNode z){
		rotateLeft(z.getLeft());
		rotateRight(z);
		return 2;
	}

	/**
	 *
	 * getting IAVL node and doing right rotation and then left rotation,
	 * returning 2, the number of rotations
	 */
	private int rotateRightLeft(IAVLNode z){
		rotateRight(z.getRight());
		rotateLeft(z);
		return 2;
	}

	/**
	 *
	 * getting IAVL node, checking the balance (according to the node's sons)
	 * and choosing according which balance operation to do
	 * returning the number of balance operations which were preformed
	 */
	private int balance(IAVLNode myNode){
   		if(myNode==null){ // if we got null there is no balancing needed
   			return 0;
		}
   		int diff = getDiff(myNode); // getting the diff height of the node's sons

		if(diff==1 || diff==-1){ // if the diff is -1 or 1 so we increase the height in one
			//and checking if balancing needed for the parent
			increaseHeight(myNode, 1);
			return 1+balance(myNode.getParent());
		}
		else if (diff==2){ // if the diff is 2 we're splitting into two situations
			if(getDiff(myNode.getLeft())==1){ // if the left son diff is 1, so we change
				// the height accordingly and doing right rotation
				increaseHeight(myNode,-1);
				return rotateRight(myNode)+1;
			}
			else if(getDiff(myNode.getLeft())==-1) // if the left son diff is -1, so we change
			// the height accordingly and doing left-right rotation
			{
				increaseHeight(myNode,-1);
				increaseHeight(myNode.getLeft(),-1);
				increaseHeight(myNode.getLeft().getRight(),1);
				return rotateLeftRight(myNode)+3;
			}
			else{//diff is 0
				increaseHeight(myNode.getRight(),1);
				return rotateRight(myNode)+1;

			}
		}

		else if (diff==-2){ // if the diff is -2 we're splitting into two situations
			if(getDiff(myNode.getRight())==-1){// if the right son diff is -1, so we change
				// the height accordingly and doing left rotation
				increaseHeight(myNode,-1);
				return rotateLeft(myNode)+1;
			}
			else if (getDiff(myNode.getRight())==1)// if the left son diff is 1, so we change
			// the height accordingly and doing right-left rotation
			{
				increaseHeight(myNode,-1);
				increaseHeight(myNode.getRight(),-1);
				increaseHeight(myNode.getRight().getLeft(),1);

				return rotateRightLeft(myNode)+3;
			}
			else {//diff is 0
				increaseHeight(myNode.getLeft(),1);
				return rotateLeft(myNode)+1;
			}
		}

		return 0;
	}




	private int balance_del(IAVLNode myNode){

		int SonsDiff;
		if(myNode==null){ // if we got null there is no balancing needed
			return 0;
		}
		int Diff = getDiff(myNode);
		if(Diff==0){// demote of father needed
			increaseHeight(myNode,-1);
			return 1+balance_del((myNode.getParent()));
		}
		else if(Diff==-1||Diff==1){
			return 0;
		}
		else if(Diff==-2){
			SonsDiff = getDiff(myNode.getRight());
			if(SonsDiff == 0){

				increaseHeight(myNode,-1);
				increaseHeight(myNode.getRight(),1);
				return(rotateLeft(myNode)+2);

			}
			else if(SonsDiff == -1){
				increaseHeight(myNode,-2);

				return(rotateLeft(myNode)+1+ balance_del(myNode.getParent().getParent()));// because mynode changed its location during rotate
			}
			else if(SonsDiff == 1){
				increaseHeight(myNode,-2);
				increaseHeight(myNode.getRight(),-1);
				increaseHeight(myNode.getRight().getLeft(),1);
				return(rotateRightLeft(myNode)+1+ balance_del(myNode.getParent().getParent()));// because mynode changed its location during rotate
			}

		}
		else if(Diff==2){
			SonsDiff = getDiff(myNode.getLeft());
			if(SonsDiff == 0){

				increaseHeight(myNode,-1);
				increaseHeight(myNode.getLeft(),1);
				return(rotateRight(myNode)+2);

			}
			else if(SonsDiff == 1){
				increaseHeight(myNode,-2);

				return(rotateRight(myNode)+1+ balance_del(myNode.getParent().getParent()));// because mynode changed its location during rotate
			}
			else if(SonsDiff == -1){
				increaseHeight(myNode,-2);
				increaseHeight(myNode.getLeft(),-1);
				increaseHeight(myNode.getLeft().getRight(),1);
				return(rotateLeftRight(myNode)+1+ balance_del(myNode.getParent().getParent()));// because mynode changed its location during rotate
			}

		}

	return 0;
	}
	/**
	 * getting an IAV node and returning the left son's height minus the right son's height
	 */
	private int getDiff(IAVLNode myNode){
   		return myNode.getLeft().getHeight()-myNode.getRight().getHeight(); //gap between left and right heights
	}

	/**
	 * getting a node and an integer (d) and increase his height in d
	 */
	private void increaseHeight(IAVLNode myNode, int d){
   		myNode.setHeight(myNode.getHeight()+d);
	}

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{
  		private int key;  //The key of the node, affecting the order of the tree
  		private String info; // The info of the node, not affecting the order of the tree
	   	private IAVLNode left; // pointer to the left son of the node, can be real or not
	   	private IAVLNode right; // pointer to the right son of the node, can be real or not
	   	private int height; // value of the height of the node in the tree
	   	private IAVLNode parent; // pointer to the parent of this node, if null the node is the root
		private int size; //size of the subtree whose root is the current node
	   /**
		* constructor of node, creating non-real node (height -1), when creating this node
		* we may change his pointer to the parent according to func
		* the other values are the default, and we will change them if the node becoming real
		*/
	   	public AVLNode()
		{
			this.height = -1;
		}

	   /**
		* returning the key of this specific node
		*/
		public int getKey()
		{
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
		public String getValue()
		{
			return this.info;
		}

	   /**
		* get String and change the info of the node to this String
		*/
	   	public void setValue(String i){
		   this.info = i;
	   }

	   /**
		* the func get a pointer to a IAVLNode and change the left son of the current node to point it
		*/
	   public void setLeft(IAVLNode node)
	   {
		   this.left=node;
	   }

	   /**
		* returning a pointer to the left son of the node
		*/
	   public IAVLNode getLeft()
	   {
		   return this.left;
	   }

	   /**
		* the func get a pointer to a IAVLNode and change the right son of the current node to point it
		*/
	   public void setRight(IAVLNode node)
	   {
		   this.right=node;
	   }

	   /**
		* returning a pointer to the right son of the node
		*/
	   public IAVLNode getRight()
	   {
		   return this.right;
	   }

	   /**
		* the func get a pointer to a IAVLNode and change the parent of the current node to point it
		*/
	   public void setParent(IAVLNode node)
	   {
		   this.parent=node;
	   }

	   /**
		* returning a pointer to the parent of the node or null if it has no parent (will be the root)
		*/
	   public IAVLNode getParent()
	   {
		   return this.parent;
	   }

	   /**
		* returning if the node is real - only non-real nodes has height -1
		*/
	   public boolean isRealNode()
	   {
		   return (height!=-1);
	   }

	   /**
		* the func get integer and set the height of the node accordingly
		*/
	   public void setHeight(int height)
	   {
		   this.height=height;
	   }
	   /**
		* returning integer of the height of the node
		*/
	   public int getHeight()
	   {
		   return this.height;
	   }

	   /**
		* the func get integer and set the height of the node accordingly
		*/
	   public void setSize(int size)
	   {
		   this.size=size;
	   }
	   /**
		* returning integer of the height of the node
		*/
	   public int getSize()
	   {
		   return this.size;
	   }

  }

}
  
