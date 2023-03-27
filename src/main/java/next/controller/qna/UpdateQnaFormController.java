package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.QnaDao;
import next.model.Qna;
import next.model.User;

public class UpdateQnaFormController implements Controller{
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug("questionId : {} ", req.getParameter("questionId"));
		
		String questionId = req.getParameter("questionId");
		
		QnaDao qnaDao = new QnaDao();
		Qna qna = qnaDao.select( questionId );
		
		if( qna == null ) {
			throw new IllegalStateException("존재하지 않는 글입니다.");
		}
		
		String originalWriter = qna.getWriter();
		logger.debug("originalWriter : {}", originalWriter);
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		logger.debug("userName : {}", user.getUserId());
		
		if( user == null ) {
			return "redirect:/users/loginForm";
		}
		
		if( !originalWriter.equals(user.getUserId()) ) {
			throw new IllegalStateException("다른 사용자의 글을 수정할 수 없습니다.");
		}
		
		req.setAttribute("question", qna);
		return "/qna/updateForm.jsp";
	}

}
