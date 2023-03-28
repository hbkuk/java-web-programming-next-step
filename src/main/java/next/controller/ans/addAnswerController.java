package next.controller.ans;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.AnsDao;
import next.model.Ans;
import next.model.User;

public class addAnswerController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(addAnswerController.class);
	
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }
        
    	HttpSession session = req.getSession();
    	User user = (User) session.getAttribute("user");
    	
        Ans answer = new Ans(user.getUserId(), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        
        logger.debug("answer : {}", answer);

        AnsDao answerDao = new AnsDao();
        Ans savedAnswer = answerDao.insert(answer);
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(savedAnswer));
        return null;
    }

}
