package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        	
        	HttpRequest request = new HttpRequest(in);
        	HttpResponse response = new HttpResponse(out);
        	String url = getDefalutPath(request.getPath());
        	
        	if( url.contains("/user/create")) {
        		User user = new User( 
        			request.getParameter("userId"),
        			request.getParameter("password"),
        			request.getParameter("name"),
        			request.getParameter("email") );
        		log.debug( "User : {}", user );
        		DataBase.addUser(user);
        		response.sendRedirect("/index.html");
        		
        	} else if (url.equals("/user/login")) {
        		User user = DataBase.findUserById( request.getParameter("userId") );
        		if( user == null ) {
        			log.debug( "user Not Found!!" );
        			response.sendRedirect("/user/login_failed.html");
        		} else if ( user.getPassword().equals( request.getParameter("password")) ) {
        			log.debug( "login Success!!" );
        			response.addHeader("Set-Cookie", "logined=true");
        			response.sendRedirect("/index.html");
        		} else {
        			log.debug( "password Mismatch!!" );
        			response.sendRedirect("/user/login_failed.html");
        		}

        	} else {
        		response.forward(url);
        	}
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private String getDefalutPath(String path) {
		if (path.equals("/")) {
			return "/index.html";
		}
		return path;
	}
}
