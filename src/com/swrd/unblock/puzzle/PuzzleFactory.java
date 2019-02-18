package com.swrd.unblock.puzzle;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;

import com.swrd.unblock.ems.PuzzleType;

public class PuzzleFactory {
	private static final String PuzzleTypePathUnBlockMe = "puzzles/unblockme";
	private static final String PuzzleTypePathHRRoad    = "puzzles/hrroad";
	private static final String PuzzleTypePathP9        = "puzzles/p9";
	private static Map<PuzzleType, List<Puzzle>> map = new HashMap<>();
	private static Map<String, Puzzle> map2 = new HashMap<>();
	private static Comparator<Puzzle> cmp = new Comparator<Puzzle>() {
		@Override
		public int compare(Puzzle o1, Puzzle o2) {
			try {
				return o1.getName().compareTo(o2.getName());
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	};
	
	public static List<Puzzle> getList(PuzzleType type) {
		return map.get(type);
	}
	
	
	public static boolean contains(Puzzle puzzle) {
		return contains(puzzle.getType(), puzzle.getName());
	}
	public static boolean contains(PuzzleType type, String name) {
		return map2.containsKey(mkey(type, name));
	}
	
	public static boolean addPuzzle(Puzzle p, boolean save) {
		map.get(p.getType()).add(p);
		map2.put(mkey(p), p);
		if(save) {
			String path;
			if(p.getType() == PuzzleType.UnBlockMe) {
				path = PuzzleTypePathUnBlockMe;
			} else if(p.getType() == PuzzleType.UnBlockMe) {
				path = PuzzleTypePathHRRoad;
			} else {
				path = PuzzleTypePathP9;
			}
			
			FileOutputStream fos = null;
			try {
				File file = new File(path+"/"+p.getName());
				if(file.createNewFile()) {
					fos = new FileOutputStream(file);
					fos.write(p.toString().getBytes());
					fos.flush();
				}
			} catch (Exception e) {
				MessageDialog.openError(null, "Error", "Puzzle save failed");
				return false;
			} finally {
				if(fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
	
	static {
		load(PuzzleType.UnBlockMe, PuzzleTypePathUnBlockMe);
		load(PuzzleType.HRRoad,    PuzzleTypePathHRRoad);
		load(PuzzleType.P9,        PuzzleTypePathP9);
	}
	
	
	private static void load(PuzzleType type, String path) {
		List<Puzzle> list = new ArrayList<>();
		map.put(type, list);
		
		File file = new File(path);
		for(File f : file.listFiles()) {
			if(f.isFile()) {
				try {
					Puzzle p = PuzzleLoader.load(f);
					addPuzzle(p, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Collections.sort(list, cmp);
	}
	
	private static String mkey(Puzzle p) {
		return mkey(p.getType(), p.getName());
	}
	
	private static String mkey(PuzzleType type, String name) {
		return type.name() + "_" + name;
	}
}
