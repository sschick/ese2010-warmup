import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.*;

import java.util.*;

import javax.persistence.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	Question bobQuestion;
	Answer georgeAnswer, charlesAnswer;
	User bob, george, charles;
	
	@Before
	public void setUp() {
		Fixtures.deleteAll();
		bob = new User("bob@gmail.com", "secret", "Bob").save();
		george = new User("george@gmail.com", "asdfgh", "george").save();
		charles = new User("charles@gmail.com", "mymom", "charles").save();
		bobQuestion = bob.ask("Whazup?");
		georgeAnswer = george.answer("Nice post", bobQuestion);
		charlesAnswer = charles.answer("Yeah", bobQuestion);
	}
	
	@Test
	public void createAndRetrieveUser() {
	    User x = User.find("byEmail", "bob@gmail.com").first();
	    assertNotNull(x);
	    assertEquals("Bob", x.fullname);
	}
	
	@Test
	public void tryConnectAsUser() {
	    assertNotNull(User.connect("bob@gmail.com", "secret"));
	    assertNull(User.connect("bob@gmail.com", "badpassword"));
	    assertNull(User.connect("tom@gmail.com", "secret"));
	}
	@Test
	public void createQuestion() {
	    // Test that the question has been created
	    assertEquals(1, Question.count());    
	    // Retrieve all questions created by Bob
	    List<Question> bobPosts = Question.find("byOwner", bob).fetch();
	    assertEquals(1, bobPosts.size());
	    
	    Question firstPost = bobPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals(bob, firstPost.owner);
	    assertEquals("Whazup?", firstPost.toString());
	    assertNotNull(firstPost.timestamp);
	}
	
	@Test
	public void answerQuestion() {
	    List<Answer> bobQuestionAnswers = Answer.find("byQuestion", bobQuestion).fetch();
	    assertEquals(2,bobQuestion.answers.size());
	    assertEquals(2, bobQuestionAnswers.size());
	    // assertEquals(2, bob.getAnswers().size());
	   
	    Answer firstComment = bobQuestionAnswers.get(0);
	    assertNotNull(firstComment);
	    assertEquals(george, firstComment.owner);
	    assertEquals("Nice post", firstComment.content);
	    assertNotNull(firstComment.timestamp);
	 
	    Answer secondComment = bobQuestionAnswers.get(1);
	    assertNotNull(secondComment);
	    assertEquals(charles, secondComment.owner);
	    assertEquals("Yeah", secondComment.content);
	    assertNotNull(secondComment.timestamp);
	}
	
	@Test
	public void useTheCommentsRelation() {
	    // Count things
	    assertEquals(3, User.count());
	    assertEquals(1, Question.count());
	    assertEquals(2, Answer.count());
	 
	    // Retrieve Bob's post
	    Question bobPost = Question.find("byOwner", bob).first();
	    assertNotNull(bobPost);
	 
	    // Navigate to comments
	    assertEquals(2, bobPost.answers.size());
	    assertEquals(george, bobPost.answers.get(0).owner);
	    
	    // Delete the post
	    bobPost.delete();
	    
	    // Check that all comments have been deleted
	    assertEquals(3, User.count());
	    assertEquals(0, Question.count());
	    assertEquals(0, Answer.count());
	}
	
	@Test
	public void fullTest() {
		Fixtures.deleteAll();
	    Fixtures.load("TestData.yml");
	 
	    // Count things
	    assertEquals(3, User.count());
	    assertEquals(2, Question.count());
	    assertEquals(2, Answer.count());
	 
	    // Try to connect as users
	    assertNotNull(User.connect("bob@gmail.com", "secret"));
	    assertNotNull(User.connect("charles@gmail.com", "mymom"));
	    assertNotNull(User.connect("george@gmail.com", "asdfgh"));
	    
	    assertNull(User.connect("bob@gmail.com", "badpassword"));
	    assertNull(User.connect("tom@gmail.com", "secret"));
	 
	    // Find all of Bob's posts
	    List<Question> bobQuestions = Question.find("owner.email", "bob@gmail.com").fetch();
	    assertEquals(2, bobQuestions.size());
	 
	    // Find all comments related to Bob's posts
	    List<Answer> bobComments = Answer.find("question.owner.email", "bob@gmail.com").fetch();
	    assertEquals(2, bobComments.size());
	 
	    // Tests voting
	    IConversation bobQuestion = Question.find("owner.email", "bob@gmail.com").first();
	    User bob = User.find("byFullname", "Bob").first();
	    //bob.voteUp(bobQuestion);
	    assertNotNull(bobQuestion);
	    
	    //assertEquals(1, bob.getQuestions().size());
	    //assertEquals(1, bobQuestion.getNegativeVote());
	}


}
