package poo.demos.puzzle.model;

import java.util.LinkedList;

/**
 * Class that stands as a facade to the puzzle model subsystem.
 */
public class Puzzle {
	
	/**
	 * Contract to be supported by listeners of {@link Grid} instance
	 * state modifications.  
	 */
	public static interface OnModificationListener
	{
		/**
		 * Callback method that signals that a piece has moved.
		 * 
		 * @param evt The object containing the event information
		 */
		public void onPieceMoved(PieceMovedEvent evt);
	}
	
	/**
	 * Holds the list of listeners.
	 */
	private final LinkedList<OnModificationListener> listeners;
	
	/**
	 * Fires notifications of piece moved events.
	 *  
	 * @param evt the object containing the event information
	 */
	private void firePieceMovedEvent(PieceMovedEvent evt)
	{
		for(OnModificationListener listener : listeners)
			listener.onPieceMoved(evt);
	}
	
	/**
	 * Registers the given listener to receive the corresponding events.
	 * 
	 * @param listener The listener instance
	 */
	public void registerOnModificationListener(OnModificationListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Unregisters the given listener. From that point on, the listener will no longer
	 * receive {@link PieceMovedEvent} events.
	 * 
	 * @param listener The listener to remove from the list of registered listeners
	 */
	public void unregisterOnModificationListener(OnModificationListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Holds the puzzle's grid instance.
	 */
	private final Grid grid;

	/**
	 * Initiates a puzzle instance with the given dimension and eventually shuffled.
	 * 
	 * @param side The puzzle's dimension (i.e. its side)
	 * @param shuffled A boolean value indicating if the puzzle instance is to be
	 * shuffled upon instantiation
	 */
	public Puzzle(int side, boolean shuffled)
	{
		grid = shuffled ? Grid.createRandomPuzzle(side) : Grid.createPuzzle(side);
		listeners = new LinkedList<Puzzle.OnModificationListener>();
	}
	
	/**
	 * Gets the piece at the given position. If the position is free,
	 * the method returns {@code null}.
	 * 
	 * @param position the {@link Position} instance
	 * @return the piece at the given position, or {@code null} if that position 
	 * is empty
	 * @throws IllegalArgumentException if the given position is not within the 
	 * grid's bounds 
	 */
	public Piece getPieceAtPosition(Position position)
	{
		return grid.getPieceAtPosition(position);
	}
	
	/**
	 * Gets the puzzle's size.
	 * 
	 * @return the puzzle's size
	 */
	public int getSize()
	{
		return grid.getSize();
	}
	
	/**
	 * Moves the given piece to the puzzle's empty space, assuming that the piece 
	 * is adjacent to it.
	 * 
	 * @param piece The piece to be moved
	 * @return {@code true} if the piece has been moved, {@code false} if the piece 
	 * cannot be moved, that is, it is not adjacent to the empty space.
	 */
	public boolean doMove(Piece piece)
	{
		Position from = piece.getPosition();
		boolean moved = grid.doMove(piece);

		if(moved)
			firePieceMovedEvent(new PieceMovedEvent(piece, from, piece.getPosition()));
		
		return moved;
	}	
}
