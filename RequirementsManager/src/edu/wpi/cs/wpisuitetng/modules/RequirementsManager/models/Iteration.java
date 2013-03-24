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
 * Arica Liu
 * Tushar Narayan
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;


/**
 * Class for storing an iteration.
 * 
 * @author Arica Liu
 * @author Tushar Narayan
 */
public class Iteration extends AbstractModel{
	private int iterationNumber;
	private Date startDate, endDate;
	private List<Integer> requirements;
	
	/**
	 * @param iterationNumber
	 * @param startDate
	 * @param endDate
	 * @param requirements
	 */
	public Iteration(int iterationNumber, Date startDate, Date endDate
	) {
		super();
		this.iterationNumber = iterationNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.requirements = new ArrayList<Integer>();
	}

	/**
	 * @return the iterationNumber
	 */
	public int getIterationNumber() {
		return this.iterationNumber;
	}
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the requirements
	 */
	public List<Integer> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(List<Integer> requirements) {
		this.requirements = requirements;
	}

	/**
	 * @param iterationNumber the iterationNumber to set
	 */
	public void setIterationNumber(int iterationNumber) {
		this.iterationNumber = iterationNumber;
	}
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
}
