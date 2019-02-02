package com.swrd.unblock.solver;

import com.swrd.unblock.ems.SolverType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.abs.AbstractGroupSolver;
import com.swrd.unblock.solver.api.Solver;

public class IAstarGroupSolver extends AbstractGroupSolver {

	public IAstarGroupSolver(Puzzle puzzle, String name) {
		super(puzzle, name);
	}

	@Override
	public Solver getSolver(Puzzle puzzle) {
		return new AstarSolver(puzzle, name, SolverType.IAstar);
	}

}
