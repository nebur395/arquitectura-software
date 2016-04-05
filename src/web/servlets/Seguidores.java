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

import web.database.dataAccessObject.FollowsDAO;
import web.database.valueObject.UsuarioVO;

/**
 * Servlet implementation class Seguidores
 */ 
public class Seguidores extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Seguidores() {
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
		
		JSONArray ja = new JSONArray();
		Iterator<UsuarioVO> iterador = (FollowsDAO.getFollows(id)).iterator();
		while(iterador.hasNext()){
			UsuarioVO vo = iterador.next();
			JSONObject user = new JSONObject();
			user.element("nombreUsuario", vo.getNickname());
			user.element("id", vo.get_id());
			user.element("nombreApellidos", vo.getNombre());
			ja.add(user);
		}
		JSONObject mainJson = new JSONObject();
		mainJson.element("seguidores", ja);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(mainJson.toString());
	}   
}
