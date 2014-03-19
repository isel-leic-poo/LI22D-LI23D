package poo.demos.puzzle.model;

/**
 * Class whose instances represent the moves made so far in the game.
 * The stack is implemented as a linked list.
 */
public class MovesStack {

	/**
	 * Class that represents linked list nodes. 
	 */
	private static class Node
	{
		/**
		 * The next node in the list, or null if none exists.
		 */
		public Node next;
		
		/**
		 * The move stored in the node.
		 */
		public Move elem;
	}
	
	/**
	 * Holds the top of the stack.
	 */
	private Node head = null;
	
	/**
	 * Holds the current size of the stack.
	 */
	private int size = 0;
	
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
		
		// Create node instance
		Node newNode = new Node();
		newNode.elem = move;
		newNode.next = head;
		// Add it to the top of the stack
		head = newNode;
		size += 1;
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
		
		Move moveAtTop = head.elem;
		head = head.next;
		size -= 1;
		return moveAtTop;
	}
	
	/**
	 * Gets the element currently at the top of the stack, or null if none exists.
	 * 
	 * @return The element at the top of the stack, or {@code null} if the stack 
	 * is empty
	 */
	public Move top()
	{
		return !isEmpty() ? head.elem : null;
	}

	/**
	 * Gets a boolean value indicating whether the stack is empty.
	 * 
	 * @return {@code true} if the stack is empty, {@code false} otherwise 
	 */
	public boolean isEmpty()
	{
		return head == null;
	}
	
	/**
	 * Gets the current number of elements.
	 * 
	 * @return the number of elements stored in the stack
	 */
	public int getSize()
	{
		return size;
	}
}
