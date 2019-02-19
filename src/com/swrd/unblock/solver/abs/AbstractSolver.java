package com.swrd.unblock.solver.abs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swrd.unblock.ems.SolverType;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.solver.api.Solver;

public abstract class AbstractSolver implements Solver {

	private String name;
	private long startTime;
	private long endTime;
	private boolean solved;
	private boolean stop;
	private List<Step> steps;
	private Map<String, Puzzle> expandNodes = new HashMap<>();
	private int maxStep = Integer.MAX_VALUE;
	private int maxNodes = Integer.MAX_VALUE;
	private SolverType type;

	public AbstractSolver(String name, SolverType type) {
		this.name = name;
		this.type = type;
	}

	protected abstract Puzzle getNextPuzzle();

	protected abstract void putPuzzle(Puzzle p);
	
	protected abstract void removePuzzle(Puzzle p);

	private boolean checkSolved(Puzzle p) {
		if (p.isSolved()) {
			solved = true;
			steps = p.getSteps();
		}
		return solved;
	}
	
	private boolean addPuzzle(Puzzle p) {
		String key = p.getKey();
		if(expandNodes.size() > maxNodes) {
			stop = true;
			return false;
		}
		int s = 0;
		int os = 0;
		if (p.getStep() != null) {
			s = p.getStep().getLevel();
			if(s > maxStep) {
				return false;
			}
		}
		Puzzle op = expandNodes.get(key);
		if (op != null && op.getStep() != null) {
			os = op.getStep().getLevel();
			if(os > s) {
				//NOTICE: replace with low level can get better ans, but waste some time.
				removePuzzle(op);
				op = null;
			}
		}
		if (op == null) { 
			putPuzzle(p);
			expandNodes.put(key, p);
//			if(expandNodes.size() % 1000 == 0) {
//				System.out.println(expandNodes.size());
//			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean search() {
		startTime = System.currentTimeMillis();

		Puzzle pz = getNextPuzzle();
		checkSolved(pz);
		addPuzzle(pz);
		stop = false;
		while (!solved && !stop) {
			Puzzle puzzle = getNextPuzzle();
			if (puzzle == null) {
				break;
			}

			List<Puzzle> neighs = puzzle.neighbors();
			if(type != SolverType.Astar && type != SolverType.IAstar) {
				Collections.sort(neighs);
				if(type == SolverType.DFS || type == SolverType.IDFS) {
					Collections.reverse(neighs);
				}
			}
			for (Puzzle p : neighs) {
				if (checkSolved(p)) {
					break;
				}
				addPuzzle(p);
			}
		}

		endTime = System.currentTimeMillis();
		return solved;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public long getRunTime() {
		return endTime - startTime;
	}

	@Override
	public long getExpandNodes() {
		return expandNodes.size();
	}

	@Override
	public boolean isSolved() {
		return solved;
	}

	@Override
	public List<Step> steps() {
		return steps;
	}

	public int getMaxStep() {
		return maxStep;
	}

	@Override
	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}

	public int getMaxNodes() {
		return maxNodes;
	}

	@Override
	public void setMaxNodes(int maxNodes) {
		this.maxNodes = maxNodes;
	}
}
