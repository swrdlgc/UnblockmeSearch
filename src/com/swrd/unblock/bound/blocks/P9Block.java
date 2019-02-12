package com.swrd.unblock.bound.blocks;

import java.awt.Rectangle;

import com.swrd.unblock.utils.RectangleUtils;

/**
 * 九宫格小块
 * 
 * @author swrdlgc
 *
 */
public class P9Block extends Block {
	public final static int Width = 3;
	public final static int Height = 3;
	public final static Rectangle DestinationVer[] = new Rectangle[] { 
		new Rectangle(0, 0, 1, 1), new Rectangle(0, 1, 1, 1), new Rectangle(0, 2, 1, 1),
		new Rectangle(1, 0, 1, 1), new Rectangle(1, 1, 1, 1), new Rectangle(1, 2, 1, 1),
		new Rectangle(2, 0, 1, 1), new Rectangle(2, 1, 1, 1)
		};
	public final static Rectangle DestinationHor[] = new Rectangle[] { 
			new Rectangle(0, 0, 1, 1), new Rectangle(1, 0, 1, 1), new Rectangle(2, 0, 1, 1),
			new Rectangle(1, 0, 1, 1), new Rectangle(1, 1, 1, 1), new Rectangle(2, 1, 1, 1),
			new Rectangle(2, 0, 1, 1), new Rectangle(2, 1, 1, 1)
			};
	public final static Rectangle Bounds = new Rectangle(Width, Height);
	
	public P9Block(Rectangle cell, int i) {
		super(cell, DestinationVer[i], (char)('0'+i));
	}

	public P9Block(Rectangle cell, Rectangle dest, char label) {
		super(cell, dest, label);
	}
	
	public P9Block(Rectangle cell, Rectangle dest, int id, char label) {
		super(cell, dest, id, label);
	}

	public Block copy() {
		return new P9Block(RectangleUtils.CopyRectangle(cell), dest, getId(), getLabel());
	}
}
