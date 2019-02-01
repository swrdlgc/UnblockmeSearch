package com.swrd.unblock.main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.UnBlock;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.puzzle.Puzzle;

public class UnBlockMeMain extends Main {
	
	public static void main(String[] args) {
		play(getPuzzle(), args);
	}

	public static Puzzle getPuzzle() {
		List<Block> blocks = new ArrayList<Block>();

		// pz1
//		blocks.add(new UnBlock(new Rectangle(2, 0, 1, 3)));
//		blocks.add(new UnBlock(new Rectangle(0, 2, 2, 1), UnBlock.UnBlockMeDestination)); // king
		
		// pz2
		blocks.add(new UnBlock(new Rectangle(0, 0, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(2, 0, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(3, 0, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(5, 0, 1, 3)));
		blocks.add(new UnBlock(new Rectangle(0, 1, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(1, 2, 2, 1), UnBlock.UnBlockMeDestination));
		blocks.add(new UnBlock(new Rectangle(4, 1, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(0, 3, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(1, 3, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(2, 3, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(4, 3, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(0, 5, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(3, 4, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(4, 4, 2, 1)));
		
		// pz3
//		blocks.add(new UnBlock(new Rectangle(0, 0, 1, 3)));
//		blocks.add(new UnBlock(new Rectangle(1, 0, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(3, 0, 1, 2)));
//		blocks.add(new UnBlock(new Rectangle(5, 0, 1, 3)));
//		blocks.add(new UnBlock(new Rectangle(1, 1, 1, 2)));
//		blocks.add(new UnBlock(new Rectangle(2, 2, 2, 1), UnBlock.UnBlockMeDestination));
//		blocks.add(new UnBlock(new Rectangle(4, 1, 1, 3)));
//		blocks.add(new UnBlock(new Rectangle(0, 3, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(2, 3, 1, 2)));
//		blocks.add(new UnBlock(new Rectangle(3, 3, 1, 2)));
//		blocks.add(new UnBlock(new Rectangle(4, 4, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(1, 5, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(3, 5, 2, 1)));

		Puzzle p = new Puzzle("UB-1", PuzzleType.UnBlockMe, new Rectangle(UnBlock.UnBlockMeWidth, UnBlock.UnBlockMeHeight), blocks);
		p.setExit(new Rectangle(6, 2, 1, 1));

		return p;
	}
}
