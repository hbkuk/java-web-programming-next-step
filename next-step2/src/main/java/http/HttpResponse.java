package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	private Map<String, String> headers = new HashMap<>();
	DataOutputStream dos = null; 
	
	public HttpResponse(OutputStream out) {
		dos = new DataOutputStream(out);
	}
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}
	
	public void forward(String url) {
		try {
			byte[] body = Files.readAllBytes(Paths.get("./webapp" + url));
			if( url.endsWith(".css") ) {
				headers.put("Content-Type", "text/css,*/*;q=0.1");
			} else if ( url.endsWith(".js") ) {
				headers.put("Content-Type", "application/javascript");
			} else {
				headers.put("Content-Type", "text/html;charset=utf-8");
			}
			headers.put("Content-Length", body.length + "");
			response200Header(dos);
			responseBody(dos, body);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRedirect( String redirectUrl ) {
        try {
        	dos.writeBytes("HTTP/1.1 302 Found \r\n");
        	processHeader();
            dos.writeBytes("Location: " + redirectUrl + " \r\n" );
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	
	private void processHeader() {
        Set<String> keySet = headers.keySet();
        for (String key : keySet) {
        	try {
				dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeader();
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
    
    public void forwardBody(String body) {
    	byte[] contents = body.getBytes();
    	headers.put("Content-Type", "text/html;charset=utf-8");
    	headers.put("Content-Length", contents.length +"");
    	response200Header(dos);
    	responseBody(dos, contents);
    }
}
