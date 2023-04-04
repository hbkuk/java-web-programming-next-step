package core.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.MyController;

public class ControllerScanner {
	private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
	private Reflections reflections;
	
	public ControllerScanner( Object...basePackage) {
		reflections = new Reflections(basePackage);
	}
	
	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> preInitiatedControllers = 
				reflections.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(preInitiatedControllers);
	}
	
	public Map<Class<?>, Object> instantiateControllers( Set<Class<?>> preInitiatedControllers ) {
		Map<Class<?>, Object> controllers = new HashMap<>();
		for( Class<?> clazz : preInitiatedControllers ) {
			try {
				Constructor<?> constructor = clazz.getConstructor();
				controllers.put(clazz, constructor.newInstance() );
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				log.error( e.getMessage() );
			}
		}
		return controllers;
	}
}
