package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;

import com.swrd.unblock.ems.BlockType;
import com.swrd.unblock.utils.RectangleUtils;

/**
 * UnblockMe小块
 * 
 * @author swrdlgc
 *
 */
public class UnBlock extends Block {
	
	public UnBlock(Rectangle cell) {
		super(cell);
	}

	public UnBlock(Rectangle cell, Rectangle dest) {
		super(cell, dest);
	}
	
	public UnBlock(Rectangle cell, Rectangle dest, int id) {
		super(cell, dest, id);
	}

	public Block copy() {
		return new UnBlock(RectangleUtils.CopyRectangle(cell), RectangleUtils.CopyRectangle(dest), getId());
	}

	@Override
	protected void initType() {
		type = (cell.width == 1) ? BlockType.VER : BlockType.HOR;
	}
}
