package models;

import java.util.ArrayList;
import java.util.Iterator;
import javax.persistence.*;
import play.db.jpa.*;


/**
 * This class handels user-specific actions: putting a question, responding to a concrete question and deleting the user.
 * @author 	Sebastian Wolfinger 05-922-059 <br>
 * 			Simon Schick 07-911-639
 * @version ESE warming-up 09/28/10
 */

@Entity
public class User extends Model {

	public String email;
	public String password;
	public String fullname;
	public ArrayList<Question> questions;
	public ArrayList<Answer> answers;
	public ArrayList<Vote> votes;

	public User(String email, String password, String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		questions = new ArrayList<Question>();
		answers = new ArrayList<Answer>();
		votes = new ArrayList<Vote>();
	}

	/**
	 * This method "deletes" the user by setting his question-, answer- and votelist to null. 
	 * The votes will be call. 
	 */
	public void deleteUser() {
		Iterator<Vote> iterator;
		iterator = votes.iterator();
		while(iterator.hasNext()) {
			Vote v = iterator.next();
			v.getItem().deleteVote(v.getSign());
		}
		votes = null;
		questions = null;
		answers = null;
		this.fullname = null;
	}

	public Question ask(String s) {
		Question q = new Question(s, this).save();
		this.questions.add(q);
		this.save();
		return q;
	}

	public Answer answer(String s, Question q) {
		Answer a = new Answer(s, q, this).save();
		this.answers.add(a);
		q.getAnswers().add(a);
		this.save();
		return a;
	}
	
	public void voteUp(IConversation c) {
		Vote vote = new Vote(Turnout.positive, this, c);
		this.votes.add(vote);
		c.incPositive();
	}
	
	public void voteDown(IConversation c) {
		Vote vote = new Vote(Turnout.negative, this, (Answer)c);
		this.votes.add(vote);
		c.incNegative();
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}

	
	public String getName() {
		return fullname;
	}
	
	public Question getQuestion(Question q) {
		int i = questions.indexOf(q);
		return questions.get(i);
	}

	public ArrayList<Question> getQuestions() {
		return this.questions;
	}

	public ArrayList<Answer> getAnswers() {
		return this.answers;
	}

	public ArrayList<Vote> getVotes() {
		return this.votes;
	}
	
}
