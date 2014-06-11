package poo.demos.puzzle.views;

import poo.demos.common.views.Moveable;
import poo.demos.common.views.Tile;
import poo.demos.common.views.TileView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;

/**
 * Class whose instances visually represent puzzle pieces.  
 */
class NumberedTile extends Tile implements Moveable
{
	/**
	 * Used to specify the rounded corners radius.
	 */
	private static final int ARC = 14;
	
	/**
	 * Used to specify the alpha value applied to the parent's background color.
	 */
	private static final int ALPHA = 220;
	
	/**
	 * The brushes used to paint the tiles. These brushes are 
	 * shared across all tile instances.
	 */
	private static Paint tileOutlineBrush, tileFillBrush;
	
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
	
	private void computeNumberBounds()
	{
		numberX = (int) (bounds.left + (bounds.width() - numberBounds.width()) / 2);
		numberY = (int) (bounds.top + bounds.height() - (bounds.height() - numberBounds.height()) / 2);
	}
	
	/**
	 * Initiates a puzzle tile instance with the given bounds. 
	 * 
	 * @param parent The tile's parent view
	 * @param number The tile's face number
	 * @param bounds The tile's initial bounds
	 */
	public NumberedTile(TileView parent, int number, RectF bounds)
	{
		super(parent, bounds);
		
		this.number = Integer.toString(number);
		numberBounds = new Rect();
		
		// Are the shared brushes initialized?
		if(tileOutlineBrush == null)
		{
			tileOutlineBrush = parent.getOutlineBrush();
			tileFillBrush = new Paint(parent.getBackgroundBrush());
			tileFillBrush.setAlpha(ALPHA);
		}
		
		tileOutlineBrush.getTextBounds(this.number, 0, this.number.length() , numberBounds);
		computeNumberBounds();
	}
	
	@Override
	public void doDraw(Canvas canvas)
	{
		canvas.drawRoundRect(bounds, ARC, ARC, tileFillBrush);
		final Style savedStyle = tileOutlineBrush.getStyle();
		tileOutlineBrush.setStyle(Style.STROKE);
		canvas.drawRoundRect(bounds, ARC, ARC, tileOutlineBrush);
		tileOutlineBrush.setStyle(savedStyle);
		canvas.drawText(number, numberX, numberY, tileOutlineBrush);
	}

	@Override
	public void moveBy(float dx, float dy) 
	{
		RectF affectedArea = new RectF(bounds);
		this.bounds.offset(dx, dy);
		affectedArea.union(bounds);
		affectedArea.roundOut(dirty);
		parent.invalidate(dirty);
		computeNumberBounds();
	}

	@Override
	public void moveTo(float newLeft, float newTop) 
	{
		setPosition(newLeft, newTop);
		computeNumberBounds();
	}

	@Override
	public RectF getCurrentBounds() 
	{
		return this.getBounds();
	}
}