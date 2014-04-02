package poo.demos.puzzle.model;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class whose instances represent the moves made so far in the game.
 * The stack is implemented as a linked list.
 */
public class MovesStack {

	private final LinkedList<Move> container = new LinkedList<Move>();
	
	/**
	 * Pushes the given element to the top of the stack.
	 * 
	 * @param move The element to add at the top of the stack
	 * @throws IllegalArgumentException if the {@code move} argument is {@code null}
	 */
	public void push(Move move)
	{
		if(move == null)
			throw new IllegalArgumentException();

		container.addFirst(move);
	}
	
	/**
	 * Removes the element currently at the top of the stack.
	 * 
	 * @return The removed element
	 * @throws IllegalStateException if the stack is empty
	 */
	public Move pop()
	{
		if(isEmpty())
			throw new IllegalStateException();

		return container.removeFirst();
	}
	
	/**
	 * Gets the element currently at the top of the stack, or null if none exists.
	 * 
	 * @return The element at the top of the stack, or {@code null} if the stack 
	 * is empty
	 */
	public Move top()
	{
		return !container.isEmpty() ? container.getFirst() : null;
	}

	/**
	 * Gets a boolean value indicating whether the stack is empty.
	 * 
	 * @return {@code true} if the stack is empty, {@code false} otherwise 
	 */
	public boolean isEmpty()
	{
		return container.isEmpty();
	}
	
	/**
	 * Gets the current number of elements.
	 * 
	 * @return the number of elements stored in the stack
	 */
	public int getSize()
	{
		return container.size();
	}
	
	public Iterator<Move> iterator()
	{
		return container.iterator();
	}
	
	
	
	
	
	
	
	
	
}
