package http;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class HttpRequestTest {
	private String testDirectory = "./src/test/java/";
			
	@Test
	public void Get_HttpRequest() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory
				+"Http_GET.txt"));
		HttpRequest request = new HttpRequest(in);
		assertEquals("GET", request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("javajigi", request.getParameter("userId") );
		assertEquals("localhost:8080", request.getHeader("Host"));
		assertEquals("keep-alive", request.getHeader("Connection"));
	}
	
	@Test
	public void Post_HttpRequest() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory
				+"Http_POST.txt"));
		HttpRequest request = new HttpRequest(in);
		assertEquals("POST", request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("javajigi", request.getParameter("userId") );
		assertEquals("localhost:8080", request.getHeader("Host"));
		assertEquals("keep-alive", request.getHeader("Connection"));
	}
	
}
