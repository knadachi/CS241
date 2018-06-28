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

/**
 * Given interface that we were required to implement in NodeHeap. 
 * It contains abstract methods that are explained in more detail 
 * when implemented in NodeHeap.
 * 
 * @param <V> Specified data type.
 */
public interface Heap< V extends Comparable<V> >
{
	public void add( V value );
	public V[] toArray( V[] array );
	public V remove();
	public void fromArray( V[] array );
	public V[] getSortedContents( V[] array );
}
