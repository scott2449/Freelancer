package com.panic.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

/**
 * Servlet implementation class OAuthCallback
 */
public class OAuthCallback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		Consumer panic = ctx.getBean("panic", Consumer.class);
		Provider freelancer = ctx.getBean("freelancer", Provider.class);

		TransactionManager tm = new TransactionManager(panic, freelancer);
		
		HttpSession user = request.getSession(true);
		OAuthToken oat = (OAuthToken) user.getAttribute("token");
		
		if (oat == null) {
			URLConnection url = (new URL(tm.getAccessURL(request.getQueryString()))).openConnection();
			url.connect();
			InputStreamReader test = new InputStreamReader((InputStream) url.getContent());
			StringBuffer sb = new StringBuffer();
			while (test.ready()) {
				sb.append(((char) test.read()));
			}

			panic.setOAuthToken(new OAuthToken(sb.toString()));
			user.setAttribute("token", panic.getOAuthToken());
		}else{
			panic.setOAuthToken(oat);
		}
		
		String destURL = (String) user.getAttribute("url");
		try {
			response.sendRedirect((tm.signURL(destURL)));
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		
//		try {
//			TransformerFactory tf = TransformerFactory.newInstance();
//			Transformer trans = tf.newTransformer(new StreamSource(getServletContext().getResourceAsStream("/WEB-INF/xsl/AccountDetail.xsl")));
//			URLConnection url2 = (new URL(tm.signURL("http://api.sandbox.freelancer.com/Profile/getAccountDetails.xml"))).openConnection();
//			url2.connect();
//			trans.transform(new StreamSource(new InputStreamReader((InputStream) url2.getContent())), new StreamResult(response.getOutputStream()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
