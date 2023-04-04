package core.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.ReflectionUtils;

import core.nmvc.HandlerExecution;
import core.nmvc.HandlerKey;

public class AnnotataionHandlerMapping {
	public static void reset() {
		ControllerScanner controllerScanner = new ControllerScanner();
		
		Map<Class<?>, Object> map = controllerScanner.scan();
		
        Set<Class<?>> keySet = map.keySet();
        Set<Method> set = null;
        for (Class<?> clazz : keySet) {
        	set = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
        }
        
        Map<HandlerKey, HandlerExecution> mappingMap = new HashMap<>();
	}
}
