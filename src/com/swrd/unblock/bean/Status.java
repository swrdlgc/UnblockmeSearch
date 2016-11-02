package com.swrd.unblock.bean;

import java.awt.Rectangle;

public class Status {
	private Status status;
	private Step step;
	private long code;
	private int cntStep;

	public Status(Status status, Step step, long code, int cntStep) {
		this.status = status;
		this.step = step;
		this.code = code;
		this.cntStep = cntStep;
	}

	public Status getStatus() {
		return status;
	}

	public Step getStep() {
		return step;
	}

	public long getCode() {
		return code;
	}
	
	public int getCntStep() {
		return cntStep;
	}

	public void print() {
		if (status != null) {
			step.stepBack();
			status.print();
			System.err.format("move [%s]->%s", print(step.getBlock().getCell()), step.getMove());
			step.stepIn();
			System.err.format(" = [%s]\n", print(step.getBlock().getCell()));
		}
	}
	
	private String print(Rectangle rect) {
		return String.format("%d, %d, %d, %d", rect.x, rect.y, rect.width, rect.height);
	}
}
