package com.swrd.unblock.bean;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swrd.unblock.bean.Step.Move;

public class Puzzle {

	public static final int MaxHor = 5;
	public static final int MaxVer = 5;
	public static final int EntryX = 5;
	public static final int EntryY = 2;

	private int maxStep = 1000;
	private int maxStatus = 100000000;
	private boolean success = false;
	private String successStatus;
	private Block king;
	private List<Block> blocks = new ArrayList<>();
	private Map<String, Status> statuss = new HashMap<>();

	public void addBlock(Block block) {
		blocks.add(block);
		if (block.isKing()) {
			king = block;
		}
	}

	public void search(Status status) {
		// System.out.println(status.getCode());
		if (checkSuccess()) {
			StringBuilder sb = new StringBuilder();
			status.print(sb);
			successStatus = sb.toString();
			success = true;
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(status.getCode());
		for (Block block : blocks) {
			sb.append(block.getCell().toString());
		}
		String key = sb.toString();
		if (statuss.containsKey(key) && status.getCntStep() >= statuss.get(key).getCntStep()) {
			return;
		}
		statuss.put(key, status);
		if (maxStatus < statuss.size()) {
			return;
		}
		for (Block block : blocks) {
			long code = status.getCode();
			if (success || maxStep <= status.getCntStep()) {
				return;
			}
			for (int i = block.canLeft(code); i > 0; --i) {
				block.moveLeft(i);
				search(new Status(status, new Step(block, Move.Left, i),
						getCode(), status.getCntStep() + 1));
				block.moveRight(i);
			}
			if (success || maxStep <= status.getCntStep()) {
				return;
			}
			for (int i = block.canRight(code); i > 0; --i) {
				block.moveRight(i);
				search(new Status(status, new Step(block, Move.Right, i),
						getCode(), status.getCntStep() + 1));
				block.moveLeft(i);
			}
			if (success || maxStep <= status.getCntStep()) {
				return;
			}
			for (int i = block.canUp(code); i > 0; --i) {
				block.moveUp(i);
				search(new Status(status, new Step(block, Move.Up, i),
						getCode(), status.getCntStep() + 1));
				block.moveDown(i);
			}
			if (success || maxStep <= status.getCntStep()) {
				return;
			}
			for (int i = block.canDown(code); i > 0; --i) {
				block.moveDown(i);
				search(new Status(status, new Step(block, Move.Down, i),
						getCode(), status.getCntStep() + 1));
				block.moveUp(i);
			}
		}
	}

	public boolean checkSuccess() {
		Rectangle rect = king.getCell();
		for (int i = 0; i < rect.width; ++i) {
			for (int j = 0; j < rect.height; ++j) {
				int x = rect.x + i;
				int y = rect.y + j;
				if (x == EntryX && y == EntryY) {
					return true;
				}
			}
		}
		return false;
	}

	public long getCode() {
		long code = 0;
		for (Block block : blocks) {
			Rectangle rect = block.getCell();
			for (int i = 0; i < rect.width; ++i) {
				for (int j = 0; j < rect.height; ++j) {
					int x = rect.x + i;
					int y = rect.y + j;
					code |= getBitCode(x, y);
				}
			}
		}
		return code;
	}

	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}

	public void setMaxStatus(int maxStatus) {
		this.maxStatus = maxStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getSuccessStatus() {
		return successStatus;
	}

	public static long getBitCode(int x, int y) {
		return (1L << (x + y * (MaxHor + 1)));
	}
}
