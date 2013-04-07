/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author Chris Hanna
 *
 */
public class ReqTreeCellRenderer extends DefaultTreeCellRenderer{
	private ImageIcon no_priority_icon;
	private ImageIcon low_priority_icon;
	private ImageIcon med_priority_icon;
	private ImageIcon high_priority_icon;
	
	public ReqTreeCellRenderer(){
	}
	
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(
				tree, value, sel,
				expanded, leaf, row,
				hasFocus);
		//if (leaf && isTutorialBook(value)) {
			//setIcon(icon);
			setToolTipText("This book is in the Tutorial series.");
		//} else {
		//	setToolTipText(null); //no tool tip
		//} 

		return this;
	}
	
}
