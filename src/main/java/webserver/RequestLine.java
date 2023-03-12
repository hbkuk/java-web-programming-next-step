package webserver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;

public class RequestLine {
	
	private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
	
	public String method;
	public String path;
	private Map<String, String> params = new HashMap<String, String>();
	
	public RequestLine( String requestLine ) {
		
    	String[] tokens = requestLine.split(" ");
    	this.method = tokens[0];
    	
    	if( tokens.length != 3 ) {
    		throw new IllegalAccessError( requestLine + "이 형식에 맞지 않습니다." );
    	}
    	
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
	
	public Map<String, String> getParams() {
		return params;
	}

}