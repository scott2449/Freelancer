package com.panic.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SignatureException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.panic.beans.Consumer;
import com.panic.beans.OAuthToken;
import com.panic.beans.Provider;
import com.panic.managers.TransactionManager;


public class OAuthRequestToken extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9113735109911836381L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {			
			
			HttpSession user = req.getSession(true);
			OAuthToken oat = (OAuthToken) user.getAttribute("token");
			if(oat != null){
				resp.sendRedirect("/Freelancer/servlet/OAuthCallback");
				return;
			}
			
			WebApplicationContext ctx = 
				WebApplicationContextUtils.getRequiredWebApplicationContext(
					this.getServletContext());
			Consumer panic = ctx.getBean("panic", Consumer.class);
			Provider freelancer = ctx.getBean("freelancer", Provider.class);

			TransactionManager tm = new TransactionManager(panic,freelancer);
						
			URLConnection url = (new URL(tm.getRequestURL())).openConnection();
			url.connect();
			InputStreamReader test = new InputStreamReader((InputStream) url.getContent());
			StringBuffer sb = new StringBuffer();
			while (test.ready()){
				sb.append(((char)test.read()));
			}
			
			panic.setOAuthToken(new OAuthToken(sb.toString()));
			
			resp.sendRedirect(tm.getAuthURL());
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
