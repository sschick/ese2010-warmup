package models;

import javax.persistence.*;
import play.db.jpa.*;

/**
 * A vote knows his sign (see enum positive/negative) and to which question or answer it belongs.
 * @author 	Sebastian Wolfinger 05-922-059 <br>
 * 			Simon Schick 07-911-639 
 * @version ESE warming-up 09/28/10
 */

//@Entity
public class Vote extends Model {
	
	public Turnout sign;
	//@ManyToOne
	public User owner;
	//@ManyToOne
	public IConversation item;
	private long timestamp;
	
	public Vote(Turnout t, User u, IConversation item) {
		sign = t;
		owner = u;
		this.item = item;
		timestamp = System.currentTimeMillis();
		setToItem();
	}
	
	private void setToItem() {
		if (sign.equals(Turnout.positive))
			this.item.incPositive();
		else this.item.incNegative();
	}

	public IConversation getItem() {
		return item;
	}

	public Turnout getSign() {
		return sign;
	}
	
}
