package com.swrd.unblock.solver;

import java.util.LinkedList;
import java.util.List;

import com.swrd.unblock.puzzle.Puzzle;

public class DfsSolver extends AbstractSolver {

	private List<Puzzle> puzzles = new LinkedList<>();
	
	public DfsSolver(Puzzle puzzle, String name) {
		super(name);
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
