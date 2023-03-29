package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.controller.UserSessionUtils;

public class CreateQnaFormController implements Controller {
	
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new JspView("redirect:/users/loginForm");
        }
		
		return new JspView("/qna/form.jsp");
	}
	
	
}
