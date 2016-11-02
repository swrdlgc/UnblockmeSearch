package com.swrd.unblock.bean;

public class Step {
	enum Move {
		Left, Right, Up, Down
	};

	private Block block;
	private Move move;

	public Step(Block block, Move move) {
		this.block = block;
		this.move = move;
	}

	public Block getBlock() {
		return block;
	}

	public Move getMove() {
		return move;
	}
	
	public void stepBack() {
		switch (move) {
		case Left:
			block.moveRight();
			break;
		case Right:
			block.moveLeft();
			break;
		case Up:
			block.moveDown();
			break;
		case Down:
			block.moveUp();
			break;
		default:
			break;
		}
	}
	
	public void stepIn() {
		switch (move) {
		case Left:
			block.moveLeft();
			break;
		case Right:
			block.moveRight();
			break;
		case Up:
			block.moveUp();
			break;
		case Down:
			block.moveDown();
			break;
		default:
			break;
		}
	}
}