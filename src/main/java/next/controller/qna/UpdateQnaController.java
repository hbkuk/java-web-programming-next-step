package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.QnaDao;
import next.model.Qna;
import next.model.User;

public class UpdateQnaController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(UpdateQnaController.class);
	
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug( req.getParameter("questionId") );
		logger.debug( req.getParameter("title") );
		logger.debug( req.getParameter("contents") );
		
		QnaDao qnaDao = new QnaDao();
		Qna qna = qnaDao.select(req.getParameter("questionId"));
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		qna.update(
				Long.parseLong(req.getParameter("questionId")),
				user.getUserId(), 
				req.getParameter("title"), 
				req.getParameter("contents"));
		
		qnaDao.update(qna);
		
		return new JspView("redirect:/");
	}
}
