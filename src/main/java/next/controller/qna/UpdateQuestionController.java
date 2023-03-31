package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdateQuestionController extends AbstractController{
	QuestionDao questionDao = new QuestionDao();
	Question question;
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		question = questionDao.findById(questionId);
		question.update(
				request.getParameter("title"), 
				request.getParameter("contents"));
		questionDao.update(question);
		
		return jspView("redirect:/");
	}
}
