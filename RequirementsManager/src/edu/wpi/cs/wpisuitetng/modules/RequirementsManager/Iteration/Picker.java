/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author tnarayan
 *
 */
public class Picker {   

	public static void main(String[] args) {  
		JLabel label = new JLabel("Selected Date:");
		final JTextField text = new JTextField(20);
		final JButton b = new JButton("popup");
		JPanel p = new JPanel();
		p.add(label);
		p.add(text);
		p.add(b);
		final JFrame f = new JFrame();
		f.getContentPane().add(p);
		f.pack();
		f.setVisible(true);
		b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                DatePicker dp = new DatePicker(f);
                Point bP = b.getLocationOnScreen();
                dp.d.setLocation(bP.x, bP.y + b.getHeight()); 
                dp.d.setVisible(true);
                text.setText(dp.setPickedDate());
            }
        });
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}