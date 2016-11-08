package com.swrd.unblock.utils;

import java.util.List;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.solver.api.Solver;

public class SolverUtils {
	
	public static String printSolver(Solver solver) {
		StringBuilder sb = new StringBuilder();

		sb.append("==========================================================================\n");
		sb.append(String.format("Name: %s\n", solver.getName()));
		sb.append(String.format("Solved: %s\n", solver.isSolved()));
		sb.append(String.format("RunTime: %d\n", solver.getRunTime()));
		sb.append(String.format("ExpandNodes: %d\n", solver.getExpandNodes()));
		if (solver.isSolved()) {
			sb.append("==========================\n");
			List<Step> steps = solver.steps();
			sb.append(String.format("Steps: %d\n", steps.size()));
			for (int i = 0; i < steps.size(); ++i) {
				Step step = steps.get(i);
				Block block = step.getBlock();
				sb.append(String.format("step %4d: %4d[%s]->{%10s %d}", i,
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
