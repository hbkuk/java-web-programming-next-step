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

@WebServlet("/user/update")
public class UpdateUserFormServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	log.debug( "userid : {} ", req.getParameter("userId") );
    	
    	User user = DataBase.findUserById(req.getParameter("userId"));
    	
    	if( user == null ) {
    		throw new NullPointerException("사용자를 찾을 수 없습니다.");
    	}
    	
    	HttpSession session = req.getSession();
    	User sessionUser = (User) session.getAttribute("user");
    	
    	if( !user.getUserId().equals(sessionUser.getUserId())) {
    		throw new IllegalStateException( "다른 사용자의 정보를 수정할 수 없습니다." );
    		
    	}
    	
    	req.setAttribute("user", user);
    	RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
    	rd.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	log.debug( "userId : {} , password : {} , name : {} , email : {}  " , 
    			req.getParameter("userId"), 
    			req.getParameter("password"),
    			req.getParameter("name"),
    			req.getParameter("email"));
    	User user = DataBase.findUserById( req.getParameter("userId") );
    	user.updateUser(
    			req.getParameter("userId"), 
    			req.getParameter("password"), 
    			req.getParameter("name"), 
    			req.getParameter("email"));
    	resp.sendRedirect("/user/list");
    }
}
