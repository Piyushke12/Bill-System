import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class AddProducts extends JFrame {

	private JPanel contentPane;
	private JTextField product_price;
	private JTextField no_of_product;
	private JTextField product_name;
	private JTextField product_tax;
	private JTextField product_des;
	static Connection connection=null;
	/**
	 * Launch the application.
	 */
	
			
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProducts frame = new AddProducts();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	//Create table
	public void CreateTableNew1() {
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet  set = dmd.getTables(null, null, "ADDPRODUCTS", null);
			
			if(set.next())
			{
				//if table exist do nothing
			}
			else {
				String create_table = "create table ADDPRODUCTS (product_name varchar2(20),product_price number(10),"
									 +"no_of_product number(10),tax number(10),discription varchar2(30))";
				
				PreparedStatement statement = connection.prepareStatement(create_table);
				statement.executeUpdate();
				
				//JOptionPane.showMessageDialog(null, "Table Created Successfully");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	} 

	/**
	 * Create the frame.
	 */
	public AddProducts() {
		
		//database connection
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","HR","hr");
		
		//JOptionPane.showMessageDialog(null, "Connection Successful");
		
		}
		catch(ClassNotFoundException | SQLException e1) {
			
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new LineBorder(new Color(255, 165, 0), 6));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddYourProducts = new JLabel("Add your Products");
		lblAddYourProducts.setBounds(544, 100, 275, 39);
		lblAddYourProducts.setForeground(new Color(240, 255, 255));
		lblAddYourProducts.setFont(new Font("Dialog", Font.BOLD, 31));
		contentPane.add(lblAddYourProducts);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(389, 212, 554, 391);
		contentPane.add(panel);
		panel.setLayout(null);
		
		product_name = new JTextField();
		product_name.setBounds(269, 22, 144, 25);
		panel.add(product_name);
		product_name.setColumns(10);
		
		product_price = new JTextField();
		product_price.setBounds(269, 72, 144, 25);
		panel.add(product_price);
		product_price.setColumns(10);
		
		no_of_product = new JTextField();
		no_of_product.setBounds(269, 120, 144, 25);
		panel.add(no_of_product);
		no_of_product.setColumns(10);
		
		product_tax = new JTextField();
		product_tax.setBounds(269, 168, 144, 25);
		panel.add(product_tax);
		product_tax.setColumns(10);
		
		product_des = new JTextField();
		product_des.setBounds(269, 217, 144, 25);
		panel.add(product_des);
		product_des.setColumns(10);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setBounds(65, 22, 144, 25);
		panel.add(lblProductName);
		lblProductName.setForeground(new Color(240, 255, 240));
		
		JLabel lblProductPrice = new JLabel("Product Price");
		lblProductPrice.setBounds(63, 72, 107, 25);
		panel.add(lblProductPrice);
		lblProductPrice.setForeground(new Color(240, 255, 240));
		
		JLabel lblNoOfUnits = new JLabel("No. of Units");
		lblNoOfUnits.setBounds(63, 120, 107, 25);
		panel.add(lblNoOfUnits);
		lblNoOfUnits.setForeground(new Color(240, 248, 255));
		
		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setBounds(63, 217, 121, 16);
		panel.add(lblProductDescription);
		lblProductDescription.setForeground(new Color(255, 255, 255));
		
		JLabel lblTax = new JLabel("Tax");
		lblTax.setBounds(63, 168, 34, 25);
		panel.add(lblTax);
		lblTax.setForeground(new Color(255, 255, 255));
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(411, 337, 98, 26);
		panel.add(btnClose);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(206, 277, 98, 26);
		panel.add(btnSubmit);
		
		JButton btnUpdateStock = new JButton("Update Stock");
		btnUpdateStock.setBounds(63, 277, 121, 26);
		panel.add(btnUpdateStock);
		btnUpdateStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UpdateStock().setVisible(true);
				dispose();
				
			}
		});
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String insert = "insert into ADDPRODUCTS values(?,?,?,?,?)";
				 
			    
				try {
					PreparedStatement statement = connection.prepareStatement(insert);
					statement.setString(1, product_name.getText());
					statement.setString(2, product_price.getText());
					statement.setString(3, no_of_product.getText());
					statement.setString(4, product_tax.getText());
					statement.setString(5, product_des.getText());
					int data_entered = statement.executeUpdate();
					
					if(data_entered > 0) {
						JOptionPane.showMessageDialog(null, "Products Added");
						
						product_name.setText("");
						product_price.setText("");
						no_of_product.setText("");
						product_tax.setText("");
						product_des.setText("");
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Tasks().setVisible(true);
				dispose();
			}
		});
		CreateTableNew1();
		
		
	}
}
