package next.controller.ans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.AnsDao;
import next.model.Ans;
import next.model.User;

public class CreateAnsController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(CreateAnsController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		logger.debug("questionId : {}", req.getParameter("questionId") );
		logger.debug("contents : {}", req.getParameter("contents") );
		
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }
        
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        Ans ans = new Ans(
        		user.getUserId(),
        		req.getParameter("contents"), 
        		req.getParameter("questionId") );
        
        AnsDao ansDao = new AnsDao();
        ansDao.insert(ans);
        
		return "redirect:/";
	}

}
