package next.controller.ans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnsDao;
import next.model.Ans;
import next.model.User;

public class addAnswerController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(addAnswerController.class);
	
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	
    	HttpSession session = req.getSession();
    	User user = (User) session.getAttribute("user");
    	
        Ans answer = new Ans(user.getUserId(), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        logger.debug("answer : {}", answer);
        
        AnsDao answerDao = new AnsDao();
        Ans savedAnswer = answerDao.insert(answer);
        
        return new ModelAndView(new JsonView()).addModel("answer", savedAnswer);
    }

}
