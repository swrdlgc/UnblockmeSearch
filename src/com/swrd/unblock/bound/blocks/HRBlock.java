package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;

import com.swrd.unblock.utils.RectangleUtils;

/**
 * 华容道小块
 * 
 * @author swrdlgc
 *
 */
public class HRBlock extends Block implements Comparable<HRBlock> {

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
	
	public int getArea() {
		return cell.width * cell.height;
	}

	@Override
	public int compareTo(HRBlock o) {
		int a = getArea();
		int oa = getArea();
		if(a != oa) {
			return a - oa;
		}
		
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
