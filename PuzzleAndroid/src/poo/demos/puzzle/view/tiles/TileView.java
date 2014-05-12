package poo.demos.puzzle.view.tiles;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Control that displays a puzzle. The control is composed of tiles, which 
 * are implemented without resorting to Android's coordinate system.
 */
public class TileView extends View {
	
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
	 * Holds a shortcut to the empty tile's index, thereby preventing a search on the 
	 * tiles' list
	 */
	private int emptyTileIndex;
	
	/**
	 * Holds a reference to the animation queue
	 */
	private TileAnimator animationQueue;

	/**
	 * Holds a reference to the listener that triggers animations.
	 */
	final View.OnTouchListener listener;
	
	/**
	 * Helper method that initializes the tiles' container
	 */
	private void initTiles() 
	{
		tiles = new ArrayList<Tile>(tileCount * tileCount);
		
		// Create puzzle tiles
		final int totalTileCount = tileCount * tileCount - 1;
		int currentLeft = 0, currentTop = 0;
		for(int i = 0; i < totalTileCount; ++i)
		{
			currentLeft = (i % tileCount) * tileSize;
			currentTop = (i / tileCount) * tileSize;
			tiles.add(new PuzzleTile(this, i + 1, new RectF(currentLeft, currentTop, currentLeft + tileSize, currentTop + tileSize)));
		}
		
		// Create empty tile and position it at the last available space
		currentLeft = (tileCount - 1) * tileSize;
		currentTop = (tileCount -1) * tileSize;
		tiles.add(new EmptyTile(this, new RectF(currentLeft, currentTop, currentLeft + tileSize, currentTop + tileSize)));
		emptyTileIndex = totalTileCount;
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
	 * Gets the brush to be used to paint the tiles' outline.
	 * 
	 * @return The Paint instance
	 */
	Paint getTileOutlineBrush()
	{
		return tileOutlineBrush;
	}
	
	/**
	 * Gets the brush to be used to paint the tiles' fill.
	 * 
	 * @return The Paint instance
	 */
	Paint getTileFillBrush()
	{
		return tileFillBrush;
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
		
		final int ANIMATION_STEPS = 20;
		
		// TODO: Modify to use property injection
		animationQueue = new TileAnimator(ANIMATION_STEPS);
		
		setOnTouchListener(listener = new View.OnTouchListener() {
			
			private int getTouchedTileIndex(MotionEvent event)
			{
				int column = (int) (event.getX() / tileSize);
				int line = (int) (event.getY() / tileSize);
				return line * tileCount + column;
			}
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					final int targetTileIndex = getTouchedTileIndex(event);
					final Tile targetTile = tiles.get(targetTileIndex);
					final Tile emptyTile = tiles.get(emptyTileIndex);
					final float targetLeft = targetTile.getBounds().left;
					final float targetTop = targetTile.getBounds().top;
					
					if(!(targetTile instanceof EmptyTile))
					{
						TileView.this.setOnTouchListener(null);

						animationQueue.doMove(targetTile, emptyTile.getBounds(), new TileAnimator.Callback() {
							@Override
							public void onCompleted() 
							{
								// Exchange tiles on indexing structure
								tiles.set(targetTileIndex, emptyTile);
								tiles.set(emptyTileIndex, targetTile);
								emptyTileIndex = targetTileIndex;
								emptyTile.moveTo(targetLeft, targetTop);
								TileView.this.invalidate();
								TileView.this.setOnTouchListener(listener);
							}

							@Override
							public void onStep(RectF affectedArea) 
							{
								TileView.this.invalidate();
							}
						});
					}
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
