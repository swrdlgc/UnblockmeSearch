package com.swrd.unblock.bound.blocks.score;

import com.swrd.unblock.puzzle.Puzzle;

public class LeverScorer extends AbstractScorer {
	public static final Scorer Instance = new LeverScorer();
	@Override
	public double score(Puzzle puzzle) {
		return -puzzle.getLevel();
	}
}
