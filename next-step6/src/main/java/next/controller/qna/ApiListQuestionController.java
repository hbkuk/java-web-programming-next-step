package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;

public class ApiListQuestionController extends AbstractController {
	private QuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return jsonView().addObject("questions", jdbcQuestionDao.findAll());
	}

}
