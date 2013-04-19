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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules;

import java.util.ArrayList;

/**
 * Insert Description Here
 *
 * @author Chris
 *
 * @version Apr 17, 2013
 *
 */
public class ListCompare<T> implements Comparable{

	ArrayList<T> list;
	/**
	 * Enter Description here.
	 * 
	 */
	public ListCompare(ArrayList<T> list) {
		this.list = list;
	}

	public int size(){
		return list.size();
	}
	
	@Override
	public int compareTo(Object o) {
		
		if (list.contains(o))
			return 0;
		
		return 1;
	}

}
