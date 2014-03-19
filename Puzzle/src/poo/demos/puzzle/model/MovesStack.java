package poo.demos.puzzle.model;

public class MovesStack {

	private static class Node
	{
		public Node next;
		public Move elem;
	}
	
	private Node head = null;
	
	public void push(Move move)
	{
		if(move == null)
			throw new IllegalArgumentException();
		
		// Insert at top of stack
		Node newNode = new Node();
		newNode.elem = move;
		newNode.next = head;
		head = newNode;
	}
	
	public Move pop()
	{
		if(isEmpty())
			throw new IllegalStateException();
		
		Move moveAtTop = head.elem;
		head = head.next;
		return moveAtTop;
	}
	
	public Move top()
	{
		return !isEmpty() ? head.elem : null;
	}

	public boolean isEmpty()
	{
		return head == null;
	}
}
