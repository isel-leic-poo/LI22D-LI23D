package poo.demos.puzzle.model;

/**
 * Class whose instances hold information relative to piece movement events.
 */
public class PieceMovedEvent {

	/**
	 * The piece that was moved
	 */
	public final Piece piece;
	
	/**
	 * The movement origin
	 */
	public final Position from;
	
	/**
	 * The movement destination
	 */
	public final Position to;
	
	/**
	 * Initializes an instance with the given parameters.
	 * 
	 * @param piece the {@link Piece} instance that has been moved
	 * @param from the position from where the piece has been moved
	 * @param to the position where the piece has been moved to
	 * @throws IllegalArgumentException if any of the arguments is null 
	 */
	public PieceMovedEvent(Piece piece, Position from, Position to)
	{
		if(piece == null || from == null || to == null)
			throw new IllegalArgumentException();
		
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
}
