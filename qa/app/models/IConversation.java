package models;

import javax.persistence.Entity;

/**
 * Implemented by Question and Answer.
 * @author 	Sebastian Wolfinger 05-922-059 <br>
 * 			Simon Schick 07-911-639
 * @version ESE warming-up 09/28/10
 */

public interface IConversation {

	public abstract void incPositive();

	public abstract void incNegative();

	public abstract void deleteVote(Turnout t);
	
	public abstract int getNegativeVote();
	
	public abstract int getPositiveVote();
}