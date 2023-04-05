package webserver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class RequestHandlerTest {
	private String testDirectory = "./src/test/java/resources/";
	

	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
		
		HttpRequest request = new HttpRequest(in);
		assertEquals("GET", request.getMethod() );
		assertEquals( "/user/create", request.getPath() );
		assertEquals( "localhost:8080", request.getHeader("Host") );
		assertEquals( "javajigi", request.getParameter("userId") );
		assertEquals( "JaeSung", request.getParameter("name") );
	}
	
	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
		
		HttpRequest request = new HttpRequest(in);
		assertEquals("POST", request.getMethod() );
		assertEquals( "/user/create", request.getPath() );
		assertEquals( "localhost:8080", request.getHeader("Host") );
		assertEquals( "javajigi", request.getParameter("userId") );
		assertEquals( "JaeSung", request.getParameter("name") );
	}

}
