package core.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class ControllerScanner {
	public Map<Class<?>, Object> scan(){
		String pakageName = "next.controller";
		Reflections reflections = new Reflections(pakageName);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		
		Iterator<Class<?>> it = annotated.iterator(); // 반복자 생성
		
		Map<Class<?>, Object> map = new HashMap<>();
		while (it.hasNext()) {
			Class<?> clazz = it.next();
			try {
				Constructor<?> constructor = clazz.getConstructor();
				map.put(clazz, constructor.newInstance() );
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
