package poo.demos;

import poo.demos.puzzleandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class CounterActivity extends Activity {

	private class OnSwipeBehaviour implements View.OnTouchListener
	{
		private float startY;
		
		@Override
		public boolean onTouch(View view, MotionEvent event) 
		{
			boolean processed = false;
			CounterView counter = (CounterView) view;
			switch(event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					startY = event.getY();
					processed = true;
					break;
					
				case MotionEvent.ACTION_UP:
					float currentY = event.getY();
					if(currentY != startY)
					{
						float delta = startY - currentY;
						counterValue += delta < 0 ? -1 : 1;
						counter.setValue(counterValue);
					}
					processed = true;
					break;
				
				case MotionEvent.ACTION_MOVE:
					processed = true;
					break;
			}
			return processed;
		}
	}
	
	private static final String COUNTER_KEY = "CounterActivity.counterValue";
	private CounterView counterView;
	private int counterValue;
	private int initialValue;
	
	private void save(Bundle outState)
	{
		outState.putInt(COUNTER_KEY, counterValue);
	}
	
	private void load(Bundle savedInstanceState)
	{
		counterValue = savedInstanceState.getInt(COUNTER_KEY, initialValue);
		counterView.setValue(counterValue);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		
		counterView = (CounterView) findViewById(R.id.counterView);
		counterValue = initialValue = counterView.getValue();
		
		if(savedInstanceState != null)
			load(savedInstanceState);
		
		counterView.setOnTouchListener(new OnSwipeBehaviour());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		load(savedInstanceState);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		save(outState);
	}
}
