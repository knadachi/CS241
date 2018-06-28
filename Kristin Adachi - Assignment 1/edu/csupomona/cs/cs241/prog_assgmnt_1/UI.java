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

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Class: UI
 * 
 * The UI class represents the user interface of the Restaurant 
 * application (exercise 2). It prompts the user to add a party  
 * to the waiting list, seat the next party on the waiting list, 
 * or display the current waiting list of the restaurant.
 */
public class UI 
{
	/**
	 * Private Variable: input
	 * Type: Scanner
	 * Opens the Scanner that takes in user input.
	 */
	private Scanner input = new Scanner( System.in );
	/**
	 * Private Variable: list
	 * Type: NodeHeap
	 * Creates a node heap that will be used as a priority queue 
	 * for the restaurant.
	 */
	private NodeHeap< Customer > list = new NodeHeap< Customer >();
	
	/**
	 * Constructor: UI
	 * Creates a UI that displays the intro and starts the user 
	 * interface.
	 */
	public UI() 
	{
		intro();
	}
	
	/**
	 * Public Method: start
	 * Runs the main menu of the program.
	 */
	public void start()
	{
		menu();
	}
	
	/**
	 * Public Method: intro
	 * Displays the introductory message.
	 */
	public void intro()
	{
		System.out.println( "Welcome to the restaurant!\n" );
	}
	
	/**
	 * Public Method: menu
	 * Displays the main menu. Prompts the user to choose whether to 
	 * add a customer to the waiting list, seat a customer, display 
	 * the current waiting list, or exit the program.
	 */
	public void menu()
	{
		int choice = 0;
		try
		{
			System.out.println( "Please make a selection:" );
			System.out.println( "   1. Add a customer to the waiting list" );
			System.out.println( "   2. Seat a customer" );
			System.out.println( "   3. Display waiting list" );
			System.out.println( "   4. Exit" );
			System.out.print( "Enter a command: " );
			choice = input.nextInt();
			input.nextLine();
			
			switch( choice )
			{
				case 1:
					addEntry();
				case 2:
					removeEntry();
				case 3:
					System.out.println( getList() );
					menu();
				case 4:
					System.out.println( "\nThank you for using the restaurant program! (:\n" );
					System.exit( 0 );
				default:
					System.out.println( "\nInvalid Input. Please try again.\n" );
					menu();
			}
			menu();
		}
		catch( InputMismatchException e )
		{
			System.out.println( "\nInvalid Input. Please try again.\n" );
			input.nextLine();
			menu();
		}
	}
	
	/**
	 * Public Method: addEntry
	 * Prompts the user for a customer name and their priority. It then 
	 * creates a customer with the specified name and priority and adds 
	 * it to the priority heap.
	 */
	public void addEntry()
	{
		System.out.print( "Please enter the customer's name: " );
		String name = input.nextLine();
		int priority = priorityMenu();
		Customer c = new Customer( name, priority );
		list.add( c );
		System.out.println( "\n" + c.getName() + "'s party has been added to the waiting list.\n" );
		menu();
	}
	
	/**
	 * Public Method: priorityMenu
	 * Displays the priority menu and prompts the user to choose what 
	 * type of customer they are adding.
	 * 
	 * @return The specified priority of the customer.
	 */
	public int priorityMenu()
	{
		int choice = 0;
		try
		{
			System.out.println( "Please choose customer type: " );
			System.out.println( "   1. VIP" );
			System.out.println( "   2. Reservation (called in advance)" );
			System.out.println( "   3. Seniors" );
			System.out.println( "   4. Veterans" );
			System.out.println( "   5. Large Groups (more than 4 in the party)" );
			System.out.println( "   6. Families with Children" );
			System.out.println( "   7. Other" );
			System.out.print( "Enter the priority (1-7): " );
			choice = input.nextInt();
			
			if( choice > 7 || choice < 1 )
			{
				System.out.println( "\nInvalid Input. Please try again.\n" );
				priorityMenu();
			}
		}
		catch( InputMismatchException e )
		{
			System.out.println( "\nInvalid Input. Please try again.\n" );
			input.nextLine();
			priorityMenu();
		}
		
		return choice;
	}
	
	/**
	 * Public Method: removeEntry
	 * Removes the next customer on the waiting list and the priority 
	 * queue. It also displays who was removed from the list.
	 */
	public void removeEntry()
	{
		Customer c = list.remove();
		if( c == null )
		{
			System.out.println( "\nNo one is on the current waiting list.\n" );
			menu();
		}
		System.out.println( "\n" + c.getName() + "'s party is ready to be seated.\n" );
		menu();
	}
	
	/**
	 * Public Method: getList
	 * Displays the current waiting list of the restaurant. This method 
	 * uses NodeHeap's getSortedContents method to sort the heap and 
	 * create an array of customers ordered from least to greatest by 
	 * their priority.
	 * 
	 * @return A String of customers (representing the waiting list).
	 */
	public String getList()
	{
		Customer[] waitlist = new Customer[ list.getSize() ];
		String str = "\nThe waiting list:\n";
		for( int i = 1; i <= waitlist.length; i++ )
		{
			waitlist[ i-1 ] = list.find( i ).getElement();
		}
		NodeHeap< Customer > temp = new NodeHeap< Customer >();
		waitlist = temp.getSortedContents( waitlist );
		if( temp.getSize() == 0 ){
			return "\nNo one is on the current waiting list.\n";
		}
		for( int i = 0; i < waitlist.length; i++ )
		{
			int num = i + 1;
			str += "   " + num + ". " + waitlist[ i ].getName() + "\n";
		}
		return str;
	}
}
