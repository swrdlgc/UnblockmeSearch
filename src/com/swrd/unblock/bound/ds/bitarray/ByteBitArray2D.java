package com.swrd.unblock.bound.ds.bitarray;

import java.awt.Rectangle;

import com.swrd.unblock.bound.Bound;

public class ByteBitArray2D extends AbstractBitArray2D {
	private final static byte ByteLength = 8;
	private final static byte Shift = 3;
	private final static byte Mask = 0x7;
	private byte[][] bitMap;

	public ByteBitArray2D(Rectangle bound) {
		super(bound);
		bitMap = new byte[bound.height][getSizeIndex(bound.width) + 1];
		for(int i = 0; i < bitMap.length; ++i) {
			for(int j = 0; j < bitMap[i].length; ++j) {
				bitMap[i][j] = 0x00;
			}
		}
	}

	@Override
	public boolean isFilled(int x, int y) {
		return ((bitMap[y][getSizeIndex(x)]) & getSizeOffset(x)) > 0;
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
	public boolean fill(int x, int y) {
		if (isFilled(x, y)) {
			return false;
		} else {
			(bitMap[y][getSizeIndex(x)]) |= getSizeOffset(x);
			return true;
		}
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

	@Override
	public boolean clean(int x, int y) {
		if (isFilled(x, y)) {
			(bitMap[y][getSizeIndex(x)]) &= (0xff ^ getSizeOffset(x));
			return true;
		} else {
			return false;
		}
	}

	private static int getSizeIndex(int size) {
		return size >> Shift;
	}

	private static int getSizeOffset(int size) {
		return (1 << (size & Mask));
	}

	public static void main(String[] args) throws Exception {
		ByteBitArray2D bba = new ByteBitArray2D(new Rectangle(100, 90));
		System.err.println(bba.isFilled(7, 60));
		bba.fill(7, 60);
		System.err.println(bba.isFilled(7, 60));
		bba.clean(7, 60);
		System.err.println(bba.isFilled(7, 60));

		byte b = 0;
		for (int i = 0; i < ByteLength; ++i) {
			b |= getSizeOffset(i);
		}
		System.err.println(b);

		for (int i = 0; i < ByteLength; ++i) {
			b &= (0xff ^ getSizeOffset(i));
		}
		System.err.println(b);

		for (int i = 0; i < ByteLength; ++i) {
			System.err.println(String.format("%d, size=%d, offset=%d", i,
					getSizeIndex(i), getSizeOffset(i)));
		}
	}
}
