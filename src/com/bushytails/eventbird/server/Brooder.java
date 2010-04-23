package com.bushytails.eventbird.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bushytails.eventbird.util.Interpreter;
import com.bushytails.eventbird.util.Keeper;


public class Brooder extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String q = req.getQueryString();
		if(q == null){
			log.log(Level.WARNING, "anonymouse access");
			return;
		}
		Map<String, String> map = makeQueryMap(q);
		String code = map.get("code");
		if(code == null || ! code.equals(getInitParameter("brooder.code.initialize"))){
			log.log(Level.WARNING, "access denied");
			return;
		}

		Keeper keeper = new Keeper();
		keeper.buildRoost();
		log.log(Level.INFO, "build roost");
		
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;
		try {
			StringBuffer html = new StringBuffer();
			html.append("<html><head><title>");
			html.append(Interpreter.interpret("brooder.title", req.getLocale()));
			html.append("</title></head><body>");
			html.append(Interpreter.interpret("brooder.info", req.getLocale()));
			html.append("<hr/>humming by EventBird</body></html>");
			writer = resp.getWriter();
			writer.println(html.toString());
		} catch(IOException e) {
			if(writer != null){
				writer.close();
			}
			log.log(Level.SEVERE, "can not response", e);
		}
	}
	
	private Map<String, String> makeQueryMap(String query){
		Map<String, String> map = new HashMap<String, String>();
		String[] codes = query.split("&");
		for(String pair : codes){
			String[] temp = pair.split("=");
			map.put(temp[0].trim(), temp[1].trim());
		}
		return map;
	}
	
	private static final Logger log = Logger.getLogger(Brooder.class.getName());
}
