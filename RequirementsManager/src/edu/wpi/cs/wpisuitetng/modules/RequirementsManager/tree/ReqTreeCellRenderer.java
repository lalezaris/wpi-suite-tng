/**
 * This file was developed for CS3733: Software Engineering The course was taken
 * at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * Chris Hanna 
 * Tyler Stone
 * Arica Liu
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.jar.JarFile;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * The Class to hold ReqTreeCellRenderer.
 * 
 * @author Chris Hanna
 */
@SuppressWarnings("serial")
public class ReqTreeCellRenderer extends DefaultTreeCellRenderer {
	private ImageIcon no_priority_icon;
	private ImageIcon low_priority_icon;
	private ImageIcon med_priority_icon;
	private ImageIcon high_priority_icon;
	private ImageIcon default_folder;
	private ImageIcon iteration_past;
	private ImageIcon iteration_future;
	private ImageIcon iteration_current;

	/**
	 * Instantiates a new req tree cell renderer.
	 */
	public ReqTreeCellRenderer() {
		JarFile jarFile;
		try {
			jarFile = new JarFile("modules/RequirementsManager.jar");
			no_priority_icon = new ImageIcon(getImageResource(jarFile,
					"req_no_priority.png"));
			low_priority_icon = new ImageIcon(getImageResource(jarFile,
					"req_low_priority.png"));
			med_priority_icon = new ImageIcon(getImageResource(jarFile,
					"req_med_priority.png"));
			high_priority_icon = new ImageIcon(getImageResource(jarFile,
					"req_high_priority.png"));
			default_folder = new ImageIcon(getImageResource(jarFile,
					"iter_folder_default.png"));
			iteration_past = new ImageIcon(getImageResource(jarFile,
					"iter_folder_past.png"));
			iteration_future = new ImageIcon(getImageResource(jarFile,
					"iter_folder_future.png"));
			iteration_current = new ImageIcon(getImageResource(jarFile,
					"iter_folder_current.png"));
		} catch (IOException e) {
			// failed
			e.printStackTrace();
		}
	}

	/** 
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent
	 * (javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int,
	 * boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		Component comp = super.getTreeCellRendererComponent(tree, value, sel,
				expanded, leaf, row, hasFocus);

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
			
			// Color the fake requirements
			if (req.checkFake()) {
	//			setEnabled(false);
				setForeground(Color.magenta);
			}
			// Color the requirement whose iteration is different than its parent's iteration
			Requirement[] reqs = ((ReqTreeModel)tree.getModel()).getRequirements();
			for (int i = 0; i < reqs.length; i++) {
				if (!(reqs[i].checkFake())) {
					if (reqs[i].getId() == req.getParentRequirementId()) {
						if (reqs[i].getIterationId() != req.getIterationId()) {
							setForeground(Color.gray);
							setText(getText() + "*");
						}
					}
				}
			}
		} else if (node.getUserObject() instanceof Iteration) {
			Iteration iter = (Iteration) node.getUserObject();
			Calendar cStart = Calendar.getInstance();
			cStart.setTime(iter.getStartDate());
			Calendar cEnd = Calendar.getInstance();
			cEnd.setTime(iter.getEndDate());
			cEnd.add(Calendar.DATE, 1);
			
			if (iter.getName().equals("Backlog")) {
				setIcon(default_folder);
			} else {
				Date now = new Date();
				if (cStart.compareTo(Calendar.getInstance()) > 0) {
					setIcon(iteration_future);
				} else if (cEnd.compareTo(Calendar.getInstance()) == 0) {
					setIcon(iteration_current);
				} else if (cEnd.compareTo(Calendar.getInstance()) < 0) {
					setIcon(iteration_past);
				} else {
					setIcon(iteration_current);
				}
			}
		} else if (node.getUserObject() instanceof String) {
			String text = (String) node.getUserObject();
			if (text.equals("Deleted")) {
				setIcon(default_folder);
			}
		}

		return comp;
	}

	/**
	 * Gets image resource
	 * 
	 * @param jarFile the jarfile for the image
	 * @param name the name of the image
	 * @return the image resource
	 */
	private byte[] getImageResource(JarFile jarFile, String name) {
		InputStream inStream = null;
		byte[] retVal = null;
		try {
			inStream = jarFile.getInputStream(jarFile.getJarEntry(name));
			int fileSize = (int) jarFile.getJarEntry(name).getSize();
			retVal = new byte[fileSize];
			inStream.read(retVal, 0, fileSize);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return retVal;
	}
}