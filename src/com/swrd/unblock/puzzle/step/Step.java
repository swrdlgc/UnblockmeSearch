package com.swrd.unblock.puzzle.step;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.ems.Direction;
import com.swrd.unblock.utils.RectangleUtils;

public class Step {
	private Block block;
	private Direction direct;
	private int offset;
	private int level;

	public Step(Block block, Direction direct, int offset, int level) {
		this.block = block;
		this.direct = direct;
		this.offset = offset;
		this.level = level;
	}

	public Block getBlock() {
		return block;
	}

	public Direction getDirect() {
		return direct;
	}

	public int getOffset() {
		return offset;
	}

	public int getLevel() {
		return level;
	}

	public void stepBack() {
		block.move(direct, -offset);
	}

	public void stepIn() {
		block.move(direct, offset);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("step: %c[%s]->{%10s %d}", 
				block.getLabel(), 
				RectangleUtils.EncodeRectangle(block.getCell()),
				direct, offset));
		sb.append(String.format(" = [%s]",
				RectangleUtils.EncodeRectangle(block.getCell())));
		
		return sb.toString();
	}
}