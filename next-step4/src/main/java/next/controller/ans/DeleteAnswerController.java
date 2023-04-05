
package next.controller.ans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.controller.qna.ShowController;
import next.dao.AnsDao;
import next.model.Ans;
import next.model.Result;

public class DeleteAnswerController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnsDao answerDao = new AnsDao();
        Ans deleteAnswer = answerDao.select(Long.parseLong(req.getParameter("answerId")));
        answerDao.delete(deleteAnswer);
        req.setAttribute("status", Result.ok());
        
        return new ModelAndView( new JsonView() );
	}

}
