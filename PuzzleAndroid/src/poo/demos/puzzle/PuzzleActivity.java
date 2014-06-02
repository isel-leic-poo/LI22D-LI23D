package poo.demos.puzzle;

import poo.demos.puzzle.model.Puzzle;
import poo.demos.puzzle.view.tiles.TileView;
import android.app.Activity;
import android.os.Bundle;

public class PuzzleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);
		
		TileView puzzleView = (TileView) findViewById(R.id.puzzleView);
		Puzzle puzzle = new Puzzle(puzzleView.getTilesPerSide(), false);
		puzzleView.setModel(puzzle);
	}

}
