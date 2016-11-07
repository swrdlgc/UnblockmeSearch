package com.swrd.unblock;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.P9Block;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.AstarSolver;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.DfsSolver;
import com.swrd.unblock.solver.Solver;

public class P9Main {
	public static void main(String[] args) {
		unBlockMe();
	}
	
	static Puzzle getPuzzle() {
		List<Block> blocks = new ArrayList<Block>();

		// pz1
//		blocks.add(new P9Block(new Rectangle(0, 0, 1, 1), P9Block.P9BlockDestination[0]));
//		blocks.add(new P9Block(new Rectangle(0, 1, 1, 1), P9Block.P9BlockDestination[1]));
//		blocks.add(new P9Block(new Rectangle(0, 2, 1, 1), P9Block.P9BlockDestination[2]));
//		blocks.add(new P9Block(new Rectangle(1, 0, 1, 1), P9Block.P9BlockDestination[3]));
//		blocks.add(new P9Block(new Rectangle(1, 1, 1, 1), P9Block.P9BlockDestination[4]));
//		blocks.add(new P9Block(new Rectangle(1, 2, 1, 1), P9Block.P9BlockDestination[5]));
//		blocks.add(new P9Block(new Rectangle(2, 0, 1, 1), P9Block.P9BlockDestination[6]));
//		blocks.add(new P9Block(new Rectangle(2, 1, 1, 1), P9Block.P9BlockDestination[7]));
		
		// pz2
		blocks.add(new P9Block(new Rectangle(0, 0, 1, 1), P9Block.P9BlockDestination[1]));
		blocks.add(new P9Block(new Rectangle(0, 1, 1, 1), P9Block.P9BlockDestination[2]));
		blocks.add(new P9Block(new Rectangle(0, 2, 1, 1), P9Block.P9BlockDestination[0]));
		blocks.add(new P9Block(new Rectangle(1, 0, 1, 1), P9Block.P9BlockDestination[4]));
		blocks.add(new P9Block(new Rectangle(1, 1, 1, 1), P9Block.P9BlockDestination[5]));
		blocks.add(new P9Block(new Rectangle(1, 2, 1, 1), P9Block.P9BlockDestination[6]));
		blocks.add(new P9Block(new Rectangle(2, 0, 1, 1), P9Block.P9BlockDestination[7]));
		blocks.add(new P9Block(new Rectangle(2, 1, 1, 1), P9Block.P9BlockDestination[3]));
		
		Puzzle p = new Puzzle("P9-1", PuzzleType.P9, new Rectangle(P9Block.P9Width, P9Block.P9Height), blocks);

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
