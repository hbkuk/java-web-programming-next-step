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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        	
        	int contentLength = 0;
        	while( (line = br.readLine()) != null ) {
        		log.debug("Request Header : {}", line);
        		
        		if( line.contains("Content-Length")) {
        			contentLength = Integer.parseInt( line.split(": ")[1] );
        		}
        		
        		if( line.equals("")) {
        			break;
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
        		
        		log.debug("User Model : {}", user.toString());
        		
        		requestURI = "/index.html";
        		
                DataOutputStream dos = new DataOutputStream(out);
                
                byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestURI));
                
                response302Header(dos, requestURI);
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
