package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdateQuestionController extends AbstractController{
	private QuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!UserSessionUtils.isLogined(request.getSession())) {
			throw new IllegalAccessError("로그인 정보가 없습니다.");
		}
		
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		
		Question question = jdbcQuestionDao.findById(questionId);
		if( !question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
			throw new IllegalAccessError("다른 사용자의 글을 수정할 수 없습니다."); 
		}
		
        Question newQuestion = new Question(question.getWriter(), request.getParameter("title"),
        		request.getParameter("contents"));
		
		question.update(newQuestion);
		jdbcQuestionDao.update(question);
		
		return jspView("redirect:/");
	}
}
