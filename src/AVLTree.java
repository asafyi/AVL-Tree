import java.util.Arrays;
import java.util.Stack;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	int size;
	IAVLNode root;

	public AVLTree(){ //constructor
		this.size=0;
		root=new AVLNode();
	}
  /**`
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
  	IAVLNode node= find(k);
  	if(node.isRealNode()){
  		return node.getValue();
	}

  	else{
  		return null;
	}

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
	AVLNode myNode = (AVLNode) find(k); //myNode is initialized  with a pointer
   	if(myNode.isRealNode()){
   		return -1;
	}
   	this.size+=1;
   	//inserting k into the tree
   	myNode.setHeight(0);
   	IAVLNode leftSon = new AVLNode();
   	IAVLNode rightSon = new AVLNode();
	myNode.setLeft((leftSon));
	myNode.setRight(rightSon);
	leftSon.setParent(myNode);
	rightSon.setParent(myNode);
	myNode.setKey(k);
	myNode.setValue(i);
	if(this.size==1){
		return 0;
	}
	//balancing the tree
	IAVLNode parent = myNode.parent;
	if(parent.isRealNode()){

	}
	//not inserting on a leaf
	if(parent.getLeft().isRealNode()&&parent.getRight().isRealNode()){ //no balancing required
		return 0;
	}
	//inserting on a leaf
	else{
		parent.setHeight(parent.getHeight()+1);
		return (balance(parent.getParent()));
	}
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
	   return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
   	IAVLNode pointer=root;
   	String min = null;
   	while(pointer.isRealNode()){
		min= pointer.getValue();
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
	   IAVLNode pointer=root;
	   String max = null;
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
  	int[] arr = new int[size];
  	int i=0;

  	Stack<IAVLNode> st = new Stack<>();
  	IAVLNode pointer = root;

  	while(pointer.isRealNode() || !st.isEmpty()){
	  	while (pointer.isRealNode()){
	  		st.push(pointer);
	  		pointer=pointer.getLeft();
		}
	  	pointer = st.pop();
	  	arr[i] = pointer.getKey();
//	  	System.out.println(Arrays.toString(arr));
	  	i++;
	  	pointer=pointer.getRight();
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
	  String[] arr = new String[size];
	  int i=0;

	  Stack<IAVLNode> st = new Stack<>();
	  IAVLNode pointer = root;

	  while(pointer.isRealNode() || !st.isEmpty()){
		  while (pointer.isRealNode()){
			  st.push(pointer);
			  pointer=pointer.getLeft();
		  }
		  pointer = st.pop();
		  arr[i] = pointer.getValue();
		  i++;
		  pointer=pointer.getRight();
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
	   return null; 
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
	   return -1;
   }




   private IAVLNode find(int key){
   	IAVLNode pointer=root;
   	while (pointer.isRealNode()){
   		if(pointer.getKey()==key){
   			return pointer;
		}
		else if(key<pointer.getKey()){
			pointer=pointer.getLeft();
		}
   		else if(key>pointer.getKey()){
   			pointer=pointer.getRight();
		}
	   }
	   return pointer;
   }

   private int rotateRight(IAVLNode z){
   	IAVLNode x = z.getLeft();
   	IAVLNode parent = z.getParent();
   	IAVLNode b = x.getRight();
   	if(z!=root){

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
   	x.setParent(parent);
   	x.setRight(z);
   	z.setParent(x);
	if(b!=null){
		z.setLeft(b);
		b.setParent(z);
	}


   //	z.setHeight(z.getHeight()-1);
	//x.setHeight(x.getHeight()+1);

   	return 1;
   }


	private int rotateLeft(IAVLNode z){
		IAVLNode x = z.getRight();
		IAVLNode parent = z.getParent();
		IAVLNode b = x.getLeft();
		if(z!=root){

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
		x.setParent(parent);
		x.setLeft(z);
		z.setParent(x);

		if(b!=null){
			z.setRight(b);
			b.setParent(z);
		}


		//z.setHeight(z.getHeight()-1);
		//x.setHeight(x.getHeight()+1);

		return 1;
	}

	private int rotateLeftRight(IAVLNode z){
		rotateLeft(z.getLeft());
		rotateRight(z);
		return 2;
	}
	private int rotateRightLeft(IAVLNode z){
		rotateRight(z.getRight());
		rotateLeft(z);
		return 2;
	}


	private int balance(IAVLNode myNode){
   		if(myNode==null){
   			return 0;
		}
   		int diff = getDiff(myNode);
		if(diff==1 || diff==-1){
			myNode.setHeight(myNode.getHeight()+1);
			return 1+balance(myNode.getParent());
		}
		else if (diff==2){

			if(getDiff(myNode.getLeft())==1){
				myNode.setHeight(myNode.getHeight()-1);
				return rotateRight(myNode);

			}
			else //getDiff(myNode.getLeft())==-1
			{
				increaseLvlByD(myNode,-1);
				increaseLvlByD(myNode.getLeft(),-1);
				increaseLvlByD(myNode.getLeft().getRight(),1);
				return rotateLeftRight(myNode);
			}
		}

		else if (diff==-2){

			if(getDiff(myNode.getRight())==-1){
				increaseLvlByD(myNode,-1);
				return rotateLeft(myNode);
			}
			else //getDiff(myNode.getRight())==1
			{
				increaseLvlByD(myNode,-1);
				increaseLvlByD(myNode.getRight(),-1);
				increaseLvlByD(myNode.getRight().getLeft(),1);

				return rotateRightLeft(myNode);
			}
		}

		return 0;
	}
	private int getDiff(IAVLNode myNode){//gap between left and right heights
   		return myNode.getLeft().getHeight()-myNode.getRight().getHeight();
	}

	private void increaseLvlByD(IAVLNode myNode, int D){
   	myNode.setHeight(myNode.getHeight()+D);
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
  		private int key;
  		private String info;
	   	private IAVLNode left;
	   	private IAVLNode right;
	   	private int height;
	   	private IAVLNode parent;


	   	public AVLNode()
		{
			this.height = -1;

		}

		public int getKey()
		{
			return this.key; //
		}
		public void setKey(int k){
	   		this.key = k;
		}
		public String getValue()
		{
			return this.info; // to be replaced by student code
		}
	   	public void setValue(String i){
		   this.info = i;
	   }
		public void setLeft(IAVLNode node)
		{
			this.left=node;
		}
		public IAVLNode getLeft()
		{
			return this.left; // to be replaced by student code
		}
		public void setRight(IAVLNode node)
		{
			this.right=node;
		}
		public IAVLNode getRight()
		{
			return this.right; // to be replaced by student code
		}
		public void setParent(IAVLNode node)
		{
			this.parent=node;
		}
		public IAVLNode getParent()
		{
			return this.parent; // to be replaced by student code
		}
		public boolean isRealNode()
		{
			return (height!=-1); // to be replaced by student code
		}
	    public void setHeight(int height)
	    {
	      this.height=height;
	    }
	    public int getHeight()
	    {
	      return this.height;
	    }


  }

}
  
