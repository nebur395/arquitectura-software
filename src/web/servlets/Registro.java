package web.servlets;

import java.io.IOException;
import java.util.Base64;
import java.lang.StringBuffer;
import java.io.BufferedReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import web.database.dataAccessObject.UsuariosDAO;
import web.database.valueObject.UsuarioVO;


/**
 * Servlet implementation class Registro
 */
//@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registro() {
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
		boolean error = false;
		String usuario = "";
		String pass = "";
		String repass = "";
		
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
		usuario = json.getString("name");
		pass = json.getString("pass1");
		repass = json.getString("pass2");
		response.setContentType("text/html; charset=UTF-8");
		
		
		if((usuario==null) || (usuario.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Usuario incorrecto");
			error = true;
		}
		if((pass==null) || (pass.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Contraseña no válida");
			error = true;
		}
		if((repass==null) || (repass.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Las contraseñas no coinciden");
			error = true;
		}
		if(!(repass.equals(pass))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Las contraseñas no coinciden");
			error = true;
		}
		
		if(!error){
			try{
				if(UsuariosDAO.addUser(usuario, "", pass)){
					response.setStatus(HttpServletResponse.SC_OK);
					UsuarioVO vo = UsuariosDAO.findUser(usuario);
					JSONObject user = new JSONObject();
					user.element("nombreUsuario", usuario);
					user.element("id", vo.get_id());
					user.element("nombreApellidos", vo.getNombre());
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().write(user.toString());
				}
				else{
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().println("El nombre de usuario ya está en uso");
				}
			}
			catch(Exception e){
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
			}
		}
	}
}
