package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;

import com.swrd.unblock.utils.RectangleUtils;

/**
 * 华容道小块
 * 
 * @author swrdlgc
 *
 */
public class HRBlock extends Block {

	public HRBlock(Rectangle cell) {
		super(cell);
	}

	public HRBlock(Rectangle cell, Rectangle dest) {
		super(cell, dest);
	}
	
	public HRBlock(Rectangle cell, Rectangle dest, int id) {
		super(cell, dest, id);
	}

	public Block copy() {
		return new HRBlock(RectangleUtils.CopyRectangle(cell), RectangleUtils.CopyRectangle(dest), getId());
	}
}
