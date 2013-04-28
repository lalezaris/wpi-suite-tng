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
 *  Sam Lalezari
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * A tree node for the requirement tree.
 *
 * @author Sam Lalezari
 *
 * @version Mar 20, 2013
 *
 */
@SuppressWarnings({ "serial" })
public class ReqTreeNode extends DefaultMutableTreeNode {

	protected Requirement req = null;

	/**
	 * Instantiates a new requirement tree node.
	 *
	 * @param req the requirement
	 */
	public ReqTreeNode(Requirement req){
		super(req.getTitle());
		this.req = req;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name of the requirement within the node
	 */
	public String getName(){
		return req.getTitle();
	}

	/**
	 * Gets the requirement.
	 *
	 * @return the requirement within the node
	 */
	public Requirement getRequirement() {
		return req;
	}
}