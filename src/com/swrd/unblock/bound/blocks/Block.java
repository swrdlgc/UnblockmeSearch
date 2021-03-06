package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.graphics.Color;

import com.swrd.unblock.bound.Bound;
import com.swrd.unblock.bound.blocks.score.ScorerFactory;
import com.swrd.unblock.bound.ds.bitarray.BitArray2D;
import com.swrd.unblock.ems.BlockType;
import com.swrd.unblock.ems.Direction;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.utils.ColorUtils;

public abstract class Block implements Bound, Comparable<Block> {
	public static AtomicInteger idi = new AtomicInteger(0);

	protected int id;
	private double score = -1;
	protected BlockType type;
	protected Rectangle cell;
	protected Rectangle dest;
	protected Color color;
	protected boolean selected;
	protected char label;

	public Block(Rectangle cell) {
		this(cell, null, (char) 0);
	}
	
	public Block(Rectangle cell, char label) {
		this(cell, null, label);
	}

	public Block(Rectangle cell, Rectangle dest, char label) {
		this(cell, dest, BlockType.BOTH, idi.incrementAndGet(), label);
	}
	
	public Block(Rectangle cell, Rectangle dest, int id, char label) {
		this(cell, dest, BlockType.BOTH, id, label);
	}

	public Block(Rectangle cell, Rectangle dest, BlockType type, int id, char label) {
		this.cell = cell;
		this.dest = dest;
		this.type = type;
		if(dest == null) {
			this.color = ColorUtils.Yellow;
		} else {
			this.color = ColorUtils.Red;
		}
		this.id = id;
		this.label = label;
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
				score = ScorerFactory.scorer.score(cell, dest);
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
// 通过计算移动后新增矩形来优化，没有效果，可能因为cell面积小
//			int x = 0, y = 0, w = 0, h = 0;
			switch (dir) {
			case Left:
				r.x = r.x - i;
				
//				x = r.x;
//				y = cell.y;
//				w = i;
//				h = cell.height;
				break;
			case Right:
				r.x = r.x + i;
				
//				x = cell.x + cell.width;
//				y = cell.y;
//				w = i;
//				h = cell.height;
				break;
			case Up:
				r.y = r.y - i;
				
//				x = cell.x;
//				y = r.y;
//				w = cell.width;
//				h = i;
				break;
			case Down:
				r.y = r.y + i;
				
//				x = cell.x;
//				y = cell.y + cell.height;
//				w = cell.width;
//				h = i;
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
						
//			for(int ri = 0; ri < w; ++ri) {
//				for(int rj = 0; rj < h; ++rj) {
//					if (ba.isFilled(x+ri, y+rj)) {
//						return i - 1;
//					}
//				}
//			}
			
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
	
	public Step getStep(int col, int row) {
		if(cell.x <= col && col < cell.x + cell.width) {
			if(row < cell.y) {
				return new Step(this, Direction.Up, cell.y - row, 0);
			} else if(row >= cell.y + cell.height) {
				return new Step(this, Direction.Down, row - cell.y - cell.height + 1, 0);
			}
		}
		if(cell.y <= row && row < cell.y + cell.height) {
			if(col < cell.x) {
				return new Step(this, Direction.Left, cell.x - col, 0);
			}
			if(col >= cell.x + cell.width) {
				return new Step(this, Direction.Right, col - cell.x - cell.width + 1, 0);
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
	
	public char getLabel() {
		return label;
	}
	
	public void setLabel(char label) {
		this.label = label;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Block) {
			return id == ((Block)obj).id; 
		}
		return false;
	}
	
	public int getArea() {
		return cell.width * cell.height;
	}
	
	@Override
	public int compareTo(Block o) {
		return id - o.id;
	}
}
