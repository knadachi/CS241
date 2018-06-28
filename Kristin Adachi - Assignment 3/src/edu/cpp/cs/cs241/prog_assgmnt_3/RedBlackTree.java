/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Program Assignment #3: Red Black Tree
 * 
 * <description-of-assignment>
 * 
 * The purpose of this assignment is to help us understand the 
 * implementation of a red black tree. A red black tree is one form 
 * of a self balancing binary search tree. Balance is preserved 
 * by assigning each node of the tree with a color (red or black) 
 * that follows a certain set of cases. When the tree is modified, 
 * the new tree is rearranged and the nodes are recolored such that 
 * it follows those cases (explained throughout the code).
 * 
 * @author Kristin Adachi
 */

package edu.cpp.cs.cs241.prog_assgmnt_3;


/**
 * Class: RedBlackTree
 * 
 * A red black tree is a type of self balancing binary search tree. 
 * Balance is preserved by assigning each node of the tree with a 
 * color (red or black) that follows a certain set of cases. The 
 * tree performs rotations and recolors the nodes such that it 
 * follows those cases.
 *
 * @param <K> Specified data type for the key.
 * @param <V> Specified data type for the value.
 */
public class RedBlackTree< K extends Comparable< K >, V > implements Tree< K, V >
{
	/**
	 * Public Static Variable: Color
	 * Type: enum
	 * Each node is assigned a color (red or black).
	 */
	public static enum Color{ RED, BLACK }
	/**
	 * Private Variable: tabSize
	 * Type: String
	 * Specified space that is used in the toPrettyString method. 
	 * It helps determine the amount of space between each node 
	 * when the tree is printed.
	 */
	private String tabSize = "       ";
	/**
	 * Private Variable: size
	 * Type: int
	 * Initially set to 0 and keeps track of the number of 
	 * nodes in the red black tree (including the null leaves).
	 */
	private int size;
	/**
	 * Private Variable: root
	 * Type: Node
	 * Keeps track of the root of the red black tree.
	 */
	private Node root;
	
	/**
	 * Constructor: RedBlackTree
	 * Constructor that creates an empty red black tree. The root 
	 * is set to null and the size is set to 0.
	 */
	public RedBlackTree() 
	{
		root = null;
		size = 0;
	}
	
	/**
	 * Public Method: leftRotate
	 * Performs a left rotate on the specified node. It is used 
	 * to rearrange the tree and follows the red black tree cases.
	 * 
	 * @param node Node the left rotate is being performed on.
	 */
	public void leftRotate( Node node )
	{
		Node temp = node.right.left;
		Node newRoot = node.right;
		newRoot.left = node;
		newRoot.left.right = temp;
		newRoot.parent = newRoot.left.parent;
		newRoot.left.parent = newRoot;
		temp.parent = node;
		
		if( newRoot.parent == null ){
			root = newRoot;
		}
		else if( newRoot.parent.left == newRoot.left  ){
			newRoot.parent.left = newRoot;
		}
		else{
			newRoot.parent.right = newRoot;
		}
	}
	
	/**
	 * Public Method: rightRotate
	 * Performs a right rotate on the specified node. It is used 
	 * to rearrange the tree and follows the red black tree 
	 * cases.
	 * 
	 * @param node Node the right rotate is being performed on.
	 */
	public void rightRotate( Node node )
	{
		Node temp = node.left.right;
		Node newRoot = node.left;
		newRoot.right = node;
		newRoot.right.left = temp;
		newRoot.parent = newRoot.right.parent;
		newRoot.right.parent = newRoot;
		temp.parent = node;
		
		if( newRoot.parent == null ){
			root = newRoot;
		}
		else if( newRoot.parent.left == newRoot.right ){
			newRoot.parent.left = newRoot;
		}
		else{
			newRoot.parent.right = newRoot;
		}
	}
	
	/**
	 * Public Method: addLeaf
	 * Adds a null leaf to the specified node. A null leaf is 
	 * initialized with no children and the specified node is 
	 * set to be its parent. Its color is also black.
	 * 
	 * @param parent The node that the null leaf is being added 
	 *               to.
	 * @return The null leaf.
	 */
	public Node addLeaf( Node parent )
	{
		return new Node( null, null, Color.BLACK, parent );
	}
	
	/**
	 * Public Method: getUncle
	 * Finds the uncle of the specified node.
	 * 
	 * @param node The node that is used to find its uncle.
	 * @return The uncle of the node if it has one and null 
	 *         otherwise.
	 */
	public Node getUncle( Node node )
	{
		Node grandparent = getGrandparent( node );
		if( grandparent == null ){
			return null;
		}
		if( node.parent == grandparent.left ){
			return grandparent.right;
		}
		else{
			return grandparent.left;
		}
	}
	
	/**
	 * Public Method: getGrandparent
	 * Finds the grandparent of the specified node.
	 * 
	 * @param node The node that is used to find its grandparent.
	 * @return The grandparent of the node if it has one and null 
	 *         otherwise.
	 */
	public Node getGrandparent( Node node )
	{
		if( node != null && node.parent != null ){
			return node.parent.parent;
		}
		else{
			return null;
		}
	}
	
	/**
	 * Public Method: getSibling
	 * Finds the sibling of the specified node.
	 * 
	 * @param node The node that is used to find its sibling.
	 * @return The sibling of the node if it has one and null 
	 *         otherwise.
	 */
	public Node getSibling( Node node )
	{
		if( node.isLeftChild() ){
			return node.parent.right;
		}
		else{
			return node.parent.left;
		}
	}

	/**
	 * Public Method: getPredecessor
	 * Finds the in order predecessor of the specified node. 
	 * The node's left child is passed into this method and 
	 * continues to go as far right as it can until it reaches 
	 * a null node.
	 * 
	 * @param node The node's left child.
	 * @return The in order predecessor and null otherwise.
	 */
	public Node getPredecessor( Node node )
	{
		if( node.key == null){
			return null;
		}
		if( node.right == null ){
			return node.parent;
		}
		if( node.right.key != null ){
			return getPredecessor( node.right );
		}
		return node;
	}

    /**
     * Public Method: swap
     * Takes in two nodes and swaps them by switching their keys 
     * and values (their links are not changed and the colors are 
     * not swapped).
     * 
     * @param n1 First node to be swapped.
     * @param n2 Second node to be swapped.
     */
    public void swap( Node n1, Node n2 )
    {
    	K tempK = n1.key;
    	V tempV = n1.value;
    	n1.key = n2.key;
    	n1.value = n2.value;
    	n2.key = tempK;
    	n2.value = tempV;
    }
	
	/**
	 * Public Method: findAdd
	 * This recursive method finds the location of the expected parent 
	 * of the node being added to the red black tree. It starts at the 
	 * root and compares the given key with the node's key. If the key 
	 * is greater than the node's key, it proceeds to the left subtree. 
	 * Otherwise it proceeds to the right subtree. It continues to 
	 * traverse the tree until it reaches a null leaf.
	 * 
	 * @param key The key that is needed to find its expected parent.
	 * @param node The current node whose key we compare to the given 
	 *             key.
	 * @return The expected parent of the node being added.
	 */
	public Node findAdd( K key, Node node )
	{
		if( node.left != null && node.left.key != null && key.compareTo( node.key ) <= 0 ){
			return findAdd( key, node.left );
		}
		else if( node.right != null && node.right.key != null && key.compareTo( node.key ) > 0 ){
			return findAdd( key, node.right );
		}
		return node;
	}
	
	/**
	 * Public Method: findRemove
	 * This recursive method is similar to findAdd. It traverses the 
	 * tree to locate the node being removed in a similar manner. 
	 * However, this method also checks if the removed node is in 
	 * the red black tree before removing.
	 * 
	 * @param key The key of the node that is being removed.
	 * @param node The current node whose key we compare to the given 
	 *             key.
	 * @return The proper location in the tree of the node being 
	 *         removed and null if it isn't found.
	 */
	public Node findRemove( K key, Node node )
	{
		if( size == 0 ){
			return null;
		}
		if( node.left.key != null && key.compareTo( node.key ) < 0 ){
			return findRemove( key, node.left );
		}
		else if( node.right.key != null && key.compareTo( node.key ) > 0 ){
			return findRemove( key, node.right );
		}
		else if( node.left.key == null && node.right.key == null && key != node.key ){
			return null;
		}
		return node;
	}
	
	/**
	 * Public Method: Add
	 * Adds the specified key and value to the red black tree as a 
	 * node. If the tree is empty, the node is added as the root. 
	 * If it isn't, the expected parent of the node being added is 
	 * found using the findAdd helper method. It is then added as 
	 * a red node with the given key and value, no children, and 
	 * expected parent. Finally, the null leaves are added to the 
	 * new node and the necessary add cases are performed.
	 * 
	 * @param key The specified key for the node being added.
	 * @param value The specified value for the node being added.
	 */
	public void add( K key, V value )
	{
		Node node = null;
		
		//add case 1: tree is empty and node is added as root
		if( root == null )
		{
			root = new Node( key, value, Color.BLACK, null );
			root.left = addLeaf( root );
			root.right = addLeaf( root );
			size += 3;
			return;
		}
		else
		{
			Node position = findAdd( key, root );
			if( key.compareTo( position.key ) <= 0 )
			{
				position.left = new Node( key, value, Color.RED, position );
				node = position.left;
			}
			else if( key.compareTo( position.key ) > 0 )
			{
				position.right = new Node( key, value, Color.RED, position );
				node = position.right;
			}
			node.left = addLeaf( node );
			node.right = addLeaf( node );
			
			size += 2;
		}
		addCaseOne( node );
	}
	
	/**
	 * Public Method: addCaseOne
	 * This method represents add case 2 (case 1 was checked in 
	 * the add method) where the parent's color is black. If the 
	 * color is black, then the tree isn't changed. If it isn't, 
	 * the node is sent to the method addCaseTwo.
	 * 
	 * @param node The node being verified for add case 2.
	 */
	public void addCaseOne( Node node )
	{
		if( node.parent.color == Color.BLACK )
		{
			root.color = Color.BLACK;
			return;
		}
		else{
			addCaseTwo( node );
		}
	}
	
	/**
	 * Public Method: addCaseTwo
	 * This method represents add case 3.1 where the node's uncle 
	 * is red. If it is, the node's parent becomes black, the 
	 * uncle's color becomes black, and the grandparent's color 
	 * becomes red. The node is updated if necessary and sent back 
	 * to addCaseOne. Otherwise, the node is sent to the method 
	 * addCaseThree.
	 * 
	 * @param node The node being verified for add case 3.1.
	 */
	public void addCaseTwo( Node node )
	{
		Node uncle = getUncle( node );
		Node grandparent = getGrandparent( node );
		if( uncle.color == Color.RED )
		{
			node.parent.color = Color.BLACK;
			uncle.color = Color.BLACK;
			grandparent.color = Color.RED;
			if( grandparent != root ){
				node = grandparent;
			}
			addCaseOne( node );
		}
		else{
			addCaseThree( node );
		}
	}
	
	/**
	 * Public Method: addCaseThree
	 * This method represents add case 3.2 where the node's uncle 
	 * is black and the node is internal. If it is internal, the 
	 * method updates the node and performs the necessary rotation 
	 * depending on if it is a left or right child. Again, it is 
	 * sent back to addCaseOne. If the node is external, it is sent 
	 * to the method addCaseFour.
	 * 
	 * @param node The node being verified for add case 3.2.
	 */
	public void addCaseThree( Node node )
	{
		if( node.isInternal() )
		{
			if( node.isLeftChild() )
			{
				node = node.parent;
				rightRotate( node );
			}
			else
			{
				node = node.parent;
				leftRotate( node );
			}
			addCaseOne( node );
		}
		else{
			addCaseFour( node );
		}
	}

	/**
	 * Public Method: addCaseFour
	 * This method represents add case 3.3 where the node's uncle 
	 * is black and the node is external. If it is external, the 
	 * parent becomes black and the grandparent becomes red. Then, 
	 * the necessary rotation is performed on the grandparent 
	 * depending on if node is a left or right child. Finally, it 
	 * is sent back to addCaseOne.
	 * 
	 * @param node The node being verified for add case 3.3.
	 */
	public void addCaseFour( Node node )
	{
		Node parent = node.parent;
		Node grandparent = getGrandparent( node );
		if( !node.isInternal() )
		{
			parent.color = Color.BLACK;
			grandparent.color = Color.RED;
			if( node.isLeftChild() ){
				rightRotate( grandparent );
			}
			else{
				leftRotate( grandparent );
			}
			addCaseOne( node );
		}
	}

	/**
	 * Public Method: remove
	 * Removes the node with the specified key from the red black 
	 * tree. It locates the node being removed by using the helper 
	 * method findRemove. Then it checks for different remove cases: 
	 * (1) tree only contains the root (2) the node is a root and 
	 * has more than one node (3) the node is a leaf or has only one 
	 * child (4) the node has two children. If necessary, the node 
	 * is then sent to the method removeCaseOne to check for the 
	 * remove cases.
	 * 
	 * @param key The specified key of the node being removed.
	 * @return The value of the node that was removed or null if the 
	 *         node wasn't in the tree.
	 */
    public V remove( K key )
    {
    	Node node = findRemove( key, root );
    	Color col = node.color;
    	V returnValue = node.value;
    	Node x = null;

    	if( size == 3 )
    	{
    		returnValue = root.value;
    		root = null;
    		size = 0;
    		return returnValue;
    	}
    	
    	if( node == root )
    	{
    		size -= 2;
    		return removeRoot( node );
    	}
    	
    	if( node.left.key == null || node.right.key == null )
    	{
    		x = remove2( node );
    	}
    	else
    	{
    		col = getPredecessor( node.left ).color;
    		x = remove3( node );
    	}
    	
    	if( col == Color.BLACK ){
    		if( x != root && x.color == Color.BLACK ){
    			removeCaseOne( x );
    		}
    		else{
    			x.color = Color.BLACK;
    		}
    	}
    	size -= 2;
    	return returnValue;
    }

    /**
     * Public Method: removeRoot
     * Helper method for the remove method. This method is used when 
     * the node being removed is the root and there is more than one 
     * node in the tree. It checks how many children it has and 
     * performs the corresponding removal and updates the root.
     * 
     * @param node The root of the tree.
     * @return The value of the root.
     */
    public V removeRoot( Node node )
    {
    	Node x = null;
    	Node pred = getPredecessor( node.left );
    	V returnValue = node.value;
    	if( node.left.key != null && node.right.key != null )
		{
			Color col = pred.color;
			x = remove3( node );
			if( col == Color.BLACK ){
	    		if( x != root && x.color == Color.BLACK ){
	    			removeCaseOne( x );
	    		}
	    		else{
	    			x.color = Color.BLACK;
	    		}
	    	}
		}
		else
		{
    		if( node.left.key != null ){
    			root = node.left;
    		}
    		else{
    			root = node.right;
    		}
    		node = null;
		}
		root.color = Color.BLACK;
		return returnValue;
    }
    
    /**
     * Public Method: remove2
     * Helper method for the remove method. This method is used when 
     * the node being removed is a leaf or has only one child. 
     * Depending on if the node is a left or right child, the 
     * links between the nodes are updated and the node is removed.
     * 
     * @param node The node being removed.
     * @return The value of the node being removed.
     */
    public Node remove2( Node node )
    {
    	Node x = null;
    	Node parent = node.parent;
    	if( node.left.key == null )
    	{
    		x = node.right;
    		if( parent.right == node ){
    			parent.right = node.right;
    		}
    		else{
    			parent.left = node.right;
    		}
    		node.right.parent = parent;
    	}
    	else if( node.right.key == null )
    	{
    		x = node.left;
    		if( parent.right == node ){
    			parent.right = node.left;
    		}
    		else{
    			parent.left = node.left;
    		}
    		node.left.parent = parent;
    	}
    	return x;
    }
    
    /**
     * Public Method: remove3
     * Helper method for the remove method. This method is used when 
     * the node being removed has two children. It finds the 
     * predecessor and swaps it with the node being removed. Depending 
     * if the predecessor is a left or right child, it performs the 
     * necessary removal.
     * 
     * @param node The node being removed.
     * @return The value of the node being removed.
     */
    public Node remove3( Node node )
    {	
    	Node pred = getPredecessor( node.left );
    	Node predParent = pred.parent;
    	Node x = null;
    	
    	if( node.left == pred ){
    		predParent = node;
    	}
    	swap( node, pred );
    	if( predParent.right == pred )
    	{
    		predParent.right = pred.left;
    		predParent.right.parent = predParent;
    		x = predParent.right;
    	}
    	else
    	{
    		predParent.left = pred.left;
    		predParent.left.parent = predParent;
    		x = predParent.left;
    	}
    	node = null;
    	return x;
    }

	/**
	 * Public Method: removeCaseOne
	 * This method represents remove case 1 where the sibling's color 
	 * is red. If it is red, the colors of its sibling and parent are 
	 * switched. Depending on if the sibling is a left or right child, 
	 * it performs the necessary rotation on the parent and updates 
	 * sibling. It then checks for remove cases 2-4.
	 * 
	 * @param node The node being verified for remove case 1.
	 */
	public void removeCaseOne( Node node )
	{
		Node parent = node.parent;
		Node sibling = getSibling( node );
		
		if( sibling.color == Color.RED )
		{
			Color tempCol = sibling.color;
			sibling.color = parent.color;
			parent.color = tempCol;
			
			if( sibling.isLeftChild() )
			{
				rightRotate( parent );
				sibling = node.parent.left;
			}
			else
			{
				leftRotate( parent );
				sibling = node.parent.right;
			}
		}
		if( sibling.color == Color.BLACK )
		{
			if( sibling.left.color == Color.BLACK && sibling.right.color == Color.BLACK ){
				removeCaseTwo( node );
			}
			else
			{
				if( sibling.isLeftChild() )
				{
					if( sibling.left.color == Color.BLACK && sibling.right.color == Color.RED ){
						removeCaseThree( node );
					}
					else{
						removeCaseFour( node );
					}
				}
				else
				{
					if( sibling.right.color == Color.BLACK && sibling.left.color == Color.RED ){
						removeCaseThree( node );
					}
					else{
						removeCaseFour( node );
					}
				}
			}
		}
	}

	/**
	 * Public Method: removeCaseTwo
	 * This method represents remove case 2 where the sibling's color 
	 * is black and both of the sibling's children are black. The 
	 * sibling's color becomes red and the node is updated. It uses 
	 * the helper method isDone to check if the tree follows the 
	 * remove cases. If it doesn't, the updated node is sent back to 
	 * the method removeCaseOne.
	 * 
	 * @param node The node being verified for remove case 2.
	 */
	public void removeCaseTwo( Node node )
	{
		Node parent = node.parent;
		Node sibling = getSibling( node );
		sibling.color = Color.RED;
		node = parent;
		if( isDone( node ) ){
			return;
		}
		else{
			removeCaseOne( node );
		}
	}

	/**
	 * Public Method: removeCaseThree
	 * This method represents remove case 3 where the sibling's color 
	 * is black and the sibling's internal child is red and external 
	 * child is black. Depending on if the sibling is a left or right 
	 * child, its color is updated and the necessary rotation is 
	 * performed on the sibling. It uses the helper method isDone to 
	 * check if the tree follows the remove cases. If it doesn't, the 
	 * node is sent back to the method removeCaseOne.
	 * 
	 * @param node The node being verified for remove case 3.
	 */
	public void removeCaseThree( Node node )
	{
		Node sibling = getSibling( node );
		Color tempCol = sibling.color;
		
		if( sibling.isLeftChild() )
		{
			sibling.color = sibling.right.color;
			sibling.right.color = tempCol;
			leftRotate( sibling );
		}
		else
		{
			sibling.color = sibling.left.color;
			sibling.left.color = tempCol;
			rightRotate( sibling );
		}
		
		if( isDone( node ) ){
			return;
		}
		else{
			removeCaseOne( node );
		}
	}

	/**
	 * Public Method: removeCaseFour
	 * This method represents remove case 4 where the sibling's color 
	 * is black and the sibling's external child is red. The 
	 * sibling's color becomes the parent's color and the parent's 
	 * color becomes black. Depending on if the sibling is a left or 
	 * right child, it performs the appropriate coloring and rotation 
	 * on the parent. Finally, the node is updated to be the root.
	 * 
	 * @param node The node being verified for remove case 4.
	 */
	public void removeCaseFour( Node node )
	{
		Node parent = node.parent;
		Node sibling = getSibling( node );
		sibling.color = parent.color;
		parent.color = Color.BLACK;
		if( sibling.isLeftChild() )
		{
			sibling.left.color = Color.BLACK;
			rightRotate( parent );
		}
		else
		{
			sibling.right.color = Color.BLACK;
			leftRotate( parent );
		}
		node = root;
	}
	
	/**
	 * Public Method: isDone
	 * Helper method for the remove cases that checks if the red black 
	 * tree follows the remove cases. If the node is the root or if 
	 * the node's color is red, the node's color becomes black and 
	 * remove is done.
	 * 
	 * @param node The node that helps verify if remove is finished.
	 * @return True if the tree is done removing.
	 *         False if the tree is not done removing and needs to 
	 *               verify the remove cases.
	 */
	public boolean isDone( Node node )
	{
		if( node == root || node.color == Color.RED )
		{
			node.color = Color.BLACK;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Public Method: lookup
	 * Finds the specified key in the red black tree and returns its 
	 * value.
	 * 
	 * @param key The specified key we want to find.
	 * @return The value of the key and null if it is not in the tree.
	 */
	public V lookup( K key )
	{
		return find( key, root );
	}
	
	/**
	 * Public Method: find
	 * Helper method for lookup that searches the tree for the node 
	 * represented by the specified key. It is a recursive method 
	 * that starts at the root and compares the current node's key 
	 * with the specified key. If the specified key is less than 
	 * the current node's key, then it moves to the left subtree. 
	 * Otherwise it moves to the right subtree. It continues until 
	 * it finds the node with the specified key or until it reaches 
	 * a null leaf.
	 * 
	 * @param key The key used to find the node.
	 * @param cur The current node whose key we compare to the 
	 *            specified key.
	 * @return The node of the specified key or null if it isn't in 
	 *         the tree.
	 */
	public V find( K key, Node cur )
	{
		if( cur.key.compareTo( key ) == 0 ){
			return cur.value;
		}
		else if( cur.key.compareTo( key ) < 0 ){
			find( key, cur.right );
		}
		else{
			find( key, cur.left );
		}
		return null;
	}
	
	/**
	 * Public Method: toPrettyString
	 * Creates a String of the red black tree that represents the nodes 
	 * in a pyramid fashion that helps visualize the tree. Depending on 
	 * the height of the tree, the amount of spaces is calculated for 
	 * each level and added to the String. The nodes are also added to 
	 * the string when necessary. The only problem is that the more 
	 * levels there are in the tree, the wider the tree becomes.
	 * 
	 * @return A String that contains a visual representation of the 
	 *         tree as a pyramid.
	 */
	public String toPrettyString() 
	{
		String str = "";
		int h = height( root ) - 1;
		int level = 1;
		while( h >= 0 )
		{
			for( int i = 0; h != 0 && i < Math.pow( 2, h ) - 1; i++ )
			{
				str += tabSize;
			}
			
			str += printLevel( root, level, h + 1, true);
			str += "\n";
			h--;
			level++;
		}

		return str;
	}
	
	
	/**
	 * Public Method: height
	 * Finds the height of the red black tree by traversing the 
	 * red black tree.
	 * 
	 * @param root Root of the red black tree.
	 * @return The height of the red black tree.
	 */
	public int height( Node root ) 
	{
		if( root == null ){
			return 0;
		}
		else 
		{
			int lheight = height( root.left );
			int rheight = height( root.right );
			
			if ( lheight > rheight ){
				return ( lheight + 1 );
			}
			else{
				return ( rheight + 1 );
			}
		}
	}

	
	/**
	 * Public Method: printLevel
	 * Adds the nodes on the given level of the red black tree 
	 * to a String. It is a recursive method that traverses the 
	 * tree by passing in level - 1. When level = 1, it means 
	 * that you reached the indicated level you wanted to add 
	 * to the String. When n = null, there is nothing to print 
	 * on that level.
	 * 
	 * @param n The current node in the tree.
	 * @param level The current level of the tree.
	 * @param depth The depth of the tree.
	 * @param right Boolean that determines if the tabs needed to 
	 *              be added to the right or not (otherwise it 
	 *              wraps around).
	 * @return A String that contains a visual representation of 
	 *         the tree as a pyramid.
	 */
	public String printLevel( Node n, int level, int depth, boolean right ) 
	{
		String str = "";
		if (n == null)
		{
			for( int j = 0; j < Math.pow( 2, depth); j++ )
			{
				str += tabSize;
			}
		}
		else if (level == 1)
		{
			String nStr = n.toString();
			int l = nStr.length();

			int half = ( int )( ( tabSize.length() - l ) / 2.0);
			for( int j = 0; j < half; j++ )
			{
				str += " ";
			}

			str += nStr;

			for( int j = 0; j < tabSize.length() - l - half; j++ )
			{
				str += " ";
			}
            
            if ( !right )
            {
				for( int j = 0; j < Math.pow( 2, depth ) - 1; j++ )
				{
					str += tabSize;
				}
			}
		}
		else if ( level > 1 ) 
		{
			str += printLevel( n.left, level - 1, depth, false );

			if ( right ){
			  str += printLevel( n.right, level - 1, depth, true );
			}
			else{
			  str += printLevel( n.right, level - 1, depth, false );
			}
		}
		return str;
	}

	/**
	 * Nested Class: Node
	 * 
	 * A linked list implementation of a Node object. It references its
	 * parent, left child, and right child. This node is generic and 
	 * contains a key of type K and a value of type V.
	 */
	public class Node
	{
		/**
		 * Private Variable: key
		 * Type: K
		 * Generic key of the node.
		 */
		private K key;
		/*
		 * Private Variable: value
		 * Type: V
		 * Generic value of the node.
		 */
		private V value;
		/*
		 * Private Variable: color
		 * Type: Color
		 * Color of the node.
		 */
		private Color color;
		/*
		 * Private Variable: parent
		 * Type: Node
		 * Parent of the node.
		 */
		private Node parent;
		/*
		 * Private Variable: left
		 * Type: Node
		 * Left child of the node.
		 */
		private Node left;
		/**
		 * Private Variable: right
		 * Type: Node
		 * Right child of the node.
		 */
		private Node right;
		
		/**
		 * Constructor: Node
		 * Creates a node object. It holds information about the node 
		 * including its key, value, color, parent, left child, and 
		 * right child. The left and right child are set to null. Its
		 * key, value, color, and parent are specified.
		 * 
		 * @param key Specified key.
		 * @param value Specified value.
		 * @param color Specified color.
		 * @param parent Specified parent.
		 */
		private Node( K key, V value, Color color, Node parent )
		{
			this.key = key;
			this.value = value;
			this.color = color;
			this.parent = parent;
			left = null;
			right = null;
		}
		
		/**
		 * Public Method: isLeftChild
		 * Checks if the node is a left child. It checks if the node 
		 * is the parent's left child.
		 * 
		 * @return True if the node is a left child.
		 *         False if the node is not a left child.
		 */
		public boolean isLeftChild()
		{
			if( parent == null ){
				return false;
			}
			return this == this.parent.left;
		}
		
		/**
		 * Public Method: isLeaf
		 * Checks if the node is a leaf. It is considered a leaf if 
		 * both its children's keys are null.
		 * 
		 * @return True if the node is a leaf.
		 *         False if the node is not a leaf.
		 */
		public boolean isLeaf()
		{
			if( left.key == null && right.key == null ){
				return true;
			}
			else{
				return false;
			}
		}
		
		/**
		 * Public Method: isInternal
		 * Checks if the node is an internal node. It is considered 
		 * internal if the node is a left child and the parent is 
		 * not a left child.
		 * 
		 * @return True if the node is internal.
		 *         False if the node is external or if the parent is null.
		 */
		public boolean isInternal()
		{
			if( parent == null ){
				return false;
			}
			return this.isLeftChild() != this.parent.isLeftChild();
		}
		
		/**
		 * Public Method: toString
		 * Creates a String of the node. Displays the node's key and 
		 * color.
		 * 
		 * @return The node represented as a String.
		 */
		public String toString()
		{
			String col = "(";
			if( this.color == Color.BLACK ){
				col += "B)";
			}
			else{
				col += "R)";
			}
			return this.key + col;
		}
	}
}
