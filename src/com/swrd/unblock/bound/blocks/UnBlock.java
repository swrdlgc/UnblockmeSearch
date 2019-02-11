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
	public final static Rectangle UnBlockMeDestination = new Rectangle(4, 2, 2, 1);
	public final static int UnBlockMeWidth = 6;
	public final static int UnBlockMeHeight = 6;
	
	public UnBlock(Rectangle cell) {
		super(cell);
	}
	
	public UnBlock(Rectangle cell, char label) {
		super(cell, label);
	}

	public UnBlock(Rectangle cell, Rectangle dest, char label) {
		super(cell, dest, label);
	}
	
	public UnBlock(Rectangle cell, Rectangle dest, int id, char label) {
		super(cell, dest, id, label);
	}

	public Block copy() {
		return new UnBlock(RectangleUtils.CopyRectangle(cell), dest, getId(), getLabel());
	}

	@Override
	protected void initType() {
		type = (cell.width == 1) ? BlockType.VER : BlockType.HOR;
	}
}
