package com.swrd.unblock.bean;

import static com.swrd.unblock.bean.Puzzle.MaxHor;
import static com.swrd.unblock.bean.Puzzle.MaxVer;

import java.awt.Rectangle;

public class Block {
	enum Type {
		HOR, VER, BOTH
	};

	private Rectangle cell;
	private boolean isKing = false;
	private Type type;

	public Block(Rectangle cell) throws Exception {
		this(cell, false);
	}

	public Block(Rectangle cell, boolean isKing) throws Exception {
		this.cell = cell;
		this.isKing = isKing;

		checkType();
	}

	private void checkType() throws Exception {
		if (cell.height != 1 && cell.width == 1) {
			type = Type.VER;
		} else if (cell.height == 1 && cell.width != 1) {
			type = Type.HOR;
		} else if (cell.height == 1 && cell.width == 1) {
			type = Type.BOTH;
		} else {
			throw new Exception("Bad block");
		}
	}

	public boolean canLeft(long code) {
		if (type != Type.HOR && type != Type.BOTH) {
			return false;
		}
		if (cell.x <= 0) {
			return false;
		}
		if ((code & Puzzle.getBitCode(cell.x - 1, cell.y)) > 0) {
			return false;
		}
		return true;
	}

	public boolean canRight(long code) {
		if (type != Type.HOR && type != Type.BOTH) {
			return false;
		}
		if (cell.x + cell.width > MaxHor) {
			return false;
		}
		if ((code & Puzzle.getBitCode(cell.x + cell.width, cell.y)) > 0) {
			return false;
		}
		return true;
	}

	public boolean canUp(long code) {
		if (type != Type.VER && type != Type.BOTH) {
			return false;
		}
		if (cell.y <= 0) {
			return false;
		}
		if ((code & Puzzle.getBitCode(cell.x, cell.y - 1)) > 0) {
			return false;
		}
		return true;
	}

	public boolean canDown(long code) {
		if (type != Type.VER && type != Type.BOTH) {
			return false;
		}
		if (cell.y + cell.height > MaxVer) {
			return false;
		}
		if ((code & Puzzle.getBitCode(cell.x, cell.y + cell.height)) > 0) {
			return false;
		}
		return true;
	}

	public void moveLeft() {
		moveHor(true);
	}

	public void moveRight() {
		moveHor(false);
	}

	public void moveUp() {
		moveVer(true);
	}

	public void moveDown() {
		moveVer(false);
	}

	private void moveHor(boolean left) {
		if (left) {
			cell.x = cell.x - 1;
		} else {
			cell.x = cell.x + 1;
		}
	}

	private void moveVer(boolean up) {
		if (up) {
			cell.y = cell.y - 1;
		} else {
			cell.y = cell.y + 1;
		}
	}

	public Rectangle getCell() {
		return cell;
	}

	public boolean isKing() {
		return isKing;
	}

}
