import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JPasswordField;
import java.sql.*;
import java.awt.Component;


public class MainPage {

	static JFrame frame;
	private JTextField dob_field;
	private JTextField age_field;
	private JTextField org_field;
	private JTextField name_field;
	private JTextField validate_username;
	private JLayeredPane layeredPane;
	private JPanel Sign_in_panel;
	private JPanel Log_in_panel;
	private JPasswordField validate_password;
	private JPasswordField password_field;
	private JTextField mobile_field;
	public static String getuser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void dispose() {
		frame.setVisible(false);
		
	}
	
	public void switchPanel(JPanel Panel) {
		
		layeredPane.removeAll();
		layeredPane.add(Panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	Connection connection;
	
	public void CreateTableNew1() {
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet  set = dmd.getTables(null, null, "USERTABLE", null);
			
			if(set.next())
			{
				//if table exist do nothing
			}
			else {
				String create_table = "create table USERTABLE (username varchar2(20),password varchar2(15),"
									 +"organization varchar2(30),dateofbirth varchar2(10),age varchar(3),mobile varchar2(10))";
				
				PreparedStatement statement = connection.prepareStatement(create_table);
				statement.executeUpdate();
				
				//JOptionPane.showMessageDialog(null, "Table Created Succesfully");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		
		//database connection
		try {
			
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","HR","hr");
		//JOptionPane.showMessageDialog(null, "Connection Successful");
		
		}catch(ClassNotFoundException | SQLException e1) {
			
			e1.printStackTrace();
		}
		
		
		frame = new JFrame("Billing System");
		frame.setBackground(new Color(105, 105, 105));
		frame.setLocale(null);
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(0, 0, 1366, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblBillingSystem = new JLabel("Billing System");
		lblBillingSystem.setBounds(546, 157, 274, 56);
		lblBillingSystem.setForeground(new Color(240, 248, 255));
		lblBillingSystem.setFont(new Font("Dialog", Font.BOLD, 40));
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(460, 240, 451, 314);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		//initialize the table
		CreateTableNew1();
		
		Log_in_panel = new JPanel();
		Log_in_panel.setBackground(new Color(0, 0, 0));
		Log_in_panel.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		layeredPane.add(Log_in_panel, "name_778843359284500");
		
		JLabel lblLogIn = new JLabel("Log In");
		lblLogIn.setBounds(175, 14, 102, 24);
		lblLogIn.setForeground(new Color(240, 255, 240));
		lblLogIn.setVerticalAlignment(SwingConstants.TOP);
		lblLogIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogIn.setFont(new Font("Dialog", Font.BOLD, 18));
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(109, 68, 85, 46);
		lblUsername.setForeground(new Color(240, 255, 240));
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 15));
		
		validate_username = new JTextField();
		validate_username.setBounds(237, 80, 142, 22);
		validate_username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(109, 126, 110, 26);
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JButton login2 = new JButton("LogIn");
		login2.setBounds(179, 189, 98, 23);
		login2.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				String validate = "select username,password from USERTABLE where username=? and password=?";
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(validate);
					statement.setString(1, validate_username.getText());
					statement.setString(2, validate_password.getText());
					ResultSet set = statement.executeQuery();
					
					if(set.next())
					{
						getuser = validate_username.getText();
						validate_username.setText("");
						validate_password.setText("");
						new Tasks().setVisible(true);
						dispose();	
					}else {
						JOptionPane.showMessageDialog(null,"Invalid Username or password");
					}
				}
				
				
				catch (SQLException e) {
					e.printStackTrace();
				}
				
			}

		});
		
		JButton btnCreateANew = new JButton("Create Account");
		btnCreateANew.setBounds(163, 257, 130, 23);
		btnCreateANew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchPanel(Sign_in_panel);
			}
		});
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setBounds(219, 225, 20, 19);
		lblOr.setForeground(new Color(240, 248, 255));
		lblOr.setFont(new Font("Courier New", Font.BOLD, 16));
		
		validate_password = new JPasswordField();
		validate_password.setBounds(237, 129, 142, 22);
		Log_in_panel.setLayout(null);
		Log_in_panel.add(lblLogIn);
		Log_in_panel.add(lblUsername);
		Log_in_panel.add(validate_username);
		Log_in_panel.add(lblPassword);
		Log_in_panel.add(validate_password);
		Log_in_panel.add(btnCreateANew);
		Log_in_panel.add(lblOr);
		Log_in_panel.add(login2);
		
		Sign_in_panel = new JPanel();
		Sign_in_panel.setBackground(new Color(0, 0, 0));
		Sign_in_panel.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		layeredPane.add(Sign_in_panel, "name_778774038171300");
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(97, 73, 58, 16);
		lblPassword_1.setForeground(new Color(240, 255, 255));
		
		JLabel lblOrganization = new JLabel("Organization");
		lblOrganization.setBounds(83, 106, 72, 16);
		lblOrganization.setForeground(new Color(240, 248, 255));
		
		JLabel lblNewLabel_1 = new JLabel(" Username");
		lblNewLabel_1.setBounds(93, 38, 62, 23);
		lblNewLabel_1.setForeground(new Color(240, 255, 255));
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(85, 134, 70, 16);
		lblDateOfBirth.setForeground(new Color(240, 248, 255));
		
		JLabel lblMobile = new JLabel("Age");
		lblMobile.setBounds(133, 168, 22, 16);
		lblMobile.setForeground(new Color(240, 248, 255));
		lblMobile.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		dob_field = new JTextField();
		dob_field.setBounds(212, 134, 114, 20);
		dob_field.setColumns(10);
		
		age_field = new JTextField();
		age_field.setBounds(212, 166, 114, 20);
		age_field.setColumns(10);
		
		org_field = new JTextField();
		org_field.setBounds(212, 102, 114, 20);
		org_field.setColumns(10);
		
		name_field = new JTextField();
		name_field.setBounds(212, 38, 114, 20);
		name_field.setColumns(20);
		
		JButton create_field = new JButton("Create Account");
		create_field.setBounds(151, 239, 122, 26);
		create_field.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String insert_data = "insert into USERTABLE values(?,?,?,?,?,?)";
				try {
					PreparedStatement statement  = connection.prepareStatement(insert_data);
					statement.setString(1, name_field.getText());
					statement.setString(2, password_field.getText());
					statement.setString(3, org_field.getText());
					statement.setString(4, dob_field.getText());
					statement.setString(5, age_field.getText());
					statement.setString(6, mobile_field.getText());
					
					int data_entered = statement.executeUpdate();
					
					if(data_entered > 0) {
						JOptionPane.showMessageDialog(null,"Account Created Succesfully");
						switchPanel(Log_in_panel);
					}
					else
						JOptionPane.showMessageDialog(null,"Unable to Create Account");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblUserDeatails = new JLabel("User Deatails");
		lblUserDeatails.setBounds(156, 3, 173, 23);
		lblUserDeatails.setForeground(new Color(240, 248, 255));
		lblUserDeatails.setFont(new Font("Dialog", Font.BOLD, 17));
		
		JButton login1 = new JButton("Log In");
		login1.setBounds(256, 288, 73, 22);
		login1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			switchPanel(Log_in_panel);
		}
		});
		
		JLabel lblHaveAnAccount = new JLabel("Have an Account");
		lblHaveAnAccount.setBounds(88, 289, 112, 19);
		lblHaveAnAccount.setForeground(Color.WHITE);
		lblHaveAnAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		password_field = new JPasswordField();
		password_field.setBounds(212, 70, 114, 20);
		
		JLabel lblMobile_1 = new JLabel("Mobile");
		lblMobile_1.setBounds(118, 196, 37, 16);
		lblMobile_1.setForeground(new Color(240, 248, 255));
		
		mobile_field = new JTextField();
		mobile_field.setBounds(212, 196, 114, 20);
		mobile_field.setColumns(10);
		Sign_in_panel.setLayout(null);
		Sign_in_panel.add(lblNewLabel_1);
		Sign_in_panel.add(name_field);
		Sign_in_panel.add(lblPassword_1);
		Sign_in_panel.add(password_field);
		Sign_in_panel.add(lblDateOfBirth);
		Sign_in_panel.add(lblOrganization);
		Sign_in_panel.add(org_field);
		Sign_in_panel.add(dob_field);
		Sign_in_panel.add(lblHaveAnAccount);
		Sign_in_panel.add(lblMobile);
		Sign_in_panel.add(lblMobile_1);
		Sign_in_panel.add(mobile_field);
		Sign_in_panel.add(age_field);
		Sign_in_panel.add(create_field);
		Sign_in_panel.add(login1);
		Sign_in_panel.add(lblUserDeatails);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblBillingSystem);
		frame.getContentPane().add(layeredPane);
	}

}
