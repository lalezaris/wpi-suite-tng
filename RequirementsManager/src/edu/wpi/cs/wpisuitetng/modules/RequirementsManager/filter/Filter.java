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
 *  Chris Hanna
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.Rule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleTargetException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * The basic filter class that may be applied to different objects 'n things.
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public class Filter{
	
	private final List<Rule> rules;
	
	public Filter(){
		rules = new ArrayList<Rule>();
	}
	
	/**
	 * Add a filter rule
	 * 
	 * @param rule
	 */
	public void addRule(Rule rule){
		rules.add(rule);
	}
	
	/**
	 * Return all the rules
	 * 
	 * @return
	 */
	public List<Rule> getRules(){
		return rules;
	}
	
	public void removeAllRules(){
		rules.clear();
	}
	
	/**
	 * filter a list of objects based on the filters within this Filter
	 * 
	 * @param in
	 * @return the objects that passed all the tests
	 * @throws RuleTargetException 
	 */
	public Requirement[] getFilteredObjects(Requirement[] in) throws RuleTargetException{
		List<Requirement> out = new ArrayList<Requirement>();
		if (in!=null){
			for (int i = 0 ; i < in.length ; i ++){
				
				boolean alreadyTakenCareOf = false;
				int filterPassCount = 0;
				for (int r = 0 ; r < rules.size();r++){
					boolean passedFilter = (rules.get(r).apply(in[i]) == true);
					if (!passedFilter){
						if (rules.get(r).isAnd()){
							//AN AND FAILED. NO MATTER WHAT, DO NOT ADD OUT
							alreadyTakenCareOf = true;
							System.out.println("AND complete" + r);
							break;
						}
					} else{
						filterPassCount++;
						if (!rules.get(r).isAnd()){
							//AN OR PASSED. NO MATTER WHAT, ADD TO OUT
							alreadyTakenCareOf = true;
							System.out.println("OR complete" + r);
							out.add(in[i]);
							break;
						}
					}
				}
				if (!alreadyTakenCareOf){
					if (filterPassCount == rules.size()){
						out.add(in[i]);
					}
				}
				
			}
		}
		Requirement[] outArray = new Requirement[out.size()];
		for (int i = 0 ; i < outArray.length ; i ++)
			outArray[i] = out.get(i);
		return outArray;
	}
	
}
