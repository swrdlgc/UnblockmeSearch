package com.swrd.unblock.solver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.utils.RectangleUtils;

public abstract class AbstractSolver implements Solver {

	private String name;
	private long startTime;
	private long endTime;
	private boolean solved;
	private List<Step> steps;
	private Map<String, Puzzle> expandNodes = new HashMap<>();
	private int maxStep = Integer.MAX_VALUE;
	private int maxNodes = Integer.MAX_VALUE;

	public AbstractSolver(String name) {
		this.name = name;
	}

	protected abstract Puzzle getNextPuzzle();

	protected abstract void putPuzzle(Puzzle p);

	@Override
	public boolean search() {
		startTime = System.currentTimeMillis();

		while (!solved) {
			Puzzle puzzle = getNextPuzzle();
			if (puzzle == null) {
				break;
			}

			List<Puzzle> neighs = puzzle.neighbors();
			for (Puzzle p : neighs) {
				if (p.isSolved()) {
					solved = true;
					steps = p.getSteps();
					break;
				}
				String key = p.getKey();
				if (p.getStep().getLevel() <= maxStep
						&& expandNodes.size() <= maxNodes) {
					Puzzle op = expandNodes.get(key);
					if (op == null
							|| op.getStep().getLevel() > p.getStep().getLevel()) {
						putPuzzle(p);
						expandNodes.put(key, p);
					}
				}
			}
		}

		endTime = System.currentTimeMillis();
		return solved;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("==========================================================================\n");
		sb.append(String.format("Name: %s\n", getName()));
		sb.append(String.format("Solved: %s\n", solved));
		sb.append(String.format("RunTime: %d\n", getRunTime()));
		sb.append(String.format("ExpandNodes: %d\n", getExpandNodes()));
		if (solved) {
			sb.append("==========================\n");
			sb.append(String.format("Steps: %d\n", steps.size()));
			for (int i = 0; i < steps.size(); ++i) {
				Step step = steps.get(i);
				Block block = step.getBlock();
				sb.append(String.format("step %d: %4d[%s]->{%10s %d}", i,
						block.getId(),
						RectangleUtils.EncodeRectangle(block.getCell()),
						step.getDirect(), step.getOffset()));
				step.stepIn();
				sb.append(String.format(" = %4d[%s]\n", block.getId(),
						RectangleUtils.EncodeRectangle(block.getCell())));
				step.stepBack();
			}
		}

		return sb.toString();
	}
}
