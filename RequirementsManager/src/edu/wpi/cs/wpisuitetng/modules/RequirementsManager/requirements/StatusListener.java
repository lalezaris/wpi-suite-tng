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
 */
public class StatusListener implements ActionListener{
	
	RequirementView parent;
	public StatusListener(RequirementView parent){
		this.parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent status) {
		JComboBox cb = (JComboBox)status.getSource();
		System.out.println(cb.getSelectedItem());

		changeIteration(cb);
	}
	
	/**
	 * Change the iteration to which a requirement belongs.
	 *
	 * @param cb the JComboBox which contains all the iterations.
	 */
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
