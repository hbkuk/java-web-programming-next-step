package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.controller.UserSessionUtils;
import next.dao.QnaDao;
import next.model.Qna;
import next.model.User;

public class UpdateQnaFormController implements Controller{
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug("questionId : {} ", req.getParameter("questionId"));
		QnaDao qnaDao = new QnaDao();
		Qna qna = qnaDao.select( req.getParameter("questionId") );
		if( qna == null ) {
			throw new IllegalStateException("존재하지 않는 글입니다.");
		}
		String originalWriter = qna.getWriter();
		HttpSession session = req.getSession();
		if( !UserSessionUtils.isLogined(session) ) {
			return new ModelAndView( new JspView("redirect:/users/loginForm") );
		}
		User user = (User) session.getAttribute("user");
		if( !originalWriter.equals(user.getUserId()) ) {
			throw new IllegalStateException("다른 사용자의 글을 수정할 수 없습니다.");
		}
		req.setAttribute("question", qna);
		return new ModelAndView( new JspView("/qna/updateForm.jsp") );
	}

}
