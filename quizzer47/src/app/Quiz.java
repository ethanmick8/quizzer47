package app;

import java.util.ArrayList;

// This abstract class provides a framework for quizzes that Quizzer47 uses. They share many qualities,
// so the existence of this class simplies their implementation.
public abstract class Quiz {
	// getters
	public abstract ArrayList<String> getQuestions();
	public abstract ArrayList<String> getChoices();
	public abstract String getQuizName();
	public abstract ArrayList<String> getAnswers();
	// method that determines whether a user passed or failed a quiz
	public static boolean passFail(int score) {
		if (score >= 6) {
			System.out.println(String.format("Passed: Score: %s/10", score));
			return true; // passed
		}
		System.out.println(String.format("Failed: Score: %s/10", score));
		return false; // failed
	}
	// method that scores a quiz and returns an integer value representing the number of correct answers
	public static int scoreQuiz(ArrayList<String> user_choices, ArrayList<String> answers) {
		int res = 0;
		for (int i = 0; i <= 9; i++) {
			if ((user_choices.get(i)).equals(answers.get(i))) { // right answer
				System.out.println("Right: Question: " + i);
				res = res + 1;
			} else {
				System.out.println("Wrong: Question: " + i);
			}
		}
		return res;
	}
}
