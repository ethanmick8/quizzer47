package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// This form provides a UI for registering new users into the "account" database for the
// application. It has functionality for a test user if the database is not desirable.
public class RegisterForm extends JFrame implements PageInterface {
	private static final long serialVersionUID = 1L;
	private Container c;
	private JLabel title, username, password, fav_color, age, email, phone_num, message;
	private JTextField ut, pt, ft, at, et, pnt;
	private JButton sub;
	private Font info_font = new Font("Arial", Font.PLAIN, 20);
	private Font textfield_font = new Font("Arial", Font.PLAIN, 15);
	
	// main
	public static void main(String[] args) {
		RegisterForm rf = new RegisterForm();
		PageInterface.runPage(rf);
	}
	
	// constructor
	public RegisterForm() {
		defaultSettings();
		pageTitle();
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.orange);
		
		// title
		title = new JLabel("Registration Form");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setSize(300, 30);
		title.setLocation(300, 30);
		c.add(title);
		// username
	    username = new JLabel();
	    username.setText("Username :");
	    username.setFont(info_font);
	    username.setSize(200, 20);
	    username.setLocation(200, 100);
	    c.add(username);
	    // Username text field
	    ut = new JTextField();
	    ut.setFont(textfield_font);
	    ut.setSize(250, 20);
	    ut.setLocation(400, 100);
	    c.add(ut);
	    // Password Label
	    password = new JLabel();
	    password.setText("Password :");
	    password.setFont(info_font);
	    password.setSize(200, 20);
	    password.setLocation(200, 150);
	    c.add(password);
	    // Password text field
	    pt = new JPasswordField();
	    pt.setFont(textfield_font);
	    pt.setSize(250, 20);
	    pt.setLocation(400, 150);
	    c.add(pt);	
	    // fav color
	    fav_color = new JLabel();
	    fav_color.setText("Favorite Color :");
	    fav_color.setFont(info_font);
	    fav_color.setSize(200, 20);
	    fav_color.setLocation(200, 200);
	    c.add(fav_color);
	    // fav color text field
	    ft = new JTextField();
	    ft.setFont(textfield_font);
	    ft.setSize(250, 20);
	    ft.setLocation(400, 200);
	    c.add(ft);
	    // age
	    age = new JLabel();
	    age.setText("Age :");
	    age.setFont(info_font);
	    age.setSize(200, 20);
	    age.setLocation(200, 250);
	    c.add(age);
	    // age text field
	    at = new JTextField();
	    at.setFont(textfield_font);
	    at.setSize(250, 20);
	    at.setLocation(400, 250);
	    c.add(at);
	    // email
	    email = new JLabel();
	    email.setText("E-Mail :");
	    email.setFont(info_font);
	    email.setSize(200, 20);
	    email.setLocation(200, 300);
	    c.add(email);
	    // email text field
	    et = new JTextField();
	    et.setFont(textfield_font);
	    et.setSize(250, 20);
	    et.setLocation(400, 300);
	    c.add(et);
	    // phone number
	    phone_num = new JLabel();
	    phone_num.setText("Phone :");
	    phone_num.setFont(info_font);
	    phone_num.setSize(200, 20);
	    phone_num.setLocation(200, 350);
	    c.add(phone_num);
	    // phone text field
	    pnt = new JTextField();
	    pnt.setFont(textfield_font);
	    pnt.setSize(250, 20);
	    pnt.setLocation(400, 350);
	    c.add(pnt);
	    // Submit Button
	    sub = new JButton("SUBMIT");
	    sub.setFont(new Font("Arial", Font.PLAIN, 15));
	    sub.setSize(100, 20);
	    sub.setLocation(350, 400);
	    sub.addActionListener(new ActionListener() { // attempt to create user
	    	@Override
	    	public void actionPerformed(ActionEvent ae) {
	    	    String username = ut.getText();
	    	    String password = pt.getText();
	    	    String fav_color = ft.getText();
	    	    String age = at.getText();
	    	    String email = et.getText();
	    	    String phone_num = pnt.getText();
	    	    int length = phone_num.length();
	    	    
	    	    if (length != 10) { // invalid phone num
	    	    	message.setText("Please enter a valid 10-digit number.");
	    	    	return;
	    	    }
	    	    
	    	    if (!isInteger(age)) { // invalid age
	    	    	message.setText("Please enter a valid number for age.");
	    	    	return;
	    	    }
	    	    // check first to see if this is the register form test user being "created"
	    	    if (User.isRegisterTestUser(username, password, fav_color, age, email, phone_num)) {
	    	    	User user = new User(username, password, fav_color, age, email, phone_num);
    	    		System.out.println(String.format("Register test user '%s' being used.", username));
    	    		dispose(); // terminate login page
        	    	HomePage page = new HomePage(user); // bring user to their home page
        	    	page.setVisible(true);
        	    	return; // escape actionPerformed
	    	    }
  
	    	    try { // attempt to execute an insert query with user provided data
	    	    	Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:/quizzer47", "root", "password");
	    	    	String query = "INSERT INTO account values('" + username + "', '" + password + "', '" + fav_color + "','" +
	    	    			age + "', '" + email + "', '" + phone_num + "')";
	    	    	java.sql.Statement state = connection.createStatement();
	    	    	int ex = state.executeUpdate(query);
	    	    	if (ex == 0) {
	    	    		message.setText("A user with this username already exists.");
	    	    		System.out.println("User attempted creation of already exisiting user.");
	    	    	} else {
	    	    		User user = new User(username, password, fav_color, age, email, phone_num);
	    	    		System.out.println(String.format("User %s successfully created.", username));
	    	    		dispose(); // terminate login page
	        	    	HomePage page = new HomePage(user); // bring user to their home page
	        	    	page.setVisible(true);
	    	    	}
	    	    	connection.close();
	    	    } catch (Exception e) {
	    	    	e.printStackTrace();
	    	    }
	    	}    
	    });
	    c.add(sub);
	    // User input feedback
	    message = new JLabel("");
	    message.setFont(new Font("Arial", Font.PLAIN, 20));
	    message.setSize(800, 30);
	    message.setLocation(350, 450);
	    c.add(message);   
	}
	
	// method to ensure age string user entered is an integer
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public void defaultSettings() { // interface implementation
		setSize(400,200);
		setBounds(300, 90, 900, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	   
	public void pageTitle() {
		setTitle("Please Create a New User!");
	}
}
