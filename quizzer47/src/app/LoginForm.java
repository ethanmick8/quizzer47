package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// This function provides a UI that serves as the launch point for the Quizzer47 app.
// It logs users into the app if the input matches a username, password pair in the "account"
// database (or if it matches a test user). If so, the user is brought totheir home page.
public class LoginForm extends JFrame implements PageInterface {
   private static final long serialVersionUID = 1L;
   private Container c;
   private JLabel username, password, message, title;
   private JTextField userName_text;
   private JPasswordField password_text;
   private JButton sub, newuser;
   
   // main
   public static void main(String[] args) {
	   LoginForm lf = new LoginForm();
	   PageInterface.runPage(lf);   
   }
   
   // constructor
   public LoginForm() {
	   // set up Container
	   pageTitle();
	   defaultSettings();
	   c = getContentPane();
	   c.setLayout(null);
	   c.setBackground(Color.orange);
	   // Title label
	   title = new JLabel("Please Login Here!");
	   title.setFont(new Font("Arial", Font.PLAIN, 30));
	   title.setSize(300, 38);
	   title.setLocation(300, 30);
	   c.add(title);
       // Username Label
       username = new JLabel();
       username.setText("Username :");
       username.setFont(new Font("Arial", Font.PLAIN, 20));
       username.setSize(200, 20);
       username.setLocation(200, 100);
       c.add(username);
       // Username text field
       userName_text = new JTextField();
       userName_text.setFont(new Font("Arial", Font.PLAIN, 15));
       userName_text.setSize(250, 20);
       userName_text.setLocation(400, 100);
       c.add(userName_text);
       // Password Label
       password = new JLabel();
       password.setText("Password :");
       password.setFont(new Font("Arial", Font.PLAIN, 20));
       password.setSize(200, 20);
       password.setLocation(200, 150);
       c.add(password);
       // Password text field
       password_text = new JPasswordField();
       password_text.setFont(new Font("Arial", Font.PLAIN, 15));
       password_text.setSize(250, 20);
       password_text.setLocation(400, 150);
       c.add(password_text);
       // Submit Button
       sub = new JButton("SUBMIT");
       sub.setFont(new Font("Arial", Font.PLAIN, 15));
       sub.setSize(100, 20);
       sub.setLocation(280, 200);
       sub.addActionListener(new ActionListener() { // attempt to log user in
    	   @Override
    	   public void actionPerformed(ActionEvent ae) {
    	       String username = userName_text.getText();
    	       String password = password_text.getText();
    	       System.out.println(String.format("%d", User.isLoginTestUser(username, password)));
    	       // before accessing SQL database, check to see if one of the two login test users is being used
    	       // apologies for the clunkiness, just needed a quick way to permit easy testing
    	       if (User.isLoginTestUser(username, password) == 1 || User.isLoginTestUser(username, password) == 2) {
    	    	   if (User.isLoginTestUser(username, password) == 1) { // 1
    	    		   User user = new User(username, password, "red", "16", "r@t", "1111111112");
        	    	   dispose();
        	    	   System.out.println(String.format("Login test user '%s' logging in.", username));
        	    	   HomePage page = new HomePage(user); // bring user to home page
        	    	   page.setVisible(true);
        	    	   return; // exit actionPerformed to avoid sql try-catch below
    	    	   } else { // 2
    	    		   User user = new User(username, password, "blue", "45", "b@t", "5555555555");
    	    		   System.out.println(String.format("Login test user '%s' logging in.", username));
    	    		   dispose();
        	    	   HomePage page = new HomePage(user);
        	    	   page.setVisible(true);
        	    	   return;
    	    	   }
    	       }
    	       try { // execute a select query to see if such a user exists
    	    	   Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:/quizzer47", "root", "password");
    	    	   PreparedStatement state = (PreparedStatement) connection.prepareStatement(
    	    			   "Select username, password from account where username=? and password=?");
    	    	   state.setString(1, username);
    	    	   state.setString(2, password);
    	    	   ResultSet res = state.executeQuery();
    	    	   if (res.next()) {
    	    		   // retrieve the remaining user data from the database
    	    		   PreparedStatement userData = (PreparedStatement) connection.prepareStatement(
    	    				   "Select fav_color, age, email, phone_num from account where username"
    	    				   		+ String.format("= %s", username));
    		    	   ResultSet data = userData.executeQuery();
    		    	   while (data.next()) {
    		    		   String fav_color = data.getString(1);
    		    		   String age = data.getString(2);
    		    		   String email = data.getString(3);
    		    		   String phone_num = data.getString(4);
    		    		   System.out.println("User data successfully retrieved.");
        	    		   User user = new User(username, password, fav_color, age, email, phone_num);
        	    		   System.out.println(String.format("User %s successfully logged in.", username));
        	    		   dispose(); // terminate login page
            	    	   HomePage page = new HomePage(user); // bring user to home page
            	    	   page.setVisible(true);
    		    	   }
    	    	   } else {
    	    		   message.setText("Wrong username and password. Try again.");
    	    		   return; 
    	    	   }
    	    			   
    	       } catch (Exception se) { // exception occuring if database is not hooked up correctly
    	    	   se.printStackTrace();
    	       }
    	   }
       });
       c.add(sub);
       // New User Button
       newuser = new JButton("CREATE NEW USER");
       newuser.setFont(new Font("Arial", Font.PLAIN, 15));
       newuser.setSize(200, 20);
       newuser.setLocation(400, 200);
       newuser.addActionListener(new ActionListener() {
    	   @Override
    	   public void actionPerformed(ActionEvent ae) {
    		   setVisible(false); // leave login page
    		   RegisterForm rf = new RegisterForm(); // navigate user to register page
    		   rf.setVisible(true);
    	   }
       });
       c.add(newuser);
       // User input feedback
       message = new JLabel("");
       message.setFont(new Font("Arial", Font.PLAIN, 20));
       message.setSize(500, 30);
       message.setLocation(200, 250);
       c.add(message);

       setVisible(true);
   }
   
   public void defaultSettings() { // interface implementation
	   setBounds(300, 90, 900, 600);
	   setDefaultCloseOperation(EXIT_ON_CLOSE);
	   setResizable(false);
   }
   
   public void pageTitle() {
	   setTitle("Quizzer47 Login Page");
   }  
}
