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
import web.database.dataAccessObject.UsuariosDAO;
import web.database.valueObject.VJuegoVO;
import web.database.valueObject.UsuarioVO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;
import web.utils.PuntuacionesUtils;

public class Videojuego extends HttpServlet {
	
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
		idJuego = json.getInt("idVideojuego");
		
		VJuegoVO jVo = VJuegosDAO.findVJuego(idJuego);
		JSONObject juego = JSONObject.fromObject(jVo.serialize());
		juego.element("valoracion", PuntuacionesUtils.calcularPuntuacion(PuntuacionesDAO.listPuntuaciones(jVo.get_id())));
		
		JSONArray jaComentarios = new JSONArray();
		Iterator<ComentarioVO> comentsIter = (ComentariosDAO.listComentario(idJuego)).iterator();
		while(comentsIter.hasNext()){
			ComentarioVO vo = comentsIter.next();
			UsuarioVO uVo = UsuariosDAO.findUser(vo.getUsuarioID());
			JSONObject comentario = new JSONObject();
			comentario.element("idUsuario", uVo.get_id());
			comentario.element("nombreUsuario", uVo.getNickname());
			comentario.element("nombreApellidos", uVo.getNombre());
			comentario.element("fecha", (vo.getFecha()).substring(0,10));
			comentario.element("contenido", vo.getComentario());
			jaComentarios.add(comentario);
		}
		
		JSONArray jaPuntuaciones = new JSONArray();
		Iterator<PuntuacionVO> puntuacionesIter = (PuntuacionesDAO.listPuntuaciones(idJuego)).iterator();
		while(puntuacionesIter.hasNext()){
			PuntuacionVO vo = puntuacionesIter.next();
			UsuarioVO uVo = UsuariosDAO.findUser(vo.getUsuarioID());
			JSONObject puntuacion = new JSONObject();
			puntuacion.element("idUsuario", uVo.get_id());
			puntuacion.element("nombreUsuario", uVo.getNickname());
			puntuacion.element("nombreApellidos", uVo.getNombre());
			puntuacion.element("fecha", (vo.getFecha()).substring(0,10));
			puntuacion.element("valoracion", PuntuacionesUtils.puntuacionToString(vo.getPuntuacion())+"Estrella");
			jaPuntuaciones.add(puntuacion);
		}
		
		JSONObject mainJson = new JSONObject();
		mainJson.element("videojuego", juego);
		mainJson.element("comentarios", jaComentarios);
		mainJson.element("valoraciones", jaPuntuaciones);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(mainJson.toString());
	}
}
