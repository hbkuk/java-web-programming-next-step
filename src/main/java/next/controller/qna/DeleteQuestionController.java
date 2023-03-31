 package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.CannotDeleteException;
import next.service.QnaService;

public class DeleteQuestionController extends AbstractController {
	private QnaService qnaService = QnaService.getInstance();
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		if( !UserSessionUtils.isLogined(request.getSession()) ) {
			return jspView("redirect:/users/login");
		}
		try {
			qnaService.deleteQuestion(request);
			return jspView("redirect:/");
		} catch (CannotDeleteException e) {
			return jspView("redirect:/qna/show.jsp")
					.addObject("question", questionDao.findById(questionId))
					.addObject("answers", answerDao.findAllByQuestionId(questionId))
					.addObject("errorMessage", e.getMessage());
		}
	}
}
