package web.servlets;

import java.io.IOException;
import java.util.Base64;
import java.lang.StringBuffer;
import java.io.BufferedReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import web.database.dataAccessObject.FollowsDAO;

public class Seguir extends HttpServlet {
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Seguir() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		int idFollow;
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try{
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
			  jb.append(line);
			}
		}
		catch (Exception e){
			System.out.printf("Error al leer el JSON");
		}
		JSONObject json = JSONObject.fromObject(jb.toString());
		id = json.getInt("idUser");
		idFollow = json.getInt("idSeguidor");
		
		if(FollowsDAO.follow(id, idFollow)){
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
