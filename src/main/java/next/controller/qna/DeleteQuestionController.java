 package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.exception.CannotDeleteException;
import next.service.QnaService;

public class DeleteQuestionController extends AbstractController {
	private QnaService qnaService = QnaService.getInstance(
			JdbcQuestionDao.getInstance(), JdbcAnswerDao.getInstance());
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();
		AnswerDao jdbcAnswerDao = JdbcAnswerDao.getInstance();
		
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		if( !UserSessionUtils.isLogined(request.getSession()) ) {
			return jspView("redirect:/users/login");
		}
		try {
			qnaService.deleteQuestion(request);
			return jspView("redirect:/");
		} catch (CannotDeleteException e) {
			return jspView("redirect:/qna/show.jsp")
					.addObject("question", jdbcQuestionDao.findById(questionId))
					.addObject("answers", jdbcAnswerDao.findAllByQuestionId(questionId))
					.addObject("errorMessage", e.getMessage());
		}
	}
}
