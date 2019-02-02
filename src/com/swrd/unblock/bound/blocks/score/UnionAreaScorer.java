package com.swrd.unblock.bound.blocks.score;

import java.awt.Rectangle;

public class UnionAreaScorer extends AbstractScorer {
	public static final Scorer Instance = new UnionAreaScorer();

	private UnionAreaScorer() {
	}

	@Override
	public double score(Rectangle src, Rectangle dst) {
		double res = 1.0;

		if (dst != null) {
			Rectangle ur = src.union(dst);
			res *= src.width;
			res *= src.height;
			res /= ur.width;
			res /= ur.height;
		}
		return res;
	}

}
