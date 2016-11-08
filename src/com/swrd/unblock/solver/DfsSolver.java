package com.swrd.unblock.solver;

import java.util.LinkedList;
import java.util.List;

import com.swrd.unblock.ems.SolverType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.abs.AbstractSolver;

public class DfsSolver extends AbstractSolver {

	private List<Puzzle> puzzles = new LinkedList<>();
	
	public DfsSolver(Puzzle puzzle, String name) {
		this(puzzle, name, SolverType.DFS);
	}
	
	public DfsSolver(Puzzle puzzle, String name, SolverType type) {
		super(name, type);
		putPuzzle(puzzle);
	}
	
	@Override
	protected Puzzle getNextPuzzle() {
		if(puzzles.isEmpty()) {
			return null;
		}
		return puzzles.remove(0);
	}

	@Override
	protected void putPuzzle(Puzzle p) {
		puzzles.add(0, p);
	}

}
