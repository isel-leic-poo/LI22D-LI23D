package poo.demos.puzzle.view.tiles;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Abstract class that visually represents tiles. 
 * Tiles may either be empty or they may display a puzzle piece.  
 */
abstract class Tile {
	
	/**
	 * The tile's parent.
	 */
	protected final TileView parent;
	
	/**
	 * The tile's bounds.
	 */
	protected RectF bounds;
	
	/**
	 * Initiates the tile placing it at the given bounds.
	 * 
	 * @param tileView The TileView instance that contains the current tile 
	 * @param bounds The bounds where the tile is placed
	 */
	protected Tile(TileView parent, RectF bounds)
	{
		this.parent = parent;
		this.bounds = bounds;
	}
	
	/**
	 * Draws the tile in the given canvas.
	 * 
	 * @param canvas The canvas where the tile will be drawn
	 */
	public abstract void doDraw(Canvas canvas);
}