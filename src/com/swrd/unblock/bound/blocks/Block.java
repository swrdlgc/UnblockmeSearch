package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.swrd.unblock.bound.Bound;
import com.swrd.unblock.bound.blocks.score.Scorer;
import com.swrd.unblock.bound.ds.bitarray.BitArray2D;
import com.swrd.unblock.ems.BlockType;
import com.swrd.unblock.ems.Direction;

public abstract class Block implements Bound {
	public static AtomicInteger idi = new AtomicInteger(0);
	public static Color YellowColor;
	public static Color RedColor;

	static {
		try {
			YellowColor = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
			RedColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		} catch (Exception e) {
		}
	}
	
	private int id;
	private double score = -1;
	protected BlockType type;
	protected Rectangle cell;
	protected Rectangle dest;
	protected Color color;
	protected boolean selected;

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
		if(dest == null) {
			this.color = YellowColor;
		} else {
			this.color = RedColor;
		}
		this.id = id;
		initType();
	}
	
	public abstract Block copy();

	protected void initType() {

	}
	
	public String getLabel() {
		return null;
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
			
			for(int ri = 0; ri < r.width; ++ri) {
				for(int rj = 0; rj < r.height; ++rj) {
					int x = r.x + ri;
					int y = r.y + rj;
					if (!cell.contains(x, y) && ba.isFilled(x, y)) {
						return i - 1;
					}
				}
			}
			
//			r = RectangleUtils.SubRectangle(r, cell);
//			if (ba.isMarked(r)) {
//				break;
//			}
		}
		return i - 1;
	}

	public void move(Direction dir, int offset) {
		//System.err.print(cell);
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
		//System.err.println(" -> " + cell);
	}
	
	public boolean contains(int col, int row) {
		return cell.contains(col, row);
	}
	
	public Direction getDirection(int col, int row) {
		if(cell.x <= col && col < cell.x + cell.width) {
			if(row < cell.y) {
				return Direction.Up;
			} else if(row >= cell.y + cell.height) {
				return Direction.Down;
			}
		}
		if(cell.y <= row && row < cell.y + cell.height) {
			if(col < cell.x) {
				return Direction.Left;
			}
			if(col >= cell.x + cell.width) {
				return Direction.Right;
			}
		}
		
		return null;
	}
	
	public boolean moveTo(int col, int row) {
		if(cell.width > 1 && (cell.y <= row && row < cell.y + cell.height)) {
			//System.err.print(cell);
			if(cell.x > col) {
				cell.x = col;
			} else {
				cell.x = col - cell.width + 1;
			}
			//System.err.println(" -> " + cell);
			return true;
		}
		if(cell.height > 1 && (cell.x <= col && col < cell.x + cell.width)) {
			//System.err.print(cell);
			if(cell.y > row) {
				cell.y = row;
			} else {
				cell.y = row - cell.height + 1;
			}
			//System.err.println(" -> " + cell);
			return true;
		}
		
		return false;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Block) {
			return id == ((Block)obj).id; 
		}
		return false;
	}
}
