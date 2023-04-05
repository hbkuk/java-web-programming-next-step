package core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.HandlerMapping;

public class LegacyHandlerMapping implements HandlerMapping{
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        logger.info("Initialized Request Mapping!");
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

	@Override
	public Controller getHandler(HttpServletRequest request) {
		return mappings.get(request.getRequestURI());
	}
}
