package web.servlets;

import java.io.IOException;
import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import web.database.dataAccessObject.PuntuacionesDAO;
import web.database.dataAccessObject.ComentariosDAO;
import web.database.dataAccessObject.VJuegosDAO;
import web.database.valueObject.VJuegoVO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;

public class Videojuego extends AbstractServletCYV {
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Videojuego() {
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
		int idJuego;
		
		JSONObject json = null;
		try{
			json = readJSON(request.getReader());
		}
		catch (Exception e){
			System.out.printf("Error al leer el JSON");
		}
		idJuego = json.getInt("idVideojuego");
		
		try{
			
			Iterator<ComentarioVO> comentsIter = (ComentariosDAO.listComentario(idJuego)).iterator();
			JSONArray jaComentarios = getComentarios(comentsIter);
			
			Iterator<PuntuacionVO> puntuacionesIter = (PuntuacionesDAO.listPuntuaciones(idJuego)).iterator();
			JSONArray jaPuntuaciones = getValoraciones(puntuacionesIter);
			
			VJuegoVO jVo = VJuegosDAO.findVJuego(idJuego);
			if(jVo!=null){
				JSONObject juego = JSONObject.fromObject(jVo.serialize());
				juego.element("valoracion", calcularPuntuacion(PuntuacionesDAO.listPuntuaciones(jVo.get_id())));
				
				JSONObject mainJson = new JSONObject();
				mainJson.element("videojuego", juego);
				mainJson.element("comentarios", jaComentarios);
				mainJson.element("valoraciones", jaPuntuaciones);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(mainJson.toString());
			}
			else{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		catch (Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
		}
	}
}
