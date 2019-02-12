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
	public static final int Width = 4;
	public static final int Height = 5;
	public static final Rectangle Destination = new Rectangle(1, 3, 2, 2);
	public static final Rectangle Bounds = new Rectangle(Width, Height);
		
	public HRBlock(Rectangle cell, char label) {
		super(cell, label);
	}

	public HRBlock(Rectangle cell, Rectangle dest, char label) {
		super(cell, dest, label);
	}
	
	public HRBlock(Rectangle cell, Rectangle dest, int id, char label) {
		super(cell, dest, id, label);
	}

	public Block copy() {
		return new HRBlock(RectangleUtils.CopyRectangle(cell), dest, getId(), getLabel());
	}
	
	@Override
	public int compareTo(Block o) {
		// 先移动小的，效果不好
//		int a = getArea();
//		int oa = o.getArea();
//		if(a != oa) {
//			return a - oa;
//		}
		
		int w = cell.width;
		int ow = o.cell.width;
		if(w != ow) {
			return w - ow;
		}
		
		int x = cell.x;
		int ox = o.cell.x;
		if(x != ox) {
			return x - ox;
		}
		
		int y = cell.y;
		int oy = o.cell.y;
		if(y != oy) {
			return y - oy;
		}
		
		return 0;
	}
}
