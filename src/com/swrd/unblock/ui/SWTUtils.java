package com.swrd.unblock.ui;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class SWTUtils {
	 /*
     * Set the shell on the center of the screen
     * 
     * @param shell the shell which needs setCentered
     */
    public static void setCenter(Shell shell) {
        Rectangle rtg = shell.getMonitor().getClientArea();
        shell.setLocation((rtg.width - shell.getSize().x) / 2, (rtg.height - shell.getSize().y) / 2);
    }
    
	public static void setVisible(Control con, boolean b, boolean layout) {
		try {
			boolean anyChanged = false;
			Object obj = con.getLayoutData();
			if(obj instanceof GridData) {
				GridData gd = (GridData) obj;
				anyChanged |= (gd.exclude != !b);
				gd.exclude = !b;
			} else if(obj instanceof RowData) {
				RowData rd = ((RowData) obj);
				anyChanged |= (rd.exclude != !b);
				((RowData) obj).exclude = !b;
			}
			anyChanged |= (con.getVisible() != b);
			con.setVisible(b);
			
			if(anyChanged && layout) {
				con.getParent().layout();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
