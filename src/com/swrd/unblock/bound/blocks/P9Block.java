package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;

import com.swrd.unblock.utils.RectangleUtils;

/**
 * 九宫格小块
 * 
 * @author swrdlgc
 *
 */
public class P9Block extends Block {

	public P9Block(Rectangle cell) {
		super(cell);
	}

	public P9Block(Rectangle cell, Rectangle dest) {
		super(cell, dest);
	}

	public Block copy() {
		return new UnBlock(RectangleUtils.CopyRectangle(cell),
				RectangleUtils.CopyRectangle(dest));
	}
}
