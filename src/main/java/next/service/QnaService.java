package next.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.DispatcherServlet;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.CannotDeleteException;
import next.model.Answer;
import next.model.Question;
import next.model.User;

public class QnaService {
	private static final Logger logger = LoggerFactory.getLogger(QnaService.class);
	
	private static QnaService qnaService; 
	
	private QuestionDao questionDao;
	private AnswerDao answerDao;
	
	private QnaService( QuestionDao questionDao, AnswerDao answerDao ) {
		this.questionDao = questionDao;
		this.answerDao = answerDao;
	}
	
	public static QnaService getInstance( QuestionDao questionDao, AnswerDao answerDao) {
		if( qnaService == null ) {
			qnaService = new QnaService(questionDao, answerDao);
		}
		return qnaService;
	}

	public void deleteQuestion(HttpServletRequest request) throws CannotDeleteException {
		
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		Question question = questionDao.findById(questionId);
		if( question == null ) {
			logger.debug("situation : {}", "존재하지 않는 글, 삭제 X");
			throw new CannotDeleteException("존재하지 않는 글입니다.");
		}
		
		User user = UserSessionUtils.getUserFromSession(request.getSession());
		if( !question.isSameUser(user) ) {
			logger.debug("situation : {}", "다른 사용자가 작성한 글, 삭제 X");
			throw new CannotDeleteException("다른 사용자가 작성한 글입니다.");
		}
		
		List<Answer> answers = answerDao.findAllByQuestionId(question.getQuestionId());
		if( answers.isEmpty() ) {
			logger.debug("situation : {}", "답변이 없는 글, 삭제 O");
			questionDao.delete(questionId);
			return;
		}
		
		boolean canDelete = true;
		String originalWriter = question.getWriter();
		for( Answer answer : answers ) {
			if( !originalWriter.equals(answer.getWriter())) {
				canDelete = false;
				break;
			}
		}
		
		if( !canDelete ) {
			logger.debug("situation : {}", "다른 사용자가 작성한 댓글이 있는 글, 삭제 X");
			throw new CannotDeleteException("다른 사용자가 작성한 댓글이 있습니다.");
		}
		
		questionDao.delete(questionId);
	}
}
