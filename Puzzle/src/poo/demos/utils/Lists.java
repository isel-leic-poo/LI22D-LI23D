package poo.demos.utils;

import java.util.List;

/**
 * Class that aggregates utilities over lists.
 */
public class Lists {

	/**
	 * Contract to be implemented by operations to be performed on list's elements
	 *
	 * @param <T> the type of the list's elements
	 */
	public static interface Func<T> {
		
		public void apply(T elem, int atIndex);
	}

	/**
	 * Applies the given function to each element of the given list
	 * 
	 * @param list the list
	 * @param func the function to apply to each element of the list
	 */
	public static <T> void forEach(List<T> list, Func<T> func)
	{
		int index = 0;
		for(T elem : list)
			func.apply(elem, index++);
	}
}
