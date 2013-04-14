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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * Insert Description Here
 *
 * @author Chris
 *
 * @version Apr 14, 2013
 *
 */
public class Rule implements IFilterRule{

	boolean isAnd = true;
	Comparable target;
	RuleComparisonMode compareMode;
	String rule;
	
	public Rule(Comparable target, RuleComparisonMode compareMode, String rule){
		this.compareMode = compareMode;
		this.target = target;
		this.rule = rule;
	}
	
//	@Override 
//	public Requirement applyRule(Requirement in){
//		return null;
//	}
	
	@Override
	public boolean apply(Object parent) throws RuleTargetException{
		Comparable source = null;
		
		source = FilterTable.getInstance().getSource(rule, parent);
	
		if (source!=null){
			
			if (compareMode == RuleComparisonMode.EQUALS)
				return (target.compareTo(source) == 0);
			else if (compareMode == RuleComparisonMode.EQUALSGREATER)
				return (target.compareTo(source) <= 0);
			else if (compareMode == RuleComparisonMode.GREATER)
				return (target.compareTo(source) < 0);
			else if (compareMode == RuleComparisonMode.NOTEQUALS)
				return (target.compareTo(source) != 0);
			else if (compareMode == RuleComparisonMode.LESS)
				return (target.compareTo(source) > 0);
			else if (compareMode == RuleComparisonMode.EQUALSLESS)
				return (target.compareTo(source) >= 0);
			else if (compareMode == RuleComparisonMode.CONTAINS){
				//gross hack. Yea yea, leave me alone. 
				if (target instanceof String && source instanceof String){
					String sTarget = (String)target;
					String sSource = (String)source;
					return (sSource.contains(sTarget));
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isAnd(){
		return this.isAnd;
	}
	
	@Override
	public void setIsAnd(boolean and){
		this.isAnd = and;
	}
	
}
