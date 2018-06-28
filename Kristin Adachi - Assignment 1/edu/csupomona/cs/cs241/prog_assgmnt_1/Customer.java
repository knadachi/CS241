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
 * Class: Customer
 * 
 * This class creates a Customer object. It holds information 
 * about the customer including their name and their priority 
 * (used to determine their place on the waiting list for the 
 * restaurant). The customers place on the waiting list is 
 * determined by their priority.
 */

public class Customer implements Comparable<Customer>
{
	/**
	 * Private Variable: name
	 * Type: String
	 * Stores the name of the customer.
	 */
	private String name;
	/**
	 * Private Variable: priority
	 * Type: int
	 * Stores the priority of the variable
	 */
	private int priority;

	/**
	 * Constructor: Customer
	 * Constructor that creates a customer with a specified 
	 * name and priority.
	 *
	 * @param n Allows user to enter a specified name.
	 * @param p Allows user to enter a specified priority.
	 */
	public Customer( String n, int p )
	{
		name = n;
		priority = p;
	}

	/**
	 * Public Method: getName
	 * Allows the user to access the name of the customer.
	 * 
	 * @return The name of the customer.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Public Method: getPriority
	 * Allows the user to access the priority of the customer.
	 * 
	 * @return The priority of the customer.
	 */
	public int getPriority()
	{
		return priority;
	}

	/**
	 * Public Method: compareTo
	 * Compares the priorities of two customers. For this program, 
	 * a higher priority is represented by a lower number (1 has 
	 * the highest priority and 7 has the lowest priority in terms 
	 * of the restaurant application). It returns 1 if the customer 
	 * passed in has a higher priority than the current customer, 
	 * -1 if it has a lower priority, and 0 if it has the same 
	 * priority.
	 * 
	 * @return  The corresponding comparison of the two customers.
	 */
	@Override
	public int compareTo( Customer c ) 
	{
		if( this.getPriority() < c.getPriority() ){
			return 1;
		}
		else if( this.getPriority() > c.getPriority() ){
			return -1;
		}
		return 0;
	}
}
