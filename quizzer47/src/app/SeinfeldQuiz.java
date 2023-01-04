package app;

import java.util.ArrayList;

// this class extends the abstract quiz class, providing an example for what a quiz object
// would look like for the Quizzer47 application. I was watching Seinfeld when I got to this
// part so I just made it a Seinfeld quiz.
public class SeinfeldQuiz extends Quiz {
	// questions
	private String q1 = "Who of the following co-created Seinfeld?"; 
	private String q2 = "Who played the character of Jerry Seinfeld?";
	private String q3 = "Who played the character of George Costanza?";
	private String q4 = "Who played the character of Elaine Benes?";
	private String q5 = "What was the first name of the character known as 'Kramer'?";
	private String q6 = "In what year did the pilot episode air?";
	private String q7 = "Which main character did not appear in the first episode?";
	private String q8 = "How many seasons of Seinfeld were there?";
	private String q9 = "What is the Soup Nazi known for saying?";
	private String q10 = "On what network did Seinfeld air?";
	// choices
	private String c1 = "Jonathan Wolff,Julia Louis-Dreyfus,Larry David,Tony Soprano";
	private String c2 = "Andy Samberg,Jason Alexander,Morgan Freeman,Jerry Seinfeld";
	private String c3 = "Jason Alexander,Christopher Multisanti,Jerry Seinfeld,Jeremy Renner";
	private String c4 = "Forest Whitaker,Meryl Streep,Drea de Matteo,Julia Louis-Dreyfus";
	private String c5 = "Cosmo,Kenny,Jason,Marty";
	private String c6 = "1989,1990,1991,1776";
	private String c7 = "Jason Alexander,Julia Louis-Dreyfus,Jerry Seinfeld,Michael Richards";
	private String c8 = "1,7,8,9";
	private String c9 = "'I love soup!','Master of my domain','No soup for you!','Yada yada yada'";
	private String c10 = "CBS,AMC,NBC,HBO";
	private String title = "Seinfeld Quiz";
	// answers (no cheating!)
	private ArrayList<String> answers = new ArrayList<String>();

	@Override
	public ArrayList<String> getQuestions() {
		ArrayList<String> questions = new ArrayList<String>();
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		questions.add(q4);
		questions.add(q5);
		questions.add(q6);
		questions.add(q7);
		questions.add(q8);
		questions.add(q9);
		questions.add(q10);
		return questions;
	}

	@Override
	public ArrayList<String> getChoices() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(c1);
		choices.add(c2);
		choices.add(c3);
		choices.add(c4);
		choices.add(c5);
		choices.add(c6);
		choices.add(c7);
		choices.add(c8);
		choices.add(c9);
		choices.add(c10);
		return choices;
	}

	@Override
	public String getQuizName() {
		return title;
	}	
	
	@Override
	public ArrayList<String> getAnswers() {
		//code {"c", "d", "b", "d", "a", "a", "b", "d", "a", "c"};
		answers.add("c");
		answers.add("d");
		answers.add("a");
		answers.add("d");
		answers.add("a");
		answers.add("a");
		answers.add("b");
		answers.add("d");
		answers.add("c");
		answers.add("c");
		return answers;
	}
}
