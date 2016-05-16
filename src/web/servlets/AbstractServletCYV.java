package web.servlets;

import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.util.Iterator;
import java.sql.SQLException;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import web.database.dataAccessObject.UsuariosDAO;
import web.database.valueObject.UsuarioVO;
import web.database.dataAccessObject.VJuegosDAO;
import web.database.valueObject.VJuegoVO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;

public abstract class AbstractServletCYV extends AbstractServletP {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AbstractServletCYV() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected JSONArray getComentarios (Iterator<ComentarioVO> itr) throws ClassNotFoundException, SQLException{
		JSONArray ja = new JSONArray();
		
		while(itr.hasNext()){
			ComentarioVO cVo = itr.next();
			UsuarioVO uVo = UsuariosDAO.findUser(cVo.getUsuarioID());
			VJuegoVO jVo = VJuegosDAO.findVJuego(cVo.getvJuego());
			JSONObject comment = new JSONObject();
			comment.element("nombreUsuario", uVo.getNickname());
			comment.element("nombreReal", uVo.getNombre());
			comment.element("idUsuario", cVo.getUsuarioID());
			comment.element("juego", jVo.getTitulo());
			comment.element("idJuego", cVo.getvJuego());
			comment.element("fecha", (cVo.getFecha()).substring(0,10));
			comment.element("contenido", cVo.getComentario());
			ja.add(comment);
		}
		return ja;
	}
	
	protected JSONArray getValoraciones (Iterator<PuntuacionVO> itr) throws ClassNotFoundException, SQLException{
		JSONArray ja = new JSONArray();
		
		while(itr.hasNext()){
			PuntuacionVO pVo = itr.next();
			UsuarioVO uVo = UsuariosDAO.findUser(pVo.getUsuarioID());
			VJuegoVO jVo = VJuegosDAO.findVJuego(pVo.getvJuego());
			JSONObject puntuacion = new JSONObject();
			puntuacion.element("nombreUsuario", uVo.getNickname());
			puntuacion.element("nombreReal", uVo.getNombre());
			puntuacion.element("idUsuario", pVo.getUsuarioID());
			puntuacion.element("juego", jVo.getTitulo());
			puntuacion.element("valoracion", puntuacionToString(pVo.getPuntuacion())+"Estrella");
			puntuacion.element("idJuego", pVo.getvJuego());
			puntuacion.element("fecha", (pVo.getFecha()).substring(0,10));
			ja.add(puntuacion);
		}
		return ja;
	}
	
}	
