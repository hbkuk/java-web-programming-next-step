package http;

import java.util.Map;

import util.HttpRequestUtils;

public class HttpCookie {
	Map<String, String> cookies;
	
	public HttpCookie( String cookieValue ) {
		cookies = HttpRequestUtils.parseCookies(cookieValue);
	}
	
	public String getCookies( String name ) {
		return cookies.get(name);
	}
}
