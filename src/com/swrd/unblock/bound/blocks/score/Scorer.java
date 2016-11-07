package com.swrd.unblock.bound.blocks.score;

import java.awt.Rectangle;

public interface Scorer {
	Scorer scorer = UnionAreaScorer.Instance;

	/**
	 * return a score[0, 1], the higher the better
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	double score(Rectangle src, Rectangle dest);
}
