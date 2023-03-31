package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class UpdateQuestionFormController extends AbstractController {
	QuestionDao questionDao = new QuestionDao();
	Question question;
	HttpSession session;
	User user;
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		session = request.getSession();
		if(!UserSessionUtils.isLogined(session)) {
			throw new IllegalAccessError("로그인 정보가 없습니다.");
		}
		
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		
		question = questionDao.findById(questionId);
		user = UserSessionUtils.getUserFromSession(session);
		
		if( !question.getWriter().equals(user.getUserId())) {
			throw new IllegalAccessError("다른 사용자의 글을 수정할 수 없습니다."); 
		}
		
		ModelAndView mav = jspView("/qna/updateForm.jsp");
		mav.addObject("question", question);
		return mav;
	}

}
