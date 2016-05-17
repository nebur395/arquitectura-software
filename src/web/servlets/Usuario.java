package web.servlets;

import java.io.IOException;
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
import web.database.dataAccessObject.UsuariosDAO;
import web.database.dataAccessObject.FollowsDAO;
import web.database.valueObject.UsuarioVO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;

public class Usuario extends AbstractServletCYV {
	
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
		
		JSONObject json = null;
		try{
			json = readJSON(request.getReader());
		}
		catch (Exception e){
			System.out.printf("Error al leer el JSON");
		}
		idUser = json.getInt("idUser");
		idProfile = json.getInt("idUsuario");
		
		try{
			ArrayList<ComentarioVO> list = ComentariosDAO.listComentarioUser(idProfile);
			Iterator<ComentarioVO> comentsIter = list.iterator();
			JSONArray jaComentarios = getComentarios(comentsIter);
			
			Iterator<PuntuacionVO> puntuacionesIter = (PuntuacionesDAO.listPuntuacionesUser(idProfile)).iterator();
			JSONArray jaPuntuaciones = getValoraciones(puntuacionesIter);
			
			UsuarioVO vo = UsuariosDAO.findUser(idProfile);
			if(vo!=null){
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
