package poo.demos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CounterView extends View {

	private static final String namespace = "http://schemas.android.com/apk/res-auto";
	private static final String valueAttr = "value";
	private static final String fontSizeAttr = "fontSize";
	
	private Paint brush;
	private int value;
	private final Rect bounds;

	private void initBrushes(int fontSize)
	{
		brush = new Paint();
		brush.setColor(Color.BLACK);
		brush.setTextSize(fontSize);
	}
	
	public CounterView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		value = attrs.getAttributeIntValue(namespace, valueAttr, 0);
		initBrushes(attrs.getAttributeIntValue(namespace, fontSizeAttr, 50));
		bounds = new Rect();
		setLayerType(View.LAYER_TYPE_SOFTWARE, brush);
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		String str = Integer.toString(value);
		brush.getTextBounds(str, 0, str.length(), bounds);
		float x = (getWidth() / 2) - bounds.exactCenterX();
		float y = getHeight() / 2 - bounds.exactCenterY();
		canvas.drawText(str, x, y, brush);
	}

	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
		// Ask for a redraw
		invalidate();
	}
}
