package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet  {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RequestDispatcher rd = req.getRequestDispatcher("/user/login.jsp");
    	rd.forward(req, resp);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = DataBase.findUserById(req.getParameter("userId"));
    	log.debug("user : {}", user);
    	
    	if( user == null ) {
    		HttpSession session = req.getSession();
    		session.setAttribute("logined", false);
    		resp.sendRedirect("/user/login_failed.jsp");
    	}
    	
    	if( user.getPassword().equals(req.getParameter("password"))) {
    		HttpSession session = req.getSession();
    		session.setAttribute("logined", true);
    		session.setAttribute("user", DataBase.findUserById(req.getParameter("userId")));
    		resp.sendRedirect("/");
    	} else {
    		HttpSession session = req.getSession();
    		session.setAttribute("logined", false);
    		resp.sendRedirect("/user/login_failed.jsp");
    	}
	}
}
