package com.swrd.unblock.solver;

import java.util.List;

import com.swrd.unblock.puzzle.step.Step;

public interface Solver {
	void setMaxStep(int maxStep);

	void setMaxNodes(int maxNodes);

	boolean search();

	long getRunTime();

	long getExpandNodes();

	boolean isSolved();

	List<Step> steps();
}
