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

public class Usuario extends HttpServlet {
	
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
		int idUser;
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
		idUser = json.getInt("idUser");
		idFollow = json.getInt("idUsuario");
		
		UsuarioVO vo = UsuariosDAO.findUser(idUser);
		JSONObject usuario = JSONObject.fromOobject(vo.serialize());
		usuario.element();
		
		
	}
}
