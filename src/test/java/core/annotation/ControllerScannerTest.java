package core.annotation;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.MyController;

public class ControllerScannerTest {
	private static final Logger log = LoggerFactory.getLogger(MyController.class);
	
	private ControllerScanner cs;
	
	@Before
	public void setUp() {
		cs = new ControllerScanner("core.nmvc");
	}
	
	@Test
	public void getControllers() {
		Map<Class<?>, Object> controllers = cs.getControllers();
		for( Class<?> controller : controllers.keySet() ) {
			log.debug( "controller : {} ", controller );
		}
			
	}
}
