package http;

import java.io.BufferedReader;
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
	private Map<String, String> httpHeader = new HashMap<>();
	private Map<String, String> httpParameter = new HashMap<>();
	private String method;
	private String path;
	
	public HttpRequest( InputStream in ) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
    	String line = br.readLine();
    	
    	String[] tokens = line.split(" ");
    	this.method = tokens[0];
    	
    	while( (line = br.readLine()) != null ) {
    		
    		if( line.equals("")) {
    			break;
    		}
    		
    		String[] header = line.split(": ");
    		httpHeader.put(header[0], header[1]);
    	}
    	
    	if( this.method.equals("GET")) {
    		String[] url = tokens[1].split("\\?");
    		this.path = url[0];
    		httpParameter = HttpRequestUtils.parseQueryString(url[1]);
    		
    	} else if(this.method.equals("POST")) {
    		this.path = tokens[1];
            String requestBody = IOUtils.readData(br, Integer.parseInt(httpHeader.get("Content-Length")));
            httpParameter = HttpRequestUtils.parseQueryString(requestBody);
    	}
	}
	
	public String getMethod() {
		return method;
	}


	public String getPath() {
		return path;
	}
	
	public String getHeader(String key) {
		return httpHeader.get(key);
	}
	
	public String getParameter(String key) {
		return httpParameter.get(key);
	}

}
