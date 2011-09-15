package com.senselessweb.soundcloud.mediasupport.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayBuilder<E>
{

	private final List<E> elements = new ArrayList<E>();
	
	public ArrayBuilder<E> with(final E element)
	{
		this.elements.add(element);
		return this;
	}
	
	public ArrayBuilder<E> with(final E[] elements)
	{
		for (final E element : elements) this.elements.add(element);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public E[] build(Class<E> e)
	{
		return (E[]) this.elements.toArray((E[]) Array.newInstance(e, this.elements.size()));
	}
}
