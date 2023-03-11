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
	
	private String method;
	private String path;
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
        	processRequestLine(line);
        	
        	line = br.readLine();
        	
        	while( !"".equals(line) && line != null  ) {
        		log.debug("Header {}", line);
        		String[] tokens = line.split(": ");
        		headers.put( tokens[0].trim(), tokens[1].trim() );
        		line = br.readLine();
        	}
        	
        	if( method.equals("POST") ) {
        		String body = IOUtils.readData(br, Integer.parseInt( headers.get("Content-Length") ) );
        		params = HttpRequestUtils.parseQueryString(body);
        	}

        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	
	private void processRequestLine( String requestLine ) {
    	String[] tokens = requestLine.split(" ");
    	this.method = tokens[0];
    	
    	if( method.equals("POST")) {
    		path = tokens[1];
    		return;
    	}
    	
    	int index = tokens[1].indexOf("?");
    	if( index == -1 ) {
    		path = tokens[1];
    	} else {
    		path = tokens[1].substring(0, index);
    		params = HttpRequestUtils.parseQueryString( tokens[1].substring(index+1) );
    	}
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getParameter(String key) {
		return this.params.get(key) ;
	}
	
	public String getHeader(String key) {
		return this.headers.get(key) ;
	}
}