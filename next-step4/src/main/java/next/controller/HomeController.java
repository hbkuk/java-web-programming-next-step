package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.QnaDao;
import next.dao.UserDao;

public class HomeController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QnaDao qnaDao = new QnaDao();
        req.setAttribute("questions", qnaDao.selectAll());
        
        return new ModelAndView(new JspView("home.jsp"));
    }
}
