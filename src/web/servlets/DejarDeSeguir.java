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

/**
 * Servlet implementation class DejarDeSeguir
 */ 
 public class DejarDeSeguir extends AbstractServlet {
	 
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DejarDeSeguir() {
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
		int idUnfollow;
		
		JSONObject json = null;
		try{
			json = readJSON(request.getReader());
		}
		catch (Exception e){
			System.out.printf("Error al leer el JSON");
		}
		id = json.getInt("idUser");
		idUnfollow = json.getInt("idSeguidor");
		try{
			FollowsDAO.unFollow(id, idUnfollow);
			response.setStatus(HttpServletResponse.SC_OK);
		}
		catch (Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
		}
	}
 }
