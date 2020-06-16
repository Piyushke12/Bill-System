import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class EditProfile extends JFrame {

	private JPanel contentPane;
	private Connection connection;
	private JTextField editname;
	private JTextField editorg;
	private JTextField editdob;
	private JTextField editage;
	private JTextField editmobile;
	private JPasswordField editpassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditProfile frame = new EditProfile();
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
	public EditProfile() {
		
		//database connection
				try {
					
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","HR","hr");
				
				//JOptionPane.showMessageDialog(null, "Connection Successful");
				
				}catch(ClassNotFoundException | SQLException e1) {
					
					e1.printStackTrace();
				}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new LineBorder(new Color(255, 165, 0), 6));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPersonalInfo = new JLabel("Personal Info");
		lblPersonalInfo.setBounds(601, 118, 221, 36);
		lblPersonalInfo.setForeground(new Color(240, 248, 255));
		lblPersonalInfo.setFont(new Font("Arial", Font.BOLD, 34));
		contentPane.add(lblPersonalInfo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		panel.setBounds(440, 222, 508, 390);
		panel.setBackground(new Color(0, 0, 0));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(142, 36, 72, 20);
		lblUsername.setForeground(new Color(240, 248, 255));
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(145, 75, 69, 20);
		lblPassword.setForeground(new Color(240, 255, 255));
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblPassword);
		
		JLabel lblOrganization = new JLabel("Organization");
		lblOrganization.setBounds(126, 115, 91, 20);
		lblOrganization.setForeground(new Color(240, 248, 255));
		lblOrganization.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblOrganization);
		
		JLabel lblDob = new JLabel("DOB");
		lblDob.setBounds(178, 163, 34, 20);
		lblDob.setForeground(new Color(240, 248, 255));
		lblDob.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblDob);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(166, 247, 48, 20);
		lblMobile.setForeground(new Color(240, 248, 255));
		lblMobile.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblMobile);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(187, 203, 27, 20);
		lblAge.setForeground(new Color(240, 248, 255));
		lblAge.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblAge);
		
		editname = new JTextField();
		editname.setBounds(263, 37, 114, 20);
		editname.setColumns(10);
		panel.add(editname);
		
		editorg = new JTextField();
		editorg.setBounds(263, 122, 114, 20);
		editorg.setColumns(10);
		panel.add(editorg);
		
		editdob = new JTextField();
		editdob.setBounds(263, 164, 114, 20);
		editdob.setColumns(10);
		panel.add(editdob);
		
		editage = new JTextField();
		editage.setBounds(263, 204, 114, 20);
		editage.setColumns(10);
		panel.add(editage);
		
		editmobile = new JTextField();
		editmobile.setBounds(263, 248, 114, 20);
		editmobile.setColumns(10);
		panel.add(editmobile);
		
		editpassword = new JPasswordField();
		editpassword.setBounds(263, 81, 114, 20);
		panel.add(editpassword);
		
		JButton profilesave = new JButton("Update Profile");
		profilesave.setBounds(160, 290, 114, 26);
		profilesave.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					String update = "update USERTABLE SET username=?,password=?,organization=?,dateofbirth=?,age=?,mobile=? where username=?";
					PreparedStatement statement = connection.prepareStatement(update);
					statement.setString(1,editname.getText());
					statement.setString(2, editpassword.getText());
					statement.setString(3,editorg.getText());
					statement.setString(4,editdob.getText());
					statement.setString(5,editage.getText());
					statement.setString(6,editmobile.getText());
					statement.setString(7,editname.getText());
					int set = statement.executeUpdate();
					System.out.print("updated");
					if(set > 0) {
						JOptionPane.showMessageDialog(null, "Updated");
						new Tasks().setVisible(true);
						dispose();	
					}
					else {
						new Tasks().setVisible(true);
						dispose();
					}
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		panel.add(profilesave);
		
		JButton profileclose = new JButton("Close");
		profileclose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Tasks().setVisible(true);
				dispose();
			}
		});
		profileclose.setBounds(301, 290, 72, 26);
		panel.add(profileclose);
		
		String fetch_userinfo = "Select * from USERTABLE where username=?";
		try {
			PreparedStatement statement = connection.prepareStatement(fetch_userinfo);
			statement.setString(1, MainPage.getuser);
			ResultSet set = statement.executeQuery();
			
			while(set.next()) {
			editname.setText(set.getString("username"));
			editpassword.setText(set.getString("password"));
			editorg.setText(set.getString("organization"));
			editdob.setText(set.getString("dateofbirth"));
			editage.setText(set.getString("age"));
			editmobile.setText(set.getString("mobile"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
