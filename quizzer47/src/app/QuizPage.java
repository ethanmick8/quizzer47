package app;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class QuizPage extends JFrame implements PageInterface {
	private static final long serialVersionUID = 1L;
	private int xx = 0; // iterator
	private String quiz = "Quiz";
	private JPanel panel;
	private JLabel title;
	private JButton seinfeld, other_quiz, back, next;
	private JRadioButton a, b, c, d;
	private GridBagConstraints tc, sc, oc, backc, nc, ac, bc, cc, dc;
	private Font fonta = new Font("Arial", Font.PLAIN, 20);
	private Font head_font = new Font("Arial", Font.PLAIN, 30);
	private ArrayList<String> questions, choices, answers;
	private static final ArrayList<String> user_choices = new ArrayList<String>();
	
	// main
	public static void main(String[] args) {
		QuizPage qp = new QuizPage();
		PageInterface.runPage(qp);
	}
	
	public QuizPage() {
	}
	
	// constructor for the Quiz home page
	public QuizPage(User user) {
		defaultSettings();
		pageTitle();
		panel = new JPanel(new GridBagLayout());
		Insets i = PageInterface.setPanelLayout(panel, user, 5);
		this.add(panel);
		title = new JLabel("Please select a quiz to take:");
		tc = new GridBagConstraints();
		PageInterface.positionGridItem(panel, title, head_font, i, tc, 0, 0, true);
		seinfeld = new JButton("Seinfeld Quiz");
		sc = new GridBagConstraints();
		seinfeld.addActionListener(new ActionListener() { // user wants to take Seinfeld quiz
			public void actionPerformed(ActionEvent ae) {
				dispose();
				SeinfeldQuiz s_quiz = new SeinfeldQuiz();
				QuizPage page = new QuizPage(user, s_quiz, xx);
				page.setVisible(true);
			}
		});
		PageInterface.positionGridItem(panel, seinfeld, fonta, i, sc, 0, 1, false);
		// this next one is just a placeholder for a second option; with the scope of the project I didn't
		// think it was reasonable to create multiple quizzes, but if I did they would simply be implemented 
		// with their own respective buttons in this constructor, such as this one.
		other_quiz = new JButton("Other Quiz");
		oc = new GridBagConstraints();
		other_quiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(other_quiz, "This Quiz is not ready to be taken!");
			}
		});
		PageInterface.positionGridItem(panel, other_quiz, fonta, i, oc, 1, 1, true);
		back = new JButton("Return to Home");
		backc = new GridBagConstraints();
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
 	    	    HomePage page = new HomePage(user); // return
 	    	    page.setVisible(true);
			}
		});
		PageInterface.positionGridItem(panel, back, fonta, i, backc, 0, 2, true);
	}
	
	// constructor for the quiz question page
	public QuizPage(User user, Quiz quiz, int x) {
		defaultSettings();
		pageTitle();
		this.questions = quiz.getQuestions();
		this.choices = quiz.getChoices();
		panel = new JPanel(new GridBagLayout());
		Insets i = PageInterface.setPanelLayout(panel, user, 5);
		this.add(panel);
		title = new JLabel(String.format("Question %d: %s", x+1, questions.get(x)));
		tc = new GridBagConstraints();
		// position buttons and apply logic to ensure that one one choice can be selected
		PageInterface.positionGridItem(panel, title, head_font, i, tc, 0, 0, true);
		ArrayList<String> choice_set = new ArrayList<>(Arrays.asList(choices.get(x).split(",")));
		a = new JRadioButton(String.format("A) %s", choice_set.get(0)));
		ac = new GridBagConstraints();
		a.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (a.isSelected()) {
					b.setEnabled(false);
					c.setEnabled(false);
					d.setEnabled(false);
				} else {
					b.setEnabled(true);
					c.setEnabled(true);
					d.setEnabled(true);
				}
			}
		});
		PageInterface.positionGridItem(panel, a, fonta, i, ac, 0, 1, true);
		b = new JRadioButton(String.format("B) %s", choice_set.get(1)));
		bc = new GridBagConstraints();
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (b.isSelected()) {
					a.setEnabled(false);
					c.setEnabled(false);
					d.setEnabled(false);
				} else {
					a.setEnabled(true);
					c.setEnabled(true);
					d.setEnabled(true);
				}
			}
		});
		PageInterface.positionGridItem(panel, b, fonta, i, bc, 0, 2, true);
		c = new JRadioButton(String.format("C) %s", choice_set.get(2)));
		cc = new GridBagConstraints();
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (c.isSelected()) {
					a.setEnabled(false);
					b.setEnabled(false);
					d.setEnabled(false);
				} else {
					a.setEnabled(true);
					b.setEnabled(true);
					d.setEnabled(true);
				}
			}
		});
		PageInterface.positionGridItem(panel, c, fonta, i, cc, 0, 3, true);
		d = new JRadioButton(String.format("D) %s", choice_set.get(3)));
		dc = new GridBagConstraints();
		d.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (d.isSelected()) {
					a.setEnabled(false);
					b.setEnabled(false);
					c.setEnabled(false);
				} else {
					a.setEnabled(true);
					b.setEnabled(true);
					c.setEnabled(true);
				}
			}
		});
		PageInterface.positionGridItem(panel, d, fonta, i, dc, 0, 4, true);
		// next button that takes user to next question. the last question is an edge case, it scores
		// the quiz, displays the score, and then returns the user to quiz home
		next = new JButton("Next Question");
		nc = new GridBagConstraints();
		this.answers = quiz.getAnswers();
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(a.isSelected() || b.isSelected() || c.isSelected() || d.isSelected()) {
					// now, actually determine which button was pressed so we can update user_choices
					if (a.isSelected()) {
						user_choices.add("a");
					}
					else if (b.isSelected()) {
						user_choices.add("b");
					}
					else if (c.isSelected()) {
						user_choices.add("c");
					}
					else if (d.isSelected()) {
						user_choices.add("d");
					}
					if (x == 9) { // if it's the last question
						int score = Quiz.scoreQuiz(user_choices, answers);
						if (Quiz.passFail(score)) { // user passed quiz
							JOptionPane.showMessageDialog(next, String.format("Passed! Score: %d/10", score));
							dispose();
							QuizPage qp = new QuizPage(user);
							qp.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(next, String.format("Failed! Score: %d/10", score));
							dispose();
							QuizPage qp = new QuizPage(user);
							qp.setVisible(true);
						}
					}
					dispose();
					if (x <= 8) { // only keep going if we're not on the last question
						SeinfeldQuiz s_quiz = new SeinfeldQuiz();
						QuizPage page = new QuizPage(user, s_quiz, x+1);
						page.setVisible(true);
					}
				}
			}
		});
		PageInterface.positionGridItem(panel, next, fonta, i, nc, 0, 5, true);		
	}

	@Override
	public void defaultSettings() {
		setSize(900,600);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void pageTitle() {
		setTitle(String.format("%s", quiz));
	}


}
