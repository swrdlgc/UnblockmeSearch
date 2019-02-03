package com.swrd.unblock.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.score.LevelScorer;
import com.swrd.unblock.bound.blocks.score.MhtDisScorer;
import com.swrd.unblock.bound.blocks.score.ScorerFactory;
import com.swrd.unblock.bound.blocks.score.UnionAreaScorer;
import com.swrd.unblock.main.HrRoadMain;
import com.swrd.unblock.main.P9Main;
import com.swrd.unblock.main.UnBlockMeMain;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.solver.AstarSolver;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.DfsGroupSolver;
import com.swrd.unblock.solver.DfsSolver;
import com.swrd.unblock.solver.IAstarGroupSolver;
import com.swrd.unblock.solver.api.Solver;
import com.swrd.unblock.utils.SolverUtils;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellSelectionAdapter;

public class BlockGame {
	public static Color White;
	
	public static void main(String[] args) {
		// create a shell...
		Display display = new Display();
		shell = new Shell(display);
		shell.setText("Block games");
		
		White = display.getSystemColor(SWT.COLOR_WHITE);
		shell.setBackground(White);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setLayout(new GridLayout(2, false));
		
		createGameTable(shell);
		createControlPanel(shell);
	
		// display the shell...
		shell.setSize(800, 600);
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
    
    static Shell shell;
    static Puzzle puzzle;
    static KTable table;
    static BlockGameModel model;
    static Solver solver;
    static Text text;
    
	private static void createGameTable(Shell shell) {
		Composite child = new Composite(shell, SWT.NONE);
		GridData gd = new GridData(400, 400);
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		child.setLayoutData(gd);
		child.setLayout(new FillLayout());
		
		table = new KTable(child, SWT.DOUBLE_BUFFERED);
		table.setColorLeftBorder(White);
        //table.setColorRightBorder(White);
        table.setColorTopBorder(White);
		puzzle = UnBlockMeMain.getPuzzle();
		puzzle.setCellWidth(50);
		model = new BlockGameModel(puzzle);
		table.setModel(model);
		table.addCellSelectionListener(new KTableCellSelectionAdapter() {
			public void cellSelected(int col, int row, int statemask) {
				puzzle.selected(col, row);
				table.redraw();
				table.update();
			}
		});
	}
	
	private static void createControlPanel(Shell shell) {
		Composite child = new Composite(shell,SWT.NONE);
		child.setLayoutData(new GridData(GridData.FILL_BOTH));
		child.setLayout(new GridLayout(2, false));
		
		new Label(child, SWT.NONE).setText("Game: ");
		Combo cboGame = new Combo(child, SWT.READ_ONLY);
		cboGame.add("Unblock Me");
		cboGame.add("Huarong Road");
		cboGame.add("Eight Puzzle");
		cboGame.select(0);
		cboGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int idx = cboGame.getSelectionIndex();
				if(idx == 1) {
					puzzle = HrRoadMain.getPuzzle();
					puzzle.setCellWidth(90);
				} else if(idx == 2) {
					puzzle = P9Main.getPuzzle();
					puzzle.setCellWidth(120);
				} else {
					puzzle = UnBlockMeMain.getPuzzle();
					puzzle.setCellWidth(60);
				}
				model = new BlockGameModel(puzzle);
				table.setModel(model);
				table.redraw();
				table.update();
				text.setText("");
			}
		});
		
		new Label(child, SWT.NONE).setText("Algo: ");
		Combo cboAlgo = new Combo(child, SWT.READ_ONLY);
		cboAlgo.add("DFS");
		cboAlgo.add("BFS");
		cboAlgo.add("A*");
		cboAlgo.add("IA*");
		cboAlgo.add("StepDFS");
		cboAlgo.add("IdeaA*");
		cboAlgo.select(0);
		cboAlgo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
			}
		});
		
		new Label(child, SWT.NONE).setText("Scorer: ");
		Combo cboScorer = new Combo(child, SWT.READ_ONLY);
		cboScorer.add("UnionArea+");
		cboScorer.add("ManhattanDistance-");
		cboScorer.add("Step-");
		cboScorer.select(0);
		cboScorer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int idx = cboScorer.getSelectionIndex();
				if(idx == 1) {
					ScorerFactory.scorer = MhtDisScorer.Instance;
				} else if(idx == 2) {
					ScorerFactory.scorer = LevelScorer.Instance;
				} else {
					ScorerFactory.scorer = UnionAreaScorer.Instance;
				}
			}
		});
		
		Button btSlove = new Button(child, SWT.NONE);
		btSlove.setText("Solve");
		btSlove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setEnabled(false);
				String algo = cboAlgo.getText();
				if(algo.equals("BFS")) {
					solver = new BfsSolver(puzzle, algo);
				} else if(algo.equals("A*")) {
					solver = new AstarSolver(puzzle, algo);
				} else if(algo.equals("IA*")) {
					solver = new IAstarGroupSolver(puzzle, algo);
				} else if(algo.equals("StepDFS")) {
					solver = new DfsGroupSolver(puzzle, algo);
				} else {
					solver = new DfsSolver(puzzle, algo);
				}
				solver.search();
				if (solver.isSolved()) {
					text.setText(SolverUtils.printSolver(solver, false)+"\n");
				} else {
					MessageDialog.openWarning(null, "Info", "Puzzle not solved!");
				}
				shell.setEnabled(true);
			}
		});
		
		Button btShow = new Button(child, SWT.NONE);
		btShow.setText("Show");
		btShow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (solver == null || !solver.isSolved()) {
					MessageDialog.openWarning(null, "Info", "Please solve puzzle first!");
					return;
				}
				shell.setEnabled(false);
				new Thread(new Runnable() {
					int count = 1;
					@Override
					public void run() {
						for (Step step : solver.steps()) {
							for (Block block : puzzle.getBlocks()) {
								if (block.equals(step.getBlock())) {
									block.move(step.getDirect(), step.getOffset());
								}
							}
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									text.append(String.format("\n%04d %s", count++, step));
									table.redraw();
									table.update();
								}
							});
							try { Thread.sleep(200); } catch (InterruptedException e1) {}
						}
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								//MessageDialog.openInformation(null, "Info", "Puzzle solved!");
								shell.setEnabled(true);
							}
						});
					}
				}).start();
			}
		});
		
		text = new Text(child, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		text.setLayoutData(gd);
	}
}
