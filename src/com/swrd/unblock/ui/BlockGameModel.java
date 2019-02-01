package com.swrd.unblock.ui;

import org.eclipse.swt.graphics.Point;

import com.swrd.unblock.puzzle.Puzzle;

import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;

public class BlockGameModel extends KTableDefaultModel {
	private final int CELL_WIDTH = 50;
	
	private Puzzle puzzle;
	private KTableCellRenderer render;
	
	public BlockGameModel(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.render = new BlockGameRender(puzzle);
	}

	public int getFixedHeaderRowCount() {
		return 0;
	}

	public int getFixedSelectableRowCount() {
		return 0;
	}

	public int getFixedHeaderColumnCount() {
		return 0;
	}

	public int getFixedSelectableColumnCount() {
		return 0;
	}

	public boolean isColumnResizable(int col) {
		return false;
	}

	public boolean isRowResizable(int row) {
		return false;
	}

	public int getRowHeightMinimum() {
		return CELL_WIDTH;
	}

	public int getInitialColumnWidth(int column) {
		return CELL_WIDTH;
	}

	public int getInitialRowHeight(int row) {
		return CELL_WIDTH;
	}

	public Object doGetContentAt(int col, int row) {
		return null;
	}

	public KTableCellEditor doGetCellEditor(int col, int row) {
		return null;
	}

	public void doSetContentAt(int col, int row, Object value) {
	}

	public KTableCellRenderer doGetCellRenderer(int col, int row) {
		return render;
	}

	public int doGetRowCount() {
		return (int)(puzzle.getBound().getHeight()+(puzzle.getExit()==null?0:1));
	}

	public int doGetColumnCount() {
		return (int)(puzzle.getBound().getWidth()+(puzzle.getExit()==null?0:1));
	}

	@Override
	public Point belongsToCell(int col, int row) {
		return puzzle.belongsToCell(col, row);
	}
}
