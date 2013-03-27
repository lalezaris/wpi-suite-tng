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
 * Insert Description Here
 *
 * @author Sam Lalezari
 *
 * @version Mar 20, 2013
 *
 */
@SuppressWarnings({ "serial" })
public class ReqTreeNode extends DefaultMutableTreeNode {

	protected Requirement req = null;
	
	public ReqTreeNode(Requirement req){
		super(req.getTitle());
		this.req = req;
	}
	
	public String getName(){
		return this.req.getTitle();
	}
	
}