package com.swrd.unblock.main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.HRBlock;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.puzzle.Puzzle;

public class HrRoadMain extends Main {

	public static void main(String[] args) {
		play(getPuzzle(), args);
	}

	public static Puzzle getPuzzle() {
		List<Block> blocks = new ArrayList<Block>();

		// pz1
		blocks.add(new HRBlock(new Rectangle(1, 0, 2, 2), HRBlock.HRDestination)); // king
		blocks.add(new HRBlock(new Rectangle(3, 0, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(0, 1, 1, 2)));
		blocks.add(new HRBlock(new Rectangle(3, 1, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(1, 2, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(2, 2, 2, 1)));
		blocks.add(new HRBlock(new Rectangle(0, 3, 1, 2)));
		blocks.add(new HRBlock(new Rectangle(1, 3, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(2, 3, 2, 1)));
		blocks.add(new HRBlock(new Rectangle(2, 4, 2, 1)));
		
		// pz2
//		blocks.add(new HRBlock(new Rectangle(1, 0, 2, 2), HRBlock.HRDestination)); // king
//		blocks.add(new HRBlock(new Rectangle(0, 0, 1, 2)));
//		blocks.add(new HRBlock(new Rectangle(3, 0, 1, 2)));
//		blocks.add(new HRBlock(new Rectangle(0, 2, 1, 2)));
//		blocks.add(new HRBlock(new Rectangle(3, 2, 1, 2)));
//		blocks.add(new HRBlock(new Rectangle(1, 2, 2, 1)));
//		blocks.add(new HRBlock(new Rectangle(0, 4, 1, 1)));
//		blocks.add(new HRBlock(new Rectangle(1, 4, 1, 1)));
//		blocks.add(new HRBlock(new Rectangle(2, 4, 1, 1)));
//		blocks.add(new HRBlock(new Rectangle(3, 4, 1, 1)));

		Puzzle p = new Puzzle("HR-1", PuzzleType.HRRoad, new Rectangle(HRBlock.HRWidth, HRBlock.HRHeight), blocks);
		p.setExit(new Rectangle(1, 5, 2, 1));

		return p;
	}
}
