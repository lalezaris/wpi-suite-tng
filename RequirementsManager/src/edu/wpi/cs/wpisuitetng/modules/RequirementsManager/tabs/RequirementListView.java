/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsRequestObserver;

/**
 * @author lty
 *
 */
public class RequirementListView extends JPanel {

	private ToolbarGroupView buttonGroup;
	private JButton refreshButton;
	private RequirementListPanel mainPanel;
	private RetrieveAllRequirementsController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	
	
	
	public RequirementListView(Tab tab){
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		
		inputEnabled = true;
		
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("List all Requirement");
		
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("List all Requirement");
		
		
		// Instantiate the main create requirement panel
		mainPanel = new RequirementListPanel(this);
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				//SwingUtilities.invokeLater(new Runnable(){
				//	public void run(){
						mainPanelScrollPane.repaint();
				//	}
				//});
			}
		});
		
		this.add(mainPanelScrollPane, BorderLayout.CENTER);
		controller = new RetrieveAllRequirementsController(this);

		// Instantiate the save button and add it to the button panel
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new RefreshAction(controller));
		buttonGroup.getContent().add(refreshButton);
		buttonGroup.setPreferredWidth(150);
		
		
		controller.refreshData();
	}
	
	
	public void addRequirements(Requirement[] reqArray){
		for(Requirement r : reqArray){
			mainPanel.addRequirement(r);
		} 
	}
	
}
