package poo.demos.puzzle.viewstate;

import android.os.Parcel;
import android.os.Parcelable;
import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.Position;

/**
 * Class whose instances are used as puzzle pieces' surrogates (for serialization purposes). 
 * The idea is not have dependencies to Android's framework in the model subsystem.
 */
public class PieceSurrogate extends Piece implements Parcelable {

	/**
	 * Factory of {@link PieceSurrogate}, according to the {@link android.os.Parcelable} pattern 
	 */
	public static final Parcelable.Creator<PieceSurrogate> CREATOR = new Parcelable.Creator<PieceSurrogate>() {

		@Override
		public PieceSurrogate createFromParcel(Parcel source) 
		{
			return new PieceSurrogate(source);
		}

		@Override
		public PieceSurrogate[] newArray(int size) 
		{
			return new PieceSurrogate[size];
		}
	};
	
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
	 * @param out the parcel used to store the information
	 */
	private Position readPositionFromParcel(Parcel in)
	{
		return Position.fromCoordinates(in.readInt(), in.readInt());
	}
	
	/**
	 * Initiates an instance with the information extracted from the given {@link Parcel}
	 * instance.
	 * 
	 * @param in the parcel containing the piece information 
	 */
	private PieceSurrogate(Parcel in)
	{
		initial = readPositionFromParcel(in);
		current = readPositionFromParcel(in);
	}
	
	/**
	 * Holds the instance's initial position. 
	 */
	private final Position initial;
	
	/**
	 * Holds the instance's current position. 
	 */
	private final Position current;
	
	/**
	 * Initiates an instance with the given arguments. 
	 * 
	 * @param piece the associated {@link Piece} instance
	 */
	public PieceSurrogate(Piece piece)
	{
		this.initial = piece.getInitialPosition();
		this.current = piece.getPosition();
	}
	
	@Override
	public Position getInitialPosition() 
	{
		return initial;
	}

	@Override
	public Position getPosition() 
	{
		return current;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		writePositionToParcel(initial, out);
		writePositionToParcel(current, out);
	}
}
