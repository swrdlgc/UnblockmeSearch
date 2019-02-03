package com.swrd.unblock.bound.blocks.score;

import java.awt.Rectangle;

import com.swrd.unblock.puzzle.Puzzle;

public interface Scorer {
	/**
	 * return a score[0, 1], the higher the better
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	double score(Rectangle src, Rectangle dest);
	
	double score(Puzzle puzzle);
}
