package com.swrd.unblock.bound.ds.bitarray;

import java.awt.Rectangle;

public class BooleanBitArray2D extends AbstractBitArray2D {
	
	private boolean[][] bitMap;

	public BooleanBitArray2D(Rectangle bound) {
		super(bound);
		bitMap = new boolean[bound.height][bound.width];
	}

	@Override
	public boolean isFilled(int x, int y) {
		return bitMap[x][y];
	}

	@Override
	public boolean fill(int x, int y) {
		if (isFilled(x, y)) {
			return false;
		} else {
			bitMap[x][y] = true;
			return true;
		}
	}

	@Override
	public boolean clean(int x, int y) {
		if (isFilled(x, y)) {
			bitMap[x][y] = false;
			return true;
		} else {
			return false;
		}
	}

}
