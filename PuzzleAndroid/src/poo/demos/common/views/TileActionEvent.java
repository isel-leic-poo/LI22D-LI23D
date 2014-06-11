package poo.demos.common.views;

/**
 * Class whose immutable instances contain information regarding tile 
 * action events.
 */
public class TileActionEvent {

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
	 * Initiates an instance with the given arguments.
	 * 
	 * @param source the {@link Tile} instance that was acted upon
	 * @param column the column where the tile is located in the grid
	 * @param row the row where the tile is located in the grid
	 */
	public TileActionEvent(Tile source, int column, int row)
	{
		this.source = source;
		this.column = column;
		this.row = row;
	}
}
