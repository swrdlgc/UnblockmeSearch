package com.swrd.unblock.solver;

import com.swrd.unblock.ems.SolverType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.abs.AbstractGroupSolver;
import com.swrd.unblock.solver.api.Solver;

public class DfsGroupSolver extends AbstractGroupSolver {

	public DfsGroupSolver(Puzzle puzzle, String name) {
		super(puzzle, name);
	}

	@Override
	public Solver getSolver(Puzzle puzzle) {
		return new DfsSolver(puzzle, name, SolverType.IDFS);
	}

}
