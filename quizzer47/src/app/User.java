package app;

// class that defines a User who will navigate all pages in Quizzer47 except for LoginForm and
// RegisterForm. This implementation exists separately from the database and is used primarily to
// personalize the experience for the user while their are using Quizzer47
public class User {
	private String username;
	private String password;
	private String fav_color;
	private String age;
	private String email;
	private String phone_num;
	
	// constructor
	public User(String un, String pwd, String fav_col, String age, String email, String phone) {
		this.username = un;
		this.password = pwd;
		this.fav_color = fav_col;
		this.age = age;
		this.email = email;
		this.phone_num = phone;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFavoriteColor() {
		return fav_color;
	}
	
	public String getAge() {
		return age;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone_num;
	}
	// method to check if a login test user is being used to access the application
	// returning 1 for first test user, 2 for 2nd, and 0 on fail
	public static int isLoginTestUser(String username, String password) {
		if (username.equals("user") && password.equals("pass")) {
			return 1;
		}
		if (username.equals("123") && password.equals("456")) {
			return 2;
		}
		return 0;
	}
	// same as above method but for register user
	public static boolean isRegisterTestUser(String u, String p, String fc, String age, String em, String pn) {
		if (u.equals("test") && p.equals("pass")) {
			if (fc.equals("green") && age.equals("21")) {
				if (em.equals("c@t") && pn.equals("1234567890")) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s %s %s %s", username, password, fav_color, age, email, phone_num);
	}
}
