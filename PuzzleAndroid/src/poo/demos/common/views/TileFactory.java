package poo.demos.common.views;

import android.graphics.RectF;

/**
 * Contract to be supported by concrete {@link Tile} factories.
 *
 * This approach enables customization of the tiles to be used in 
 * {@link TileView} controls.
 */
public interface TileFactory {

	/**
	 * Creates a concrete {@link Tile} instance.
	 * 
	 * @param row The row where the tile is to be placed
	 * @param column The column where the tile is to be placed
	 * @param parent The parent {@link TileView} control instance
	 * @param tileBounds The bounds where the tile is to be drawn on
	 * @return The concrete {@link Tile} instance.
	 */
	public Tile createTile(int row, int column, TileView parent, RectF tileBounds);
}
