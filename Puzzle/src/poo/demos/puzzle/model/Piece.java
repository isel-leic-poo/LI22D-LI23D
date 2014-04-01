package poo.demos.puzzle.model;

/**
 * Class whose instances represent puzzle pieces. 
 * 
 * Puzzle pieces have an initial position (which is considered the correct 
 * piece position) and a current position. Positions are represented as 
 * rectangular coordinates (i.e. x and y) that must be positive values (i.e. >= 0).
 */
public class Piece extends Object {

	/**
	 * The instance's correct coordinates.
	 */
	private final int correctX, correctY;
	
	/**
	 * The instance's current coordinates.
	 */
	private int currentX, currentY;
	
	/**
	 * Initiates an instance with the given coordinates.
	 * 
	 * @param x The horizontal coordinate value
	 * @param y The vertical coordinate value
	 * @throws IllegalArgumentException if either coordinate has a negative value
	 */
	public Piece(int x, int y)
	{
		if(x < 0 || y < 0)
			throw new IllegalArgumentException();

		correctX = currentX = x;
		correctY = currentY = y;
	}
	
	/**
	 * Initiates an instance by copying the contents of the given piece.
	 * 
	 * @param p The piece to be copied
	 * @throw IllegalArgumentException if the argument is {@code null}
	 */
	public Piece(Piece p)
	{
		if(p == null)
			throw new IllegalArgumentException();
		
		correctX = p.correctX;
		correctY = p.correctY;
		currentX = p.currentX;
		currentY = p.currentY;
	}

	/**
	 * Gets the piece's current horizontal coordinate.
	 * 
	 * @return The horizontal coordinate value
	 */
	public int getX()
	{
		return currentX;
	}
	
	/**
	 * Gets the piece's current vertical coordinate.
	 * 
	 * @return The vertical coordinate value
	 */
	public int getY()
	{
		return currentY;
	}
	
	/**
	 * Gets the piece's initial horizontal coordinate.
	 * 
	 * @return The horizontal coordinate value
	 */
	public int getInitialX()
	{
		return correctX;
	}
	
	/**
	 * Gets the piece's initial vertical coordinate.
	 * 
	 * @return The vertical coordinate value
	 */
	public int getInitialY()
	{
		return correctY;
	}

	/**
	 * Checks if the piece is at its correct (initial) position.
	 * 
	 * @return {@code true} if the piece is at its initial position, {@code false}
	 * otherwise 
	 */
	public boolean isAtCorrectPosition()
	{
		return currentX == correctX && currentY == correctY; 
	}
	
	/**
	 * Sets the instance's current position to the given coordinate values.
	 * 
	 * @param x the horizontal coordinate value
	 * @param y the vertical coordinate value
	 * @throws IllegalArgumentException if either coordinate has a negative value
	 */
	public void setPosition(int x, int y)
	{
		if(x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		currentX = x;
		currentY = y;
	}
	
	/**
	 * Moves the instance by the given distance.
	 * 
	 * @param deltaX the distance's horizontal coordinate. Positive values
	 * move the piece to the right and negative values move it to the left 
	 * @param deltaY the distance's vertical coordinate. Positive values
	 * move the piece down and negative values move it up 
	 * @throws IllegalStateException if the resulting position is an illegal one,
	 * that is, it has a negative value in one of its coordinates
	 */
	public void move(int deltaX, int deltaY)
	{
		if(currentX + deltaX < 0 || currentY + deltaY < 0)
			throw new IllegalStateException();
		
		currentX += deltaX;
		currentY += deltaY;
	}
	
	@Override
	public String toString() 
	{
		return "{ initial=(" + correctX + "," + correctY 
				+ "); current=(" + currentX + "," + currentY + ") }";
	}
	
	// Canonical methods
	
	@Override
	public int hashCode() 
	{
		return this.toString().hashCode();
	}

	/**
	 * Checks if the instance is equivalent to the given one.
	 * Two piece instances are equivalent if their coordinates (initial and current)
	 * have the same values.  
	 * 
	 * @param other the other instance to use in the equivalence check
	 * @return {@code true} if the current instance is equivalent to the given one,
	 * {@code false} otherwise 
	 */
	@Override
	public boolean equals(Object other) 
	{
		if(!(other instanceof Piece))
			return false;
		
		if(this == other)
			return true;
		
		Piece otherPiece = (Piece) other;
		return 	this.correctX == otherPiece.correctX 
				&& this.correctY == otherPiece.correctY
				&& this.currentX == otherPiece.currentX 
				&& this.currentY == otherPiece.currentY;
	}
}
