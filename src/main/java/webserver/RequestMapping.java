package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
	private static Map<String, Controller> controller =
			new HashMap<String, Controller>();
	
	static {
		controller.put( "/user/create", new CreateUserController() );
		controller.put( "/user/login", new LoginController() );
	}
	
	public static Controller getController(String requestUrl) {
		return controller.get(requestUrl);
	}
}

