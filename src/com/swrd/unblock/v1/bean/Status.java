package com.swrd.unblock.v1.bean;

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

	public void print(StringBuilder sb) {
		if (status != null) {
			step.stepBack();
			status.print(sb);
			Block block = step.getBlock();
			sb.append(String.format("move %4d[%s]->{%10s %d}", block.getId(),
					print(block.getCell()), step.getMove(), step.getOffset()));
			step.stepIn();
			sb.append(String.format(" = %4d[%s]\n", block.getId(),
					print(block.getCell())));
		} else {
			sb.append("==========================================================================\n");
		}
	}

	private String print(Rectangle rect) {
		return String.format("%d, %d, %d, %d", rect.x, rect.y, rect.width,
				rect.height);
	}
}
