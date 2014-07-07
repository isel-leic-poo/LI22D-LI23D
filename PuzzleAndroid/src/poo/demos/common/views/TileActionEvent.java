package poo.demos.common.views;

/**
 * Class whose immutable instances contain information regarding tile 
 * action events.
 */
public class TileActionEvent {

	/**
	 * Enumeration used to identify the swipe direction.
	 * 
	 * Implementation note: The order of declaration is a part of the contract; it
	 * must not be modified.
	 */
	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	/**
	 * The {@link Tile} instance that was acted upon.
	 */
	public final Tile source;
	
	/**
	 * The column where the tile is located in the grid.
	 */
	public final int column;
	
	/**
	 * The row where the tile is located in the grid.
	 */
	public final int row;
	
	/**
	 * The swipe's direction. 
	 */
	public final Direction swipeDirection;
	
	/**
	 * Initiates an instance with the given arguments. The new instance's swipe 
	 * direction is {@code null}.
	 * 
	 * @param source the {@link Tile} instance that was acted upon
	 * @param column the column where the tile is located in the grid
	 * @param row the row where the tile is located in the grid
	 */
	public TileActionEvent(Tile source, int column, int row)
	{
		this(source, column, row, null);
	}
	
	/**
	 * Initiates an instance with the given arguments. 
	 * 
	 * @param source the {@link Tile} instance that was acted upon
	 * @param column the column where the tile is located in the grid
	 * @param row the row where the tile is located in the grid
	 * @param swipeDirection the swipe direction
	 */
	public TileActionEvent(Tile source, int column, int row, Direction swipeDirection)
	{
		this.source = source;
		this.column = column;
		this.row = row;
		this.swipeDirection = swipeDirection;
	}
}
