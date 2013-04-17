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
 *  Evan Polekoff
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jfree.chart.plot.PiePlot3D;

/**
 * The class that will rotate the pie chart when the button is pressed.
 * 
 * @author Evan Polekoff
 * 
 */
public class PieRotator extends Timer implements ActionListener{
	/** The plot. */
    private PiePlot3D plot;

    /** The angle. */
    private int angle = 0;

    /**
     * Constructor.
     *
     * @param plot  the plot to rotate.
     */
    public PieRotator(PiePlot3D plot) {
        super(100, null);
        this.plot = plot;
        addActionListener(this);//Attach a timer listener to run continuously.
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		plot.setStartAngle(angle);
        angle += 2;
        //Don't increase to infinity.
        if (angle >= 360) {
            angle -= 360;
        }
	}
}
