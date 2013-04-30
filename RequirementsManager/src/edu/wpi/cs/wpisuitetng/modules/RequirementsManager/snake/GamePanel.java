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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * The Class GamePanel.
 */
public class GamePanel extends JPanel{

	
	SnakePanel playPanel;
	Controller controller;
	Snake snake;
	
	JPanel scorePanel;
	JLabel score, scoreBody;
	JLabel highScore, highScoreBody;

	
	/**
	 * Instantiates a new game panel.
	 */
	public GamePanel() {
		Dimension size = new Dimension(500,500);
		this.setPreferredSize(new Dimension(500,500));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		playPanel = new SnakePanel();
		playPanel.setPreferredSize(new Dimension(size.width-20, size.height-80));
		playPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		c.insets = new Insets(10,10,0,0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weighty = 0;
		c.weightx = .5;
		this.add(playPanel,c);
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridBagLayout());
		scorePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		Font titleFont = new Font("Serif", Font.PLAIN, 15);
		Font bodyFont = new Font("Serif", Font.PLAIN, 25);
		score = new JLabel("SCORE:");
		score.setFont(titleFont);
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		scorePanel.add(score, c);
		
		highScore = new JLabel("HIGHSCORE:");
		highScore.setFont(titleFont);
		c.gridy = 1;
		c.gridx = 1;
		scorePanel.add(highScore, c);
		
		scoreBody = new JLabel("filler");
		scoreBody.setFont(bodyFont);
		c.gridy = 2;
		c.gridx = 0;
		scorePanel.add(scoreBody,c);
		
		highScoreBody = new JLabel("Unknown");
		highScoreBody.setFont(bodyFont);
		c.gridx = 1;
		scorePanel.add(highScoreBody,c);
		
		c.gridy = 1;
		c.gridx = 0;
		scorePanel.setPreferredSize(new Dimension(480, (int)scorePanel.getPreferredSize().getHeight()));
		this.add(scorePanel,c);
		
		snake = new Snake(new Spot(5,5));
		
		controller = new Controller(playPanel, snake, this);
		snake.setController(controller);
		
		final GamePanel me = this;
        me.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED){
                    SwingUtilities.getWindowAncestor(me).addWindowListener(new WindowListener() {
                        /* Snipped some empty handlers */

                        @Override
                        public void windowClosing(WindowEvent e) {
                            /* Finally get to remove the handler. */
                        	me.controller.moveTimer.cancel();
                        	
                        }

						@Override
						public void windowOpened(WindowEvent e) {
							
						}

						@Override
						public void windowClosed(WindowEvent e) {
							
						}

						@Override
						public void windowIconified(WindowEvent e) {
							
						}

						@Override
						public void windowDeiconified(WindowEvent e) {
							
						}

						@Override
						public void windowActivated(WindowEvent e) {
							
						}

						@Override
						public void windowDeactivated(WindowEvent e) {
							
						}
                    });
                }
            }
        });
		
	}
	
	
	Tab containingTab;
	/**
	 * Sets the tab.
	 *
	 * @param tab tab to set
	 */
	public void setTab(Tab tab)
	{
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Snake");
		containingTab.setToolTipText("play an awesome game of snake");
	}
	
	/**
	 * Gets the tab.
	 *
	 * @return the tab
	 */
	public Tab getTab() {
		return containingTab;
	}

}
