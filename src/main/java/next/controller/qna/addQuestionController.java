package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class addQuestionController extends AbstractController{
	private static final Logger log = LoggerFactory.getLogger(addQuestionController.class);
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		User user = UserSessionUtils.getUserFromSession(request.getSession());
		Question question = new Question(
						user.getUserId(), 
						request.getParameter("title"), 
						request.getParameter("contents"));
		questionDao.insert(question);
		return jspView("redirect:/");
	}
	
}
