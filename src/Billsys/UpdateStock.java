import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class UpdateStock extends JFrame {

	private JPanel contentPane;
	private Connection connection;
	private JComboBox<String> box;
	private JTextField updatequantity;
	private JTextField updateprice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateStock frame = new UpdateStock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void productDropDown(){
		try {
			String product = "Select * from ADDPRODUCTS";
			PreparedStatement statement = connection.prepareStatement(product);
			ResultSet st = statement.executeQuery(product);
			
			while(st.next())
			{
				box.addItem(st.getString("product_name"));
			}
		}
		catch(Exception e) {
			
		}
	}

	/**
	 * Create the frame
	 */
	public UpdateStock() {
		
		//database connection
		try {
			
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","HR","hr");
		
		//JOptionPane.showMessageDialog(null, "Connection Successful");
		
		}catch(ClassNotFoundException | SQLException e1) {
			
			e1.printStackTrace();;
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new LineBorder(new Color(255, 165, 0), 6));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		layeredPane.setBounds(425, 186, 460, 373);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel updatepanel = new JPanel();
		updatepanel.setBackground(SystemColor.desktop);
		layeredPane.add(updatepanel, "name_1197098584582300");
		updatepanel.setLayout(null);
		
		JLabel lblNoOfUnit = new JLabel("No. of Unit");
		lblNoOfUnit.setForeground(SystemColor.text);
		lblNoOfUnit.setBounds(68, 106, 84, 27);
		lblNoOfUnit.setFont(new Font("Dialog", Font.BOLD, 15));
		updatepanel.add(lblNoOfUnit);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setForeground(SystemColor.text);
		lblPrice.setBounds(68, 161, 84, 27);
		lblPrice.setFont(new Font("Dialog", Font.BOLD, 15));
		updatepanel.add(lblPrice);
		
		updatequantity = new JTextField();
		updatequantity.setBounds(207, 107, 151, 27);
		updatepanel.add(updatequantity);
		updatequantity.setColumns(10);
		
		updateprice = new JTextField();
		updateprice.setBounds(207, 162, 151, 27);
		updatepanel.add(updateprice);
		updateprice.setColumns(10);
		
		JButton updatebutton = new JButton("Update");
		updatebutton.setBounds(68, 243, 98, 26);
		updatepanel.add(updatebutton);
		
		JButton deleteproduct = new JButton("Delete");
		deleteproduct.setBounds(183, 243, 98, 26);
		updatepanel.add(deleteproduct);
		
		JButton closeButton = new JButton("Close");
		closeButton.setBounds(344, 329, 98, 26);
		updatepanel.add(closeButton);
		
		box = new JComboBox<String>();
		box.setBounds(207, 30, 151, 25);
		updatepanel.add(box);
		
		JLabel lblSelectProduct = new JLabel("Select Product");
		lblSelectProduct.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSelectProduct.setForeground(new Color(255, 255, 255));
		lblSelectProduct.setBounds(68, 34, 121, 16);
		updatepanel.add(lblSelectProduct);
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String fetch_row = "Select * from addproducts where product_name=?";
					PreparedStatement statement = connection.prepareStatement(fetch_row);
					statement.setString(1, (String)box.getSelectedItem());
					ResultSet set = statement.executeQuery();
					
					while(set.next()) {
						updatequantity.setText(set.getString("no_of_product"));
						updateprice.setText(set.getString("product_price"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddProducts().setVisible(true);
				dispose();
			}
		});
		deleteproduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String delete = "DELETE from addproducts where product_name=?";
					PreparedStatement statement = connection.prepareStatement(delete);
					statement.setString(1, (String)box.getSelectedItem());
					int set = statement.executeUpdate();
					
					if(set>0) {
						JOptionPane.showMessageDialog(null, "Deleted");
						updateprice.setText("");
						updatequantity.setText("");
					}
					
				}catch(Exception e){
					e.printStackTrace();
					
				}
				
			}
		});
		updatebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String update = "update addproducts SET product_price=?,no_of_product=? where product_name=?";
					PreparedStatement statement = connection.prepareStatement(update);
					statement.setString(1,updateprice.getText());
					statement.setString(2, updatequantity.getText());
					statement.setString(3,  (String)box.getSelectedItem());
					int set = statement.executeUpdate();
					
					if(set>0) {
						JOptionPane.showMessageDialog(null, "Updated");
						updateprice.setText("");
						updatequantity.setText("");
					}
					
				}catch(Exception e){
					e.printStackTrace();
					
				}
			}
		});
		
		JLabel lblProduct = new JLabel("Product");
		lblProduct.setBounds(600, 92, 136, 29);
		lblProduct.setForeground(new Color(240, 255, 240));
		lblProduct.setFont(new Font("Dialog", Font.BOLD, 30));
		contentPane.add(lblProduct);
		
		productDropDown();
	}
}
