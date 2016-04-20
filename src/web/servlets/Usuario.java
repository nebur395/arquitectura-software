package web.servlets;

import java.io.IOException;
import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.ArrayList;

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
import web.database.dataAccessObject.FollowsDAO;
import web.database.valueObject.VJuegoVO;
import web.database.valueObject.UsuarioVO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;
import web.utils.PuntuacionesUtils;

public class Usuario extends HttpServlet {
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Usuario() {
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
		int idProfile;
		
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
		idProfile = json.getInt("idUsuario");
		
		JSONArray jaComentarios = new JSONArray();
		ArrayList<ComentarioVO> list = ComentariosDAO.listComentarioUser(idProfile);
		Iterator<ComentarioVO> comentsIter = list.iterator();
		while(comentsIter.hasNext()){
			ComentarioVO cVo = comentsIter.next();
			VJuegoVO jVo = VJuegosDAO.findVJuego(cVo.getvJuego());
			JSONObject comentario = new JSONObject();
			comentario.element("idJuego", jVo.get_id());
			comentario.element("nombre", jVo.getTitulo());
			comentario.element("fecha", (cVo.getFecha()).substring(0,10));
			comentario.element("contenido", cVo.getComentario());
			jaComentarios.add(comentario);
		}
		
		JSONArray jaPuntuaciones = new JSONArray();
		Iterator<PuntuacionVO> puntuacionesIter = (PuntuacionesDAO.listPuntuacionesUser(idProfile)).iterator();
		while(puntuacionesIter.hasNext()){
			PuntuacionVO pVo = puntuacionesIter.next();
			VJuegoVO jVo = VJuegosDAO.findVJuego(pVo.getvJuego());
			JSONObject puntuacion = new JSONObject();
			puntuacion.element("idJuego", jVo.get_id());
			puntuacion.element("nombre", jVo.getTitulo());
			puntuacion.element("fecha", (pVo.getFecha()).substring(0,10));
			puntuacion.element("valoracion", PuntuacionesUtils.puntuacionToString(pVo.getPuntuacion())+"Estrella");
			jaPuntuaciones.add(puntuacion);
		}
		
		UsuarioVO vo = UsuariosDAO.findUser(idProfile);
		JSONObject usuario = JSONObject.fromObject(vo.serialize());
		usuario.element("comentarios", list.size());
		int tipo;
		if(idUser == idProfile){ tipo=1; }
		else if(FollowsDAO.isFollower(idUser, idProfile)){ tipo=3; }
		else { tipo=2; }
		usuario.element("tipo", tipo);
		
		JSONObject mainJson = new JSONObject();
		mainJson.element("usuario", usuario);
		mainJson.element("comentarios", jaComentarios);
		mainJson.element("valoraciones", jaPuntuaciones);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(mainJson.toString());
	}
}
