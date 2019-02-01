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
	public final static int P9Width = 3;
	public final static int P9Height = 3;
	public final static Rectangle P9BlockDestinationVer[] = new Rectangle[] { 
		new Rectangle(0, 0, 1, 1), new Rectangle(0, 1, 1, 1), new Rectangle(0, 2, 1, 1),
		new Rectangle(1, 0, 1, 1), new Rectangle(1, 1, 1, 1), new Rectangle(1, 2, 1, 1),
		new Rectangle(2, 0, 1, 1), new Rectangle(2, 1, 1, 1)
		};
	public final static Rectangle P9BlockDestinationHor[] = new Rectangle[] { 
			new Rectangle(0, 0, 1, 1), new Rectangle(1, 0, 1, 1), new Rectangle(2, 0, 1, 1),
			new Rectangle(1, 0, 1, 1), new Rectangle(1, 1, 1, 1), new Rectangle(2, 1, 1, 1),
			new Rectangle(2, 0, 1, 1), new Rectangle(2, 1, 1, 1)
			};
	
	private int label;
	public P9Block(Rectangle cell, int i) {
		super(cell, P9BlockDestinationVer[i]);
		this.label = i;
	}

	public P9Block(Rectangle cell, Rectangle dest) {
		super(cell, dest);
	}
	
	public P9Block(Rectangle cell, Rectangle dest, int id) {
		super(cell, dest, id);
	}

	public Block copy() {
		return new P9Block(RectangleUtils.CopyRectangle(cell), dest, getId());
	}
	
	public String getLabel() {
		return label+"";
	}
}
