package com.senselessweb.soundcloud.mediasupport.util;

/**
 * Contains static methods to check the identity of a class.
 * 
 * @author thomas
 */
public class IdentityUtils
{

	/**
	 * Checks the equalness of the given objects.
	 * 
	 * @param arguments The objects to check. The first is compared with the second and so on...
	 * 
	 * @return True if all arguments are equal. False otherwise.
	 */
	public static boolean areEqual(final Object... arguments)
	{
		if (arguments.length % 2 != 0) throw new IllegalArgumentException("Illegal size of arguments: " + arguments.length);
		
		for (int i = 0; i < arguments.length / 2; i+=2)
		{
			final Object first = arguments[i];
			final Object second = arguments[i+1];
			
			if (first == null)
			{
				if (second != null) return false;
			}
			else
			{
				if (!first.equals(second)) return false;
			}
		}
		return true;
	}
}
