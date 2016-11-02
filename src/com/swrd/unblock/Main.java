package com.swrd.unblock;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import com.swrd.unblock.bean.Block;
import com.swrd.unblock.bean.Puzzle;
import com.swrd.unblock.bean.Status;

public class Main {
	public static void main(String[] args) throws Exception {

		boolean success = false;
		// cannot binary search, not continues
		for(int step = 1; step < 1000; ++step) {
			Puzzle pz = new Puzzle();
			initPuzzle(pz);
			pz.setMaxStep(step);
			// System.err.println(Long.toHexString(pz.getCode()));
			// System.err.println(pz.getCode() == 0x2fbff7d7fL);
			Status status = new Status(null, null, pz.getCode(), 0);
			pz.search(status);
			if(pz.isSuccess()) {
				success = true;
				System.err.println("min step: " + step);
				System.err.println(pz.getSuccessStatus());
				break;
			}
		}
		
		if(!success) {
			System.err.println("failed");
		}
	}

	static void initPuzzle(Puzzle pz) throws Exception {
		Block.idi = new AtomicInteger(0);
		// pz1
		 pz.addBlock(new Block(new Rectangle(2, 0, 1, 2)));
		 pz.addBlock(new Block(new Rectangle(0, 2, 2, 1), true));

		// pz2
//		pz.addBlock(new Block(new Rectangle(0, 0, 2, 1)));
//		pz.addBlock(new Block(new Rectangle(2, 0, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(3, 0, 2, 1)));
//		pz.addBlock(new Block(new Rectangle(5, 0, 1, 3)));
//		pz.addBlock(new Block(new Rectangle(0, 1, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(1, 2, 2, 1), true));
//		pz.addBlock(new Block(new Rectangle(4, 1, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(0, 3, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(1, 3, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(2, 3, 2, 1)));
//		pz.addBlock(new Block(new Rectangle(4, 3, 2, 1)));
//		pz.addBlock(new Block(new Rectangle(0, 5, 2, 1)));
//		pz.addBlock(new Block(new Rectangle(3, 4, 1, 2)));
//		pz.addBlock(new Block(new Rectangle(4, 4, 2, 1)));
	}
}
