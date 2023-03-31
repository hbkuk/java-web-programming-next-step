 package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

public class DeleteQuestionController extends AbstractController {
	QuestionDao questionDao = new QuestionDao();
	AnswerDao answerDao = new AnswerDao();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if( !UserSessionUtils.isLogined(request.getSession()) ) {
			return jspView("redirect:/");
		}
		
		Long questionId = Long.parseLong( request.getParameter("questionId") );
		Question question = questionDao.findById(questionId);
		if( question == null ) {
			throw new IllegalAccessException("존재하지 않는 글입니다.");
		}
		
		User user = UserSessionUtils.getUserFromSession(request.getSession());
		if( !question.isSameUser(user) ) {
			return jspView("/qna/show.jsp")
					.addObject("question", question)
					.addObject("answers", answerDao.findAllByQuestionId(questionId))
					.addObject("errorMessage", "다른 사용자가 작성한 글입니다.");
		}
		List<Answer> answers = answerDao.findAllByQuestionId(question.getQuestionId());
		
		if( answers == null ) {
			questionDao.delete(questionId);
			answerDao.deleteAll(questionId);
			return jspView("redirect:/");
		}
		
		boolean canDelete = true;
		String originalWriter = question.getWriter();
		for( Answer answer : answers ) {
			if( !originalWriter.equals(answer.getWriter())) {
				canDelete = false;
				break;
			}
		}
		
		if( canDelete ) {
			questionDao.delete(questionId);
			answerDao.deleteAll(questionId);
			return jspView("redirect:/");
		}
		
		return jspView("/qna/show.jsp")
				.addObject("question", question)
				.addObject("answers", answerDao.findAllByQuestionId(questionId))
				.addObject("errorMessage", "다른 사용자가 작성한 댓글이 있어 삭제할 수 없습니다.");
	}
}
