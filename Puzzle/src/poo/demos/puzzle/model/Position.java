package poo.demos.puzzle.model;

/**
 * Class whose immutable instances represent puzzle's positions. Coordinates are
 * expressed as rectangular coordinates that must always have non-negative values.
 * 
 *	TODO: Consider the possibility of implementing a cache... 
 */
public class Position {
	
	/**
	 * The horizontal coordinate.
	 */
	public final int X;
	
	/**
	 * The vertical coordinate.
	 */
	public final int Y;
	
	/**
	 * Initiates an instance with the given coordinates.
	 * 
	 * @param x The horizontal coordinate
	 * @param y The vertical coordinate
	 * @throws IllegalArgumentException if any of the coordinates has a negative value 
	 */
	private Position(int x, int y)
	{
		if(x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		X = x;
		Y = y;
	}
	
	@Override
	public int hashCode() 
	{
		return this.toString().hashCode();
	}

	/**
	 * Checks if the instance is equivalent to the given one.
	 * Two move instances are equivalent if their coordinates are the same. 
	 * 
	 * @param other the other instance to use in the equivalence check
	 * @return {@code true} if the current instance is equivalent to the given one,
	 * {@code false} otherwise 
	 */
	@Override
	public boolean equals(Object other) 
	{
		if(!(other instanceof Position))
			return false;
		
		Position otherPosition = (Position) other;
		if(this == other)
			return true;
		
		return this.X == otherPosition.X && this.Y == otherPosition.Y;
	}

	@Override
	public String toString() 
	{
		return new StringBuilder("(").append(X).append(',').append(Y).append(')').toString();
	}
	
	/**
	 * Produces an instance with the given coordinates.
	 * 
	 * @param x The horizontal coordinate
	 * @param y The vertical coordinate
	 * @throws IllegalArgumentException if any of the coordinates has a negative value 
	 */
	public static Position fromCoordinates(int x, int y)
	{
		return new Position(x, y);
	}
}
