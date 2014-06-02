package poo.demos.puzzle.view.tiles;

import java.util.ArrayList;
import java.util.List;

import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.PieceMovedEvent;
import poo.demos.puzzle.model.Position;
import poo.demos.puzzle.model.Puzzle;
import poo.demos.puzzle.view.Animator;
import poo.demos.puzzle.view.Moveable;


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
	 * The current puzzle instance. 
	 */
	private Puzzle puzzleModel;
	
	/**
	 * Holds a reference to the animation queue
	 */
	private Animator animationQueue;

	/**
	 * Holds a reference to the listener that triggers animations.
	 */
	final View.OnTouchListener listener;
	
	/**
	 * Helper method that initializes the tiles' container
	 */
	private void initTiles() 
	{
		tiles = new ArrayList<Tile>(puzzleSideTileCount * puzzleSideTileCount);
		
		// Create puzzle tiles
		final int totalTileCount = puzzleSideTileCount * puzzleSideTileCount - 1;
		int currentLeft = 0, currentTop = 0;
		for(int i = 0; i < totalTileCount; ++i)
		{
			int column = i % puzzleSideTileCount;
			int row = i / puzzleSideTileCount;
			currentLeft = column * tileSize;
			currentTop = row * tileSize;
			Piece piece = puzzleModel.getPieceAtPosition(Position.fromCoordinates(column, row));
			tiles.add(new PuzzleTile(this, i + 1, new RectF(currentLeft, currentTop, currentLeft + tileSize, currentTop + tileSize), piece));
		}
		
		// Create empty tile and position it at the last available space
		currentLeft = (puzzleSideTileCount - 1) * tileSize;
		currentTop = (puzzleSideTileCount -1) * tileSize;
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
		int fontSize = attrs.getAttributeIntValue(NAMESPACE, FONT_SIZE_ATTR, DEFAULT_FONT_SIZE);
		tileOutlineBrush.setTextSize(fontSize);
		
		backgroundBrush = new Paint(tileFillBrush);
		backgroundBrush.setAlpha(100);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
		
		puzzleSideTileCount = attrs.getAttributeIntValue(NAMESPACE, INITIAL_PUZZLE_SIZE_ATTR, DEFAULT_PUZZLE_SIZE);

		final int ANIMATION_STEPS = 30;
		
		// TODO: Modify to use property injection
		animationQueue = new Animator(ANIMATION_STEPS);
		
		setOnTouchListener(listener = new View.OnTouchListener() {
			
			private int getTouchedTileIndex(MotionEvent event)
			{
				int column = (int) (event.getX() / tileSize);
				int line = (int) (event.getY() / tileSize);
				return line * puzzleSideTileCount + column;
			}
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					final int targetTileIndex = getTouchedTileIndex(event);
					final Tile targetTile = tiles.get(targetTileIndex);
					
					if(!(targetTile instanceof Moveable))
						return false;
					
					if(!puzzleModel.doMove(targetTile.piece))
						return true;
					
					// Move has been performed. Let's trigger the animation
					final Moveable subject = (Moveable) targetTile;
					final float subjectInitialLeft = subject.getCurrentBounds().left;
					final float subjectInitialTop = subject.getCurrentBounds().top;
					
					TileView.this.setOnTouchListener(null);

					final Tile emptyTile = tiles.get(emptyTileIndex);
					animationQueue.submitMove(subject, emptyTile.getBounds(), new Animator.OnAnimationListener() {
						private final Rect dirty = new Rect();
						
						@Override
						public void onCompleted(Moveable subject, RectF affectedArea) 
						{
							// Exchange tiles on indexing structure
							tiles.set(targetTileIndex, emptyTile);
							tiles.set(emptyTileIndex, targetTile);
							emptyTileIndex = targetTileIndex;
							emptyTile.setPosition(subjectInitialLeft, subjectInitialTop);

							affectedArea.roundOut(dirty);
							TileView.this.invalidate(dirty);
							TileView.this.setOnTouchListener(listener);
						}

						@Override
						public void onStepPerformed(Moveable subject, RectF affectedArea) 
						{
							affectedArea.roundOut(dirty);
							TileView.this.invalidate(dirty);
						}
					});
				}
				return true;
			}
		});
	}

	/**
	 * Gets the number of tiles in both sides of the puzzle.
	 * 
	 * @return The number of tiles
	 */
	public int getTilesPerSide()
	{
		return puzzleSideTileCount;
	}
	
	/**
	 * Sets the view's model.
	 * 
	 * @param puzzle The view's model instance.
	 * @throw IllegalArgumentException if the argument is {@code null}
	 */
	public void setModel(Puzzle puzzle)
	{
		if(puzzle == null)
			throw new IllegalArgumentException();
		
		puzzleModel = puzzle;
		puzzleModel.registerOnModificationListener(new Puzzle.OnModificationListener() {
			@Override
			public void onPieceMoved(PieceMovedEvent evt) 
			{
				System.out.println("piece moved from " + evt.from + " to " + evt.to);
			}
		});
		
		initTiles();
		invalidate();
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
		for(Tile t : tiles)
			t.doDraw(canvas);
	}
}
