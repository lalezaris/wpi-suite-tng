/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.Refresher;

/**
 * @author Chris
 *
 */
public class ListIterationAction extends AbstractAction {

	
	public ListIterationAction(String n){
		super(n);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("fetch iterations...");
		Iteration[] i = new Iteration[0];
		i = Refresher.getInstance().getInstantIterations(); 
		
		System.out.println("here are the iterations...");
		for (int j = 0; j < i.length; j ++)
			System.out.println(""+j + ":" + i[j].getIterationNumber());
		System.out.println("done finding iterations...");
	}

}
