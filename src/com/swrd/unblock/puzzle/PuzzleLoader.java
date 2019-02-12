package com.swrd.unblock.puzzle;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.swrd.unblock.bound.blocks.Block;
import com.swrd.unblock.bound.blocks.HRBlock;
import com.swrd.unblock.bound.blocks.P9Block;
import com.swrd.unblock.bound.blocks.UnBlock;
import com.swrd.unblock.ems.PuzzleType;

public class PuzzleLoader {
	
	public static Puzzle load(File file) throws Exception {
		return load(file.getName(), readToString(file));
	}
	
	public static Puzzle load(String name, String content) throws Exception {
		String arr[] = content.split("\n");
		PuzzleType type;
		Rectangle bounds;
		if(arr.length == 7) {
			type = PuzzleType.UnBlockMe;
			bounds = UnBlock.Bounds;
		} else if(arr.length == 6) {
			type = PuzzleType.HRRoad;
			bounds = HRBlock.Bounds;
		} else if(arr.length == 4) {
			type = PuzzleType.P9;
			bounds = P9Block.Bounds;
		} else {
			throw new Exception("unknown puzzle type");
		}
		
		for(int i = 0; i < arr.length; ++i) {
			arr[i] = arr[i].replaceAll(" ", "");
		}
		int blockSize = Integer.parseInt(arr[0]);
		
		Map<Character, List<Point>> map1 = new LinkedHashMap<>();
		for(int i = 1; i < arr.length; ++i) {
			String s = arr[i];
			for(int j = 0; j < s.length(); ++j) {
				char c = s.charAt(j);
				Point p = new Point(j, i - 1);
				if(c != '-') {
					if(!map1.containsKey(c)) {
						map1.put(c, new ArrayList<>());
					}
					map1.get(c).add(p);
				}
			}
		}
		Map<Character, Rectangle> map2 = new LinkedHashMap<>();
		Iterator<Entry<Character, List<Point>>> it1 = map1.entrySet().iterator();
		while(it1.hasNext()) {
			Entry<Character, List<Point>> e = it1.next();
			map2.put(e.getKey(), getRect(e.getValue()));
		}
		
		List<Block> blocks = new ArrayList<Block>();
		Iterator<Entry<Character, Rectangle>> it2 = map2.entrySet().iterator();
		while(it2.hasNext()) {
			Entry<Character, Rectangle> e = it2.next();
			Block block;
			
			//TODO check
			
			if(arr.length == 7) {
				if(e.getKey() == 'X') {
					block = new UnBlock(e.getValue(), UnBlock.Destination, e.getKey());
				} else {
					block = new UnBlock(e.getValue(), e.getKey());
				}
			} else if(arr.length == 6) {
				if(e.getKey() == 'X') {
					block = new HRBlock(e.getValue(), HRBlock.Destination, e.getKey());
				} else {
					block = new HRBlock(e.getValue(), e.getKey());
				}
			} else {
				block = new P9Block(e.getValue(), e.getKey()-'0');
			}
			blocks.add(block);
		}
		

		assert(blockSize == blocks.size());
		Puzzle p = new Puzzle(name, type, bounds, blocks);
		if(arr.length == 7) {
			p.setExit(new Rectangle(6, 2, 1, 1));
		} else if(arr.length == 6) {
			p.setExit(new Rectangle(1, 5, 2, 1));
		}
		return p;
	}
	

	private static String readToString(File file) throws Exception {  
        String encoding = "UTF-8";  
        Long len = file.length();  
        byte[] content = new byte[len.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(content);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String(content, encoding);  
    } 
	
	private static Rectangle getRect(List<Point> list) {
		int x1 = Integer.MAX_VALUE;
		int y1 = Integer.MAX_VALUE;
		int x2 = Integer.MIN_VALUE;
		int y2 = Integer.MIN_VALUE;
		
		for(Point p : list) {
			x1 = Math.min(x1, p.x);
			y1 = Math.min(y1, p.y);
			x2 = Math.max(x2, p.x);
			y2 = Math.max(y2, p.y);
		}
		
		return new Rectangle(x1, y1, x2-x1+1, y2-y1+1);
	}
}
