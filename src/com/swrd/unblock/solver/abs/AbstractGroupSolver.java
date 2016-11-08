package com.swrd.unblock.solver.abs;

import java.util.List;

import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.solver.api.GroupSolver;
import com.swrd.unblock.solver.api.Solver;

public abstract class AbstractGroupSolver implements GroupSolver {
	protected String name;
	private int maxStep = Integer.MAX_VALUE;
	private int maxNodes = Integer.MAX_VALUE;
	private long runTime;
	private long expandNodes;
	
	private Puzzle puzzle;
	private Solver solver;
	
	public AbstractGroupSolver(Puzzle puzzle, String name) {
		this.puzzle = puzzle;
		this.name = name;
	}

	@Override
	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}

	@Override
	public void setMaxNodes(int maxNodes) {
		this.maxNodes = maxNodes;
	}

	@Override
	public boolean search() {
		if (puzzle.isSolved()) {
			return true;
		}
		int usedNodes = 0;
		for(int step = 1; step <= maxStep && usedNodes <= maxNodes; ++step) {
			Solver s = getSolver(puzzle);
			s.setMaxStep(step);
			s.setMaxNodes(maxNodes - usedNodes);
			s.search();
			runTime += s.getRunTime();
			expandNodes += s.getExpandNodes();
			if(s.isSolved()) {
				solver = s;
				return true;
			}
		}
		return false;
	}

	@Override
	public long getRunTime() {
		return runTime;
	}

	@Override
	public long getExpandNodes() {
		return expandNodes;
	}

	@Override
	public boolean isSolved() {
		if (solver != null) {
			return solver.isSolved();
		}
		return false;
	}

	@Override
	public List<Step> steps() {
		if (solver != null) {
			return solver.steps();
		}
		return null;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
