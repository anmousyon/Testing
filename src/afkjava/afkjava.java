package afkjava;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class afkjava extends JFrame implements ActionListener{

public void ActionPerformed(ActionEvent e){
	if(e.getSource() == button){
		textfield.setText("not much");
	}

}

public static void main(String[] args){
								String newText = "What's up?";
								JFrame frame1 = new JFrame();
								frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								JPanel panel1 = new JPanel();
								JButton button = new JButton("hi");
								JTextField textfield = new JTextField();

								frame1.add(panel1);
								panel1.add(button);
								panel1.add(textfield);
								frame1.setSize(400, 400);
								frame1.setLocation(2900, 500);
								textfield.setText(newText);
								frame1.setLayout(new FlowLayout());
								button.addActionListener(this);
								frame1.setVisible(true);
}

}
