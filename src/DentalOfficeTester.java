import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DentalOfficeTester {
	public static void main(String[] args){
		DentalOfficeWindow mainFrame = new DentalOfficeWindow();
		mainFrame.setTitle("UTD Dental Services");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(450, 300);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
	}
}

class DentalOffice{
	private ArrayList<Double> costs;
	public DentalOffice(ArrayList<String> services, ArrayList<String> durations){
		String[] tempService = {"Cavity", "Root Canal", "Braces", "Bridge", "Teeth Cleaning"};
		for (int i=0; i<tempService.length; i++){
			services.add(tempService[i]);
		}
		String[]tempDuration = {"one week", "one month", "one yer"};
		for (int i=0; i<tempDuration.length; i++){
			durations.add(tempDurations[i]);
		}
		double tempCosts[] = {200.00, 600.00, 2000.00, 500.00, 100.00};
		for (int i=0; i<tempCosts.length; i++){
			costs.add(tempCosts[i]);
		}
	}
	public double getCost(int index){
		return costs.get(index);
	}
}

class DentalOfficeWindow extends JFrame implements ActionListener, ItemListener{
	private ArrayList<String> services;
	private ArrayList<String> durations;
	private JRadioButton jrbuttons[];
	private ServicePanel servicePanel;
	private JPanel durationPanel;
	private JPanel actionPanel;
	private JButton button1;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private Icon dentalIcon = new ImageIcon("dentalOffice.png");
	private double total =0 ;
	private String returnTime;
	private DentalOffice office;
	private PatientPanel patientPanel;
	
	OutputWindow outFrame;

	private ButtonGroup group1 = new ButtonGroup();
	public DentalOfficeWindow(){
		outFrame = new OutputWindow(); 
		outFrame.setTitle("Daily Activity Log");
		outFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outFrame.setSize(450, 300);
		outFrame.setLocation(new Point(this.getX() + 550, this.getY()));
		outFrame.setVisible(true);
		outFrame.setResizable(false);
		
		services = new ServicePanel();
		durations = new JPanel();
		office = new DentalOffice();
		
		setLayout(new BorderLayout());
		servicesPanel = new ServicePanel(servicesPanel);
		durationPanel = new JPanel(durationPanel);
		durationPanel.setLayout(new FlowLayout());

		actionPanel = new JPanel(new GridLayout(2,1));
		jrbuttons = new JRadioButton[durations.size()];
		button1 = new JButton("Calculate Bill");
		button1.addActionListener(this);

		label3 = new JLabel(dentalIcon);
		label1 = new JLabel("Return in");
		durationPanel.add(label1);
		patientPanel = new PatientPanel(patientPanel);

		for(int i=0; i<jrbuttons.length; i++){
			jrbuttons[i] = new JRadioButton(office.get(i));
			jrbuttons[i].addItemListener(this);
			group1.add(jrbuttons[i]);
			durationPanel.add(group1);
		}

		add(servicesPanel, BorderLayout.WEST);
		add(label3, BorderLayout.CENTER);
		add(durationPanel, BorderLayout.NORTH);
		label2 = new JLABEL("Total cost: $", SwingConstants.CENTER);
		actionPanel.add(label2);
		actionPanel.add(button1);

		add(actionPanel, BorderLayout.SOUTH);
		add(patientPanel, BorderLayout.EAST);
	}
	
	public void actionPerformed(ActionEvent e){
		setMessage();
	}

	public void itemStateChanged(ItemEvent e) {
		setMessage();
	}

	private void setMessage(){
		String message = "";
		total = 0;
		boolean checkbox = false;
		boolean radioButton = false;

		for(int k =0; k<servicesPanel.length; k++){
			if (servicesPanel.isSelected(k)){
				checkbox =true;
				total += office.getCosts(k);
			}
		}

		message = "Total cost: $" + total;
		for(int i =0; i<jrbuttons.length; i++){
			if(jrbuttons[i].isSelected()) {
				radiobutton = true;
				message += " and return in " + durations.get(i);
			}
		}
		
		if(patientPanel.getTest() != null && checkbox && radiobutton) {
			label2.setText(message);
			outFrame.setText(patientPanel.getText() + ": " + message + "\n");

			patientPanel.clearText();  //clears the textFields
         		servicesPanel.clear();  // clears the checkboxes
         		for (int i=0; i < jrbuttons.length; i++) 
            			jrbuttons[i].setSelected(false);
      			}
      			else
         			label2.setText("Please provide name, check activities, and select duration");
   			}

}
  
class PatientPanel extends JPanel {
   private JLabel fName;
   private JLabel lName;
   private JTextField fNameField;
   private JTextField lNameField;
   private JPanel personPanel;
   
   PatientPanel() {
      personPanel = new JPanel();
      personPanel.setLayout(new GridLayout(2,1));
      fName = new JLabel("First Name: ", SwingConstants.RIGHT);
      fNameField = new JTextField(10);
      lName = new JLabel("Last Name: ", SwingConstants.RIGHT);
      lNameField = new JTextField(10);
      
      personPanel.add(fname);
	personPanel.add(fNameField);
	personPanel.add(lname);
	personPanel.add(lNameField);
      add(personPanel);
   }
  
   public String getText() { //returns the text from the textfields when populated else returns null
      if (fNameField.length() > 0 && lNameField.length() > 0) {
         return fNameField.getText() + " " + lNameField.getText();
      }
      else {
	return null;
   }
}
   public void clearText() {
      fNameField.setText("");
      lNameField.setText("");
   }
}


class ServicePanel extends JPanel {
   private JPanel actionPanel;
   private JCheckBox [] checkboxes;
   public ServicePanel(ArrayList<String> services) {
      checkboxes = new JCheckBox();
      actionPanel = new JPanel();
      actionPanel.setLayout(new GridLayout(5,1));
      for (int i=0; i<checkboxes.length; i++) {
         checkboxes[i] = new JCheckBox();
         actionPanel.add(checkboxes[i]);
      }        
      add(actionPanel); 
   }  
   public boolean isSelected(int index) {
      return checkboxes[index].isSelected();
   }

   public void clear() {
      for (int i=0; i < checkboxes.length; i++) 
         checkboxes[i].setSelected(false);
   }
}

class OutputWindow extends JFrame {
   private JTextArea output;
   private JScrollPane scrollPane; 
   
   public OutputWindow() {  
      output = new JTextArea();
      output.setBackground(Color.LIGHT_GRAY);
      Font font = new Font("Arial", Font.BOLD, 12); 
      output.setFont(font);
      output.setEditable(false);
      scrollPane = new JScrollPane(); // ScrollPane with output area
      add(scrollPane);
      
   }
   
   public void setText(String s) {
      output.setText(s);
   }
}
