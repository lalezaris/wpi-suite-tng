/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
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
	
	private RetrieveAllRequirementsController reqController;
	private RetrieveAllIterationsController iterationController;
	
	private Iteration[] lastKnownIterations;
	private boolean iterationsSet;
	
	
	public Refresher(ReqTreeModel tree, RequirementListPanel table)
	{
		System.out.println("make refresher");
		instance = this;
		this.tree = tree;
		this.table = table;
		this.iterationsSet = false;
	}
	

	public void refreshRequirementsFromServer(RefresherMode mode)
	{
		this.reqController = new RetrieveAllRequirementsController(mode);
		this.reqController.refreshData();
		
	}
	
	public void refreshIterationsFromServer(IterationView view)
	{
		this.iterationController = new RetrieveAllIterationsController(view);
		this.iterationController.refreshData();
	}
	
	public Iteration[] getInstantIterations()
	{
		this.iterationsSet = false;
		this.refreshIterationsFromServer(null);
		while (!this.iterationsSet){}
		return this.lastKnownIterations;
	}
	
	public void refreshRequirements(Requirement[] reqArray, RefresherMode mode)
	{
		if (mode == RefresherMode.ALL || mode == RefresherMode.TABLE)
			table.addRequirements(reqArray);
		if (mode == RefresherMode.ALL || mode == RefresherMode.TREE)
			tree.fillTree(reqArray);
		
	}

	public void refreshIterations(Iteration[] iterations, IterationView view) {
		if (view != null)
		{
			view.addIterations(iterations);
		}
		this.lastKnownIterations = iterations;
		this.iterationsSet = true;
	}
	
	
}
