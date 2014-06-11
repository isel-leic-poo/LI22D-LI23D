package poo.demos.puzzle.views;

import poo.demos.common.views.Tile;
import poo.demos.common.views.TileView;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Class whose instances visually represent empty spaces.  
 */
class EmptyTile extends Tile
{
	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 */
	public EmptyTile(TileView parent, RectF bounds) 
	{
		super(parent, bounds);
	}

	@Override
	public void doDraw(Canvas canvas) 
	{
		// TODO: For now, we do nothing. 
		// Later, we will draw something that depicts an empty space.
	}
}