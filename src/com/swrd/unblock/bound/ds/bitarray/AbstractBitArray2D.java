package com.swrd.unblock.bound.ds.bitarray;

import java.awt.Rectangle;

import com.swrd.unblock.bound.Bound;

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

	@Override
	public boolean isMarked(Rectangle rect) {
		for (int i = 0; i < rect.width; ++i) {
			for (int j = 0; j < rect.height; ++j) {
				if (isFilled(rect.x + i, rect.y + j)) {
					return true;
				}
			}
		}
		return false;
	}
	

	@Override
	public boolean fill(Bound bound) {
		Rectangle rect = bound.getBound();
		for (int i = 0; i < rect.width; ++i) {
			for (int j = 0; j < rect.height; ++j) {
				if(!fill(rect.x + i, rect.y + j)) {
					return false;
				}
			}
		}
		return true;
	}
}
