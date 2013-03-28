/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;


import java.util.ArrayList;

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
		System.out.println("Fetching Refresher: " + (instance != null));
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
		if (this.lastKnownIterations == null){
			System.out.println("Server failed to retrieve iterations in time");
			Iteration[] failedResponse = new Iteration[1];
			failedResponse[0] = Iteration.getBacklog();
			return failedResponse;
		}
		else
			return this.lastKnownIterations;
	}
	
	public void refreshRequirements(Requirement[] reqArray, RefresherMode mode)
	{
		if (mode == RefresherMode.ALL || mode == RefresherMode.TABLE){
			
			table.addRequirements(reqArray);
		}
		if (mode == RefresherMode.ALL || mode == RefresherMode.TREE)
			tree.fillTree(reqArray);
		
	}

	public void refreshIterations(Iteration[] iterations, IterationView view) {
		if (view != null)
		{
			//view.addIterations(iterations);
		}
		for (int i = 0 ; i < iterations.length; i ++)
		{
			System.out.println("server Iteration: " + iterations[i].getIterationNumber());
		}
		setLastKnownIterations(iterations);
		this.iterationsSet = true;
		tree.refreshTree();
	
	}


	public void setLastKnownIterations(Iteration[] iterations) {
		System.out.println("Setting Iterations");
		
		if (iterations == null)
		{
			System.out.println("No server side iterations found");
			
		}
		else{
			
			ArrayList<Iteration> knownIterations = new ArrayList<Iteration>();
			knownIterations.add(Iteration.getBacklog());
			for (int i = 0; i < iterations.length; i ++){
				System.out.println("Found:" + iterations[i] + " with id = " + iterations[i].getId());
				boolean shouldAdd = true;
				for (int j = 0 ; j < knownIterations.size() ; j ++){
					if (knownIterations.get(j).equals((Iteration)iterations[i]))
					{
						//shouldAdd = false;
					}
					
				}
				if (shouldAdd)
					knownIterations.add(iterations[i]);
			}
			
			
			
			this.lastKnownIterations = new Iteration[knownIterations.size()];
			for (int i = 0 ; i < knownIterations.size() ; i ++)
			{
				
				System.out.println("Added:" + knownIterations.get(i));
				this.lastKnownIterations[i] = knownIterations.get(i);
				
			}
			//this.lastKnownIterations = (Iteration[])knownIterations.toArray();
		}
		
		//this.lastKnownIterations = iterations;
	}
	
	
}
