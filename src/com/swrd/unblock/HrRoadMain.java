package com.swrd.unblock;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.HRBlock;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.AstarSolver;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.DfsSolver;
import com.swrd.unblock.solver.Solver;

public class HrRoadMain {
	public static final int HRWidth = 4;
	public static final int HRHeight = 5;
	public static final Rectangle HRDestination = new Rectangle(1, 3, 2, 2);
	
	public static void main(String[] args) {
		hrRoad();
	}
	
	static Puzzle getPuzzle() {
		List<Block> blocks = new ArrayList<Block>();

		// pz1
		blocks.add(new HRBlock(new Rectangle(1, 0, 2, 2), HRDestination)); // king
		blocks.add(new HRBlock(new Rectangle(3, 0, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(0, 1, 1, 2)));
		blocks.add(new HRBlock(new Rectangle(3, 1, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(1, 2, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(2, 2, 2, 1)));
		blocks.add(new HRBlock(new Rectangle(0, 3, 1, 2)));
		blocks.add(new HRBlock(new Rectangle(1, 3, 1, 1)));
		blocks.add(new HRBlock(new Rectangle(2, 3, 2, 1)));
		blocks.add(new HRBlock(new Rectangle(2, 4, 2, 1)));

		Puzzle p = new Puzzle("HR-1", PuzzleType.HRRoad, new Rectangle(HRWidth, HRHeight), blocks);

		return p;
	}

	static void hrRoad() {
		Puzzle p = getPuzzle();
				
		// DFS
		Solver solver = new DfsSolver(p, "DFS");
//		solver.search();
		System.err.println(solver);
		
		// BFS
		solver = new BfsSolver(p, "BFS");
//		solver.search();
		System.err.println(solver);
		
		// A-star
		solver = new AstarSolver(p, "Astar"); // not work, too long
//		solver.search();
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
