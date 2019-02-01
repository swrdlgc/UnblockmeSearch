package com.swrd.unblock.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.main.UnBlockMeMain;
import com.swrd.unblock.main.HrRoadMain;
import com.swrd.unblock.main.P9Main;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.api.Solver;
import com.swrd.unblock.utils.SolverUtils;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellSelectionAdapter;

public class BlockGame {
	public static void main(String[] args) {
		// create a shell...
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("KTable examples");
		
		// put a tab folder in it...
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		createGameTable(tabFolder);
	
		// display the shell...
		shell.setSize(400, 480);
		shell.open();
		setCenter(shell);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	 /*
     * Set the shell on the center of the screen
     * 
     * @param shell the shell which needs setCentered
     */
    public static void setCenter(Shell shell) {
        Rectangle rtg = shell.getMonitor().getClientArea();
        shell.setLocation((rtg.width - shell.getSize().x) / 2, (rtg.height - shell.getSize().y) / 2);
    }
    
	private static void createGameTable(TabFolder tabFolder) {
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("Unblock me");
		Composite comp1 = new Composite(tabFolder, SWT.NONE);
		item1.setControl(comp1);
		comp1.setLayout(new GridLayout());
		final KTable table = new KTable(comp1, SWT.DOUBLE_BUFFERED);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		final Puzzle puzzle = UnBlockMeMain.getPuzzle();
		//final Puzzle puzzle = HrRoadMain.getPuzzle();
		//final Puzzle puzzle = P9Main.getPuzzle();
		table.setModel(new BlockGameModel(puzzle));
		table.addCellSelectionListener(new KTableCellSelectionAdapter() {
			public void cellSelected(int col, int row, int statemask) {
				puzzle.selected(col, row);
				table.redraw();
				table.update();
			}
		});
		
		Button bt = new Button(comp1, SWT.NONE);
		bt.setText("Solve");
		bt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Solver solver = new BfsSolver(puzzle, "BFS");
				solver.search();
				if (solver.isSolved()) {
					System.err.println(SolverUtils.printSolver(solver, false));
					new Thread(new Runnable() {
						@Override
						public void run() {
							int count = 1;
							for (Step step : solver.steps()) {
								for (Block block : puzzle.getBlocks()) {
									if (block.equals(step.getBlock())) {
										block.move(step.getDirect(), step.getOffset());
									}
								}
								System.err.println(String.format("%04d %s", count++, step));
								Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										table.redraw();
										table.update();
									}
								});
								try { Thread.sleep(200); } catch (InterruptedException e1) {}
							}
							Display.getDefault().syncExec(new Runnable() {
								@Override
								public void run() {
									MessageDialog.openInformation(null, "Info", "Puzzle solved!");
								}
							});
						}
					}).start();
				} else {
					MessageDialog.openInformation(null, "Info", "Puzzle not solved!");
				}
			}
		});
	}
}
