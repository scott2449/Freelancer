package com.panic.servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class APIProxy
 */
public class APIProxy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public APIProxy() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String destURL;
		
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		Consumer panic = ctx.getBean("panic", Consumer.class);
		Provider freelancer = ctx.getBean("freelancer", Provider.class);

		TransactionManager tm = new TransactionManager(panic, freelancer);
	
		HttpSession user = request.getSession(true);
		OAuthToken oat = (OAuthToken) user.getAttribute("token");
		
		String qs = request.getQueryString()!=null?"?"+request.getQueryString():"";
		destURL = "http://api.sandbox.freelancer.com"+request.getPathInfo()+qs;
		user.setAttribute("url", destURL);
		
		if(oat == null){
			request.getRequestDispatcher("/servlet/OAuthRequestToken").forward(request, response);
			return;
		}
		
		destURL = (String) user.getAttribute("url");
		panic.setOAuthToken(oat);
		
		try {
			response.sendRedirect((tm.signURL(destURL)));
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
