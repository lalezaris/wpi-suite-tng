package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;

/**
 * Toolbar Panel for Requirements Manager
 * 
 * @author Tyler Stone
 * @author Arica Liu
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends DefaultToolbarView {

	private JButton newRequirement;
	private JButton editRequirement;
	private JButton deleteRequirement;

	public ToolbarPanel() {	
		// Construct the content panel
		JPanel content = new JPanel();
		SpringLayout layout  = new SpringLayout();
		content.setLayout(layout);
		content.setOpaque(false);

		// Construct the new button
		newRequirement = new JButton("New");

		// Construct the edit button
		editRequirement = new JButton("Edit");

		// Construct the search field
		deleteRequirement = new JButton("Delete");

		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, newRequirement, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.WEST, editRequirement, 10, SpringLayout.EAST, newRequirement);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, editRequirement, 0, SpringLayout.VERTICAL_CENTER, newRequirement);
		layout.putConstraint(SpringLayout.WEST, deleteRequirement, 10, SpringLayout.EAST, editRequirement);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, deleteRequirement, 0, SpringLayout.VERTICAL_CENTER, editRequirement);

		// Add buttons to the content panel
		content.add(newRequirement);
		content.add(editRequirement);
		content.add(deleteRequirement);

		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Home", content);

		// Calculate the width of the toolbar
		Double toolbarGroupWidth = newRequirement.getPreferredSize().getWidth() + editRequirement.getPreferredSize().getWidth()+ deleteRequirement.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
	}
}
