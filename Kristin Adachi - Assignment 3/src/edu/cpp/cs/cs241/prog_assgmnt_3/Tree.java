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
 * Interface: Tree
 * 
 * Given interface that we were required to implement in RedBlackTree. 
 * It contains abstract methods that are explained in more detail 
 * when implemented in RedBlackTree.
 *
 * @param <K> Specified data type for the key.
 * @param <V> Specified data type for the value.
 */
public interface Tree< K extends Comparable<K>, V >
{
	public void add( K key, V value );
	public V remove( K key );
	public V lookup( K key );
	public String toPrettyString();
}
