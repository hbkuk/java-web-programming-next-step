package next.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.AnswerTest;
import next.model.Question;
import next.model.QuestionTest;
import next.model.UserTest;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
	
	@Mock
	private QuestionDao questionDao;
	@Mock
	private AnswerDao answerDao;
	
	private QnaService qnaService;
	
	@Before
	public void setUp() {
		qnaService = new QnaService(questionDao, answerDao); 
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_null() throws Exception {
		when(questionDao.findById(1L)).thenReturn(null);
		
		qnaService.deleteQuestion(1L, UserTest.newUser("javaman"));
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_anotherWriter() throws CannotDeleteException {
		Question question = QuestionTest.newQuestion("javaman");
		when(questionDao.findById(1L)).thenReturn(question);
		
		qnaService.deleteQuestion(1L, UserTest.newUser("javagirl"));
	}
	
	@Test
	public void deleteQuestion_existsAnotherWriterAnswer() throws Exception{
		Question question = QuestionTest.newQuestion("javaman");
		when(questionDao.findById(1L)).thenReturn(question);
		when(answerDao.findAllByQuestionId(question.getQuestionId())).thenReturn(Lists.newArrayList());
		
		qnaService.deleteQuestion(1L, UserTest.newUser("javaman"));
	}
}
