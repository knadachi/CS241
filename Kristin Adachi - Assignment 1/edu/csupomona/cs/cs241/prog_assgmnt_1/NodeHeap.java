/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Program Assignment #1: Heaps
 * 
 * <description-of-assignment>
 * 
 * The purpose of this assignment is to help us understand the 
 * implementation of a node heap that we have studied in class. 
 * A node heap is a heap that uses nodes with links to their 
 * parents and children. We were also required to maintain the 
 * same runtime complexity as its array counter part.
 * The second part of the assignment, the restaurant queue, 
 * demonstrates how we can implement a heap and use it as a 
 * priority queue.
 * 
 * @author Kristin Adachi
 */

package edu.csupomona.cs.cs241.prog_assgmnt_1;

import java.util.ArrayList;

/**
 * Class: NodeHeap
 * 
 * A node heap is a heap implementation that uses nodes instead 
 * of the more commonly used array data structure. It is more 
 * intuitive than using an array because it creates a visualization 
 * of what a heap would actually look like. This class uses 
 * binary numbers to essentially map out the heap that is explained 
 * in more detail in the add method.
 *
 * @param <V> Specified data type.
 */
public class NodeHeap< V extends Comparable<V> > implements Heap<V>
{
	/**
	 * Private Variable: root
	 * Type: Node
	 * Keeps track of the top element of the heap (the root).
	 */
	public Node root;
	
	/**
	 * Private Variable: size
	 * Type: int
	 * Initially set to zero and keeps track of the number of 
	 * elements in the heap.
	 */
	public int size;
	
	/**
	 * Constructor: NodeHeap
	 * Constructor that creates an empty NodeHeap. The root is set 
	 * to null and the size is set to 0.
	 */
	public NodeHeap()
	{
		root = null;
		size = 0;
	}
	
	/**
	 * Public Method: getSize
	 * Allows the user to access the size of the node heap.
	 * 
	 * @return The current size of the node heap.
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * Public Method: add
	 * Adds a new element into the next available spot in the node 
	 * heap. After it is added, it uses the siftup method to move 
	 * it to its correct place in the heap (according to the max 
	 * heap property).
	 * 
	 * @param value Adds the specified value to the node heap.
	 */
	public void add( V value ) 
	{
		if( size == 0 )
		{
			root = new Node( null, value );
			size++;
			return;
		}
		size++;
		Node cur = find( size / 2 );
		Node n = new Node( cur, value );
		if( size % 2 == 0 ){
			cur.left = n;
		}
		else{
			cur.right = n;
		}		
		siftup( n );
	}
	
	/**
	 * Public Method: siftup
	 * Siftup is a helper method for the NodeHeap class that 
	 * maintains its max heap property. It takes in a node, compares 
	 * it to other nodes based on priority, and swaps them when 
	 * necessary. The node is moved up within the heap until it 
	 * reaches a place where it follows the max heap property.
	 * 
	 * @param n The specified node that needs to be moved to its 
	 *          proper place.
	 */
	public void siftup( Node n )
	{
		Node cur = n;
		while( !cur.isRoot() && cur.parent.element.compareTo( cur.element ) < 0 )
		{
			swap( cur.parent, cur );
			cur = cur.parent;
		}
	}
	
	/**
	 * Public Method: find
	 * This method finds and returns the node at the specified 
	 * index. It uses binary in order to find where the node is 
	 * within the heap. We assume that our node heap is a complete 
	 * binary try where the root is considered index 1. Because of 
	 * this, we also consider that the indexes are breadth first 
	 * from left to right. With these assumptions, we can confirm 
	 * that using binary can find the node we are searching for. 
	 * The given index is translated to binary and traversed starting 
	 * from index 1 (we ignore the first bit because the root doesn't 
	 * move left or right). Each bit represents a direction: 0 
	 * indicates going left from the current node and 1 indicates 
	 * going right.
	 * 
	 * @param index Indicates the index of the node we need to locate.
	 * @return The node that is found at the indicated index.
	 */
	public Node find( int index )
	{
		Node cur = root;
		String bits = Integer.toBinaryString( index );
		for( int i = 1; i < bits.length(); i++ )
		{
			char ch = bits.charAt( i );
			if( ch == '0' ){
				cur = cur.left;
			}
			else if( ch == '1' ){
				cur = cur.right;
			}
		}
		return cur;
	}
	
	/**
	 * Public Method: siftdown
	 * Siftdown is a helper method for the NodeHeap class that 
	 * maintains its max heap property. It takes in a node, compares 
	 * it to other nodes based on priority, and swaps them when 
	 * necessary. The node is moved down the heap until it reaches a 
	 * place where it follows the max heap property.
	 * 
	 * @param n The specified node that needs to be moved to its 
	 *          proper place.
	 */
	public void siftdown( Node n )
	{
		Node cur = n;
		boolean notDone = true;
		while( notDone )
		{
			if( !cur.isLeaf() )
			{
				boolean hasChanged = false;
				if( cur.element.compareTo( cur.left.element ) < 0 ){
					hasChanged = true;
				}
				else if( cur.right != null && cur.element.compareTo( cur.right.element ) < 0 ){
					hasChanged = true;
				}
				if( hasChanged )
				{
					if( cur.right == null || cur.left.element.compareTo( cur.right.element ) > 0 )
					{
						swap( cur, cur.left );
						cur = cur.left;
					}
					else
					{
						swap( cur, cur.right );
						cur = cur.right;
					}
				}
				else{
					notDone = false;
				}
			}
			else{
				notDone = false;
			}
		}
	}
	
	/**
	 * Public Method: siftDownUntil
	 * SiftDownUntil is an alternative version of the sift down 
	 * method. It is used as a helper method for the method 
	 * getSortedContents. It performs essentially the same functions 
	 * as siftDown but is stopped at the indicated limit.
	 * 
	 * @param n The specified node that needs to be moved to its 
	 *          proper place.
	 * @param limit The limit that indicates when it should stop being 
	 *          sifted down.
	 */
	public void siftDownUntil( Node n, int limit )
	{
		Node cur = n;
		int curIndex = 1;
		boolean notDone = true;
		while( notDone )
		{
			if( !cur.isLeaf() )
			{
				boolean hasChanged = false;
				if( cur.element.compareTo( cur.left.element ) < 0 ){
					hasChanged = true;
				}
				else if( cur.right != null && cur.element.compareTo( cur.right.element ) < 0 ){
					hasChanged = true;
				}
				if( hasChanged )
				{
					if( cur.right == null || cur.left.element.compareTo( cur.right.element ) > 0 )
					{
						curIndex = 2 * curIndex;
						if( curIndex < limit )
						{
							swap( cur, cur.left );
							cur = cur.left;
						}
					}
					else
					{
						curIndex = ( 2 * curIndex ) + 1;
						if( curIndex < limit )
						{
							swap( cur, cur.right );
							cur = cur.right;
						}
					}
					if( curIndex > limit ){
						notDone = false;
					}
				}
				else{
					notDone = false;
				}
			}
			else{
				notDone = false;
			}
		}
	}
	
	/**
	 * Public Method: swap
	 * This method swaps the elements of two given nodes (doesn't 
	 * swap the nodes themselves).
	 * 
	 * @param a First node to be swapped.
	 * @param b Second node to be swapped.
	 */
	public void swap( Node a, Node b )
	{
		V temp = a.element;
		a.element = b.element;
		b.element = temp;
	}
	
	/**
	 * Public Method: toArray
	 * Takes the array and converts it to a node heap by using the 
	 * fromArray method. It is then converted back into an array that 
	 * represents a heap that follows the max heap property.
	 * 
	 * @return An array of type V that represents a heap that follows 
	 *         the max heap property.
	 */
	@SuppressWarnings( "unchecked" )
	public V[] toArray( V[] array ) 
	{
		if( size == 0 ){
			return null;
		}	
		fromArray( array );
		V[] arr = (V[]) java.lang.reflect.Array.newInstance( array.getClass().getComponentType(), array.length );
		ArrayList<Node> queue = new ArrayList<Node>();
		queue.add( root );
		int curIndex = 0;
		Node curNode = null;
		while( !queue.isEmpty() )
		{
		   curNode = queue.remove( 0 );
		   arr[ curIndex ] = curNode.element;

		   if( curNode.left != null ){
		      queue.add( curNode.left );
		   }

		   if( curNode.right != null ){
		      queue.add( curNode.right );
		   }
		   curIndex++;
		}
		return arr;
	}

	/**
	 * Public Method: remove
	 * Removes the top element of the node heap. It does so by swapping 
	 * the root with the last node, deleting the last node, and 
	 * performing siftdown on the new root.
	 * 
	 * @return The element of the node that was removed.
	 */
	public V remove() 
	{
		if( size == 0 ){
			return null;
		}
		Node last = find( size );
		if( last.isRoot() )
		{
			V removed = root.element;
			root = null;
			size--;
			return removed;
		}
		swap( root, last );
		V removed = last.element;
		if( size % 2 == 0 ){
			last.parent.left = null;
		}
		else{
			last.parent.right = null;
		}
		siftdown( root );
		size--;
		return removed;
	}

	/**
	 * Public Method: fromArray
	 * Traverses the given array and adds its elements into the node 
	 * heap that follows the max heap property.
	 */
	public void fromArray( V[] array ) 
	{
		for( int i = 0; i < array.length; i++ )
		{
			add( array[i] );
		}
	}

	/**
	 * Public Method: getSortedContents
	 * Converts the given array into a node heap by using the fromArray 
	 * method. It then sorts this heap from least to greatest according 
	 * to its elements.
	 * 
	 * @return An array of type V whose contents are sorted from least 
	 *         to greatest.
	 */
	@SuppressWarnings( "unchecked" )
	public V[] getSortedContents( V[] array ) 
	{
		fromArray( array );
		ArrayList<Node> sorted = new ArrayList<Node>();
		for( int i = size; i > 0; i-- )
		{
			Node cur = find( i );
			swap( root, cur );
			siftDownUntil( root, i );
			sorted.add( cur );
		}
		
		V[] arr = (V[]) java.lang.reflect.Array.newInstance( array.getClass().getComponentType(), size );
		Node curNode = null;
		int curIndex = 0;
		while( !sorted.isEmpty() )
		{
			curNode = sorted.remove( 0 );
			arr[ curIndex ] = curNode.element;
			curIndex++;
		}	
		return arr;
	}
	
	/**
	 * Nested Class: Node
	 * 
	 * A linked list implementation of a Node object. It references its 
	 * parent, left child, and right child. This node is generic and 
	 * contains an element of type V (can be any data type).
	 */
	public class Node
	{
		/**
		 * Type: Node
		 * Left child of the node.
		 */
		Node left;
		/**
		 * Type: Node
		 * Right child of the node.
		 */
		Node right;
		/**
		 * Type: Node
		 * Parent of the node.
		 */
		Node parent;
		/**
		 * Type: V
		 * Generic element of the node.
		 */
		V element;
		
		/**
		 * Constructor: Node
		 * Creates a node object. It holds information about the node 
		 * including its left child, right child, parent, and element. 
		 * The left and right children are originally set to null and its 
		 * parent and element are specified.
		 * 
		 * @param parent Allows the user to enter a specified parent.
		 * @param element Allows the user to enter a specified element.
		 */
		public Node( Node parent, V element )
		{
			left = null;
			right = null;
			this.parent = parent;
			this.element = element;
		}		
		
		/**
		 * Public Method: isLeaf
		 * Checks if the node is a leaf or not. It is considered a leaf if 
		 * it doesn't have a left or right child.
		 * 
		 * @return True if the node is a leaf.
		 *         False if the node isn't a leaf.
		 */
		public boolean isLeaf()
		{
			if( this.left == null && this.right == null ){
				return true;
			}
			return false;
		}
		
		/**
		 * Public Method: isRoot
		 * Checks if the node is a leaf or not. It is considered the node 
		 * if its parent is null.
		 * 
		 * @return True if the node is the root.
		 *         False if the node isn't the root.
		 */
		public boolean isRoot()
		{
			if( this.parent == null ){
				return true;
			}
			return false;
		}
		
		/**
		 * Public Method: getElement
		 * Allows the user to access the node's element.
		 * 
		 * @return The element of the node.
		 */
		public V getElement()
		{
			return element;
		}
	}
}
