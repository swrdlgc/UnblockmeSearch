package com.swrd.unblock.puzzle;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.swrd.unblock.bound.Bound;
import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.ds.bitarray.BitArray2D;
import com.swrd.unblock.bound.ds.bitarray.ByteBitArray2D;
import com.swrd.unblock.ems.Direction;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.utils.RectangleUtils;

public class Puzzle implements Bound, Comparable<Puzzle> {
	private String name;
	private PuzzleType type;
	private Rectangle bound;
	private List<Block> blocks;

	private Puzzle father;
	private Step step;
	private BitArray2D status;

	public Puzzle(String name, PuzzleType type, Rectangle bound, List<Block> blocks) {
		this(name, type, bound, blocks, null, null);
	}

	private Puzzle(String name, PuzzleType type, Rectangle bound, List<Block> blocks,
			Puzzle father, Step step) {
		this.name = name;
		this.type = type;
		this.bound = bound;
		this.blocks = blocks;
		this.father = father;
		this.step = step;

		if(type == PuzzleType.HRRoad) {
			Collections.sort((List) blocks);
		}
		if(!calcStatus()) {
			System.err.println("calc status error");
		}
	}

	private boolean calcStatus() {
		BitArray2D ba = new ByteBitArray2D(bound);
		for (Block b : blocks) {
			if (!ba.fill(b)) {
				return false;
			}
		}
		this.status = ba;
		return true;
	}

	public boolean isSolved() {
		for (Block b : blocks) {
			if (!b.atDestination()) {
				return false;
			}
		}
		return true;
	}

	public List<Puzzle> neighbors() {
		List<Puzzle> list = new ArrayList<>();

		int level = 1;
		if (step != null) {
			level = step.getLevel() + 1;
		}
		for (Block b : blocks) {
			for (Direction direct : Direction.values()) {
				int offset = b.check(status, direct);
				for (int i = offset; i >= 1; --i) {
					Step step = new Step(b, direct, i, level);
					step.stepIn();
					List<Block> blocks = copyBlocks();
					Puzzle puzzle = new Puzzle(name, type, bound, blocks, this, step);
					list.add(puzzle);
					step.stepBack();
				}
			}
		}
		Collections.sort(list);

		return list;
	}

	private List<Block> copyBlocks() {
		List<Block> list = new ArrayList<>();
		for (Block b : blocks) {
			list.add(b.copy());
		}
		return list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Rectangle getBound() {
		return bound;
	}

	public BitArray2D getStatus() {
		return status;
	}

	public Step getStep() {
		return step;
	}

	public List<Step> getSteps() {
		List<Step> steps = new ArrayList<>();

		Puzzle p = this;
		while (p.father != null) {
			steps.add(p.step);
			p = p.father;
		}
		Collections.reverse(steps);

		return steps;
	}

	public String getKey() {
		StringBuilder sb = new StringBuilder();
		for (Block b : blocks) {
			sb.append(RectangleUtils.EncodeRectangle(b.getCell()));
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public double score() {
		double s = 0;
		for (Block b : blocks) {
			s += b.score();
		}
		return s;
	}

	@Override
	public int compareTo(Puzzle o) {
		if(score() > o.score()) {
			return 1;
		} else {
			return -1;
		}
	}
}
