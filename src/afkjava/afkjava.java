package afkjava;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class afkjava extends JFrame{
	public static void main(String[] args){
		JFrame frame1 = new JFrame();
		JPanel panel1 = new JPanel();
		JButton button = new JButton("hi");
		JTextField textfield = new JTextField();
		
		frame1.add(panel1);
		panel1.add(button);
		panel1.add(textfield);
		frame1.setSize(400, 400);
		frame1.setLocation(2900, 500);
		textfield.setText("Whats up?");
		frame1.setLayout(new FlowLayout());
		button.setActionCommand("clicked");
			
		frame1.setVisible(true);
	}

}
