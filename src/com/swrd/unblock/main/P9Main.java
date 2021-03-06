package com.swrd.unblock.main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.P9Block;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.puzzle.Puzzle;

public class P9Main extends Main {
	
	public static void main(String[] args) {
		play(getPuzzle(), args);
	}
	
	public static Puzzle getPuzzle() {
		List<Block> blocks = new ArrayList<Block>();

		// pz1
//		blocks.add(new P9Block(new Rectangle(0, 0, 1, 1), 0));
//		blocks.add(new P9Block(new Rectangle(1, 0, 1, 1), 1));
//		blocks.add(new P9Block(new Rectangle(2, 0, 1, 1), 2));
//		blocks.add(new P9Block(new Rectangle(0, 1, 1, 1), 3));
//		blocks.add(new P9Block(new Rectangle(1, 1, 1, 1), 4));
//		blocks.add(new P9Block(new Rectangle(2, 1, 1, 1), 5));
//		blocks.add(new P9Block(new Rectangle(0, 2, 1, 1), 6));
//		blocks.add(new P9Block(new Rectangle(1, 2, 1, 1), 7));
		
		// pz2
		blocks.add(new P9Block(new Rectangle(0, 0, 1, 1), 1));
		blocks.add(new P9Block(new Rectangle(1, 0, 1, 1), 2));
		blocks.add(new P9Block(new Rectangle(2, 0, 1, 1), 0));
		blocks.add(new P9Block(new Rectangle(0, 1, 1, 1), 4));
		blocks.add(new P9Block(new Rectangle(1, 1, 1, 1), 5));
		blocks.add(new P9Block(new Rectangle(2, 1, 1, 1), 6));
		blocks.add(new P9Block(new Rectangle(0, 2, 1, 1), 7));
		blocks.add(new P9Block(new Rectangle(1, 2, 1, 1), 3));
		
		Puzzle p = new Puzzle("P9-1", PuzzleType.P9, new Rectangle(P9Block.Width, P9Block.Height), blocks);

		return p;
	}
}
