package com.swrd.unblock.solver.api;

import com.swrd.unblock.puzzle.Puzzle;

public interface GroupSolver extends Solver {
	Solver getSolver(Puzzle puzzle);
}
