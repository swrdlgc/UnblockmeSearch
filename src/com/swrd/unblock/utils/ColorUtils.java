package com.swrd.unblock.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ColorUtils {
	public static Color Yellow;
	public static Color Red;
	public static Color White;
	
	static {
		try {
			Yellow = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
			Red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
			White = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		} catch (Exception e) {
		}
	}
}
