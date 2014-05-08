package poo.demos.puzzle.view;

import poo.demos.puzzle.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Control that displays an empty puzzle. 
 */
public class PuzzleView extends View {
	
	private static final int MINIMUM_TILE_COUNT = 1;
	private static final int DEFAULT_TILE_COUNT = 4;
	
	private static final int DEFAULT_FONT_SIZE = 26;
	
	private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
	private static final String TILE_COUNT_ATTR = "tiles";
	private static final String FONT_SIZE_ATTR = "fontSize";
	
	/**
	 * Holds the number of tiles in each side of the puzzle, which is always a square.
	 */
	private int tileCount;
	
	/**
	 * Holds the brush used to paint the outer-frame of the grid.
	 */
	private Paint gridFrameBrush;
	
	/**
	 * Holds the brush used to paint the inner-frame of the grid.
	 */
	private Paint gridInternalBrush;
	
	/**
	 * Fields used to cache information used while drawing.
	 */
	private Rect textBounds, frame;
	
	/**
	 * Helper method that initializes all brushes used to paint the puzzle view.
	 * 
	 * @param context The context in use
	 */
	private void initBrushes(Context context, AttributeSet attrs) 
	{
		gridFrameBrush = new Paint();
		gridFrameBrush.setColor(Color.LTGRAY);
		gridFrameBrush.setStyle(Paint.Style.STROKE);
		gridFrameBrush.setStrokeWidth(4);
		
		setLayerType(View.LAYER_TYPE_SOFTWARE, gridFrameBrush);

		gridInternalBrush = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
		gridInternalBrush.setColor(Color.LTGRAY);
		gridInternalBrush.setStrokeWidth(1);
		PathEffect effect = new DashPathEffect(new float[] { 20, 20 }, 0);
		gridInternalBrush.setPathEffect(effect);
		
		int fontSize = Math.max(attrs.getAttributeIntValue(NAMESPACE, FONT_SIZE_ATTR, DEFAULT_FONT_SIZE), DEFAULT_FONT_SIZE);
		gridInternalBrush.setAntiAlias(true);
		gridInternalBrush.setTextSize(fontSize);
		//gridInternalBrush.setTextSize(getResources().getDimensionPixelSize(fontSize));
		gridInternalBrush.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		gridInternalBrush.setTextAlign(Align.CENTER);
		gridInternalBrush.setLinearText(true);
		
		setLayerType(View.LAYER_TYPE_SOFTWARE, gridInternalBrush);
	}
	
	/**
	 * Helper method used to draw the view's grid.
	 * 
	 * @param canvas The canvas to draw on
	 */
	private void drawGrid(Canvas canvas)
	{
		final int width = getWidth(), height = getHeight();
		// Draw frame
		canvas.drawRect(frame, gridFrameBrush);
		// Draw tiles' outlines
		int currentX = 0, currentY = 0;
		int tileSize = width / tileCount;
		for(int i = 0; i < tileCount-1; ++i)
		{
			currentX += tileSize;
			currentY += tileSize;
			canvas.drawLine(currentX, 0, currentX, height, gridInternalBrush);
			canvas.drawLine(0, currentY, width, currentY, gridInternalBrush);
		}

		String msg = getResources().getString(R.string.game_image_select_msg);
		gridInternalBrush.getTextBounds(msg, 0, msg.length(), textBounds);
		canvas.drawText(msg, width/2, height/2, gridInternalBrush);
	}

	/**
	 * Initiates an instance.
	 * 
	 * @param context The current context
	 */
	public PuzzleView(Context context) 
	{
		this(context, null);
	}

	/**
	 * Initiates an instance. 
	 * 
	 * @param context The current context
	 * @param attrs The attribute set to be used in the configuration of the new instance 
	 */
	public PuzzleView(Context context, AttributeSet attrs) 
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
	public PuzzleView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		tileCount = Math.max(attrs.getAttributeIntValue(NAMESPACE, TILE_COUNT_ATTR, DEFAULT_TILE_COUNT), MINIMUM_TILE_COUNT);
		initBrushes(context, attrs);
		textBounds = new Rect();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// Compute required square size
		int measuredSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
		// Make sure it is a multiple of the number of tiles
		measuredSize = (measuredSize / tileCount) * tileCount;
		// Set the view's size
		setMeasuredDimension(measuredSize, measuredSize);
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		drawGrid(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		if(frame == null || w != oldw || h == oldh)
			frame = new Rect(0, 0, w, h);
	}

	/**
	 * Changes the number of tiles in each side of the puzzle.
	 * 
	 * @param tileCount the number of tiles
	 * @throws IllegalArgumentException if the given tile count is not a positive 
	 * non-zero value
	 */
	public void setTileCount(int tileCount)
	{
		if(tileCount < MINIMUM_TILE_COUNT)
			throw new IllegalArgumentException();
		
		this.tileCount = tileCount;
		invalidate();
	}
	
	/**
	 * Returns the current number of tiles (i.e. the number of tiles in each side of the square).
	 * 
	 * @return the current number of tiles
	 */
	public int getTileCount()
	{
		return tileCount;
	}
}
