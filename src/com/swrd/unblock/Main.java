package com.swrd.unblock;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.UnBlock;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.AstarSolver;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.DfsSolver;
import com.swrd.unblock.solver.Solver;

public class Main {
	
	public static void main(String[] args) {
		unBlockMe();
	}
	
	static Puzzle getPuzzle() {
		List<Block> blocks = new ArrayList<Block>();

		// pz1
//		blocks.add(new UnBlock(new Rectangle(2, 0, 1, 3)));
//		blocks.add(new UnBlock(new Rectangle(0, 2, 2, 1), Puzzle.UnBlockMeDestination)); // king
		
		// pz2
		blocks.add(new UnBlock(new Rectangle(0, 0, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(2, 0, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(3, 0, 2, 1)));
		blocks.add(new UnBlock(new Rectangle(5, 0, 1, 3)));
		blocks.add(new UnBlock(new Rectangle(0, 1, 1, 2)));
		blocks.add(new UnBlock(new Rectangle(1, 2, 2, 1), Puzzle.UnBlockMeDestination));
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
//		blocks.add(new UnBlock(new Rectangle(2, 2, 2, 1), Puzzle.UnBlockMeDestination));
//		blocks.add(new UnBlock(new Rectangle(4, 1, 1, 3)));
//		blocks.add(new UnBlock(new Rectangle(0, 3, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(2, 3, 1, 2)));
//		blocks.add(new UnBlock(new Rectangle(3, 3, 1, 2)));
//		blocks.add(new UnBlock(new Rectangle(4, 4, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(1, 5, 2, 1)));
//		blocks.add(new UnBlock(new Rectangle(3, 5, 2, 1)));

		Puzzle p = new Puzzle("UB-1", new Rectangle(Puzzle.UnBlockMeWidth, Puzzle.UnBlockMeHeight), blocks);

		return p;
	}

	static void unBlockMe() {
		Puzzle p = getPuzzle();
				
		// DFS
		Solver solver = new DfsSolver(p, "DFS");
		solver.search();
		System.err.println(solver);
		
		// BFS
		solver = new BfsSolver(p, "BFS");
		solver.search();
		System.err.println(solver);
		
		// A-star
		solver = new AstarSolver(p, "Astar");
		solver.search();
		System.err.println(solver);
		
		// DFS + step in
		for(int i = 1; ;++i) {
			solver = new DfsSolver(p, "DFS + StepIn");
			solver.setMaxStep(i);
			solver.search();
			if(solver.isSolved()) {
				break;
			}
		}
		System.err.println(solver);
		

	}

}
