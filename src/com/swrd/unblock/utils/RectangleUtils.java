package com.swrd.unblock.utils;

import java.awt.Rectangle;

public class RectangleUtils {
	public static String EncodeRectangle(Rectangle rect) {
		return String.format("%d, %d, %d, %d", rect.x, rect.y, rect.width,
				rect.height);
	}

	public static Rectangle CopyRectangle(Rectangle rect) {
		if (rect != null) {
			return new Rectangle(rect);
		}
		return null;
	}

	// a - b
	public static Rectangle SubRectangle(Rectangle a, Rectangle b) {
		if (a.intersects(b)) {
			int xof = a.x - b.x;
			int yof = a.y - b.y;
			return new Rectangle(a.x + (xof > 0 ? a.width - xof : 0), a.y + (yof > 0 ? a.height - yof : 0), 
					Math.abs(xof) > 0 ? Math.abs(xof) : a.width,
							Math.abs(yof) > 0 ? Math.abs(yof) : a.height );
		}
		return a;
	}
	
	public static void main(String[] args) {
		Rectangle r1 = new Rectangle(1, 2, 3, 1);
		Rectangle r2 = new Rectangle(2, 2, 3, 1);
		System.err.println(SubRectangle(r2, r1));
		System.err.println(SubRectangle(r1, r2));
		
		r1 = new Rectangle(2, 1, 1, 2);
		r2 = new Rectangle(2, 4, 1, 2);
		System.err.println(SubRectangle(r2, r1));
		System.err.println(SubRectangle(r1, r2));
		
		r1 = new Rectangle(2, 1, 2, 1);
		r2 = new Rectangle(2, 2, 2, 1);
		System.err.println(SubRectangle(r2, r1));
		System.err.println(SubRectangle(r1, r2));
		
		r1 = new Rectangle(1, 1, 1, 2);
		r2 = new Rectangle(2, 1, 1, 2);
		System.err.println(SubRectangle(r2, r1));
		System.err.println(SubRectangle(r1, r2));
		
		r1 = new Rectangle(0, 1, 1, 2);
		r2 = new Rectangle(1, 1, 1, 2);
		System.err.println(SubRectangle(r2, r1));
		System.err.println(SubRectangle(r1, r2));
	}
}
