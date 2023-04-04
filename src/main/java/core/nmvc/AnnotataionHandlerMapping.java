package core.nmvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.ControllerScanner;
import core.annotation.RequestMapping;

public class AnnotataionHandlerMapping {
	private static final Logger log = LoggerFactory.getLogger(AnnotataionHandlerMapping.class);
	
	private Object[] basePackage;
	
	private Map<HandlerKey, HandlerExecution> handlerExecutions =
				new HashMap<>();
	
	public AnnotataionHandlerMapping(Object...basePackage ) {
		this.basePackage = basePackage;
	}
	
	public void initialize() {
		ControllerScanner controllerScanner = new ControllerScanner(basePackage);
		
		Map<Class<?>, Object> controllers = controllerScanner.getControllers();
		
		Set<Method> methods = getRequestMappingMethods( controllers.keySet() );
		
		for( Method method : methods ) {
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			log.debug("register handlerExecution : url is {}, method is {} ", rm.value(), method );
			handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
		}
	}
	
	private HandlerKey createHandlerKey( RequestMapping rm ) {
		return new HandlerKey(rm.value(), rm.method());
	}
	
	public Set<Method> getRequestMappingMethods( Set<Class<?>> controllers ) {
		Set<Method> requestMappingMethods = new HashSet<>();
		for( Class<?> clazz : controllers ) {
			requestMappingMethods.addAll( ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)) );
		}
		return requestMappingMethods; 
	}
	
}
