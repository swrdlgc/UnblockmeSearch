package com.swrd.unblock.solver;

import java.util.PriorityQueue;
import java.util.Queue;

import com.swrd.unblock.puzzle.Puzzle;

public class AstarSolver extends AbstractSolver {
	
	private Queue<Puzzle> pq = new PriorityQueue<Puzzle>();
	
	public AstarSolver(Puzzle puzzle, String name) {
		super(name);
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
