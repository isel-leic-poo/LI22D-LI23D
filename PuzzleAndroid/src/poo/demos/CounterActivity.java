package poo.demos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import poo.demos.puzzleandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class CounterActivity extends Activity {

	private class OnSwipeBehaviour implements View.OnTouchListener
	{
		private float startY;
		
		@Override
		public boolean onTouch(View view, MotionEvent event) 
		{
			boolean processed = false;
			TextView counter = (TextView) view;
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
						counter.setText(Integer.toString(counterValue));
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
	private TextView counterView;
	private int counterValue;
	private int initialValue;
	
	private void save(Bundle outState)
	{
		outState.putInt(COUNTER_KEY, counterValue);
	}
	
	private void load(Bundle savedInstanceState)
	{
		counterValue = savedInstanceState.getInt(COUNTER_KEY, initialValue);
		counterView.setText(Integer.toString(counterValue));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		
		counterView = (TextView) findViewById(R.id.counterView);
		
		counterValue = initialValue = Integer.parseInt(counterView.getText().toString());
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
