import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;


@SuppressWarnings("serial")
public class GenerateInvoice extends JFrame {
	protected static final Exception NotFoundException = null;
	private JPanel contentPane;
	private JLabel product_price;
	private JTextField product_quantity;
	private JComboBox<String> product_combobox;
	private JLabel product_left;
	Connection connection=null;
	private JTable bill_table;
	private DefaultTableModel model;
	private int total;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerateInvoice frame = new GenerateInvoice();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void CreateTableNew() {
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet  set = dmd.getTables(null, null, "HISTORY", null);
			
			if(set.next())
			{
				//if table exist do nothing
			}
			else {
				String create_table = "create table HISTORY (productname varchar2(40),productprice number(10),"
									 +"productquantity number(10),total number(10),Ddate DATE)";
				
				PreparedStatement statement = connection.prepareStatement(create_table);
				statement.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Table Created Succesfully");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	
	private void productDropDown(){
		try {
			String product = "Select * from ADDPRODUCTS";
			PreparedStatement statement = connection.prepareStatement(product);
			ResultSet set = statement.executeQuery(product);
			TreeSet<String> t = new TreeSet<>();
			while(set.next())
			{
				t.add(set.getString("product_name"));
			}
			Iterator<String> it = t.iterator();
			while(it.hasNext())
				product_combobox.addItem(it.next());
			
		}
		catch(Exception e) {
			
		}
	}
	
	public GenerateInvoice() {
		
		//database connection
				try {
					
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","HR","hr");
				
				//JOptionPane.showMessageDialog(null, "Connection Successful");
				
				}catch(ClassNotFoundException | SQLException e1) {
					
					e1.printStackTrace();
				}
		/**
		 * Create the frame.
		 */
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new LineBorder(new Color(255, 165, 0), 6));
		setContentPane(contentPane);
		
		product_combobox = new JComboBox<String>();
		product_combobox.setBounds(431, 151, 176, 22);
		product_combobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String fetch_row = "Select * from ADDPRODUCTS where product_name=?";
					PreparedStatement statement = connection.prepareStatement(fetch_row);
					statement.setString(1, (String)product_combobox.getSelectedItem());
					ResultSet set= statement.executeQuery();

					while(set.next())
					{
						product_left.setText(set.getString("no_of_product"));	
						product_price.setText(set.getString("product_price"));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		});
		
		JLabel lblSelectProduct = new JLabel("Select Product");
		lblSelectProduct.setBounds(431, 121, 124, 14);
		lblSelectProduct.setForeground(new Color(240, 255, 240));
		
		product_price = new JLabel();
		product_price.setBounds(781, 153, 98, 20);
		product_price.setForeground(new Color(240, 248, 255));
		
		product_quantity = new JTextField();
		product_quantity.setBounds(509, 213, 98, 20);
		product_quantity.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(760, 120, 55, 16);
		lblPrice.setForeground(new Color(240, 255, 255));
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setFont(new Font("Dialog", Font.BOLD, 14));
		lblQuantity.setBounds(431, 215, 71, 16);
		lblQuantity.setForeground(new Color(240, 255, 240));
		
		JButton btnSubmit = new JButton("Add Item");
		btnSubmit.setBounds(640, 210, 98, 23);
			btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = (DefaultTableModel)bill_table.getModel();
				try {
					if(Integer.parseInt(product_quantity.getText())>Integer.parseInt(product_left.getText()))
					{
						throw new Exception();
					}
				total = Integer.parseInt(product_price.getText()) * Integer.parseInt(product_quantity.getText());
				total = total + (total*3)/100;
				model.insertRow(model.getRowCount(), new Object[] {(String)product_combobox.getSelectedItem(),product_price.getText(),product_quantity.getText(),total});
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Error! Quanity or Not Enough Stock");
				}
				
			}
			});
		
		JLabel lblNoOfUnits = new JLabel("No. of Units");
		lblNoOfUnits.setBounds(640, 120, 85, 16);
		lblNoOfUnits.setForeground(new Color(240, 255, 240));
		
		product_left = new JLabel(" ");
		product_left.setBounds(640, 154, 98, 20);
		product_left.setForeground(new Color(240, 248, 255));
		contentPane.setLayout(null);
		contentPane.add(product_combobox);
		contentPane.add(lblSelectProduct);
		contentPane.add(product_price);
		contentPane.add(product_quantity);
		contentPane.add(lblPrice);
		contentPane.add(lblQuantity);
		contentPane.add(btnSubmit);
		contentPane.add(lblNoOfUnits);
		contentPane.add(product_left);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(431, 247, 499, 191);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		bill_table = new JTable();
		bill_table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product Name", "Price", "Quantity", "Total"
			}
		) {
			
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class, Integer.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		bill_table.getColumnModel().getColumn(0).setPreferredWidth(85);
		bill_table.getColumnModel().getColumn(1).setMinWidth(21);
		bill_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bill_table.setFillsViewportHeight(true);
		bill_table.setColumnSelectionAllowed(true);
		bill_table.setCellSelectionEnabled(true);
		bill_table.setSurrendersFocusOnKeystroke(true);
		scrollPane.setViewportView(bill_table);
		
		JButton btnPrintBill = new JButton("Print Bill");
		btnPrintBill.setBounds(832, 458, 98, 26);
		btnPrintBill.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Inserting data to database
				String insert = "insert into HISTORY values(?,?,?,?,?)";
				Date date = new Date();  
			    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			    String strDate = formatter.format(date); 
				try 
				{
				PreparedStatement statement;
				statement=connection.prepareStatement(insert);
				
							
							for(int i=0;i<bill_table.getRowCount();i++)
								for(int j=0;j<bill_table.getColumnCount();j++) {
							
							String pname =bill_table.getValueAt(i,j).toString();
							String pprice =bill_table.getValueAt(i,j+=1).toString();
							String pquantity=bill_table.getValueAt(i,j+=1).toString();
							String ptotal =bill_table.getValueAt(i,j+=1).toString();
							statement.setString(1, pname);
							statement.setString(2, pprice);
							statement.setString(3, pquantity);
							statement.setString(4,ptotal);
							statement.setString(5, strDate);
								}
							int data_entered = statement.executeUpdate();
								
							
							if(data_entered > 0)
							{
								//Printing the Bill
						
						            boolean print = bill_table.print();
						            if (!print) {
						                throw NotFoundException;
						            }
								JOptionPane.showMessageDialog(null, "Bill Printed");					
								while(model.getRowCount()>0)
								for(int i=0;i<bill_table.getRowCount();i++)
								model.removeRow(i);
								String query = "Update ADDPRODUCTS set no_of_product=? where product_name=?";
								Integer total = Integer.parseInt(product_left.getText()) - Integer.parseInt(product_quantity.getText());
								PreparedStatement st;
								st=connection.prepareStatement(query);
								st.setString(1, total.toString());
								st.setString(2, (String)product_combobox.getSelectedItem());
								st.executeQuery();
								
									String fetch_row = "Select no_of_product from ADDPRODUCTS where product_name=?";
									PreparedStatement sta = connection.prepareStatement(fetch_row);
									sta.setString(1, (String)product_combobox.getSelectedItem());
									ResultSet set= sta.executeQuery();
									
									while(set.next())
									{
										product_left.setText(set.getString("no_of_product"));	
									}
									product_quantity.setText("");
							}
								}
						catch(Exception e) {
							JOptionPane.showMessageDialog(null, "Unable To Print !!..");
							for(int i=0;i<bill_table.getRowCount();i++)
								model.removeRow(i);
						}
			}
		});
		contentPane.add(btnPrintBill);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(717, 458, 98, 26);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Tasks().setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnClose);
		
		productDropDown();
		CreateTableNew();
	}
}
