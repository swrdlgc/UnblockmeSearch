package com.swrd.unblock.bound.ds.bitarray;

import java.awt.Rectangle;

import com.swrd.unblock.bound.Bound;

public interface BitArray2D extends Bound {
	boolean checkBound(int x, int y);

	boolean checkBound(Rectangle rect);

	boolean isFilled(int x, int y);

	boolean isMarked(Rectangle rect);

	boolean fill(int x, int y);

	boolean fill(Bound bound);

	boolean clean(int x, int y);
}
