package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.QnaDao;
import next.model.Qna;
import next.model.User;

public class DeleteQnaController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(DeleteQnaController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug("questionId : {}", req.getParameter( "questionId") );
		
		QnaDao qnaDao = new QnaDao();
		Qna qna =qnaDao.select( req.getParameter( "questionId") );
		
		if( qna == null ) {
			throw new IllegalStateException("존재하지 않는 글입니다.");
		}
		
		String originalWriter = qna.getWriter();
		logger.debug("originalWriter : {}", originalWriter);
		
		HttpSession session = req.getSession();
		
		if( !UserSessionUtils.isLogined(session) ) {
			return "redirect:/users/loginForm";
		}
		
		User user = (User) session.getAttribute("user");
		logger.debug("userName : {}", user.getUserId());
		
		if( !originalWriter.equals(user.getUserId()) ) {
			throw new IllegalStateException("다른 사용자의 글을 삭제할 수 없습니다.");
		}
		
		qnaDao.delete(qna);
		
		return "redirect:/";
	}

}
