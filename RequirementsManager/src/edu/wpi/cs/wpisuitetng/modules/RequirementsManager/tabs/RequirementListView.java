/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsRequestObserver;

/**
 * Insert Description Here
 *
 * @author lty
 *
 * @version Mar 21, 2013
 *
 */
public class RequirementListView extends JPanel implements IToolbarGroupProvider {

	private ToolbarGroupView buttonGroup;
	private JButton refreshButton;
	private RequirementListPanel mainPanel;
	private RetrieveAllRequirementsController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	
	
	
	public RequirementListView(Tab tab){
		containingTab = tab;

		inputEnabled = true;
		
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Requirements List");
		
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Requirements List");
		
		
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
	}
	
	
	public void addRequirements(Requirement[] reqArray){
		mainPanel.clearList();
		
		for(Requirement r : reqArray){
			mainPanel.addRequirement(r);
		} 
	}
	
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
	
}
