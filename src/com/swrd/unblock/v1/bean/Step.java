package com.swrd.unblock.v1.bean;

public class Step {
	enum Move {
		Left, Right, Up, Down
	};

	private Block block;
	private Move move;
	private int offset;

	public Step(Block block, Move move, int offset) {
		this.block = block;
		this.move = move;
		this.offset = offset;
	}

	public Block getBlock() {
		return block;
	}

	public Move getMove() {
		return move;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void stepBack() {
		switch (move) {
		case Left:
			block.moveRight(offset);
			break;
		case Right:
			block.moveLeft(offset);
			break;
		case Up:
			block.moveDown(offset);
			break;
		case Down:
			block.moveUp(offset);
			break;
		default:
			break;
		}
	}
	
	public void stepIn() {
		switch (move) {
		case Left:
			block.moveLeft(offset);
			break;
		case Right:
			block.moveRight(offset);
			break;
		case Up:
			block.moveUp(offset);
			break;
		case Down:
			block.moveDown(offset);
			break;
		default:
			break;
		}
	}
}