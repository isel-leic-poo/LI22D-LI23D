package poo.demos.puzzle.view;

import android.graphics.RectF;

/**
 * Contract to be supported by objects that can be moved around 
 * on the display. 
 */
public interface Moveable {

	/**
	 * Moves the instance by displacing it by the given delta.
	 *  
	 * @param dx The horizontal displacement
	 * @param dy The vertical displacement
	 */
	public void moveBy(float dx, float dy);
	
	/**
	 * Moves the instance to the given position (i.e. the new upper-left corner).
	 * 
	 * @param newLeft The new horizontal coordinate
	 * @param newTop The new vertical coordinate
	 */
	public void moveTo(float newLeft, float newTop);
	
	/**
	 * Gets the instance's current position.
	 *  
	 * @return The instance's current position, expressed by the {@link RectF instance} 
	 * that defines its bounds.
	 */
	public RectF getCurrentBounds();
}
