package core.ref;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        
        // 1. 필드 출력 
        Field[] fields = clazz.getDeclaredFields();
        for( Field field : fields ) {
        	System.out.println( field );
        }
        
        // 2. 생성자 출력
        Constructor<?>[] constructor = clazz.getDeclaredConstructors();
        for (int i = 0; i < constructor.length; i++) {
        	System.out.println( constructor[i] );
		}
        
        // 3. 메서드 출력
        Method[] method = clazz.getDeclaredMethods();
        for (int i = 0; i < method.length; i++) {
			System.out.println( method[i] );
		}
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        
        Constructor<?>[] constructor = clazz.getDeclaredConstructors();
        for (int i = 0; i < constructor.length; i++) {
        	try {
				constructor[i].newInstance("테스터1", "1234", "테스터1", "tester1@tester.com");
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
    }
    
    @Test
    public void privateFieldAccess(){
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        
        Student student = new Student();
        try {
			Field field = clazz.getDeclaredField("name");
			field.setAccessible(true);
			field.set(student, "주한");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
        assertEquals("주한", student.getName());
        
    }
}
