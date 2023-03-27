package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.controller.UserSessionUtils;

public class QnaFormController implements Controller {
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }
		
		return "/qna/form.jsp";
	}
	
	
}
