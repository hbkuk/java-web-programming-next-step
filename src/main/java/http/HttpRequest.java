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
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> parameters = new HashMap<>();
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
    		headers.put(header[0], header[1]);
    	}
    	
    	if( this.method.equals("GET")) {
    		int index = tokens[1].indexOf("\\?");
    		
    		if( index == -1 ) {
    			this.path = tokens[1];
    		} else {
        		String[] url = tokens[1].split("\\?");
        		this.path = url[0];
        		parameters = HttpRequestUtils.parseQueryString(url[1]);
    		}
    	} else if(this.method.equals("POST")) {
    		this.path = tokens[1];
            String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            parameters = HttpRequestUtils.parseQueryString(requestBody);
    	}
	}
	
	public String getMethod() {
		return method;
	}


	public String getPath() {
		return path;
	}
	
	public String getHeader(String key) {
		return headers.get(key);
	}
	
	public String getParameter(String key) {
		return parameters.get(key);
	}

}
