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
        	
        	while( (line = br.readLine()) != null ) {
        		if( line.equals("")) {
        			break;
        		}
        	}
        	String[] uri = tokens[1].split("\\?");
        	log.debug("requestURL : {} ",line);
        	String requestPath = uri[0];
        	String parameters = uri[1];
        	
        	Map<String, String> parameterMap = HttpRequestUtils.parseQueryString(parameters);
        	
        	User user = new User( 
        			parameterMap.get("userId"), 
        			parameterMap.get("password"), 
        			parameterMap.get("name"), 
        			parameterMap.get("email") );
        	
        	log.debug("User Model : {}", user.toString());
        	
            DataOutputStream dos = new DataOutputStream(out);
            
            byte[] body = Files.readAllBytes(Paths.get("./webapp" + requestPath));
            
            response200Header(dos, body.length);
            responseBody(dos, body);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
