package poo.demos.puzzle;

import poo.demos.common.views.TileView;
import android.app.Activity;
import android.os.Bundle;

/**
 * Activity that implements the screen presented while solving a puzzle.
 */
public class PuzzleActivity extends Activity {

	/**
	 * The associated controller instance
	 */
	private PuzzleController puzzleController;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		puzzleController.saveState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);

		TileView tileView = (TileView) findViewById(R.id.puzzleView);
		
		puzzleController = (savedInstanceState != null) ? 
				PuzzleController.createController(tileView, savedInstanceState) :
				PuzzleController.createController(tileView, true);
	}
}
