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
 * Evan Polekoff
 * Ned Shelton
 * chrisfresher hanmiester
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;


/**
 * The listener interface for receiving status events.
 * The class that is interested in processing a status
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addStatusListener<code> method. When
 * the status event occurs, that object's appropriate
 * method is invoked.
 * 
 * @author Evan Polekoff
 * @author Ned Shelton
 */
public class StatusListener implements ActionListener{
	
	RequirementView parent;
	public StatusListener(RequirementView parent){
		this.parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent status) {
		JComboBox cb = (JComboBox)status.getSource();

		if (cb.getSelectedItem() != null) {
			changeIteration(cb);
		}
	}
	
	/**
	 * Change the iteration to which a requirement belongs.
	 *
	 * @param cb the JComboBox which contains all the iterations.
	 */
	@SuppressWarnings("rawtypes")
	public void changeIteration(JComboBox cb){

		
		if(RequirementStatus.valueOf((String) cb.getSelectedItem()) == RequirementStatus.OPEN && parent.getReqModel().getRequirement().getIterationId() != Iteration.getBacklog().getId() ){
			parent.getRequirementPanel().getCmbIteration().setSelectedIndex(parent.getRequirementPanel().getCmbIteration().getItemCount()-1);
			parent.getRequirementPanel().getCmbIteration().setEnabled(false);
		} else if((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.COMPLETE || parent.getReqModel().getRequirement().getStatus() == RequirementStatus.DELETED)  && parent.getReqModel().getRequirement().getIterationId() != Iteration.getBacklog().getId()){ 
			for (int i = 0; i < parent.getRequirementPanel().getCmbIteration().getItemCount(); i++) {
				if (parent.getReqModel().getRequirement().getIteration().toString().equals(parent.getRequirementPanel().getKnownIterations()[i].toString()) ){
					parent.getRequirementPanel().getCmbIteration().setSelectedIndex(i);
					if(parent.getReqModel().getRequirement().getStatus() == RequirementStatus.COMPLETE && parent.getReqModel().getRequirement().getParentRequirementId() == -1){
						parent.getRequirementPanel().getCmbIteration().setEnabled(true);
					}
				}
			}
		}
	}
}
