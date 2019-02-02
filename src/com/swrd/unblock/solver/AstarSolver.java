package com.swrd.unblock.solver;

import java.util.PriorityQueue;
import java.util.Queue;

import com.swrd.unblock.ems.SolverType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.solver.abs.AbstractSolver;

public class AstarSolver extends AbstractSolver {
	
	private Queue<Puzzle> pq = new PriorityQueue<Puzzle>();
	
	public AstarSolver(Puzzle puzzle, String name) {
		this(puzzle, name, SolverType.Astar);
	}
	
	public AstarSolver(Puzzle puzzle, String name, SolverType type) {
		super(name, type);
		putPuzzle(puzzle);
	}

	@Override
	protected Puzzle getNextPuzzle() {
		return pq.poll();
	}

	@Override
	protected void putPuzzle(Puzzle p) {
		pq.add(p);
	}
}
