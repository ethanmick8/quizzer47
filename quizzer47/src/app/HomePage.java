package app;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// This page provides a UI to the user for navigating Quizzer47. Its background color,
// like all logged-in page classes, matches the user's favorite color field if that field corresponds
// to a valid Color class field. From here, users can logout, or they can navigate to either the Quiz
// Page or their Profile Page.
public class HomePage extends JFrame implements PageInterface {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel title;
	private JButton quiz, profile, logout;
	private GridBagConstraints tc, qc, pc, lc;
	private Font font = new Font("Arial", Font.PLAIN, 20);

	// main
	public static void main(String[] args) {
		HomePage rf = new HomePage();
		PageInterface.runPage(rf);
	}
	
	public HomePage() { // empty constructor for vanilla page access
	}
	
	// constructor
	public HomePage(User user) {
		defaultSettings();
		pageTitle();
		panel = new JPanel(new GridBagLayout());
		//c = getContentPane();
		// personalize user's background to match their favorite color
		PageInterface.setBackgroundColor(panel, user);
		System.out.println("User background updated!");
		this.add(panel);
		Insets i = new Insets(7, 7, 7, 7);
		// displayed title
		title = new JLabel(String.format("Welcome to Quizzer47, %s!", user.getUsername()));
		title.setFont(font);
		tc = new GridBagConstraints();
		tc.gridx = 0;
		tc.gridy = 0;
		tc.gridwidth = GridBagConstraints.REMAINDER;
		tc.insets = i;
		panel.add(title, tc);
		// button that navigates to quiz page
		quiz = new JButton("Take a Quiz!");
		qc = new GridBagConstraints();
		qc.gridx = 0;
		qc.gridy = 1;
		qc.insets = i;
		quiz.addActionListener(new ActionListener() { // navigate to quiz page
			public void actionPerformed(ActionEvent ae) {
				dispose();
				QuizPage qp = new QuizPage(user);
				qp.setVisible(true);
			}
		});
		panel.add(quiz, qc);
		// button that navigates to user profile page
		profile = new JButton("View my Profile");
		pc = new GridBagConstraints();
		pc.gridx = 1;
		pc.gridy = 1;
		pc.insets = i;
		profile.addActionListener(new ActionListener() { // profile page
			public void actionPerformed(ActionEvent ae) {
				dispose();
				ProfilePage pp = new ProfilePage(user);
				pp.setVisible(true);
			}
		});
		panel.add(profile, pc);
		// button that logs user out
		logout = new JButton("Logout");
		lc = new GridBagConstraints();
		lc.gridx = 2;
		lc.gridy = 1;
		lc.insets = i;
		logout.addActionListener(new ActionListener() { // log user out
			public void actionPerformed(ActionEvent ae) {
				int x = JOptionPane.showConfirmDialog(logout, "Are you sure?"); // confirm they want to
				if (x == JOptionPane.YES_OPTION) {
					dispose();
					LoginForm lp = new LoginForm();
					lp.setVisible(true);
				}
			}
		});
		panel.add(logout, lc);
	}
	
	public void defaultSettings() {
		setSize(900,600);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public void pageTitle() {
		setTitle("Quizzer47 Home Page");
	}
}
