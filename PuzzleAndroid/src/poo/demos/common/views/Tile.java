package poo.demos.common.views;

import android.graphics.Canvas;
import android.graphics.Rect;
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
public abstract class Tile {
	
	/**
	 * The tile's parent.
	 */
	protected final TileView parent;
	
	/**
	 * The tile's bounds.
	 */
	protected final RectF bounds;
	
	/**
	 * Cached instance to used when some area of the parent control is invalidated.
	 */
	protected final Rect dirty;
	
	/**
	 * Initiates the tile placing it at the given bounds and associating it to a 
	 * specific piece instance.
	 * 
	 * @param tileView The TileView instance that contains the current tile 
	 * @param bounds The bounds where the tile is placed
	 * @throw IllegalArgumentException if any of the given arguments are {@code null}
	 */
	protected Tile(TileView parent, RectF bounds)
	{
		this.parent = parent;
		this.bounds = bounds;
		this.dirty = new Rect();
	}
	
	/**
	 * Gets the tile's bounds. The behavior that results from the direct modification 
	 * of the returned instance is unspecified.  
	 * 
	 * @return The tile's bounds
	 */
	public final RectF getBounds()
	{
		return bounds;
	}
	
	/**
	 * Sets the instance's position, that is, its bounding rectangle is repositioned to
	 * the given coordinates. The call promotes the parent view to be redrawn.
	 * 
	 * @param newLeft The horizontal coordinate
	 * @param newTop The vertical coordinate
	 */
	public final void setPosition(float newLeft, float newTop)
	{
		RectF affectedArea = new RectF(bounds);
		bounds.offsetTo(newLeft, newTop);
		affectedArea.union(bounds);
		affectedArea.roundOut(dirty);
		parent.invalidate(dirty);
	}
	
	/**
	 * Draws the tile in the given canvas.
	 * 
	 * @param canvas The canvas where the tile will be drawn
	 */
	public abstract void doDraw(Canvas canvas);
}