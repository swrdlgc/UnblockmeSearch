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
//		blocks.add(new UnBlock(new Rectangle(2, 0, 1, 3), 'a'));
//		blocks.add(new UnBlock(new Rectangle(0, 2, 2, 1), UnBlock.Destination, 'X')); // king
		
		// pz2
		blocks.add(new UnBlock(new Rectangle(0, 0, 2, 1), 'a'));
		blocks.add(new UnBlock(new Rectangle(2, 0, 1, 2), 'b'));
		blocks.add(new UnBlock(new Rectangle(3, 0, 2, 1), 'c'));
		blocks.add(new UnBlock(new Rectangle(5, 0, 1, 3), 'd'));
		blocks.add(new UnBlock(new Rectangle(0, 1, 1, 2), 'e'));
		blocks.add(new UnBlock(new Rectangle(1, 2, 2, 1), UnBlock.Destination, 'X'));
		blocks.add(new UnBlock(new Rectangle(4, 1, 1, 2), 'f'));
		blocks.add(new UnBlock(new Rectangle(0, 3, 1, 2), 'g'));
		blocks.add(new UnBlock(new Rectangle(1, 3, 1, 2), 'h'));
		blocks.add(new UnBlock(new Rectangle(2, 3, 2, 1), 'i'));
		blocks.add(new UnBlock(new Rectangle(4, 3, 2, 1), 'j'));
		blocks.add(new UnBlock(new Rectangle(0, 5, 2, 1), 'k'));
		blocks.add(new UnBlock(new Rectangle(3, 4, 1, 2), 'l'));
		blocks.add(new UnBlock(new Rectangle(4, 4, 2, 1), 'm'));
		
		// pz3
//		blocks.add(new UnBlock(new Rectangle(0, 0, 1, 3), 'a'));
//		blocks.add(new UnBlock(new Rectangle(1, 0, 2, 1), 'b'));
//		blocks.add(new UnBlock(new Rectangle(3, 0, 1, 2), 'c'));
//		blocks.add(new UnBlock(new Rectangle(5, 0, 1, 3), 'd'));
//		blocks.add(new UnBlock(new Rectangle(1, 1, 1, 2), 'e'));
//		blocks.add(new UnBlock(new Rectangle(2, 2, 2, 1), UnBlock.Destination, 'X'));
//		blocks.add(new UnBlock(new Rectangle(4, 1, 1, 3), 'f'));
//		blocks.add(new UnBlock(new Rectangle(0, 3, 2, 1), 'g'));
//		blocks.add(new UnBlock(new Rectangle(2, 3, 1, 2), 'h'));
//		blocks.add(new UnBlock(new Rectangle(3, 3, 1, 2), 'i'));
//		blocks.add(new UnBlock(new Rectangle(4, 4, 2, 1), 'j'));
//		blocks.add(new UnBlock(new Rectangle(1, 5, 2, 1), 'k'));
//		blocks.add(new UnBlock(new Rectangle(3, 5, 2, 1), 'l'));

		Puzzle p = new Puzzle("UB-1", PuzzleType.UnBlockMe, new Rectangle(UnBlock.Width, UnBlock.Height), blocks);
		p.setExit(new Rectangle(6, 2, 1, 1));

		return p;
	}
}
