package next.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import next.CannotDeleteException;

public class QuestionTest {
	public static Question newQuestion( String writer ) {
		return new Question(1L, writer, "title", "contents", new Date(), 0);
	}
	
	@Test(expected = CannotDeleteException.class)
	public void 글쓴이가_다르다() throws CannotDeleteException {
		User user = UserTest.newUser("javaman");
		Question question = QuestionTest.newQuestion("javagirl");
		question.canDelete(user, new ArrayList<>());
	}
	
	@Test
	public void 글쓴이_같음() throws CannotDeleteException {
		User user = UserTest.newUser("javaman");
		Question question = QuestionTest.newQuestion("javaman");
		question.canDelete(user, new ArrayList<>());
	}
	
	@Test
	public void 같은사용자_댓글존재() throws CannotDeleteException {
		String wrtier1 = "javaman";
		User user = UserTest.newUser(wrtier1);
		Question question = QuestionTest.newQuestion(wrtier1);
		List<Answer> answers = Arrays.asList(AnswerTest.newAnswer(wrtier1));
		question.canDelete(user, answers);
	}
	
	@Test(expected = CannotDeleteException.class)
	public void 다른사용자_댓글존재() throws CannotDeleteException {
		String wrtier1 = "javaman";
		String wrtier2 = "javagirl";
		User user = UserTest.newUser(wrtier1);
		Question question = QuestionTest.newQuestion(wrtier1);
		List<Answer> answers = Arrays.asList(AnswerTest.newAnswer(wrtier2));
		question.canDelete(user, answers);
	}
}
