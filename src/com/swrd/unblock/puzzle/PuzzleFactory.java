package com.swrd.unblock.puzzle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swrd.unblock.ems.PuzzleType;

public class PuzzleFactory {
	private static Map<PuzzleType, List<Puzzle>> map = new HashMap<>();
	
	public static List<Puzzle> getList(PuzzleType type) {
		return map.get(type);
	}
	
	static {
		load(PuzzleType.UnBlockMe, "puzzles/unblockme");
		load(PuzzleType.HRRoad, "puzzles/hrroad");
		load(PuzzleType.P9, "puzzles/p9");
	}
	
	private static void load(PuzzleType type, String path) {
		List<Puzzle> list = new ArrayList<>();
		map.put(type, list);
		
		File file = new File(path);
		for(File f : file.listFiles()) {
			if(f.isFile()) {
				try {
					Puzzle p = PuzzleLoader.load(f);
					list.add(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
