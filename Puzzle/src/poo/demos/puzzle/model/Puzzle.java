package poo.demos.puzzle.model;

import java.util.Iterator;

/**
 * Class that stands as a facade to the puzzle model subsystem.
 */
public class Puzzle implements Iterable<Piece> {
	
	/**
	 * Holds the puzzle's grid instance.
	 */
	private final Grid grid;

	/**
	 * Initiates a puzzle instance with the given grid.
	 * 
	 * @param grid The grid instance
	 */
	public Puzzle(Grid grid)
	{
		this.grid = grid;
	}
	
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
	 * Gets the position of the puzzle's empty space.
	 * 
	 * @return The current position of the puzzle's empty space
	 */
	public Position getEmptySpacePosition()
	{
		return grid.getEmptySpacePosition();
	}
	
	/**
	 * Gets the puzzle's size, that is, the number of pieces in each side.
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
		return grid.doMove(piece);
	}
	
	/**
	 * Gets an iterator for the puzzle's pieces. The empty space is not included
	 * in the iterated sequence.
	 * 
	 * @return the iterator for the puzzle's pieces.
	 */
	@Override
	public Iterator<Piece> iterator()
	{
		return grid.iterator(); 
	}
}
