package com.swrd.unblock.bound.ds.bitarray;

import java.awt.Rectangle;

public abstract class AbstractBitArray2D implements BitArray2D {

	protected Rectangle bound;

	public AbstractBitArray2D(Rectangle bound) {
		this.bound = bound;
	}

	@Override
	public boolean checkBound(int x, int y) {
		return bound.contains(x, y);
	}

	@Override
	public boolean checkBound(Rectangle rect) {
		return bound.contains(rect);
	}

	@Override
	public Rectangle getBound() {
		return bound;
	}

}
