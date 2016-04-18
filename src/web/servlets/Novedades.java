package web.servlets;

import java.lang.StringBuffer;
import java.io.BufferedReader;
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

import web.database.dataAccessObject.UsuariosDAO;
import web.database.valueObject.UsuarioVO;
import web.database.dataAccessObject.VJuegosDAO;
import web.database.valueObject.VJuegoVO;
import web.database.dataAccessObject.FeedComentariosDAO;
import web.database.dataAccessObject.FeedPuntuacionesDAO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;
import web.utils.PuntuacionesUtils;

/**
 * Servlet implementation class DejarDeSeguir
 */ 
 public class Novedades extends HttpServlet {
	 
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
		
		JSONArray jaComentarios = new JSONArray();
		Iterator <ComentarioVO> commentsIter = (FeedComentariosDAO.findFeed(id)).iterator();
		while(commentsIter.hasNext()){
			ComentarioVO cVo = commentsIter.next();
			UsuarioVO uVo = UsuariosDAO.findUser(cVo.getUsuarioID());
			VJuegoVO jVo = VJuegosDAO.findVJuego(cVo.getvJuego());
			JSONObject comment = new JSONObject();
			comment.element("nombreUsuario", uVo.getNickname());
			comment.element("nombreApellidos", uVo.getNombre());
			comment.element("idUsuario", cVo.getUsuarioID());
			comment.element("juego", jVo.getTitulo());
			comment.element("idJuego", cVo.getvJuego());
			jaComentarios.add(comment);
		}
		
		JSONArray jaPuntuaciones = new JSONArray();
		Iterator <PuntuacionVO> puntuacionesIter = (FeedPuntuacionesDAO.findFeed(id)).iterator();
		while(puntuacionesIter.hasNext()){
			PuntuacionVO pVo = puntuacionesIter.next();
			UsuarioVO uVo = UsuariosDAO.findUser(pVo.getUsuarioID());
			VJuegoVO jVo = VJuegosDAO.findVJuego(pVo.getvJuego());
			JSONObject puntuacion = new JSONObject();
			puntuacion.element("nombreUsuario", uVo.getNickname());
			puntuacion.element("nombreApellidos", uVo.getNombre());
			puntuacion.element("idUsuario", pVo.getUsuarioID());
			puntuacion.element("juego", jVo.getTitulo());
			puntuacion.element("valoracion", PuntuacionesUtils.puntuacionToString(pVo.getPuntuacion())+"Estrella");
			puntuacion.element("idJuego", pVo.getvJuego());
			jaPuntuaciones.add(puntuacion);
		}
		
		JSONObject mainJson = new JSONObject();
		mainJson.element("comentarios", jaComentarios);
		mainJson.element("valoraciones", jaPuntuaciones);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(mainJson.toString());
		
	}
 }
