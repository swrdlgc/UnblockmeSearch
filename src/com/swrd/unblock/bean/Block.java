package com.swrd.unblock.bean;

import static com.swrd.unblock.bean.Puzzle.MaxHor;
import static com.swrd.unblock.bean.Puzzle.MaxVer;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

public class Block {
	public static AtomicInteger idi = new AtomicInteger(0);
	enum Type {
		HOR, VER, BOTH
	};

	private int id;
	private Rectangle cell;
	private boolean isKing = false;
	private Type type;

	public Block(Rectangle cell) throws Exception {
		this(cell, false);
	}

	public Block(Rectangle cell, boolean isKing) throws Exception {
		this.id = idi.incrementAndGet();
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

	public int canLeft(long code) {
		if (type != Type.HOR && type != Type.BOTH) {
			return 0;
		}
		if (cell.x <= 0) {
			return 0;
		}
		for (int i = 1; i <= MaxHor; ++i) {
			if ((code & Puzzle.getBitCode(cell.x - i, cell.y)) > 0 || cell.x - i < 0) {
				return i - 1;
			}
		}
		return 0;
	}

	public int canRight(long code) {
		if (type != Type.HOR && type != Type.BOTH) {
			return 0;
		}
		if (cell.x + cell.width > MaxHor) {
			return 0;
		}
		for (int i = 1; i <= MaxHor; ++i) {
			if ((code & Puzzle.getBitCode(cell.x + cell.width + i - 1, cell.y)) > 0 || cell.x + cell.width + i - 1 > MaxHor) {
				return i - 1;
			}
		}
		return 0;
	}

	public int canUp(long code) {
		if (type != Type.VER && type != Type.BOTH) {
			return 0;
		}
		if (cell.y <= 0) {
			return 0;
		}
		for (int i = 1; i <= MaxVer; ++i) {
			if ((code & Puzzle.getBitCode(cell.x, cell.y - i)) > 0 || cell.y - i < 0) {
				return i - 1;
			}
		}
		return 0;
	}

	public int canDown(long code) {
		if (type != Type.VER && type != Type.BOTH) {
			return 0;
		}
		if (cell.y + cell.height > MaxVer) {
			return 0;
		}
		for (int i = 1; i <= MaxVer; ++i) {
			if ((code & Puzzle.getBitCode(cell.x, cell.y + cell.height + i - 1)) > 0 || cell.y + cell.height + i - 1 > MaxVer) {
				return i - 1;
			}
		}
		return 0;
	}

	public void moveLeft(int offset) {
		moveHor(true, offset);
	}

	public void moveRight(int offset) {
		moveHor(false, offset);
	}

	public void moveUp(int offset) {
		moveVer(true, offset);
	}

	public void moveDown(int offset) {
		moveVer(false, offset);
	}

	private void moveHor(boolean left, int offset) {
		if (left) {
			cell.x = cell.x - offset;
		} else {
			cell.x = cell.x + offset;
		}
	}

	private void moveVer(boolean up, int offset) {
		if (up) {
			cell.y = cell.y - offset;
		} else {
			cell.y = cell.y + offset;
		}
	}
	
	public int getId() {
		return id;
	}

	public Rectangle getCell() {
		return cell;
	}

	public boolean isKing() {
		return isKing;
	}

}
