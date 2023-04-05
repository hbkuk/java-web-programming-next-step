package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {
	public static Map<String, HttpSession> sessions = new HashMap<>();
	
	public static HttpSession getSession(String id) {
		HttpSession session = sessions.get(id);
		
		if( session == null ) {
			session = new HttpSession(id);
			sessions.put(id, session);
			return session;
		}
		return session;
	}
	
	public static void removeSession( String id ) {
		sessions.remove(id);
	}
}
