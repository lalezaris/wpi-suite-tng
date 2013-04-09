/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * @author Evan Polekoff
 *
 */
public class BarChartView extends JPanel implements IToolbarGroupProvider {
	
	private ToolbarGroupView buttonGroup;
	private BarChartPanel mainPanel;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	
	
	/**Constructs a Bar Chart View so the bar chart can be viewed.
	 * 
	 */
	public BarChartView(Tab tab){
		System.out.println("Bar Chart View Created!");
		
		containingTab = tab;
		
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		
		containingTab.setIcon(new ImageIcon()); 
		
		// Instantiate the main create requirement panel
		// TODO
		mainPanel = new BarChartPanel();
		
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
		public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				mainPanelScrollPane.repaint();
			}
		});
		
		//this.add(mainPanelScrollPane, BorderLayout.CENTER);
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

}
