/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * @author Ned Shelton
 *
 */
public class IterationListener implements ActionListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */

	@Override
	public void actionPerformed(ActionEvent iterations) {
		JComboBox cb = (JComboBox)iterations.getSource();
		System.out.println(cb.getSelectedItem());
		
	}

}
