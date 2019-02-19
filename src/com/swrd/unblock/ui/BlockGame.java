package com.swrd.unblock.ui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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
import com.swrd.unblock.bound.blocks.HRBlock;
import com.swrd.unblock.bound.blocks.P9Block;
import com.swrd.unblock.bound.blocks.UnBlock;
import com.swrd.unblock.bound.blocks.score.LevelScorer;
import com.swrd.unblock.bound.blocks.score.MhtDisScorer;
import com.swrd.unblock.bound.blocks.score.ScorerFactory;
import com.swrd.unblock.bound.blocks.score.UnionAreaScorer;
import com.swrd.unblock.ems.PuzzleType;
import com.swrd.unblock.font.FontManager;
import com.swrd.unblock.puzzle.Puzzle;
import com.swrd.unblock.puzzle.PuzzleFactory;
import com.swrd.unblock.puzzle.PuzzleLoader;
import com.swrd.unblock.puzzle.step.Step;
import com.swrd.unblock.solver.AstarSolver;
import com.swrd.unblock.solver.BfsSolver;
import com.swrd.unblock.solver.DfsGroupSolver;
import com.swrd.unblock.solver.DfsSolver;
import com.swrd.unblock.solver.IAstarGroupSolver;
import com.swrd.unblock.solver.api.Solver;
import com.swrd.unblock.utils.ColorUtils;
import com.swrd.unblock.utils.SWTUtils;
import com.swrd.unblock.utils.SolverUtils;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellSelectionAdapter;

public class BlockGame {
	
	public static void main(String[] args) {
		// create a shell...
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Block games");
		
		shell.setBackground(ColorUtils.White);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setLayout(new GridLayout(2, false));
		
		new BlockGame(shell);
	
		// display the shell...
		shell.setSize(800, 600);
		shell.open();
		SWTUtils.setCenter(shell);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	Composite parent;
	public BlockGame(Composite parent) {
		this.parent = parent;
		createGameTable(parent);
		createControlPanel(parent);
	}
	
    Puzzle puzzle;
    KTable table;
    BlockGameModel model;
    
    private void redrawTable() {
    	int idx = cboGame.getSelectionIndex();
		if(idx == 1) {
			puzzle.setCellWidth(90);
		} else if(idx == 2) {
			puzzle.setCellWidth(120);
		} else {
			puzzle.setCellWidth(60);
		}
    	model = new BlockGameModel(puzzle);
		table.setModel(model);
		table.redraw();
		table.update();
		txtSolver.setText("");
		txtSolver.setData("id", 1);
		txtSearcher.setText("");
    }
    
	private void createGameTable(Composite parent) {
		Composite child = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(400, 400);
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		child.setLayoutData(gd);
		child.setLayout(new FillLayout());
		
		table = new KTable(child, SWT.DOUBLE_BUFFERED);
		table.setColorLeftBorder(ColorUtils.White);
        //table.setColorRightBorder(ColorUtils.White);
        table.setColorTopBorder(ColorUtils.White);
		table.addCellSelectionListener(new KTableCellSelectionAdapter() {
			public void cellSelected(int col, int row, int statemask) {
				Step step = puzzle.selected(col, row);
				if (step != null) {
					int id = (int) txtSolver.getData("id");
					txtSolver.append(String.format("\n%04d %s", id, step));
					txtSolver.setData("id", id+1);
				}
				table.redraw();
				table.update();
			}
		});
	}
	
	Button btEdit;
	Composite compSolve;
	Composite compEdit;
	Composite compSearch;
	Combo cboGame;
	Combo cboPuzzle;
	Text txtPuzzle;
	private void puzzleChanged() {
    	puzzle = ((Puzzle) cboPuzzle.getData(cboPuzzle.getText())).copy();
		redrawTable();
    }
    private void initComboPuzzle() {
    	int idx = cboGame.getSelectionIndex();
    	List<Puzzle> puzzles;
		if(idx == 1) {
			puzzles = PuzzleFactory.getList(PuzzleType.HRRoad);
		} else if(idx == 2) {
			puzzles = PuzzleFactory.getList(PuzzleType.P9);
		} else {
			puzzles = PuzzleFactory.getList(PuzzleType.UnBlockMe);
		}
		cboPuzzle.removeAll();
		for(Puzzle p : puzzles) {
			cboPuzzle.add(p.getName());
			cboPuzzle.setData(p.getName(), p);
		}
		cboPuzzle.select(0);
		puzzleChanged();
    }
    private void resetEmptyPuzzle() {
    	resetEmptyPuzzle(true);
    }
    private void resetEmptyPuzzle(boolean redraw) {
    	int id = cboGame.getSelectionIndex();
		if(id == 0) {
			puzzle = new Puzzle("", PuzzleType.UnBlockMe, UnBlock.Bounds, new ArrayList<Block>());
			puzzle.setExit(new Rectangle(6, 2, 1, 1));
		} else if(id == 1) {
			puzzle = new Puzzle("", PuzzleType.HRRoad, HRBlock.Bounds, new ArrayList<Block>());
			puzzle.setExit(new Rectangle(1, 5, 2, 1));
		} else {
			puzzle = new Puzzle("", PuzzleType.P9, P9Block.Bounds, new ArrayList<Block>());
		}
		if(redraw) {
			redrawTable();
		}
    }
    private void resetPuzzle() {
    	if(btEdit.getSelection()) {
			resetEmptyPuzzle();
		} else {
			initComboPuzzle();
		}
    }
	private void createControlPanel(Composite parent) {
		Composite child = new Composite(parent,SWT.NONE);
		child.setLayoutData(new GridData(GridData.FILL_BOTH));
		child.setLayout(new GridLayout(2, false));
		
		new Label(child, SWT.NONE).setText("Mode: ");
		Composite comp = new Composite(child, SWT.NONE);
		comp.setLayout(new GridLayout(3, true));
		Button btSolve = new Button(comp, SWT.RADIO);
		btSolve.setText("Play");
		btSolve.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SWTUtils.setVisible(cboPuzzle, true, false);
				SWTUtils.setVisible(txtPuzzle, false, false);
				
				SWTUtils.setVisible(compEdit, false, false);
				SWTUtils.setVisible(compSearch, false, false);
				SWTUtils.setVisible(compSolve, true, true);

				initComboPuzzle();
				puzzleChanged();
				table.setEnabled(true);
			}
		});
		
		btEdit = new Button(comp, SWT.RADIO);
		btEdit.setText("Edit");
		btEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SWTUtils.setVisible(cboPuzzle, false, false);
				SWTUtils.setVisible(txtPuzzle, true, false);
				
				SWTUtils.setVisible(compSearch, false, false);
				SWTUtils.setVisible(compSolve, false, false);
				SWTUtils.setVisible(compEdit, true, true);
				
				resetEmptyPuzzle();
				redrawTable();
				table.setEnabled(false);
			}
		});
		
		Button btSearch = new Button(comp, SWT.RADIO);
		btSearch.setText("Search");
		btSearch.setSelection(true);
		btSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SWTUtils.setVisible(cboPuzzle, true, false);
				SWTUtils.setVisible(txtPuzzle, false, false);
				
				SWTUtils.setVisible(compEdit, false, false);
				SWTUtils.setVisible(compSolve, false, false);
				SWTUtils.setVisible(compSearch, true, true);
				
				initComboPuzzle();
				puzzleChanged();
				table.setEnabled(false);
			}
		});
		
		new Label(child, SWT.NONE).setText("Game: ");
		cboGame = new Combo(child, SWT.READ_ONLY);
		cboGame.add("Unblock Me");
		cboGame.add("Huarong Road");
		cboGame.add("Eight Puzzle");
		cboGame.select(0);
		cboGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resetPuzzle();
			}
		});
		
		new Label(child, SWT.NONE).setText("Puzzle: ");
		cboPuzzle = new Combo(child, SWT.READ_ONLY);
		cboPuzzle.setLayoutData(new GridData());
		cboPuzzle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				puzzleChanged();
			}
		});
		txtPuzzle = new Text(child, SWT.SINGLE|SWT.BORDER);
		txtPuzzle.setMessage("Please input 'name-id.puzzle'");
		txtPuzzle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		SWTUtils.setVisible(txtPuzzle, false, false);
		
		new Label(child, SWT.NONE);
		Button btReset = new Button(child, SWT.NONE);
		btReset.setText("Reset");
		btReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btEdit.getSelection()) {
					txtEditor.setText("");
				}
				resetPuzzle();
			}
		});
		
		Label lbl = new Label(child, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.heightHint = 30;
		lbl.setLayoutData(gd);
		
		compSolve = createComp(child);
		compEdit = createComp(child);
		compSearch = createComp(child);
		
		createSolvePanel(compSolve);
		createEditPanel(compEdit);
		createSearchPanel(compSearch);
		
		initComboPuzzle();
		
		SWTUtils.setVisible(compEdit, false, false);
		SWTUtils.setVisible(compSolve, false, false);
		SWTUtils.setVisible(compSearch, true, true);
		table.setEnabled(false);
	}
	
	
	private Composite createComp(Composite child) {
		Composite comp = new Composite(child, SWT.NONE);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		GridLayout gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		comp.setLayout(gl);
		
		return comp;
	}
	
	Text txtSolver;
	private void createSolvePanel(Composite child) {
		txtSolver = new Text(child, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		txtSolver.setLayoutData(gd);
		FontManager.setFont(txtSolver);
	}
	
	Text txtEditor;
	private void initEmptyPuzzle() {
		String content = txtEditor.getText();
		try {
			//TODO check content
			List<Block> blocks = PuzzleLoader.getBlocks(content);
			resetEmptyPuzzle(false);
			puzzle.setBlocks(blocks);
			redrawTable();
		} catch (Exception e1) {
			MessageDialog.openError(null, "Error", e1.getMessage());
			return;
		}
	}
	private void createEditPanel(Composite child) {
		Button btPreview = new Button(child, SWT.NONE);
		btPreview.setText("Preview");
		btPreview.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initEmptyPuzzle();
			}
		});
		
		Button btSave = new Button(child, SWT.NONE);
		btSave.setText("Save");
		btSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = txtPuzzle.getText().trim();
				if(name.isEmpty()) {
					MessageDialog.openError(null, "Error", "Puzzle name cannot be empty");
					return;
				}
				if(!name.contains(".")) {
					name += ".puzzle";
				}
				if(!name.endsWith(".puzzle")) {
					MessageDialog.openError(null, "Error", "Puzzle name must end with .puzzle");
					return;
				}
				initEmptyPuzzle();
				puzzle.setName(name);
				if(PuzzleFactory.contains(puzzle)) {
					MessageDialog.openError(null, "Error", "Puzzle name exist");
					return;
				}
				if(PuzzleFactory.addPuzzle(puzzle, true)) {
					MessageDialog.openInformation(null, "Info", "Puzzle save success");
				}
			}
		});
		
		txtEditor = new Text(child, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		txtEditor.setLayoutData(gd);
		FontManager.setFont(txtEditor);
		txtEditor.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				if(e.character != 0 && 
						e.character != SWT.DEL && e.character != SWT.BS && e.character != SWT.CR && e.character != SWT.LF &&
						"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0234567 ".indexOf(e.character) == -1) {
					e.doit = false;
				}
			}
		});
		
		Label lbl = new Label(child, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 2;
		lbl.setLayoutData(gd);
		lbl.setText("Tips: \n"
				+ "\t1. input an 2-d char array\n"
				+ "\t2. input 'a-zA-Z0-9' for non empty cell\n"
				+ "\t3. input '- ' for empty cell\n"
				+ "\t4. input 'X' for destination cell\n");
	}
	
    Text txtSearcher;
    Solver solver;
	private void createSearchPanel(Composite child) {
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
				txtSearcher.setText("");
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
				parent.setEnabled(false);
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
					txtSearcher.setText(SolverUtils.printSolver(solver, false)+"\n");
				} else {
					MessageDialog.openWarning(null, "Info", "Puzzle not solved!");
				}
				parent.setEnabled(true);
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
				parent.setEnabled(false);
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
									txtSearcher.append(String.format("\n%04d %s", count++, step));
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
								parent.setEnabled(true);
							}
						});
					}
				}).start();
			}
		});
		
		txtSearcher = new Text(child, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		txtSearcher.setLayoutData(gd);
		FontManager.setFont(txtSearcher);
	}
}
