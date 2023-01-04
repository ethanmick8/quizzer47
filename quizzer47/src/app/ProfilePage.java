package app;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// This page provides a UI that displays information based on the User instance that is accessing it.
// Information is read-only, but a change password button at the bottom will allow database users to change
// their password (test users cannot)
public class ProfilePage extends JFrame implements PageInterface {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private Font fonta = new Font("Arial", Font.PLAIN, 20);
	private Font head_font = new Font("Arial", Font.PLAIN, 30);
	private JLabel header, username, fav_color, age, email, phone_num;
	private JTextField ut, ft, at, et, pnt;
	private GridBagConstraints hc, uc, uct, fc, fct, ac, act, ec, ect, pnc, pnct, cpc, bc;
	private JButton change_pass, back;

	// main
	public static void main(String[] args) {
		ProfilePage pp = new ProfilePage();
		PageInterface.runPage(pp);
	}
	
	public ProfilePage() {
	}
	
	public ProfilePage(User user) {
		defaultSettings();
		pageTitle();
		panel = new JPanel(new GridBagLayout());
		Insets i = PageInterface.setPanelLayout(panel, user, 5);
		this.add(panel);
		header = new JLabel(String.format("Profile for user: %s", user.getUsername()));
		hc = new GridBagConstraints();
		// interface static positioning method is called for each item
		PageInterface.positionGridItem(panel, header, head_font, i, hc, 0, 0, true);
		username = new JLabel("Username: ");
		uc = new GridBagConstraints();
		PageInterface.positionGridItem(panel, username, fonta, i, uc, 0, 1, false);;
		ut = new JTextField(String.format("%s", user.getUsername()));
		ut.setEditable(false); // text fields are read-only here
		uct = new GridBagConstraints();
		PageInterface.positionGridItem(panel, ut, fonta, i, uct, 1, 1, true);
		fav_color = new JLabel("Favorite Color: ");
		fc = new GridBagConstraints();
		PageInterface.positionGridItem(panel, fav_color, fonta, i, fc, 0, 2, false);
		ft = new JTextField(String.format("%s", user.getFavoriteColor()));
		ft.setEditable(false);
		fct = new GridBagConstraints();
		PageInterface.positionGridItem(panel, ft, fonta, i, fct, 1, 2, true);
		age = new JLabel("Age: ");
		ac = new GridBagConstraints();
		PageInterface.positionGridItem(panel, age, fonta, i, ac, 0, 3, false);
		at = new JTextField(String.format("%s", user.getAge()));
		at.setEditable(false);
		act = new GridBagConstraints();
		PageInterface.positionGridItem(panel, at, fonta, i, act, 1, 3, true);
		email = new JLabel("E-Mail: ");
		ec = new GridBagConstraints();
		PageInterface.positionGridItem(panel, email, fonta, i, ec, 0, 4, false);
		et = new JTextField(String.format("%s", user.getEmail()));
		et.setEditable(false);
		ect = new GridBagConstraints();
		PageInterface.positionGridItem(panel, et, fonta, i, ect, 1, 4, true);
		phone_num = new JLabel("Phone Number: ");
		pnc = new GridBagConstraints();
		PageInterface.positionGridItem(panel, phone_num, fonta, i, pnc, 0, 5, false);
		pnt = new JTextField(String.format("%s", user.getPhone()));
		pnt.setEditable(false);
		pnct = new GridBagConstraints();
		PageInterface.positionGridItem(panel, pnt, fonta, i, pnct, 1, 5, true);
		change_pass = new JButton("Change Password");
		cpc = new GridBagConstraints();
		Insets button_i = new Insets(15, 15, 15, 15); // different for buttons
		change_pass.addActionListener(new ActionListener() { // change password
			public void actionPerformed(ActionEvent ae) {
				ChangePasswordForm cpf = new ChangePasswordForm(user);
				cpf.setVisible(true);
			}
		});
		PageInterface.positionGridItem(panel, change_pass, fonta, button_i, cpc, 0, 7, false);
		panel.add(change_pass, cpc);
		back = new JButton("Return to Home");
		bc = new GridBagConstraints();
		back.addActionListener(new ActionListener() { // navigate back home
			public void actionPerformed(ActionEvent ae) {
				dispose();
 	    	    HomePage page = new HomePage(user); // return
 	    	    page.setVisible(true);
			}
		});
		PageInterface.positionGridItem(panel, back, fonta, button_i, bc, 1, 7, true);
	}
	

	public void defaultSettings() {
		setSize(900,600);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public void pageTitle() {
		setTitle("Profile Settings");
	}
}
