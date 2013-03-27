/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementTable;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.ReqTreeModel;

/**
 * @author Chris Hanna
 *
 */
public class Refresher {

	private static Refresher instance;
	
	
	public static Refresher getInstance(){
		return instance;
	}
	
	
	private ReqTreeModel tree;
	private RequirementListPanel table;
	
	public Refresher(ReqTreeModel tree, RequirementListPanel table)
	{
		System.out.println("make refresher");
		instance = this;
		this.tree = tree;
		this.table = table;
	}
	
	
	
	public void refresh(Requirement[] reqArray, RefresherMode mode)
	{
		if (mode == RefresherMode.ALL || mode == RefresherMode.TABLE)
			table.addRequirements(reqArray);
		if (mode == RefresherMode.ALL || mode == RefresherMode.TREE)
			tree.fillTree(reqArray);
		
	}
	
	
}
