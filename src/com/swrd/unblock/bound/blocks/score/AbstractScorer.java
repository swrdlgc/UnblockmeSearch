package com.swrd.unblock.bound.blocks.score;

import java.awt.Rectangle;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.puzzle.Puzzle;

public abstract class AbstractScorer implements Scorer {

	@Override
	public double score(Rectangle src, Rectangle dest) {
		return 0;
	}

	@Override
	public double score(Puzzle puzzle) {
		double s = 0;
		for (Block b : puzzle.getBlocks()) {
			s += b.score();
		}
		return s;
	}

}
