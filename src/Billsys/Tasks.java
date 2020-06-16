import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Tasks extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tasks frame = new Tasks();
					frame.setTitle("Billing System");
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
	public Tasks() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new LineBorder(new Color(255, 165, 0), 6));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(148, 170, -46, -38);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JLabel lblServices = new JLabel("Services");
		lblServices.setForeground(new Color(255, 255, 255));
		lblServices.setBounds(603, 74, 170, 50);
		lblServices.setFont(new Font("Dialog", Font.BOLD, 38));
		contentPane.add(lblServices);
		
		JButton btnAddProducts = new JButton("Add Products");
		btnAddProducts.setBounds(603, 233, 170, 26);
		btnAddProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddProducts().setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnAddProducts);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		panel.setBackground(Color.BLACK);
		panel.setBounds(457, 176, 484, 442);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnGenerateInvoice = new JButton("Generate Invoice");
		btnGenerateInvoice.setBounds(145, 131, 170, 26);
		panel.add(btnGenerateInvoice);
		
		JButton btnReports = new JButton("Edit Profile");
		btnReports.setBounds(145, 202, 170, 26);
		panel.add(btnReports);
		
		JButton btnHistory = new JButton("History");
		btnHistory.setBounds(145, 271, 170, 26);
		panel.add(btnHistory);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(183, 341, 98, 26);
		panel.add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new History().setVisible(true);
				dispose();
			}
		});
		btnReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new EditProfile().setVisible(true);
				dispose();
			}
		});
		btnGenerateInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateInvoice().setVisible(true);
				dispose();
			}
		});
	}
}
