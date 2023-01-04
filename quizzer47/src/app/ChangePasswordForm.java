package app;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// class that allows users registered in the database table account to change their password field.
// test users are special cases, their passwords are not changed if they access this page.
public class ChangePasswordForm extends JFrame implements PageInterface {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel title;
	private JTextField password;
	private JButton submit;
	private GridBagConstraints tc, pc, sc;
	private Font fonta = new Font("Arial", Font.PLAIN, 20);
	private Font head_font = new Font("Arial", Font.PLAIN, 30);
	
	// main
	public static void main(String[] args) {
		ChangePasswordForm cpf = new ChangePasswordForm();
		PageInterface.runPage(cpf);   
	}
	
	public ChangePasswordForm() {
	}
	
	public ChangePasswordForm(User user) {
		defaultSettings();
		pageTitle();
		panel = new JPanel(new GridBagLayout());
		Insets i = PageInterface.setPanelLayout(panel, user, 10);
		this.add(panel);
		// position items on display
		title = new JLabel(String.format("Enter a new Password"));
		tc = new GridBagConstraints();
		PageInterface.positionGridItem(panel, title, head_font, i, tc, 0, 0, true);
		password = new JTextField(String.format("%s", user.getPassword()));
		pc = new GridBagConstraints();
		PageInterface.positionGridItem(panel, password, fonta, i, pc, 0, 1, true);
		submit = new JButton("Submit");
		sc = new GridBagConstraints();
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// firstly, make sure a test user isn't trying to change their password
				if (1 == User.isLoginTestUser(user.getUsername(), user.getPassword()) || 2 == 
						User.isLoginTestUser(user.getUsername(), user.getPassword()) || true ==
						User.isRegisterTestUser(user.getUsername(), user.getPassword(), user.getFavoriteColor(), 
						user.getAge(), user.getEmail(), user.getPhone())) {
					System.out.println(String.format("Test user '%s' attempted password change.", user.getUsername()));
					JOptionPane.showMessageDialog(submit, "Nice try test user.");
					dispose();
					return;
				}
				try { // update password for user
					Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:/quizzer47", "root", "password");
					PreparedStatement state = (PreparedStatement) connection.prepareStatement(
							"update account set password=? where username=?");
					state.setString(1, password.getText());
					state.setString(2, user.getUsername());
	    	    	state.executeUpdate();
	    	    	JOptionPane.showMessageDialog(submit, "Password successfully changed!");
	    	    	dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
		});
		PageInterface.positionGridItem(panel, submit, fonta, i, sc, 0, 2, true);
	}

	public void defaultSettings() {
		setSize(500,300);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public void pageTitle() {
		setTitle("Change Password");
	}
}
