package wsr;

import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
 import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.PatternSyntaxException;
  


import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.*;

public class Table extends JFrame implements ActionListener
 {

	 // public static String table = "db_clients";
	  
	  public static FileSystem FS;
	  public Statement statement;
	  public DB_Link link;  
 	  
	  
	  //For Table
	  String col[] = {"ID","Last Name","Fisrt Name","Gender"};	 	  
	  DefaultTableModel model;	 
	  JTextField[] fields;
	  public JTable table;		  			  	
	  final TableRowSorter<TableModel> sorter;	  
	  String selectedData;     
 	  
	  
//For  Profile
		private JTextField nameTextField, addressTextField, cityTextField,
					stateTextField, zipTextField, phoneTextField, emailTextField,
	     nameTextField1, addressTextField1, cityTextField1,
		stateTextField1, zipTextField1, phoneTextField1, emailTextField1,testfield;
		
		private JLabel nameLabel, addressLabel, cityLabel, stateLabel, zipLabel,
		phoneLabel, emailLabel, nameLabel1, addressLabel1, cityLabel1,
		stateLabel1, zipLabel1, phoneLabel1, emailLabel1, testlabel;
	        
		JButton enterButton;
		
		Container contentPane;    
		
		
	  
	 public Table() throws ClassNotFoundException{
		 /*
		  * Initialize classes
		  */
		  link = new DB_Link();
  
		   /*
		    * For the table
		    */
		   model = new DefaultTableModel(col,link.totalEntries());	 
		    table=new JTable(model){
			  	@Override public boolean isCellEditable(int arg0, int arg1){ return false; } };	   	
		         sorter = new TableRowSorter<TableModel>(model);
		 	 
	 }
 
	  
	  public JScrollPane db_table() throws ClassNotFoundException 
	  {
 
		    /*
		     * Make the UI_table
		     */
			        table.setRowSorter(sorter);
			       
					  /*
					   * This calls the function to populate the UI_table
					   * Returns the number of items in the table
					   */
					  DisplayDataBase(link.getTable());
 					  /*
					   * Mouse listener to find ID of selected client
					   */
					    ListSelectionModel cellSelectionModel = table.getSelectionModel();
					    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
					        public void valueChanged(ListSelectionEvent e) {				   
					              selectedData = (String) table.getValueAt(table.getSelectedRows()[0], 0);		
					        }
					      });
					    
			        JScrollPane pane = new JScrollPane(table); //pane 
			        return pane;
	  }
	  
	  /*
	   * Call an object of the ResutlSet to display all the data available on the database
	   * 
	   */
	  public void DisplayDataBase(ResultSet rs) {
		  	int i = 0;
		    try {
		      while(rs.next()) {
		    	  table.setValueAt(rs.getString("ID"),i,0); 
		    	  table.setValueAt(rs.getString("LastName"),i,1); 
		    	  table.setValueAt(rs.getString("FirstName"),i,2); 
		    	  table.setValueAt(rs.getString("Gender"),i,3); 
		    	i++;  
		      }
		    }
		    catch(SQLException e)
		    {
		      System.err.println(e.getMessage());
		    }  
	  }
	  
	/*
	 * This is the seach bar to seach on the table  
	 */
	  public JPanel searchField() {
 
			        JPanel panel = new JPanel(new BorderLayout());
			        JLabel label = new JLabel("Search");
			        panel.add(label, BorderLayout.WEST);
			        
			        /// this is the text to appear on the search space      
			        final JTextField filterText = new JTextField(""); 
			        
					/*
					 * adds panel with a filter to the pane
					 */
			        panel.add(filterText, BorderLayout.CENTER);
			        			        
			        // button
			        JButton button = new JButton("Search");				  			        
			        button.addActionListener(new ActionListener() {        	
			          public void actionPerformed(ActionEvent e) {
			            String text = filterText.getText();
			            if (text.length() == 0) {
			              sorter.setRowFilter(null);
			            } else {
			              try {
			                sorter.setRowFilter(
			                    RowFilter.regexFilter(text));
			              } catch (PatternSyntaxException pse) {
			                System.err.println("Bad regex pattern");
			              }
			            }
			          }
			        });
			         
			        panel.add(button, BorderLayout.EAST);

 return panel;
	  }
 
 	 /*
 	  * Returns selected data from the table
 	  */
	public String getSelectedData() {
		return selectedData;
	}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  /*
	   * Generates the text fields behind the table
	   */
	  public JPanel userProfile() throws ClassNotFoundException {
		    final JPanel panel = new JPanel(new BorderLayout());		    
		    //adds Address book
		    panel.add(mainProfile(), BorderLayout.CENTER);//fields from AddressBook class
		    //adds the picture area 
		   panel.add(background(), BorderLayout.WEST);

 return panel;
}
	  

	  
	  public Container mainProfile(){
		  
		  Client client = new Client();

	        
	 		Container contentPane = getContentPane();
			contentPane.setBackground(Color.WHITE);
			contentPane.setLayout(new GridLayout(8,4));
			

			  nameLabel = new JLabel("Name: ");
			contentPane.add(nameLabel);
			String name = client.GetFirstName() + " " + client.GetLastName();
			nameTextField = new JTextField(name,18);
			contentPane.add(nameTextField);

			  addressLabel = new JLabel("Address:  ");
			contentPane.add(addressLabel);
			addressTextField = new JTextField(client.GetAddress(),18);
			contentPane.add(addressTextField);

			  cityLabel = new JLabel("City: ");
			contentPane.add(cityLabel);
			cityTextField = new JTextField(client.GetCity(),18);
			contentPane.add(cityTextField);

			  stateLabel = new JLabel("State: ");
			contentPane.add(stateLabel);
			stateTextField = new JTextField(client.GetState(),18);
			contentPane.add(stateTextField);

			  zipLabel = new JLabel("Zip code: ");
			contentPane.add(zipLabel);
			zipTextField = new JTextField(client.GetZip(),18);
			contentPane.add(zipTextField);

			  phoneLabel = new JLabel("Phone number: ");
			contentPane.add(phoneLabel);
			phoneTextField = new JTextField(client.GetPhone(),18);
			contentPane.add(phoneTextField);

			  emailLabel = new JLabel("Email: ");
			contentPane.add(emailLabel);
			emailTextField = new JTextField("xxxx@gmail.com",18);
			contentPane.add(emailTextField);

			  nameLabel1 = new JLabel("Data of Birth: ");
			contentPane.add(nameLabel1);
			nameTextField1 = new JTextField(client.GetDOB(),18);
			//final JTextField filterText = new JTextField(""); 
			contentPane.add(nameTextField1);

			  addressLabel1 = new JLabel("Age:  ");
			contentPane.add(addressLabel1);
			addressTextField1 = new JTextField(client.GetAge(),18);
			contentPane.add(addressTextField1);

			  cityLabel1 = new JLabel("Gender: ");
			contentPane.add(cityLabel1);
			cityTextField1 = new JTextField(client.GetGender(),18);
			contentPane.add(cityTextField1);

			  stateLabel1 = new JLabel("Date of Entry: ");
			contentPane.add(stateLabel1);
			stateTextField1 = new JTextField("xx/xx/xxxx",18);
			contentPane.add(stateTextField1);

			  zipLabel1 = new JLabel("Date of Exit: ");
			contentPane.add(zipLabel1);
			zipTextField1 = new JTextField("xx/xx/xxxx",18);
			contentPane.add(zipTextField1);

			  phoneLabel1 = new JLabel("Status: ");
			contentPane.add(phoneLabel1);
			phoneTextField1 = new JTextField(client.GetRank(),18);
			contentPane.add(phoneTextField1);

			  emailLabel1 = new JLabel("Program Turn out: ");
			contentPane.add(emailLabel1);
			emailTextField1 = new JTextField("Not Succesful",18);
			contentPane.add(emailTextField1);

			  enterButton = new JButton("Update Record");
			enterButton.addActionListener(this);
			contentPane.add(enterButton);

			JButton exitButton = new JButton("Exit");
			exitButton.addActionListener(this);
			contentPane.add(exitButton);
 			
		 
			
			return contentPane;
		}

		
		
		
		public void actionPerformed(ActionEvent e)
		{
			String actionCommand = e.getActionCommand();
if(actionCommand.equals("Update Record"))
			{
				String display = nameTextField.getText() + "\n";
				display += addressTextField.getText() + "\n";
				display += cityTextField.getText() + "\n";
				display += stateTextField.getText() + "\n";
				display += zipTextField.getText() + "\n";
				display += phoneTextField.getText() + "\n";
				display += emailTextField.getText();

				nameTextField.setText("");
				addressTextField.setText("");
				cityTextField.setText("");
				stateTextField.setText("");
				zipTextField.setText("");
				phoneTextField.setText("");
				emailTextField.setText("");

				JOptionPane.showMessageDialog(null, display);
			}
			else
				
				
				System.exit(0);
		}
		  
	  
	  
 
	  public JPanel background() {
			String title = "<html><body style='width: 120px; padding: 5px;'>"
	                + "<h1>Do U C Me?</h1>";

			JPanel f = new JPanel(new GridBagLayout());
			f.setBorder(
			        new TitledBorder("PROFILE") );
			
	        BufferedImage image = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
	        Graphics2D imageGraphics = image.createGraphics();
	   
	        GradientPaint gp = new GradientPaint(20f,20f,Color.red,180f,180f,Color.orange);
	        imageGraphics.setPaint(gp);
	        
	        imageGraphics.fillRect(0, 0, 200, 200);
  
	        JLabel textLabel = new JLabel(title);
	        textLabel.setSize(textLabel.getPreferredSize());

	        Dimension d = textLabel.getPreferredSize();
	        BufferedImage bi = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
	 
	        
	        Graphics g = bi.createGraphics();
	        g.setColor(new Color(255, 255, 255, 128));
	 
	        
	        g.fillRect(0,0,200,200);
	        	        
	        g.setColor(Color.black);
	        textLabel.paint(g);
	       
       
	        
	        Graphics g2 = image.getGraphics();
	       g2.drawImage(bi, 20, 20, f);
	  
	       
	        ImageIcon ii = new ImageIcon(image);
	        JLabel imageLabel = new JLabel(ii);
 

	        f.add(imageLabel,null);
	    

	        
		return f;
			
		} 
	 
 
	

	 
 
	  
	  
	  
	  /**
	   * @param args not used
	   */
	  public static void main(String[] args)  throws ClassNotFoundException 
	  {
		 Table example = new Table();
		  example.db_table();
	  }
	
}