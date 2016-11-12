package com.swrd.unblock.main;

import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.AstarSolver;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.DfsGroupSolver;
import com.swrd.unblock.solver.DfsSolver;
import com.swrd.unblock.solver.IAstarGroupSolver;
import com.swrd.unblock.solver.api.Solver;
import com.swrd.unblock.utils.SolverUtils;

public abstract class Main {

	public static void play(Puzzle p, String[] args) {

		// DFS
		Solver solver = new DfsSolver(p, "DFS");
		solver.search();
		System.err.println(SolverUtils.printSolver(solver));
		
		// BFS
		solver = new BfsSolver(p, "BFS");
		solver.search();
		System.err.println(SolverUtils.printSolver(solver));
		
		// A-star
		solver = new AstarSolver(p, "Astar"); 
		solver.search();
		System.err.println(SolverUtils.printSolver(solver));
		
		// DFS + step in
		solver = new DfsGroupSolver(p, "DFS + StepIn");
		solver.search();
		System.err.println(SolverUtils.printSolver(solver));
		
		// IA-star
		solver = new IAstarGroupSolver(p, "IAstar"); 
		solver.search();
		System.err.println(SolverUtils.printSolver(solver));
	}

}
