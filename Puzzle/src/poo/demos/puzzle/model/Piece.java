package poo.demos.puzzle.model;

/**
 * Base abstract class for representing puzzle pieces.
 * 
 * Puzzle pieces have an initial position (which is considered the correct 
 * piece position) and a current position. Positions are represented as 
 * rectangular coordinates (i.e. x and y) that must be positive values (i.e. >= 0).
 * 
 * This class establishes the public interface for all Piece instances and contains 
 * the implementation of the corresponding canonical methods (i.e. equivalence)  
 */
public abstract class Piece {

	/**
	 * Gets the piece's initial horizontal coordinate.
	 * 
	 * @return The horizontal coordinate value
	 */
	public abstract int getInitialX();
	
	/**
	 * Gets the piece's initial vertical coordinate.
	 * 
	 * @return The vertical coordinate value
	 */
	public abstract int getInitialY();
	
	/**
	 * Checks if the piece is at its correct (initial) position.
	 * 
	 * @return {@code true} if the piece is at its initial position, {@code false}
	 * otherwise 
	 */
	public boolean isAtCorrectPosition()
	{
		return getX() == getInitialX() && getY() == getInitialY(); 
	}

	/**
	 * Gets the piece's current horizontal coordinate.
	 * 
	 * @return The horizontal coordinate value
	 */
	public abstract int getX();
	
	/**
	 * Gets the piece's current vertical coordinate.
	 * 
	 * @return The vertical coordinate value
	 */
	public abstract int getY();

	// Canonical methods
	
	/**
	 * {@see java.lang.Object#toString}
	 */
	@Override
	public String toString() 
	{
		return "{ initial=(" + getInitialX() + "," + getInitialY() 
				+ "); current=(" + getX() + "," + getY() + ") }";
	}	
	
	/**
	 * {@see java.lang.Object#hashCode}
	 */
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
	 * 
	 * {@see java.lang.Object#equals}
	 */
	@Override
	public boolean equals(Object other) 
	{
		if(!(other instanceof Piece))
			return false;
		
		if(this == other)
			return true;
		
		Piece otherPiece = (Piece) other;
		return 	this.getInitialX() == otherPiece.getInitialX() 
				&& this.getInitialY() == otherPiece.getInitialY() 
				&& this.getX() == otherPiece.getX() 
				&& this.getY() == otherPiece.getY();
	}
}
