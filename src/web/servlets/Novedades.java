package web.servlets;

import java.util.Iterator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import web.database.dataAccessObject.FeedComentariosDAO;
import web.database.dataAccessObject.FeedPuntuacionesDAO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;

/**
 * Servlet implementation class DejarDeSeguir
 */ 
 public class Novedades extends AbstractServletCYV {
	 
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Novedades() {
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
		
		JSONObject json = null;
		try{
			json = readJSON(request.getReader());
		}
		catch (Exception e){
			System.out.printf("Error al leer el JSON");
		}
		id = json.getInt("idUser");
		
		try{
			Iterator <ComentarioVO> commentsIter = (FeedComentariosDAO.findFeed(id)).iterator();
			JSONArray jaComentarios = getComentarios(commentsIter);
			
			Iterator <PuntuacionVO> puntuacionesIter = (FeedPuntuacionesDAO.findFeed(id)).iterator();
			JSONArray jaPuntuaciones = getValoraciones(puntuacionesIter);
			
			JSONObject mainJson = new JSONObject();
			mainJson.element("comentarios", jaComentarios);
			mainJson.element("valoraciones", jaPuntuaciones);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(mainJson.toString());
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
		}
	}
 }
