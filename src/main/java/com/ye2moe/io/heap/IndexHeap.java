package com.ye2moe.io.heap;

import java.util.Arrays;

public class IndexHeap<T> extends AbstractMyHeap<T> implements MyHeapI<T> {

	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Shared empty array instance used for empty instances.
	 */
	private static final Object[] EMPTY_ELEMENTDATA = {};

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	/**
	 * Shared empty array instance used for default sized empty instances. We
	 * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
	 * first element is added.
	 */
	private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

	transient Object[] elementData;
	transient int[] indexes;

	int size;

	public IndexHeap(int initialCapacity) {
		if (initialCapacity > 0) {
			this.elementData = new Object[initialCapacity + 1];
			indexes = new int[initialCapacity + 1];
		} else if (initialCapacity == 0) {
			this.elementData = EMPTY_ELEMENTDATA;
			indexes = new int[0];
		} else {
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		}
	}

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public IndexHeap() {
		this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
		indexes = new int[0];
	}

	public boolean add(T t) {
		ensureCapacityInternal(size + 1); // Increments modCount!!
		elementData[size++] = t;
		return true;
	}

	private static int calculateCapacity(Object[] elementData, int minCapacity) {
		if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
			return Math.max(DEFAULT_CAPACITY, minCapacity);
		}
		return minCapacity;
	}

	private void ensureCapacityInternal(int minCapacity) {
		ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
	}

	private void ensureExplicitCapacity(int minCapacity) {
		modCount++;

		// overflow-conscious code
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		// minCapacity is usually close to size, so this is a win:
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}
}
