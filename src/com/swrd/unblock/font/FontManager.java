package com.swrd.unblock.font;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class FontManager {
	public static final Font font;
	static {
		Display.getCurrent().loadFont("font/Menlo.ttc");
		// Menlo Regular               -  Menlo.ttc
		// Source Code Variable        -  SourceCodeVariable.ttf
		FontData fd = new FontData("Menlo Regular", 12, SWT.NORMAL); 
        font = new Font(Display.getCurrent(), fd);
	}
	
	public static void setFont(Control con) {
		con.setFont(font);
	}
}
