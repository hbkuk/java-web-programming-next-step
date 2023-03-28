package next.controller.ans;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.controller.qna.ShowController;
import next.dao.AnsDao;
import next.model.Ans;
import next.model.Result;

public class DeleteAnswerController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        AnsDao answerDao = new AnsDao();
        Ans deleteAnswer = answerDao.select(Long.parseLong(req.getParameter("answerId")));
        answerDao.delete(deleteAnswer);
        
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(Result.ok()));
        return null;
	}

}
