package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] method = clazz.getDeclaredMethods();
        for (int i = 0; i < method.length; i++) {
			if(method[i].isAnnotationPresent(MyTest.class)) {
				method[i].invoke(clazz.newInstance());
			}
		}

    }
}
