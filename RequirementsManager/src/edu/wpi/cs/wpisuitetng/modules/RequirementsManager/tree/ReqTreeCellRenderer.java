/**
 * Chris Hanna
 * Tyler Stone
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * @author Chris Hanna
 *
 */
public class ReqTreeCellRenderer extends DefaultTreeCellRenderer{
	private ImageIcon no_priority_icon = new ImageIcon("..\\RequirementsManager\\src\\media\\req_no_priority.png");
	private ImageIcon low_priority_icon = new ImageIcon("..\\RequirementsManager\\src\\media\\req_low_priority.png");
	private ImageIcon med_priority_icon = new ImageIcon("..\\RequirementsManager\\src\\media\\req_med_priority.png");
	private ImageIcon high_priority_icon = new ImageIcon("..\\RequirementsManager\\src\\media\\req_high_priority.png");
	
	public ReqTreeCellRenderer(){}
	
	@Override
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {
	

		Component comp = super.getTreeCellRendererComponent(
				tree, value, sel,
				expanded, leaf, row,
				hasFocus); 
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		
		if (node.getUserObject() instanceof Requirement) {
			Requirement req = (Requirement) node.getUserObject();
			
			switch (req.getPriority()) {
			case BLANK:
				setIcon(no_priority_icon);
				break;
			case LOW:
				setIcon(low_priority_icon);
				break;
			case MEDIUM:
				setIcon(med_priority_icon);
				break;
			case HIGH:
				setIcon(high_priority_icon);
				break;
			default:
				setIcon(no_priority_icon);
			}
		}
		
		return comp;
	}
	
}
