package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.AnsDao;
import next.dao.QnaDao;
import next.model.Ans;
import next.model.Qna;

public class ShowController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug("questionId : {} ", req.getParameter("questionId"));
		
		QnaDao qnaDao = new QnaDao();
		Qna qna = qnaDao.select(req.getParameter("questionId"));
		req.setAttribute("question", qna);
		
		AnsDao ansDao = new AnsDao();
		List<Ans> answers = ansDao.selectAll(qna.getQuestionId());
		req.setAttribute("answers", answers);
		
		return "/qna/show.jsp";
	}

	
}
