package poo.demos.puzzle.view.tiles;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Abstract class that visually represents tiles. 
 * 
 * Tiles may either be empty or they may display a puzzle piece. Each instance 
 * maintains its own RectF instance, which represents the tile's bounds within the 
 * parent's view coordinate system.
 * 
 * Because the Tile hierarchy is not directly based on Android's view system,
 * repaint behavior is left to the parent View.
 */
abstract class Tile {
	
	/**
	 * The tile's parent.
	 */
	protected final TileView parent;
	
	/**
	 * The tile's bounds.
	 */
	protected final RectF bounds;
	
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
	
	/**
	 * Gets the tile's bounds. The behavior that results from the direct modification 
	 * of the returned instance is unspecified. To modify a tile's bounds, use instead
	 * {@link #moveBy(float, float)} and {@link } 
	 * 
	 * @return The tile's bounds
	 */
	public final RectF getBounds()
	{
		return bounds;
	}
	
	/**
	 * Moves the tile instance by displacing it by the given delta.
	 *  
	 * @param dx The horizontal displacement
	 * @param dy The vertical displacement
	 */
	public void moveBy(float dx, float dy)
	{
		bounds.offset(dx, dy);
	}
	
	/**
	 * Moves the tile instance to the given position (i.e. the new upper-left corner).
	 * 
	 * @param newLeft The new horizontal coordinate
	 * @param newTop The new vertical coordinate
	 */
	public void moveTo(float newLeft, float newTop)
	{
		bounds.offsetTo(newLeft, newTop);
	}
}