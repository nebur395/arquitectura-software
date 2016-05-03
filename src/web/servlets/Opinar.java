package web.servlets;

import java.io.IOException;
import java.util.Base64;
import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import web.database.dataAccessObject.PuntuacionesDAO;
import web.database.dataAccessObject.ComentariosDAO;
import web.utils.PuntuacionesUtils;

public class Opinar extends HttpServlet{
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Opinar() {
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
		int idUser;
		int idJuego;
		
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
		idUser = json.getInt("idUser");
		idJuego = json.getInt("idVideojuego");
		DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
			try{
			//Si es un comentario
			if(json.getBoolean("tipo")){
				String opinion = json.getString("opinion");
				ComentariosDAO.addComentario(idUser, idJuego, opinion);
				JSONObject respuesta = new JSONObject();
				respuesta.element("fecha", dateF.format(date));
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(respuesta.toString());
			}
			//Si es una valoración
			else{
				int opinion = json.getInt("opinion");
				PuntuacionesDAO.addPuntuacion(idUser, idJuego, opinion);
				//Extraigo la nueva puntuación total del juego
				String valoracion = PuntuacionesUtils.calcularPuntuacion(PuntuacionesDAO.listPuntuaciones(idJuego));
				JSONObject respuesta = new JSONObject();
				respuesta.element("fecha", dateF.format(date));
				respuesta.element("valoracion", valoracion);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(respuesta.toString());
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
		}
	}
}
