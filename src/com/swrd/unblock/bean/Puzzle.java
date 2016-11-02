package com.swrd.unblock.bean;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swrd.unblock.bean.Step.Move;

public class Puzzle {
	public static final int MaxStep = 200;
	public static final int MaxStatus = 10000000;

	public static final int MaxHor = 5;
	public static final int MaxVer = 5;
	public static final int EntryX = 5;
	public static final int EntryY = 2;

	private Block king;
	private List<Block> blocks = new ArrayList<>();
	private Map<String, Status> statuss = new HashMap<>();

	public void addBlock(Block block) {
		blocks.add(block);
		if (block.isKing()) {
			king = block;
		}
	}

	boolean success = false;
	public void search(Status status) {
		//System.out.println(status.getCode());
		if (success || checkSuccess()) {
			status.print();
			success = true;
			return;
		}
		if (MaxStep < status.getCntStep()) {
			return;
		}
		if (statuss.containsKey(status.getCode())) {
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(status.getCode());
		for (Block block : blocks) {
			sb.append(block.getCell().toString());
		}
		String key = sb.toString();
		if(statuss.containsKey(key)) {
			return;
		}
		statuss.put(key, status);
		if (MaxStatus < statuss.size()) {
			//return;
		}
		for (Block block : blocks) {
			long code = status.getCode();
			if (!success && block.canLeft(code)) {
				block.moveLeft();
				search(new Status(status, new Step(block, Move.Left),
						getCode(), status.getCntStep() + 1));
				block.moveRight();
			}
			if (!success && block.canRight(code)) {
				block.moveRight();
				search(new Status(status, new Step(block, Move.Right),
						getCode(), status.getCntStep() + 1));
				block.moveLeft();
			}
			if (!success && block.canUp(code)) {
				block.moveUp();
				search(new Status(status, new Step(block, Move.Up), getCode(),
						status.getCntStep() + 1));
				block.moveDown();
			}
			if (!success && block.canDown(code)) {
				block.moveDown();
				search(new Status(status, new Step(block, Move.Down),
						getCode(), status.getCntStep() + 1));
				block.moveUp();
			}
		}
	}

	public boolean checkSuccess() {
		Rectangle rect = king.getCell();
		for (int i = 0; i < rect.width; ++i) {
			for (int j = 0; j < rect.height; ++j) {
				int x = rect.x + i;
				int y = rect.y + j;
				if(x == EntryX && y == EntryY) {
					return true;
				}
			}
		}
		return false;
	}

	private long getCode() {
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

	public static long getBitCode(int x, int y) {
		return (1L << (x + y * (MaxHor + 1)));
	}

	public static void main(String[] args) throws Exception {
		Puzzle pz = new Puzzle();
		initPuzzle(pz);
		Status status = new Status(null, null, pz.getCode(), 0);
		//System.err.println(Long.toHexString(pz.getCode()));
		//System.err.println(pz.getCode() == 0x2fbff7d7fL);
		pz.search(status);
	}
	
	static void initPuzzle(Puzzle pz) throws Exception {
		// pz1
//		pz.addBlock(new Block(new Rectangle(2, 0, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(0, 2, 2, 1), true));
		
		// pz2
		pz.addBlock(new Block(new Rectangle(0, 0, 2, 1)));
		pz.addBlock(new Block(new Rectangle(2, 0, 1, 2)));
		pz.addBlock(new Block(new Rectangle(3, 0, 2, 1)));
		pz.addBlock(new Block(new Rectangle(5, 0, 1, 3)));
		pz.addBlock(new Block(new Rectangle(0, 1, 1, 2)));
		pz.addBlock(new Block(new Rectangle(1, 2, 2, 1), true));
		pz.addBlock(new Block(new Rectangle(4, 1, 1, 2)));
		pz.addBlock(new Block(new Rectangle(0, 3, 1, 2)));
		pz.addBlock(new Block(new Rectangle(1, 3, 1, 2)));
		pz.addBlock(new Block(new Rectangle(2, 3, 2, 1)));
		pz.addBlock(new Block(new Rectangle(4, 3, 2, 1)));
		pz.addBlock(new Block(new Rectangle(0, 5, 2, 1)));
		pz.addBlock(new Block(new Rectangle(3, 4, 1, 2)));
		pz.addBlock(new Block(new Rectangle(4, 4, 2, 1)));
	}
}
