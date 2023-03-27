package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.QnaDao;
import next.model.Qna;

public class QnaController implements Controller{
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		logger.debug("writer : {} ", req.getParameter("writer"));
		logger.debug("title : {} ", req.getParameter("title"));
		logger.debug("contents : {} ", req.getParameter("contents"));
		
		Qna qna = new Qna(
				req.getParameter("writer"), 
				req.getParameter("title"), 
				req.getParameter("contents") );
		
		QnaDao qnaDao = new QnaDao();
		qnaDao.insert(qna);
		
		return "redirect:/";
	}

}
