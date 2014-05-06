package poo.demos.puzzle.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Control that displays a puzzle. The control is composed of tiles, which 
 * are implemented without resorting to Android's coordinate system.
 */
public class TileView extends View {
	
	/**
	 * Abstract class that visually represents tiles. 
	 * Tiles may either be empty or they may display a puzzle piece.  
	 */
	private abstract class Tile
	{
		/**
		 * The tile's bounds.
		 */
		protected RectF bounds;
		
		/**
		 * Initiates the tile placing it at the given bounds.
		 * 
		 * @param bounds The bounds where the tile is placed
		 */
		protected Tile(RectF bounds)
		{
			this.bounds = bounds;
		}
		
		/**
		 * Draws the tile in the given canvas.
		 * 
		 * @param canvas The canvas where the tile will be drawn
		 */
		public abstract void doDraw(Canvas canvas);
	}

	/**
	 * Class whose instances visually represent empty spaces.  
	 */
	private class EmptyTile extends Tile
	{

		public EmptyTile(RectF bounds) 
		{
			super(bounds);
		}

		@Override
		public void doDraw(Canvas canvas) 
		{
			// TODO: For now, we do nothing. 
			// Later, we will draw something that depicts an empty space.
		}
	}
	
	/**
	 * Class whose instances visually represent puzzle pieces.  
	 */
	private class PuzzleTile extends Tile
	{
		private static final int ARC = 14;
		private final String number;
		private int numberX, numberY;
		
		public PuzzleTile(int number, RectF bounds)
		{
			super(bounds);
			this.number = Integer.toString(number);
			Rect numberBounds = new Rect();
			tileOutlineBrush.getTextBounds(this.number, 0, this.number.length() , numberBounds);
			numberX = (int) (bounds.left + (bounds.width() - numberBounds.width()) / 2);
			numberY = (int) (bounds.top + bounds.height() - (bounds.height() - numberBounds.height()) / 2);
			// Adjust descent
			numberY -= tileOutlineBrush.getFontMetricsInt().descent;
		}
		
		@Override
		public void doDraw(Canvas canvas)
		{
			canvas.drawRoundRect(bounds, ARC, ARC, tileFillBrush);
			tileOutlineBrush.setStyle(Style.STROKE);
			canvas.drawRoundRect(bounds, ARC, ARC, tileOutlineBrush);
			tileOutlineBrush.setStyle(Style.FILL);
			canvas.drawText(number, numberX, numberY, tileOutlineBrush);
		}
	}
	
	private final int tileCount = 4;
	
	/**
	 * Used to cache the current tile size
	 */
	private int tileSize;
	
	/**
	 * The brushes used to paint the view
	 */
	private Paint tileFillBrush, tileOutlineBrush, backgroundBrush;
	
	/**
	 * Holds the view's tiles
	 */
	private List<Tile> tiles;
	
	/**
	 * Helper method that initializes the tiles' container
	 */
	private void initTiles() 
	{
		tiles = new ArrayList<TileView.Tile>(tileCount * tileCount);
		
		int currentLeft = 0, currentTop = 0;
		for(int i = 0; i < (tileCount * tileCount) - 1; ++i)
		{
			currentLeft = (i % tileCount) * tileSize;
			currentTop = (i / tileCount) * tileSize;
			tiles.add(new PuzzleTile(i + 1, new RectF(currentLeft, currentTop, currentLeft + tileSize, currentTop + tileSize)));
		}
		
		// Add empty tile
		tiles.add(new EmptyTile(new RectF(currentLeft, currentTop, currentLeft + tileSize, currentTop + tileSize)));
	}
	
	/**
	 * Helper method that initializes all brushes used to paint the puzzle view.
	 * 
	 * @param context The context in use
	 */
	private void initBrushes(Context context, AttributeSet attrs) 
	{
		tileFillBrush = new Paint();
		tileFillBrush.setColor(Color.LTGRAY);
		tileFillBrush.setStyle(Paint.Style.FILL);
		
		tileOutlineBrush = new Paint();
		tileOutlineBrush.setColor(Color.DKGRAY);
		tileOutlineBrush.setTextSize(50);
		
		backgroundBrush = new Paint(tileFillBrush);
		backgroundBrush.setAlpha(100);

		setLayerType(View.LAYER_TYPE_SOFTWARE, tileFillBrush);
		setLayerType(View.LAYER_TYPE_SOFTWARE, tileOutlineBrush);
	}
	
	/**
	 * Initiates an instance with the given number of tiles.
	 * 
	 * @param context The current context
	 */
	public TileView(Context context) 
	{
		this(context, null, 0);
	}

	/**
	 * Initiates an instance. 
	 * 
	 * @param context The current context
	 * @param attrs The attribute set to be used in the configuration of the new instance 
	 */
	public TileView(Context context, AttributeSet attrs) 
	{
		this(context, attrs, 0);
	}
	
	/**
	 * Initiates an instance.
	 * 
	 * @param context The current context
	 * @param attrs The attribute set to be used in the configuration of the new instance
	 * @param defStyleAttr The default style identifier
	 */
	public TileView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initBrushes(context, attrs);
		
		this.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					int column = (int) (event.getX() / tileSize);
					int line = (int) (event.getY() / tileSize);
					int index = line * tileCount + column;
					System.out.print(index + " : ");
					Tile targetTile = tiles.get(index);
					System.out.println(targetTile instanceof PuzzleTile ? ((PuzzleTile)targetTile).number : "empty");
				}
				return true;
			}
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// Compute required square size
		int measuredSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
		// Make sure it is a multiple of the number of tiles
		measuredSize = (tileSize = measuredSize / tileCount) * tileCount;
		// Set the view's size
		setMeasuredDimension(measuredSize, measuredSize);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) 
	{
		if(changed)
			initTiles();
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundBrush);
		for(Tile t : tiles)
			t.doDraw(canvas);
	}
}
