package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    private List<HandlerMapping> mappings = Lists.newArrayList();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    
    @Override
    public void init() throws ServletException {
    	LegacyHandlerMapping lm = new LegacyHandlerMapping();
    	AnnotationHandlerMapping am = new AnnotationHandlerMapping("next.controller");
    	
    	lm.initMapping();
    	am.initialize();
    	
    	handlerAdapters.add( new ControllerHandlerAdapter() );
    	handlerAdapters.add( new HandlerExecutionHandlerAdapter() );
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if( handler == null ) {
        	throw new IllegalArgumentException("존재하지 않는 URL입니다.");
        }
    	try {
			ModelAndView mav = execute(req, resp, handler);
			View view = mav.getView();
			view.render(mav.getModel(), req, resp);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			throw new ServletException(e.getMessage());
		}
    }

	private ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(req, resp, handler);
            }
        }
        return null;
	}

	private Object getHandler(HttpServletRequest req) {
		for( HandlerMapping handlerMapping : mappings ) {
			Object handler = handlerMapping.getHandler(req);
			if( handler != null ) {
				return handler;
			}
		}
		return null;
	}
}
