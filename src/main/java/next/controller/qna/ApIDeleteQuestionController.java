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
	Question question;
	Answer answer;
	User user;
	HttpSession session;
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		session = request.getSession();
		user = UserSessionUtils.getUserFromSession(session);
		if( user == null ) {
			throw new IllegalAccessError("로그인 정보가 없습니다.");
		}
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		question = questionDao.findById(questionId);
		if( !user.getUserId().equals(question.getWriter())) {
			throw new IllegalAccessError("다른 사용자의 글을 삭제할 수 없습니다.");
		}
		List<Answer> answers = answerDao.findAllByQuestionId(question.getQuestionId());
		for( Answer answer : answers ) {
			if( !user.getUserId().equals(answer.getWriter()) ) {
				throw new IllegalAccessError("다른 사용자의 답변이 있습니다.");
			}
		}
		
        ModelAndView mav = jsonView();
        try {
    		questionDao.delete(questionId);
    		answerDao.deleteAll(questionId);
            mav.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
		
		
	}
}
