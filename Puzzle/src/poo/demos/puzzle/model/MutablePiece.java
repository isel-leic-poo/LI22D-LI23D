package poo.demos.puzzle.model;

/**
 * Class whose instances represent puzzle pieces which can be moved around.
 */
public class MutablePiece extends Piece {

	/**
	 * The instance's initial position.
	 */
	private final Position initialPosition;
	
	/**
	 * The instance's current position.
	 */
	private Position currentPosition;
	
	/**
	 * Initiates an instance with the given coordinates.
	 * 
	 * @param x The horizontal coordinate value
	 * @param y The vertical coordinate value
	 * @throws IllegalArgumentException if either coordinate has a negative value
	 */
	public MutablePiece(int x, int y)
	{
		this(Position.fromCoordinates(x, y));
	}

	/**
	 * Initiates an instance with the given position.
	 * 
	 * @param position The instance's initial position
	 */
	public MutablePiece(Position position)
	{
		initialPosition = currentPosition = position;
	}

	/**
	 * Gets the piece's initial position.
	 * 
	 * @return The instance's initial position
	 */
	@Override
	public Position getInitialPosition() 
	{
		return initialPosition;
	}
	
	/**
	 * Gets the piece's current position.
	 * 
	 * @return The instance's current position
	 */
	@Override
	public Position getPosition() 
	{
		return currentPosition;
	}
	
	/**
	 * Moves the instance's to the given position.
	 * 
	 * @param newPosition the instance's new position
	 */
	public void moveTo(Position newPosition)
	{
		currentPosition = newPosition;
	}
	
	/**
	 * Moves the instance by the given distance.
	 * 
	 * @param delta the distance that the instance will move. 
	 * @throws IllegalStateException if the resulting position is an illegal one,
	 * that is, it has a negative value in one of its coordinates
	 */
	public void moveBy(Move.Delta delta)
	{
		try {
			currentPosition = Position.fromCoordinates(currentPosition.X + delta.X, currentPosition.Y + delta.Y);
		}
		catch(IllegalArgumentException invalidDelta)
		{
			// Convert exception to convey the correct semantics 
			throw new IllegalStateException(invalidDelta);
		}
	}
}
