package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import com.swrd.unblock.bound.Bound;
import com.swrd.unblock.bound.blocks.score.Scorer;
import com.swrd.unblock.bound.ds.bitarray.BitArray2D;
import com.swrd.unblock.ems.BlockType;
import com.swrd.unblock.ems.Direction;
import com.swrd.unblock.utils.RectangleUtils;

public abstract class Block implements Bound {
	public static AtomicInteger idi = new AtomicInteger(0);

	private int id;
	private double score = -1;
	protected BlockType type;
	protected Rectangle cell;
	protected Rectangle dest;

	public Block(Rectangle cell) {
		this(cell, null);
	}

	public Block(Rectangle cell, Rectangle dest) {
		this(cell, dest, BlockType.BOTH, idi.incrementAndGet());
	}
	
	public Block(Rectangle cell, Rectangle dest, int id) {
		this(cell, dest, BlockType.BOTH, id);
	}

	public Block(Rectangle cell, Rectangle dest, BlockType type, int id) {
		this.cell = cell;
		this.dest = dest;
		this.type = type;
		this.id = id;
		initType();
	}
	
	public abstract Block copy();

	protected void initType() {

	}

	/**
	 * return a score[0, 1], the higher the better
	 * 
	 * @return
	 */
	public double score() {
		if (dest != null) {
			if (score == -1) {
				score = Scorer.scorer.score(cell, dest);
			}
			return score;
		} 
		return 1.0;
	}

	public boolean atDestination() {
		if (dest == null) {
			return true;
		} else {
			return cell.equals(dest);
		}
	}

	public int check(BitArray2D ba, Direction dir) {
		if (type == BlockType.HOR) {
			if (dir != Direction.Left && dir != Direction.Right) {
				return 0;
			}
		} else if (type == BlockType.VER) {
			if (dir != Direction.Up && dir != Direction.Down) {
				return 0;
			}
		}

		int i = 1;
		for (;; ++i) {
			Rectangle r = new Rectangle(cell);
			switch (dir) {
			case Left:
				r.x = r.x - i;
				break;
			case Right:
				r.x = r.x + i;
				break;
			case Up:
				r.y = r.y - i;
				break;
			case Down:
				r.y = r.y + i;
				break;
			}
			if (!ba.checkBound(r)) {
				break;
			}
			r = RectangleUtils.SubRectangle(r, cell);
			if (ba.isMarked(r)) {
				break;
			}
		}
		return i - 1;
	}

	public void move(Direction dir, int offset) {
		switch (dir) {
		case Left:
			cell.x = cell.x - offset;
			break;
		case Right:
			cell.x = cell.x + offset;
			break;
		case Up:
			cell.y = cell.y - offset;
			break;
		case Down:
			cell.y = cell.y + offset;
			break;
		}
	}

	@Override
	public Rectangle getBound() {
		return cell;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	public Rectangle getCell() {
		return cell;
	}

	public void setCell(Rectangle cell) {
		this.cell = cell;
	}

	public Rectangle getDest() {
		return dest;
	}

	public void setDest(Rectangle dest) {
		this.dest = dest;
	}
}
