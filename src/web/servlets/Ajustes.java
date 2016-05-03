package web.servlets;

import java.lang.StringBuffer;
import java.io.BufferedReader;

import java.io.IOException;
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
 * Servlet implementation class Ajustes
 */
 public class Ajustes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Ajustes() {
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
		int id;
		String usuario = "";
		String nombre = "";
		String pass = "";
		String newPass = "";
		String reNewPass = "";
		
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
		id = json.getInt("id");
		usuario = json.getString("nombreUsuario");
		nombre = json.getString("nombreApellidos");
		pass = json.getString("oldPass");
		newPass = json.getString("pass");
		reNewPass = json.getString("rePass");
		response.setContentType("text/html; charset=UTF-8");
		
		if((usuario==null) || (usuario.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Usuario incorrecto");
			error = true;
		}
		if((nombre==null)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Nombre incorrecto");
			error = true;
		}
		if((pass==null) || (pass.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Contraseña actual no válida");
			error = true;
		}
		if((newPass==null)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Contraseña nueva no válida");
			error = true;
		}
		if((reNewPass==null)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Las nuevas contraseñas no coinciden");
			error = true;
		}
		if(!(reNewPass.equals(newPass))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Las nuevas contraseñas no coinciden");
			error = true;
		}
		//Si no hay ningún error en los datos introducidos
		if(!error){
			try{
				UsuarioVO vo = UsuariosDAO.findUser(usuario);
				//Si no se quiere cambiar la contraseña, se deja la actual
				if(newPass.trim().equals("")){ newPass = pass; }
				//Si el usuario no existe
				if(vo==null){
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().println("El usuario no existe");
				}
				//Si la contraseña y el usuario no coinciden
				else if(!(vo.getPasswd()).equals(pass)){
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().println("El usuario y la contraseña no coinciden");
				}
				else{
					UsuariosDAO.updateUser(id, usuario, nombre, newPass);
					response.setStatus(HttpServletResponse.SC_OK);
					JSONObject user = new JSONObject();
					user.element("nombreUsuario", usuario);
					user.element("id", id);
					user.element("nombreApellidos", nombre);
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().write(user.toString());
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
