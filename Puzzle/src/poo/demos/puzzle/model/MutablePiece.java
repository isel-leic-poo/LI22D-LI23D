package poo.demos.puzzle.model;

public class MutablePiece extends Piece {

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
	public MutablePiece(int x, int y)
	{
		super(x, y);

		currentX = x;
		currentY = y;
	}
	
	/**
	 * Gets the piece's current horizontal coordinate.
	 * 
	 * @return The horizontal coordinate value
	 */
	@Override
	public int getX()
	{
		return currentX;
	}
	
	/**
	 * Gets the piece's current vertical coordinate.
	 * 
	 * @return The vertical coordinate value
	 */
	@Override
	public int getY()
	{
		return currentY;
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
}
