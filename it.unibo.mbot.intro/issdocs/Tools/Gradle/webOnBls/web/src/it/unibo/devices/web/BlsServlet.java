package it.unibo.devices.web;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import it.unibo.devices.ILed;
import it.unibo.devices.impl.LedMock;
import it.unibo.devices.BLSSysKb.LedColor;

public class BlsServlet extends HttpServlet{
/*
 * Define the application object 	
 */
	private static int requestCounter = 0;
	private ILed led ;
	
	public BlsServlet(){
		try {
			led = new LedMock("led1",LedColor.GREEN );
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	@Override
	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		String view = processRequest(servletPath, request);
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	private String processRequest(String servletPath, HttpServletRequest request) {
		if(servletPath.equals("/on")) {
			request.setAttribute("cmd", "ON");
			led.turnOn();
 		}
		else if(servletPath.equals("/off")) {
			request.setAttribute("cmd", "OFF");
			led.turnOff();
  		}
		else if(servletPath.equals("/switch")) {
			request.setAttribute("cmd", "SWITCH");
			led.doSwitch();
  		}
		else if(servletPath.equals("/show")) {
			request.setAttribute("cmd", "SHOW");
  		}
		else{
			request.setAttribute("cmd", "UNKNOWN");
		}
		requestCounter++;
		request.setAttribute("requestCounter", requestCounter);
		request.setAttribute("ledRep", led.getDefaultRep());
		return "/jsp/led.jsp";

	}	
}