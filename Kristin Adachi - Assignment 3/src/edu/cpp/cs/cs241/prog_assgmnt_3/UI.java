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

import java.util.Scanner;

/**
 * Class: UI
 * 
 * The UI class represents the user interface of the Red Black Tree 
 * application. It prompts the user to add or remove a number from the 
 * red black tree.
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
	 * Private Variable: tree
	 * Type: RedBlackTree
	 * Creates a red black tree.
	 */
	private RedBlackTree< Integer, String > tree = new RedBlackTree< Integer, String >();
	
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
	 * Public Method: intro
	 * Displays the introductory message.
	 */
	public void intro()
	{
		System.out.println( "Welcome to the Red Black Tree Program!" );
	}
	
	/**
	 * Public Method: menu
	 * Displays the main menu. Prompts the user to choose whether to
	 * add a number to the tree, remove a number from the tree, or 
	 * exit the program.
	 */
	public void menu()
	{
		System.out.println( "\n--------------------------" );
		System.out.println( "      Current Tree: " );
		System.out.println( "--------------------------\n\n"+ tree.toPrettyString() );
		System.out.println( "--------------------------" );
		System.out.println( " Please make a selection:" );
		System.out.println( "   1. Add a number" );
		System.out.println( "   2. Remove a number" );
		System.out.println( "   3. Check if a number is in the tree" );
		System.out.println( "   4. Exit" );
		System.out.print( "Enter a command: " );
		String choice = input.nextLine();
			
		switch( choice )
		{
			case "1":
				addMenu();
			case "2":
				removeMenu();
			case "3":
				lookupMenu();
			case "4":
				System.out.println( "\nThank you for using this Red Black Tree Program! (:" );
				input.close();
				System.exit( 0 );
			default:
				System.out.println( "\nInvalid Input. Please try again." );
				menu();
		}
	}
	
	/**
	 * Public Method: addMenu
	 * Prompts the user for a number to add to the red black tree.
	 */
	public void addMenu()
	{
		System.out.print( "Please enter a number to add: " );
		String num = input.nextLine();
		tree.add( Integer.valueOf( num ), num );
		menu();
	}
	
	/**
	 * Public Method: removeMenu()
	 * Prompts the user for a number to remove from the red black tree. 
	 * It also notifies the user if the number does not exist in the 
	 * tree.
	 */
	public void removeMenu()
	{
		try
		{
			System.out.print( "Please enter a number to remove: " );
			String num = input.nextLine();
			tree.remove( Integer.valueOf( num ) );
			menu();
		}
		catch( NullPointerException e )
		{
			System.out.println( "\nDoes not exist in the tree." );
			menu();
		}
	}
	
	/**
	 * Public Method: lookupMenu
	 * Prompts the user for a number and checks if that number is in 
	 * the red black tree.
	 */
	public void lookupMenu()
	{
		try
		{
			System.out.print( "Please enter a number to look up: " );
			String num = input.nextLine();
			tree.lookup( Integer.valueOf( num ) );
			System.out.println( "\n" + num + " exists in the tree." );
			menu();
		}
		catch( NullPointerException e )
		{
			System.out.println( "\nDoes not exist in the tree." );
			menu();
		}
	}
}
