package com.swrd.unblock.solver;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.swrd.unblock.ems.SolverType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.abs.AbstractSolver;

public class DfsSolver extends AbstractSolver {

	private LinkedHashMap<String, Puzzle> puzzles = new LinkedHashMap<>();
	
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
		Iterator<String> it = puzzles.keySet().iterator();
		return puzzles.remove(it.next());
	}

	@Override
	protected void putPuzzle(Puzzle p) {
		puzzles.put(p.getKey(), p);
	}

	@Override
	protected void removePuzzle(Puzzle p) {
	}
}
