package com.swrd.unblock.bound.blocks.score;

import java.awt.Rectangle;

public class MhtDisScorer extends AbstractScorer {
	public static final Scorer Instance = new MhtDisScorer();

	private MhtDisScorer() {
	}

	@Override
	public double score(Rectangle src, Rectangle dst) {
		double res = 1.0;

		if (dst != null) {
			res += Math.abs(src.x - dst.x);
			res += Math.abs(src.y - dst.y);
			res = 1 / res;
		}
		return res;
	}

}
