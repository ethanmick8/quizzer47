package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Interface that all "pages" in the application must implement; defines methods for setting
// up pages and includes static methods that decrease the amount of code repetition in each JFrame
// class.
public interface PageInterface {
	public void defaultSettings(); // allows implementing class to specify basic settings
	public void pageTitle(); // set title
	// static page launch configuration
	public static void runPage(JFrame page) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					page.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// static color changer
	public static void setBackgroundColor(JPanel panel, User user) { 
		Color color;
		try {
		    java.lang.reflect.Field field = Color.class.getField(user.getFavoriteColor());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		if (color != null) {
			panel.setBackground(color);
		} else {
			panel.setBackground(Color.orange);
		}
	}
	// static method that is used to set up a GridBagLayout() JPanel for a JFrame page, which
	// is the most frequently used layout in the application
	public static Insets setPanelLayout(JPanel panel, User user, int inset) {
		setBackgroundColor(panel, user);
		Insets i = new Insets(inset, inset, inset, inset);
		return i;
	}
	// static
	public static void positionGridItem(JPanel panel, Component item, Font font, Insets i, GridBagConstraints con, int x, int y, boolean last) {
		item.setFont(font);
		con.gridx = x;
		con.gridy = y;
		con.insets = i;
		if (last) { // item is to be the last in row
			con.gridwidth = GridBagConstraints.REMAINDER;
		}
		panel.add(item, con);
	}
}
