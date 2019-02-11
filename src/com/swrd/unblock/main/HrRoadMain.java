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
		blocks.add(new HRBlock(new Rectangle(1, 0, 2, 2), HRBlock.HRDestination, 'X')); // king
		blocks.add(new HRBlock(new Rectangle(3, 0, 1, 1), 'a'));
		blocks.add(new HRBlock(new Rectangle(0, 1, 1, 2), 'b'));
		blocks.add(new HRBlock(new Rectangle(3, 1, 1, 1), 'c'));
		blocks.add(new HRBlock(new Rectangle(1, 2, 1, 1), 'd'));
		blocks.add(new HRBlock(new Rectangle(2, 2, 2, 1), 'e'));
		blocks.add(new HRBlock(new Rectangle(0, 3, 1, 2), 'f'));
		blocks.add(new HRBlock(new Rectangle(1, 3, 1, 1), 'g'));
		blocks.add(new HRBlock(new Rectangle(2, 3, 2, 1), 'h'));
		blocks.add(new HRBlock(new Rectangle(2, 4, 2, 1), 'i'));
		
		// pz2
//		blocks.add(new HRBlock(new Rectangle(1, 0, 2, 2), HRBlock.HRDestination, 'X')); // king
//		blocks.add(new HRBlock(new Rectangle(0, 0, 1, 2), 'a'));
//		blocks.add(new HRBlock(new Rectangle(3, 0, 1, 2), 'b'));
//		blocks.add(new HRBlock(new Rectangle(0, 2, 1, 2), 'c'));
//		blocks.add(new HRBlock(new Rectangle(3, 2, 1, 2), 'd'));
//		blocks.add(new HRBlock(new Rectangle(1, 2, 2, 1), 'e'));
//		blocks.add(new HRBlock(new Rectangle(0, 4, 1, 1), 'f'));
//		blocks.add(new HRBlock(new Rectangle(1, 4, 1, 1), 'g'));
//		blocks.add(new HRBlock(new Rectangle(2, 4, 1, 1), 'h'));
//		blocks.add(new HRBlock(new Rectangle(3, 4, 1, 1), 'i'));

		Puzzle p = new Puzzle("HR-1", PuzzleType.HRRoad, new Rectangle(HRBlock.HRWidth, HRBlock.HRHeight), blocks);
		p.setExit(new Rectangle(1, 5, 2, 1));

		return p;
	}
}
