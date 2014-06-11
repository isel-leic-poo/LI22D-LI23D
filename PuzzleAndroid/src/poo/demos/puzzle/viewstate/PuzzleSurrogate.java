package poo.demos.puzzle.viewstate;

import java.util.ArrayList;
import java.util.List;

import poo.demos.puzzle.model.Grid;
import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.Position;
import poo.demos.puzzle.model.Puzzle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class whose instances are used as puzzle model surrogates (for serialization purposes). 
 * The idea is not have dependencies to Android's framework in the model subsystem.
 */
public class PuzzleSurrogate implements Parcelable {

	/**
	 * Factory of {@link PuzzleSurrogate}, according to the {@link android.os.Parcelable} pattern 
	 */
	public static final Parcelable.Creator<PuzzleSurrogate> CREATOR = new Parcelable.Creator<PuzzleSurrogate>() {

		@Override
		public PuzzleSurrogate createFromParcel(Parcel source) 
		{
			return new PuzzleSurrogate(source);
		}

		@Override
		public PuzzleSurrogate[] newArray(int size) 
		{
			return new PuzzleSurrogate[size];
		}
	};
    
    /**
     * The associated puzzle instance.
     */
    private final Puzzle puzzle;

    /**
     * Creates a surrogate with the puzzle information contained in the received parcel.
     * 
     * @param in The parcel instance containing the required information.
     */
    private PuzzleSurrogate(Parcel in) 
    {
	    	int side = in.readInt();
	    	Position emptySpace = readPositionFromParcel(in);
	    	
	    	final int PIECE_COUNT = side * side - 1;
	    	List<Piece> pieces = new ArrayList<Piece>(PIECE_COUNT);
	    	for(int i = 0; i < PIECE_COUNT; ++i)
	    		pieces.add(PieceSurrogate.CREATOR.createFromParcel(in));
	    	
	    	puzzle = new Puzzle(Grid.createPuzzle(pieces, emptySpace));
    }
		
	/**
	 * Helper method that stores the given {@link Position} to the {@link Parcel} instance
	 * received as an argument.
	 * 
	 * @param position the {@link Position} instance to be stored
	 * @param out the parcel used to store the information
	 */
	private void writePositionToParcel(Position position, Parcel out)
	{
		out.writeInt(position.X);
		out.writeInt(position.Y);
	}
	
	/**
	 * Helper method that stores the given {@link Position} to the {@link Parcel} instance
	 * received as an argument.
	 * 
	 * @param position the {@link Position} instance to be stored
	 * @param dest the parcel used to store the information
	 */
	private Position readPositionFromParcel(Parcel in)
	{
		return Position.fromCoordinates(in.readInt(), in.readInt());
	}

	/**
	 * Initiates the surrogate with the given puzzle instance.
	 * 
	 * @param puzzle The puzzle instance associated to this surrogate 
	 */
	public PuzzleSurrogate(Puzzle puzzle)
	{
		this.puzzle = puzzle;
	}
	
	/**
	 * Gets the puzzle instance associated to this surrogate.
	 * 
	 * @return The associated puzzle instance
	 */
	public Puzzle getPuzzle()
	{
		return puzzle;
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		int side = puzzle.getSize();
		out.writeInt(side);
		
		writePositionToParcel(puzzle.getEmptySpacePosition(), out);
				
		for(Piece piece : puzzle)
			new PieceSurrogate(piece).writeToParcel(out, flags);
	}
}
