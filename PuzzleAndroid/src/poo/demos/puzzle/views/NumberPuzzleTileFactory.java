package poo.demos.puzzle.views;

import android.graphics.RectF;
import poo.demos.common.views.Tile;
import poo.demos.common.views.TileFactory;
import poo.demos.common.views.TileView;
import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.Position;
import poo.demos.puzzle.model.Puzzle;

/**
 * Class that implements the factory of tiles for a numbered puzzle. 
 */
public class NumberPuzzleTileFactory implements TileFactory {

	/**
	 * The puzzle instance.
	 */
	private Puzzle puzzle;
	
	/**
	 * Sets the puzzle instance to be used when instantiating tiles
	 * 
	 * @param puzzle the puzzle instance
	 */
	public void setPuzzle(Puzzle puzzle)
	{
		this.puzzle = puzzle;
	}
	
	@Override
	public Tile createTile(int row, int column, TileView parent, RectF tileBounds) 
	{
		Piece piece = puzzle.getPieceAtPosition(Position.fromCoordinates(column, row));
		if(piece == null)
			return new EmptyTile(parent, tileBounds);
		
		Position correctPosition = piece.getInitialPosition();
		int number = (correctPosition.Y * puzzle.getSize()) + correctPosition.X + 1;
		return new NumberedTile(parent, number, tileBounds);
	}
}
