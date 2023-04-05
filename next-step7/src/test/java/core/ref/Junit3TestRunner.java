package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] method = clazz.getDeclaredMethods();
        for (int i = 0; i < method.length; i++) {
        	if( method[i].getName().startsWith("test") ) {
        		method[i].invoke( clazz.newInstance() );
        	}
		}

    }
}
