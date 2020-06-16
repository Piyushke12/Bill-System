import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class History extends JFrame {

	private JPanel contentPane;
	private JTable historytable;
	private Connection connection;
	private DefaultTableModel model;
	private JLabel datelabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					History frame = new History();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public History() {
		setForeground(new Color(255, 215, 0));
		
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
		
		JLabel lblNewLabel = new JLabel("History");
		lblNewLabel.setBounds(603, 68, 123, 54);
		lblNewLabel.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 33));
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(399, 290, 528, 206);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		historytable = new JTable();
		historytable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product Name", "Quantity", "Price", "Total", "Date"
			}
		));
		historytable.getColumnModel().getColumn(0).setPreferredWidth(85);
		scrollPane.setViewportView(historytable);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		panel.setBackground(Color.BLACK);
		panel.setBounds(333, 52, 650, 526);
		contentPane.add(panel);
		panel.setLayout(null);
		
		datelabel = new JLabel("Date:");
		datelabel.setBounds(447, 203, 147, 22);
		panel.add(datelabel);
		datelabel.setFont(new Font("Dialog", Font.BOLD, 18));
		datelabel.setForeground(Color.WHITE);
		datelabel.setBackground(Color.WHITE);
		
		JButton btnOk = new JButton("Get History");
		btnOk.setBounds(69, 177, 98, 26);
		panel.add(btnOk);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(69, 138, 189, 26);
		panel.add(dateChooser);
		
		JButton historyclosebtn = new JButton("Close");
		historyclosebtn.setBounds(496, 465, 98, 26);
		panel.add(historyclosebtn);
		historyclosebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Tasks().setVisible(true);
				dispose();
			}
		});
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				model = (DefaultTableModel)historytable.getModel();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			    String strDate = formatter.format(dateChooser.getDate()); 
			    datelabel.setText("Date:"+strDate);
				String fetch_history = "Select * from HISTORY where Ddate=?";
				
					PreparedStatement statement;
					statement = connection.prepareStatement(fetch_history);
					statement.setString(1,strDate);
					ResultSet set = statement.executeQuery();
					while(set.next())
					model.insertRow(model.getRowCount(), new Object[] {set.getString("productname"),set.getString("productquantity"),set.getString("productprice"),set.getString("total"),set.getString("Ddate")});
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, "Error! Choose Date");
				}
			}
		});
	}
}
