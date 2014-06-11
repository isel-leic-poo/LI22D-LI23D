package poo.demos.common.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	 * Contract to be supported by listeners of tile actions (e.g. touch, drag)
	 */
	public static interface OnTileActionListener {
		
		/**
		 * Callback method that notifies 
		 * @param evt
		 */
		public void onTileTouch(TileActionEvent evt);
	}
		
	/**
	 * Holds the reference to the registered tile action listener.
	 */
	private OnTileActionListener listener;
	
	/**
	 * Registers the given listener has a receiver of tile action events.
	 * 
	 * @param listener the listener to be registered, or {@code null}, to disable notifications.
	 */
	public void setOnTileActionListener(OnTileActionListener listener)
	{
		this.listener = listener;
		
		// Disable notifications?
		if(this.listener == null)
		{
			this.setOnTouchListener(null);
			return;
		}
	
		// Register the listener that will perform event transformation
		this.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					final int column = (int) (event.getX() / tileSize);
					final int row = (int) (event.getY() / tileSize);
					fireOnTileTouchEvent(new TileActionEvent(tiles[row][column], column, row));
				}
				
				return true;
			}
		});
	}
	
	/**
	 * Dispatches the given event to the registered listener, if there is one.
	 *  
	 * @param evt the event instance to be dispatched
	 */
	private void fireOnTileTouchEvent(TileActionEvent evt)
	{
		if(listener != null)
			listener.onTileTouch(evt);
	}
	
	private static final int DEFAULT_FONT_SIZE = 50;
	private static final int DEFAULT_PUZZLE_SIZE = 4;

	private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
	private static final String FONT_SIZE_ATTR = "fontSize";
	private static final String INITIAL_PUZZLE_SIZE_ATTR = "initialPuzzleSize";

	/**
	 * The number of tiles in each side of the puzzle
	 */
	private int puzzleSideTileCount;
	
	/**
	 * Used to cache the current tile size
	 */
	private int tileSize;
	
	/**
	 * The brushes used to paint the view
	 */
	private Paint outlineBrush, backgroundBrush;
	
	/**
	 * Holds the view's tiles
	 */
	private Tile[][] tiles;
	
	/**
	 * Cached instance to used when some area of the control is invalidated.
	 */
	private Rect dirty;
	
	/**
	 * Holds the instance's concrete {@link Tile} instances factory. 
	 * A {@code null} value means that the view's tiles are not initialized,
	 * and therefore will not be drawn.
	 */
	private TileFactory tileProvider;
	
	/**
	 * Helper method that initializes the view's tiles
	 */
	private void initTiles() 
	{
		// Is there a tile factory? 
		if(tileProvider == null)
			return;
		
		tiles = new Tile[puzzleSideTileCount][puzzleSideTileCount];
		
		// Create puzzle tiles
		for(int row = 0; row < puzzleSideTileCount; ++row)
		{
			for(int column = 0; column < puzzleSideTileCount; ++column)
			{
				int currentLeft = column * tileSize;
				int currentTop = row * tileSize;
				tiles[row][column] = tileProvider.createTile(row, column, this, 
						new RectF(currentLeft, currentTop, currentLeft + tileSize, currentTop + tileSize)
				);
			}
		}
	}
	
	/**
	 * Helper method that initializes all brushes used to paint the puzzle view.
	 * 
	 * @param context The context in use
	 */
	private void initBrushes(Context context, AttributeSet attrs) 
	{
		outlineBrush = new Paint();
		outlineBrush.setColor(Color.DKGRAY);
		int fontSize = attrs.getAttributeIntValue(NAMESPACE, FONT_SIZE_ATTR, DEFAULT_FONT_SIZE);
		outlineBrush.setTextSize(fontSize);
		
		backgroundBrush = new Paint();
		backgroundBrush.setColor(Color.LTGRAY);
		backgroundBrush.setStyle(Paint.Style.FILL);
		backgroundBrush.setAlpha(100);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	/**
	 * Gets the brush to be used to paint the views' outline and, eventually,
	 * any text contained in it.
	 * 
	 * @return The Paint instance
	 */
	public Paint getOutlineBrush()
	{
		return outlineBrush;
	}
	
	/**
	 * Gets the brush to be used to paint the views' background.
	 * 
	 * @return The Paint instance
	 */
	public Paint getBackgroundBrush()
	{
		return backgroundBrush;
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
		
		puzzleSideTileCount = attrs.getAttributeIntValue(NAMESPACE, INITIAL_PUZZLE_SIZE_ATTR, DEFAULT_PUZZLE_SIZE);
		tileProvider = null;
		dirty = new Rect();
	}

	/**
	 * Gets the number of tiles in each side of the puzzle.
	 * 
	 * @return The number of tiles
	 */
	public int getNumberOfTilesPerSide()
	{
		return puzzleSideTileCount;
	}

	/**
	 * Sets the instance's concrete {@link Tile} instances factory. 
	 *  
	 * @param tileProvider The factory instance
	 * @throws IllegalArgumentException if the argument is {@code null}
	 */
	public void setTileProvider(TileFactory tileProvider)
	{
		if(tileProvider == null)
			throw new IllegalArgumentException();
		
		this.tileProvider = tileProvider;
		initTiles();
		// The view's tiles have changed. We have to force a full redraw.
		invalidate();
	}

	/**
	 * Gets the {@link Tile} instance at the given position in the grid. Grid 
	 * coordinates are expressed in the interval {@code [0..getNumberOfTilesPerSide()[}. 
	 * 
	 * @param column The grid's column
	 * @param row The grid's row
	 * @return The {@link Tile} instance at the given position in the grid
	 * @throws IllegalArgumentException if either argument is not within the grid's bounds
	 */
	public Tile getTileAt(int column, int row) 
	{
		if(column < 0 || column >= puzzleSideTileCount || row < 0 || row >= puzzleSideTileCount)
			throw new IllegalArgumentException();
		
		return tiles[row][column];
	}
	
	/**
	 * Sets the {@link Tile} instance at the given position in the grid. Grid 
	 * coordinates are expressed in the interval {@code [0..getNumberOfTilesPerSide()[}. 
	 * 
	 * @param tile The {@link Tile} instance to place at the given position
	 * @param column The grid's column
	 * @param row The grid's row
	 * @throws IllegalArgumentException if either argument is not within the grid's bounds
	 */
	public void setTileAt(Tile tile, int column, int row) 
	{
		if(column < 0 || column >= puzzleSideTileCount || row < 0 || row >= puzzleSideTileCount)
			throw new IllegalArgumentException();
		
		tiles[row][column] = tile;
		// Perform a partial redraw
		tile.getBounds().roundOut(dirty);
		invalidate(dirty);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// Compute required square size
		int measuredSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
		// Make sure it is a multiple of the number of tiles
		measuredSize = (tileSize = measuredSize / puzzleSideTileCount) * puzzleSideTileCount;
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
		// Are the tiles initialized? If so, let's draw them
		if(tileProvider != null)
		{
			for(Tile[] gridRow : tiles)
				for(Tile tile : gridRow)
					tile.doDraw(canvas);
		}
	}
}
