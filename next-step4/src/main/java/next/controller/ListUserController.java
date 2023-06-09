package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.UserDao;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
        	return new ModelAndView( new JspView("redirect:/users/loginForm"));
        }

        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAll());
        
        return new ModelAndView( new JspView("/user/list.jsp"));
    }
}
