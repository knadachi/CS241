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
 * Class: TreeRunner
 * 
 * This class contains the main method that runs the program. It 
 * creates a UI that calls a method to begin the program.
 */
public class TreeRunner 
{
	public static void main(String[] args) 
	{
		UI ui = new UI();
		ui.menu();
	}
}
