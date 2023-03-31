package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.exception.CannotDeleteException;
import next.model.Result;
import next.service.QnaService;

public class ApiDeleteQuestionController extends AbstractController {
	private QnaService qnaService = QnaService.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		if( !UserSessionUtils.isLogined(request.getSession()) ) {
			return jsonView().addObject("result", Result.fail("login is required"));
		}
		try {
			qnaService.deleteQuestion(request);
			return jsonView().addObject("result", Result.ok());
		} catch (CannotDeleteException e) {
			return jsonView().addObject("result", Result.fail(e.getMessage()));
		}
	}
}
