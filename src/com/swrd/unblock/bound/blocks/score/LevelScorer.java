package com.swrd.unblock.bound.blocks.score;

import com.swrd.unblock.puzzle.Puzzle;

public class LevelScorer extends AbstractScorer {
	public static final Scorer Instance = new LevelScorer();
	@Override
	public double score(Puzzle puzzle) {
		return -puzzle.getLevel();
	}
}
