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

import java.util.List;

/**
 * Compares objects for filters
 *
 * @author Chris Hanna
 *
 * @version Apr 17, 2013
 *
 */
@SuppressWarnings("rawtypes")
public class ListCompare<T> implements Comparable{

	List<T> list;
	/**
	 * Constructor for ListCompare
	 * 
	 * @param list the list to compare
	 * 
	 */
	public ListCompare(List<T> list) {
		this.list = list;
	}

	/**
	 * Size of the list
	 * 
	 * @return the size of the list
	 */
	public int size(){
		return list.size();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {

		if (list.contains(o))
			return 0;

		return 1;
	}

}
