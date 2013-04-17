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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The JPanel for all requirement attachments.
 * TODO: implementation
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
@SuppressWarnings("serial")
public class AttachmentsView extends JPanel {
	public AttachmentsView() {
		JLabel attachmentsLabel = new JLabel("Attachments");
		this.add(attachmentsLabel);
	}
}
