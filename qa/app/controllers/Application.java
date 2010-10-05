package controllers;

import java.util.List;

import models.Question;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        Question frontPost = Question.find("order by timestamp desc").first();
        List<Question> olderPosts = Question.find("order by timestamp desc").from(1).fetch(10);
        System.out.println(olderPosts.size());
        render(frontPost, olderPosts);
    }

}