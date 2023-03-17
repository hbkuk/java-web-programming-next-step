package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

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
        	
        	BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        	String line = br.readLine();
        	log.debug("Request Line : {}", line);
        	
        	String[] tokens = line.split(" ");
        	
        	boolean logined = false;
        	
        	int contentLength = 0;
        	while( (line = br.readLine()) != null ) {
        		log.debug("Request Header : {}", line);
        		
        		if( line.contains("Content-Length")) {
        			contentLength = getContentLength(line);
        		}
        		
        		if( line.equals("")) {
        			break;
        		}
        		
        		if( line.contains("Cookie")) {
        			logined = isLogin(line);
        		}
        	}
        	String requestURI = tokens[1]; 
        	
        	if( requestURI.contains("/user/create")) {
        	
        		String requestBody = IOUtils.readData(br, contentLength);
        		
        		Map<String, String> parameterMap = HttpRequestUtils.parseQueryString(requestBody);
        		
        		User user = new User(
        				parameterMap.get("userId"),
        				parameterMap.get("password"),
        				parameterMap.get("name"),
        				parameterMap.get("email") );
        		
        		DataBase.addUser(user);
        		
        		log.debug("User Model : {}", user.toString());
        		
        		requestURI = "/index.html";
        		
                DataOutputStream dos = new DataOutputStream(out);
                
                byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestURI));
                
                response302Header(dos, requestURI);
                responseBody(dos, body);
                
        	} else if( requestURI.equals("/user/login")) {
        		String requestBody = IOUtils.readData(br, contentLength);
        		Map<String, String> parameterMap = HttpRequestUtils.parseQueryString(requestBody);
        		
        		User user = DataBase.findUserById( parameterMap.get("userId") );
        		
        		if( user == null ) {
        			responseResource(out, "/user/login_failed.html");
        			return;
        		}
        		
        		if( user.getPassword().equals( parameterMap.get("password") ) ) {
        			DataOutputStream dos = new DataOutputStream(out);
                    response302LoginSuccessHeader(dos);
        		} else {
        			responseResource(out, "/user/login_failed.html");
        		}
        		
        		
        	} else if( requestURI.equals("/user/list")) {
        		
        		if( !logined ) {
        			responseResource(out, "/user/login.html");
        			return;
        		}
        		
        		Collection<User> users = DataBase.findAll();
        		StringBuilder sb = new StringBuilder();
        		sb.append("<table border='1'>");
        		for( User user : users ) {
        			sb.append("<tr>");
        			sb.append("<td> " + user.getUserId() + "</td>");
        			sb.append("<td> " + user.getName() + "</td>");
        			sb.append("<td> " + user.getEmail() + "</td>");
        			sb.append("</tr>");
        		}
        		sb.append("</table>");
                byte[] body = sb.toString().getBytes();
                DataOutputStream dos = new DataOutputStream(out);
                response200Header(dos, body.length);
                responseBody(dos, body);
        		
        	} else if( requestURI.endsWith(".css")) {
                DataOutputStream dos = new DataOutputStream(out);
                
                byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestURI));
                
                response200HeaderWithCss(dos, body.length);
                responseBody(dos, body);
                
        	} else if( requestURI.endsWith(".js")) {
                DataOutputStream dos = new DataOutputStream(out);
                
                byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestURI));
                
                response200HeaderWithScript(dos, body.length);
                responseBody(dos, body);
        	} else {
                DataOutputStream dos = new DataOutputStream(out);
                
                byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestURI));
                
                response200Header(dos, body.length);
                responseBody(dos, body);
        	}
        	
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

	private boolean isLogin(String line) {
		String[] headerTokens = line.split(":");
		Map<String, String> cookies =
				HttpRequestUtils.parseCookies(headerTokens[1].trim());
		String value = cookies.get("logined");
		
		if( value == null ) {
			return false;
		}
		return Boolean.parseBoolean(value);
	}

	private int getContentLength(String line) {
		String[] headerTokens = line.split(":");
		return Integer.parseInt( headerTokens[1].trim() );
	}
    
    private void response302LoginSuccessHeader(DataOutputStream dos) {
        try {
        	dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("Location: /index.html \r\n" );
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void responseResource( OutputStream out, String requestURI ) throws IOException {
    	
    	DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestURI));
        response200Header(dos, body.length);
        responseBody(dos, body);
    	
    }
    
    private void response200HeaderWithCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css,*/*;q=0.1\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void response200HeaderWithScript(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: application/javascript\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void response302Header(DataOutputStream dos, String requestURI) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + requestURI );
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
