package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * This class is the prototype of a question. 
 * It has a content, a owner, a timestamp, a list of answers and some nice variables to manage the votes.
 * @author 	Sebastian Wolfinger 05-922-059 <br>
 * 			Simon Schick 07-911-639
 * @version ESE warming-up 09/28/10
 */

@Entity
public class Question extends Model implements IConversation {
	
	@ManyToOne
	public User owner;
	@Lob
	public String content;
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	public List<Answer> answers;
	public long timestamp;
	public int positivevote, negativevote; // votes up and down are separated, so you can easily display both  
	
	public Question(String s, User u) {
		owner = u;
		content = s;
		answers = new ArrayList<Answer>();
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
	
	public String toString() {
		return content;
	}
	
	public List<Answer> getAnswers() {
		return this.answers;
	}

	public int getNegativeVote() {
		return negativevote;
	}

	public int getPositiveVote() {
		return positivevote;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
}
