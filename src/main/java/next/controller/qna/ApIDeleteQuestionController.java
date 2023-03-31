package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;

public class ApIDeleteQuestionController extends AbstractController {
	QuestionDao questionDao = new QuestionDao();
	AnswerDao answerDao = new AnswerDao();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if( !UserSessionUtils.isLogined(request.getSession()) ) {
			return jsonView()
					.addObject("result", Result.fail("login is required"));
		}
		
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		Question question = questionDao.findById(questionId);
		if( question == null ) {
			return jsonView()
					.addObject("result", Result.fail("존재하지 않는 질문입니다."));
		}
		
		User user = UserSessionUtils.getUserFromSession(request.getSession());
		if( !question.isSameUser(user) ) {
			return jsonView()
					.addObject("result", Result.fail("다른 사용자가 쓴 글입니다."));
		}
		List<Answer> answers = answerDao.findAllByQuestionId(question.getQuestionId());
		
		if( answers == null ) {
			questionDao.delete(questionId);
			answerDao.deleteAll(questionId);
			return jsonView()
					.addObject("result", Result.ok());
		}
		
		boolean canDelete = true;
		String originalWriter = question.getWriter();
		for( Answer answer : answers ) {
			if( !originalWriter.equals(answer.getWriter())) {
				canDelete = false;
				break;
			}
		}
		
		if( canDelete ) {
			questionDao.delete(questionId);
			answerDao.deleteAll(questionId);
			return jsonView()
					.addObject("result", Result.ok());
		}
		return jsonView()
				.addObject("result", Result.fail("다른 사용자가 추가한 댓글이 있습니다."));
	}
}
