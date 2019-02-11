package com.swrd.unblock.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.puzzle.Puzzle;

import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableModel;

public class BlockGameRender implements KTableCellRenderer {
	public static Color GreenColor = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	public static Color BlackColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	
	private Puzzle puzzle;
	private java.awt.Rectangle exit;

	public BlockGameRender(Puzzle puzzle) {
		this.puzzle = puzzle;
		exit = puzzle.getExit();
	}

	public int getOptimalWidth(GC gc, int col, int row, Object content, boolean fixed, KTableModel model) {
		return 0;
	}

	public void drawCell(GC gc, Rectangle rect, int col, int row, Object content, boolean focus, boolean header,
			boolean clicked, KTableModel model) {
		Color fore = gc.getForeground();
		Color back = gc.getBackground();

		if (exit != null) {
			if (exit.contains(col, row)) {
				gc.setBackground(GreenColor);
				gc.fillRectangle(getSmallerRect(rect, -1));
				gc.setForeground(BlackColor);
				//gc.drawText("出口", rect.x, rect.y);
			}
		}
		
		Block block = puzzle.getBlock(col, row);
		if(block != null) {
			Color color = block.getColor();
			String label = block.getLabel()+"";
			if(color != null) {
				gc.setBackground(color);
				gc.fillRectangle(getSmallerRect(rect, 5));
				if(puzzle.isSelected(col, row)) {
					gc.drawFocus(rect.x, rect.y, rect.width, rect.height);
				}
			}
			if(label != null && !label.isEmpty()) {
				gc.setForeground(BlackColor);
				Rectangle rect2 = getSmallerRect(rect, 15);
				gc.drawText(label, rect2.x, rect2.y);
			}
		}
		
		gc.setForeground(fore);
		gc.setBackground(back);
	}

	public Rectangle getSmallerRect(Rectangle rect, int span) {
        return new Rectangle(rect.x + span, rect.y + span, rect.width - span - span, rect.height - span - span);
    }
}
