package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {
	
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	
	private RequestLine requestLine;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> params = new HashMap<String, String>();
	
	public HttpRequest(InputStream in) {
        try {
        	BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        	String line = br.readLine();
        	log.debug(line);
        	if( line == null ) {
        		return;
        	}
        	
        	log.debug("request Line: {}", line);
        	requestLine = new RequestLine(line);
        	
        	line = br.readLine();
        	
        	while( !"".equals(line) && line != null  ) {
        		log.debug("Header {}", line);
        		String[] tokens = line.split(": ");
        		headers.put( tokens[0].trim(), tokens[1].trim() );
        		line = br.readLine();
        	}
        	
        	if( getMethod().equals("POST") ) {
        		String body = IOUtils.readData(br, Integer.parseInt( headers.get("Content-Length") ) );
        		params = HttpRequestUtils.parseQueryString(body);
        	} else {
        		params = requestLine.getParams();
        	}

        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	
 	public String getMethod() {
		return requestLine.method;
	}
	
	public String getPath() {
		return requestLine.path;
	}
	
	public String getParameter(String key) {
		return this.params.get(key) ;
	}
	
	public String getHeader(String key) {
		return this.headers.get(key) ;
	}
}