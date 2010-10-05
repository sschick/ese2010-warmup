package models;

import javax.persistence.*;
import play.db.jpa.*;

/**
 * This class is the prototype of an answer.
 * It has a content, a owner, a timestamp, a question and some nice variables to manage the votes.
 * @author 	Sebastian Wolfinger 05-922-059 <br>
 * 			Simon Schick 07-911-639 
 * @version ESE warming-up 09/28/10
 */
@Entity
public class Answer extends Model implements IConversation  {
	
	@ManyToOne
	public User owner;
	@OneToOne
	public Question question;
	@Lob
	public String content;
	public long timestamp;
	public int positivevote, negativevote; // votes up and down are separated, so you can easily display both

	public Answer(String s, Question q, User u) {
		content = s;
		owner = u;
		question = q;
		timestamp = System.currentTimeMillis();
		positivevote = 0;
		negativevote = 0;
	}

	public void incPositive() { // counts the positive votes
		this.positivevote++;
	}
	
	public void incNegative() { // counts the negative votes
		this.negativevote++;
	}
	
	public void deleteVote(Turnout t) { // calls a specific vote
		if (t.equals(Turnout.positive)) {
			positivevote--;
		}	
		else negativevote--;
	}
	
	public User getOwner() {
		return this.owner;
	}
	
	public Question getQuestion() {
		return this.question;
	}
	
	public String toString() {
		return content;
	}

	public int getPositiveVote() {
		return positivevote;
	}

	public int getNegativeVote() {
		return negativevote;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
}
