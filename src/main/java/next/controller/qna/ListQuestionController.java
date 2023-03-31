package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

public class ListQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao(); 
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return jsonView().addObject("questions", questionDao.findAll());
	}

}
