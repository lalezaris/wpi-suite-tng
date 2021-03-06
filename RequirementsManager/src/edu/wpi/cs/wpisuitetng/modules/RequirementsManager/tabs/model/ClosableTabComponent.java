/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 *  Tyler Stone
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionView;

/**
 * This provides a tab component with a close button to the left of the title.
 * Adapted from Defect Tracker.
 * 
 * @author Tyler Stone 
 *
 * @version Mar 17, 2013
 *
 */
@SuppressWarnings("serial")
public class ClosableTabComponent extends JPanel implements ActionListener {

	private final JTabbedPane tabbedPane;

	/**
	 * Create a closable tab component belonging to the given tabbedPane.
	 * The title is extracted with {@link JTabbedPane#getTitleAt(int)}.
	 * 
	 * @param tabbedPane  The JTabbedPane this tab component belongs to
	 */
	public ClosableTabComponent(JTabbedPane tabbedPane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.tabbedPane = tabbedPane;
		setOpaque(false);

		final JLabel label = new JLabel() {
			// display the title according to what's set on our JTabbedPane
			@Override
			public String getText() {
				final JTabbedPane tabbedPane = ClosableTabComponent.this.tabbedPane;
				final int index = tabbedPane.indexOfTabComponent(ClosableTabComponent.this);
				return index > -1 ? tabbedPane.getTitleAt(index) : "";
			}
		};
		label.setBorder(BorderFactory.createEmptyBorder(3, 0, 2, 7));
		add(label);

		final JButton closeButton = new JButton("\u2716");
		closeButton.setFont(closeButton.getFont().deriveFont((float) 8));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.addActionListener(this);
		add(closeButton);
	}

	/* 
	 * Action performed to close tab component
	 * @param arg0 the ActionEvent being performed
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// close this tab when close button is clicked
		final int index = tabbedPane.indexOfTabComponent(this);
		String checkTitle = tabbedPane.getTitleAt(index);
		Component component = tabbedPane.getComponentAt(index);
		if(index > -1) {
			if((checkTitle.equals("Requirement List")) || (checkTitle.equals("Bar Chart")) || (checkTitle.equals("Iteration List"))){
				tabbedPane.remove(index);
			} else {				
				if(component instanceof RequirementView){
					RequirementPanel rPanel = (RequirementPanel) ((RequirementView) component).getRequirementPanel();
					if(rPanel.isThereChanges()){
						int buttons = JOptionPane.showConfirmDialog(
								null,
								"Are you sure you want to exit? Your changes will not be saved.",
								"Warning",
								JOptionPane.YES_NO_OPTION);
						if (buttons == JOptionPane.YES_OPTION) {
							tabbedPane.remove(index);
						}
					} else {
						tabbedPane.remove(index);
					}
				} else if(component instanceof IterationView){
					IterationPanel iPanel = (IterationPanel) ((IterationView) component).getIterationPanel(); 
					if(iPanel.isThereChanges()){
						int buttons = JOptionPane.showConfirmDialog(
								null,
								"Are you sure you want to exit? Your changes will not be saved.",
								"Warning",
								JOptionPane.YES_NO_OPTION);
						if (buttons == JOptionPane.YES_OPTION) {
							tabbedPane.remove(index);
						}
					} else {
						tabbedPane.remove(index);
					}
				} else if(component instanceof UserPermissionView){
					UserPermissionPanel uPPanel = (UserPermissionPanel) ((UserPermissionView) component).getUserPermissionPanel();
					if(uPPanel.isHasChanged()){
						int buttons = JOptionPane.showConfirmDialog(
								null,
								"Are you sure you want to exit? Your changes will not be saved.",
								"Warning",
								JOptionPane.YES_NO_OPTION);
						if (buttons == JOptionPane.YES_OPTION) {
							tabbedPane.remove(index);
						}
					} else {
						tabbedPane.remove(index);
					}
				}
			} 
		}
	}
}