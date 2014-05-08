package poo.demos.puzzle.view.tiles;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Class whose instances visually represent empty spaces.  
 */
class EmptyTile extends Tile
{
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