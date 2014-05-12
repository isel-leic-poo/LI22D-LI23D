package poo.demos.puzzle.view.tiles;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;

/**
 * Class whose instances visually represent puzzle pieces.  
 */
class PuzzleTile extends Tile
{
	/**
	 * Used to specify the rounded corners radius.
	 */
	private static final int ARC = 14;
	
	/**
	 * The tile's face number
	 */
	final String number;
	
	/**
	 * The drawing coordinates of the tile's face number.
	 */
	private int numberX, numberY;
	
	/**
	 * The bounds of the tile's face number. Used to compute the drawing 
	 * coordinates.
	 */
	private final Rect numberBounds;
	
	/**
	 * The brushes used to paint the tile.
	 */
	private final Paint tileOutlineBrush, tileFillBrush;
	
	private void computeNumberBounds()
	{
		numberX = (int) (bounds.left + (bounds.width() - numberBounds.width()) / 2);
		numberY = (int) (bounds.top + bounds.height() - (bounds.height() - numberBounds.height()) / 2);
		// Adjust descent
		numberY -= tileOutlineBrush.getFontMetricsInt().descent;
	}
	
	/**
	 * Initiates a puzzle tile instance with the given bounds. 
	 * 
	 * @param parent The tile's parent view
	 * @param number The tile's face number
	 * @param bounds The tile's initial bounds
	 */
	public PuzzleTile(TileView parent, int number, RectF bounds)
	{
		super(parent, bounds);
		this.number = Integer.toString(number);
		numberBounds = new Rect();
		tileOutlineBrush = parent.getTileOutlineBrush();
		tileFillBrush = parent.getTileFillBrush();
		
		tileOutlineBrush.getTextBounds(this.number, 0, this.number.length() , numberBounds);
		computeNumberBounds();
	}
	
	@Override
	public void doDraw(Canvas canvas)
	{
		canvas.drawRoundRect(bounds, ARC, ARC, tileFillBrush);
		final Style savedStyle = tileOutlineBrush.getStyle();
		tileOutlineBrush.setStyle(Style.STROKE);
		canvas.drawRoundRect(bounds, ARC, ARC, parent.getTileOutlineBrush());
		tileOutlineBrush.setStyle(savedStyle);
		canvas.drawText(number, numberX, numberY, parent.getTileOutlineBrush());
	}

	@Override
	public void moveBy(float dx, float dy) 
	{
		super.moveBy(dx, dy);
		computeNumberBounds();
	}

	@Override
	public void moveTo(float newLeft, float newTop) 
	{
		super.moveTo(newLeft, newTop);
		computeNumberBounds();
	}
}