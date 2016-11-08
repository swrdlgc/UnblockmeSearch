package com.swrd.unblock.solver.api;

import java.util.List;

import com.swrd.unblock.puzzle.step.Step;

public interface Solver {
	String getName();
	
	void setMaxStep(int maxStep);

	void setMaxNodes(int maxNodes);

	boolean search();

	long getRunTime();

	long getExpandNodes();

	boolean isSolved();

	List<Step> steps();
}
