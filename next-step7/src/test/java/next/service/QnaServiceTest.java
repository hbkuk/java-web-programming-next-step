package next.service;

import org.junit.Before;
import org.junit.Test;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.MockAnswerDao;
import next.dao.MockQuestionDao;
import next.dao.QuestionDao;
import next.model.AnswerTest;
import next.model.QuestionTest;
import next.model.UserTest;

public class QnaServiceTest {
	private QnaService qnaService;
	private QuestionDao questionDao;
	private AnswerDao answerDao;
	
	@Before
	public void setUp() {
		questionDao = new MockQuestionDao();
		answerDao = new MockAnswerDao();
		qnaService = QnaService.getInstance(questionDao, answerDao); 
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_null() throws CannotDeleteException {
		qnaService.deleteQuestion(1L, UserTest.newUser("javaman"));
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_anotherWriter() throws CannotDeleteException {
		questionDao.insert( QuestionTest.newQuestion("javaman") );
		qnaService.deleteQuestion(1L, UserTest.newUser("javagirl"));
	}
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_existsAnotherWriterAnswer() throws CannotDeleteException {
		questionDao.insert( QuestionTest.newQuestion("javaman") );
		answerDao.insert(AnswerTest.newAnswer("javaman"));
		answerDao.insert(AnswerTest.newAnswer("javagirl"));
		qnaService.deleteQuestion(1L, UserTest.newUser("javaman"));
	}
}
