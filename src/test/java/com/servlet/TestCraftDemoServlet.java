package com.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCraftDemoServlet {
	private String expectedJsonResponse = "{\"movies\":[{\"id\":\"Stranger Things\",\"description\":\"When a young boy disappears, his mother, a police chief, and his friends must confront terrifying forces in order to get him back.\",\"category\":[\"Popular\",\"Horor\"]},{\"id\":\"Doctor Strange\",\"description\":\"After a neurosurgeon loses the use of this hangs he meets a mystical mentor who helps him harness magic to become the most powerful sorcerer on Earth.\",\"category\":[\"Action\"]},{\"id\":\"Planet Earth\",\"description\":\"This landmark series transports nature lovers all over the earth.\",\"category\":[\"Documentary\"]}],\"tv\":[{\"id\":\"Flash\",\"description\":\"Zoom challenges Barry to a race, but the team suspects a trap and tries to talk Barry out of it.\",\"category\":[\"Action\"]},{\"id\":\"Friends\",\"description\":\"This his sitcom follows the merry misadventures of six 20-somethings pals as they naviagte the pitfalls of work, life and love.\",\"category\":[\"Comedy\"]},{\"id\":\"Blue Bloods\",\"description\":\"New evidence surfaces against Danny in his shooting of a serial killer.\",\"category\":[\"Drama\"]}]}";

	@Test
	public void TestDoLoadAction() throws Exception {
		CraftDemoServlet servlet = new CraftDemoServlet();
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpServletResponse mockResponse = mock(HttpServletResponse.class);
		ServletOutputStream mockOutputStream = mock(ServletOutputStream.class);

		when(mockRequest.getParameter("ACTION_NAME")).thenReturn("LOAD_MEDIA_JSON");
		when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);

		servlet.doPost(mockRequest, mockResponse);

		verify(mockRequest, atLeast(1)).getParameter("ACTION_NAME");
		verify(mockResponse.getOutputStream()).print(eq(expectedJsonResponse));
	}
}
