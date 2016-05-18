package web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.util.Iterator;
import java.sql.SQLException;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

public abstract class AbstractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AbstractServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
        /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	protected JSONObject readJSON (BufferedReader bf) throws Exception{
		StringBuffer jb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null){
			jb.append(line);
		}
		return JSONObject.fromObject(jb.toString());
	}
}	
